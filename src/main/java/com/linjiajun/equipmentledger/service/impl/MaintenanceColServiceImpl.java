package com.linjiajun.equipmentledger.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linjiajun.equipmentledger.entity.MaintenanceCol;
import com.linjiajun.equipmentledger.service.MaintenanceColService;
import com.linjiajun.equipmentledger.mapper.MaintenanceColMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
* @author 21983
* @description 针对表【maintenance_col】的数据库操作Service实现
* @createDate 2026-01-17 15:01:17
*/
@Service
public class MaintenanceColServiceImpl extends ServiceImpl<MaintenanceColMapper, MaintenanceCol>
    implements MaintenanceColService {
    private final MaintenanceColMapper maintenanceColMapper;

    public MaintenanceColServiceImpl(MaintenanceColMapper maintenanceColMapper) {
        this.maintenanceColMapper = maintenanceColMapper;
    }

    @Override
    @Transactional
    public Long create(MaintenanceCol m) {
        // 使用存储过程（支持传入 now() 等）
        return maintenanceColMapper.insertMaintenance(
                m.getMaintenanceNo(),
                m.getDeviceNo(),
                m.getMaintenanceTime() == null ? java.time.OffsetDateTime.now() : m.getMaintenanceTime().atOffset(java.time.ZoneOffset.UTC),
                m.getFaultType(),
                m.getFaultDesc(),
                m.getRepairAction(),
                m.getRepairPerson(),
                m.getCost(),
                m.getDurationMinutes()
        );
    }

    @Override
    @Transactional
    public void update(MaintenanceCol m) {
        maintenanceColMapper.updateMaintenance(
                m.getMaintenanceId(),
                m.getMaintenanceNo(),
                m.getDeviceNo(),
                m.getMaintenanceTime() == null ? java.time.OffsetDateTime.now() : m.getMaintenanceTime().atOffset(java.time.ZoneOffset.UTC),
                m.getFaultType(),
                m.getFaultDesc(),
                m.getRepairAction(),
                m.getRepairPerson(),
                m.getCost(),
                m.getDurationMinutes()
        );
    }

    @Override
    @Transactional
    public void delete(Long maintenanceId) {
        maintenanceColMapper.deleteMaintenance(maintenanceId);
    }

    @Override
    public MaintenanceCol getById(Long id) {
        return maintenanceColMapper.selectById(id);
    }

    @Override
    public IPage<MaintenanceCol> searchByFilters(int page, int size, String deviceNo, String workshopId, String maintenanceDate, String dateType, String faultType) {
        Page<MaintenanceCol> pg = new Page<>(page, size);

        // 参数传递给 mapper，mapper 的 <if> 会忽略空值
        return maintenanceColMapper.searchByFilters(pg, deviceNo, workshopId, maintenanceDate, dateType, faultType);
    }
}




