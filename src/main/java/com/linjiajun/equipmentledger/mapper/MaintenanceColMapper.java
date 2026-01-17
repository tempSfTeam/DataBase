package com.linjiajun.equipmentledger.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    /**
     * 分页 + 多条件搜索：
     * - deviceNo (模糊)
     * - workshopId (模糊) -- 通过 join equipment e on m.device_no = e.device_no
     * - maintenanceDate & dateType: dateType='day' => compare DATE(m.maintenance_time) = maintenanceDate (YYYY-MM-DD)
     *                         dateType='month' => compare to_char(m.maintenance_time,'YYYY-MM') = maintenanceDate (YYYY-MM)
     * - faultType (模糊)
     *
     * 参数为空时忽略该条件。
     */
    IPage<MaintenanceCol> searchByFilters(
            Page<MaintenanceCol> page,
            @Param("deviceNo") String deviceNo,
            @Param("workshopId") String workshopId,
            @Param("maintenanceDate") String maintenanceDate,
            @Param("dateType") String dateType,
            @Param("faultType") String faultType
    );
}




