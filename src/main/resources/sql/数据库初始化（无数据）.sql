-- 车间信息表
CREATE TABLE workshop (
    workshop_id   VARCHAR(32)  PRIMARY KEY,         -- 车间编号，例如 W001
    name          VARCHAR(128) NOT NULL,            -- 车间名称
    manager       VARCHAR(64),                      -- 负责人
    location      VARCHAR(128),                     -- 位置/地址
    description   TEXT,                             -- 备注/描述
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- 建议索引（若按名称频繁查找）
CREATE INDEX idx_workshop_name ON workshop(name);

-- 设备信息表
CREATE TABLE equipment (
    device_no        VARCHAR(32)  PRIMARY KEY,         -- 设备编号，例如 E2025001
    model            VARCHAR(64) NOT NULL,             -- 型号
    manufacturer     VARCHAR(128),                     -- 出厂厂家
    manufacture_date DATE,                             -- 出厂日期
    purchase_date    DATE,                             -- 采购日期
    purchase_price   NUMERIC(14,2) DEFAULT 0.00 CHECK (purchase_price >= 0), -- 采购价格
    workshop_id      VARCHAR(32) NOT NULL,             -- 所属车间（外键）
    owner            VARCHAR(64),                      -- 负责人
    status           VARCHAR(16) NOT NULL DEFAULT 'IN_SERVICE', -- 设备状态
    remark           TEXT,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_equipment_workshop FOREIGN KEY (workshop_id)
        REFERENCES workshop (workshop_id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT chk_equipment_status CHECK (status IN ('IN_SERVICE','UNDER_MAINT','SCRAPPED'))
);

-- 常用索引
CREATE INDEX idx_equipment_model ON equipment(model);
CREATE INDEX idx_equipment_workshop ON equipment(workshop_id);
CREATE INDEX idx_equipment_owner ON equipment(owner);




---------------------------------------------------------------------------
-- 1) 创建序列（兼容没有 CREATE SEQUENCE IF NOT EXISTS 的版本）
---------------------------------------------------------------------------
DO
$$
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.sequences
    WHERE sequence_name = 'seq_maintenance_id'
      AND sequence_schema = current_schema()
  ) THEN
    EXECUTE 'CREATE SEQUENCE ' || quote_ident(current_schema()) || '.seq_maintenance_id
             START WITH 1
             INCREMENT BY 1
             NO MINVALUE NO MAXVALUE
             CACHE 1';
  END IF;
END;
$$ LANGUAGE plpgsql;


---------------------------------------------------------------------------
-- 2) 主表：纯列存维修记录表 maintenance_col（应用层/存储过程写入）
---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS maintenance_col (
    maintenance_id   BIGINT PRIMARY KEY,    -- 由存储过程 + 序列赋值
    maintenance_no   VARCHAR(64) UNIQUE NOT NULL,           -- 人类可读单号
    device_no        VARCHAR(32) NOT NULL,           -- 设备编号（存储过程验证存在性）
    maintenance_time TIMESTAMP NOT NULL,
    fault_type       VARCHAR(128) NOT NULL,
    fault_desc       TEXT,
    repair_action    TEXT,
    repair_person    VARCHAR(64),
    cost             NUMERIC(12,2),
    duration_minutes INT,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) WITH (orientation = 'column');  

-- 索引（如果列存不支持索引，请按你环境调整）
CREATE INDEX IF NOT EXISTS idx_mc_device ON maintenance_col (device_no);
CREATE INDEX IF NOT EXISTS idx_mc_time ON maintenance_col (maintenance_time);
CREATE INDEX IF NOT EXISTS idx_mc_device_time ON maintenance_col (device_no, maintenance_time );

---------------------------------------------------------------------------
-- 3) 存储过程实现
---------------------------------------------------------------------------
-- 插入过程：返回新生成的 maintenance_id
CREATE OR REPLACE FUNCTION sp_insert_maintenance(
    p_maintenance_no   VARCHAR,
    p_device_no        VARCHAR,
    p_maintenance_time TIMESTAMPTZ,
    p_fault_type       VARCHAR,
    p_fault_desc       TEXT,
    p_repair_action    VARCHAR,
    p_repair_person    VARCHAR,
    p_cost             NUMERIC,
    p_duration_minutes INT
) RETURNS BIGINT
LANGUAGE plpgsql
AS
$$
DECLARE
    v_id    BIGINT;
    v_cnt   INT;
    v_mtime TIMESTAMP;
BEGIN
    -- cost 校验
    IF p_cost IS NOT NULL AND p_cost < 0 THEN
        RAISE EXCEPTION 'cost 必须 >= 0: %', p_cost;
    END IF;

    -- device 存在性校验
    SELECT COUNT(*) INTO v_cnt FROM equipment WHERE device_no = p_device_no;
    IF v_cnt = 0 THEN
        RAISE EXCEPTION '引用设备不存在: %', p_device_no;
    END IF;

    -- 生成自增 id（注意：序列名使用当前 schema）
    v_id := nextval(quote_ident(current_schema()) || '.seq_maintenance_id');

    -- 处理时间：若传入 timestamptz，则转换为 timestamp
    IF p_maintenance_time IS NULL THEN
        v_mtime := CURRENT_TIMESTAMP;
    ELSE
        v_mtime := p_maintenance_time::timestamp;
    END IF;

    -- 尝试插入，捕获唯一约束冲突
    BEGIN
        INSERT INTO maintenance_col (
            maintenance_id, maintenance_no, device_no, maintenance_time,
            fault_type, fault_desc, repair_action, repair_person,
            cost, duration_minutes, created_at, updated_at
        ) VALUES (
            v_id, p_maintenance_no, p_device_no, v_mtime,
            p_fault_type, p_fault_desc, p_repair_action, p_repair_person,
            p_cost, p_duration_minutes, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
        );
    EXCEPTION WHEN unique_violation THEN
        -- maintenance_no 已存在（依赖数据库唯一约束）
        RAISE EXCEPTION 'maintenance_no 已存在: %', p_maintenance_no;
    END;

    RETURN v_id;
END;
$$
SECURITY DEFINER;

-- 更新过程：按 maintenance_id 更新记录；若修改 maintenance_no 则依赖唯一约束处理冲突
CREATE OR REPLACE FUNCTION sp_update_maintenance(
    p_maintenance_id   BIGINT,
    p_maintenance_no   VARCHAR,
    p_device_no        VARCHAR,
    p_maintenance_time TIMESTAMPTZ,
    p_fault_type       VARCHAR,
    p_fault_desc       TEXT,
    p_repair_action    VARCHAR,
    p_repair_person    VARCHAR,
    p_cost             NUMERIC,
    p_duration_minutes INT
) RETURNS VOID
LANGUAGE plpgsql
AS
$$
DECLARE
    v_old_no VARCHAR;
    v_cnt    INT;
    v_mtime  TIMESTAMP;
BEGIN
    -- 确认记录存在并取出旧 maintenance_no
    SELECT maintenance_no INTO v_old_no
    FROM maintenance_col
    WHERE maintenance_id = p_maintenance_id;
    IF NOT FOUND THEN
        RAISE EXCEPTION '维护记录 % 不存在', p_maintenance_id;
    END IF;

    -- cost 校验
    IF p_cost IS NOT NULL AND p_cost < 0 THEN
        RAISE EXCEPTION 'cost 必须 >= 0: %', p_cost;
    END IF;

    -- device 存在性校验
    SELECT COUNT(*) INTO v_cnt FROM equipment WHERE device_no = p_device_no;
    IF v_cnt = 0 THEN
        RAISE EXCEPTION '引用设备不存在: %', p_device_no;
    END IF;

    -- 处理时间：将 timestamptz 转为 timestamp（如果 p_maintenance_time 为 NULL 使用当前时间）
    IF p_maintenance_time IS NULL THEN
        v_mtime := CURRENT_TIMESTAMP;
    ELSE
        v_mtime := p_maintenance_time::timestamp;
    END IF;

    -- 执行更新并捕获唯一约束冲突（如 maintenance_no 与其它记录重复）
    BEGIN
        UPDATE maintenance_col
        SET
            maintenance_no   = p_maintenance_no,
            device_no        = p_device_no,
            maintenance_time = v_mtime,
            fault_type       = p_fault_type,
            fault_desc       = p_fault_desc,
            repair_action    = p_repair_action,
            repair_person    = p_repair_person,
            cost             = p_cost,
            duration_minutes = p_duration_minutes,
            updated_at       = CURRENT_TIMESTAMP
        WHERE maintenance_id = p_maintenance_id;
    EXCEPTION WHEN unique_violation THEN
        RAISE EXCEPTION 'maintenance_no 已存在: %', p_maintenance_no;
    END;

    RETURN;
END;
$$
SECURITY DEFINER;

-- 删除过程：按 maintenance_id 删除（不再清理 maintenance_no_lock）
CREATE OR REPLACE FUNCTION sp_delete_maintenance(p_maintenance_id BIGINT)
RETURNS VOID
LANGUAGE plpgsql
AS
$$
DECLARE
    v_old_no VARCHAR;
BEGIN
    -- 取出旧 maintenance_no（用于校验/日志等）
    SELECT maintenance_no INTO v_old_no FROM maintenance_col WHERE maintenance_id = p_maintenance_id;
    IF NOT FOUND THEN
        RAISE EXCEPTION '维护记录 % 不存在', p_maintenance_id;
    END IF;

    -- 删除记录
    DELETE FROM maintenance_col WHERE maintenance_id = p_maintenance_id;

    -- 不再维护 maintenance_no_lock 表（已移除该机制）
    RETURN;
END;
$$
SECURITY DEFINER;


---------------------------------------------------------------------------
-- 4) 外键模拟（equipment部分）
---------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION prevent_equipment_delete()
RETURNS trigger
LANGUAGE plpgsql
AS
$$
BEGIN
  IF EXISTS (SELECT 1 FROM maintenance_col WHERE device_no = OLD.device_no) THEN
    RAISE EXCEPTION '不能删除设备 %：存在维修记录，请先删除或归档相关维修记录', OLD.device_no;
  END IF;
  RETURN OLD;
END;
$$;

CREATE TRIGGER trg_prevent_equipment_delete
BEFORE DELETE ON equipment
FOR EACH ROW
EXECUTE PROCEDURE prevent_equipment_delete();
