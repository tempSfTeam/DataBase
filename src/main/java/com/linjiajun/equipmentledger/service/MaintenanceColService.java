package com.linjiajun.equipmentledger.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.linjiajun.equipmentledger.entity.MaintenanceCol;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 21983
* @description 针对表【maintenance_col】的数据库操作Service
* @createDate 2026-01-17 15:01:17
*/
public interface MaintenanceColService extends IService<MaintenanceCol> {
    // 调用存储过程插入并返回 id
    Long create(MaintenanceCol m);
    // 调用存储过程更新
    void update(MaintenanceCol m);
    // 调用存储过程删除
    void delete(Long maintenanceId);

    MaintenanceCol getById(Long id);

    /**
     * 分页 + 多条件搜索（deviceNo / workshopId / maintenanceDate(dateType=day|month) / faultType）
     */
    IPage<MaintenanceCol> searchByFilters(int page, int size,
                                          String deviceNo, String workshopId,
                                          String maintenanceDate, String dateType,
                                          String faultType);
}
