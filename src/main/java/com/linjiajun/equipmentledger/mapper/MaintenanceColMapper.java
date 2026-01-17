package com.linjiajun.equipmentledger.mapper;

import com.linjiajun.equipmentledger.entity.MaintenanceCol;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 21983
* @description 针对表【maintenance_col】的数据库操作Mapper
* @createDate 2026-01-17 15:01:17
* @Entity com.linjiajun.equipmentledger.entity.MaintenanceCol
*/
public interface MaintenanceColMapper extends BaseMapper<MaintenanceCol> {
    // 插入，返回生成的 maintenance_id
    Long insertMaintenance(
            @Param("maintenanceNo") String maintenanceNo,
            @Param("deviceNo") String deviceNo,
            @Param("maintenanceTime") java.time.OffsetDateTime maintenanceTime, // 或 java.sql.Timestamp
            @Param("faultType") String faultType,
            @Param("faultDesc") String faultDesc,
            @Param("repairAction") String repairAction,
            @Param("repairPerson") String repairPerson,
            @Param("cost") java.math.BigDecimal cost,
            @Param("durationMinutes") Integer durationMinutes
    );

    // 更新（返回受影响行数或 void）
    void updateMaintenance(
            @Param("maintenanceId") Long maintenanceId,
            @Param("maintenanceNo") String maintenanceNo,
            @Param("deviceNo") String deviceNo,
            @Param("maintenanceTime") java.time.OffsetDateTime maintenanceTime,
            @Param("faultType") String faultType,
            @Param("faultDesc") String faultDesc,
            @Param("repairAction") String repairAction,
            @Param("repairPerson") String repairPerson,
            @Param("cost") java.math.BigDecimal cost,
            @Param("durationMinutes") Integer durationMinutes
    );

    // 删除
    void deleteMaintenance(@Param("maintenanceId") Long maintenanceId);
}




