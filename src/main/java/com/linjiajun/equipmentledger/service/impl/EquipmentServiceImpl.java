package com.linjiajun.equipmentledger.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linjiajun.equipmentledger.constant.EquipmentStatusConstants;
import com.linjiajun.equipmentledger.dto.EquipmentCreateDTO;
import com.linjiajun.equipmentledger.dto.EquipmentUpdateDTO;
import com.linjiajun.equipmentledger.entity.Equipment;
import com.linjiajun.equipmentledger.mapper.MaintenanceColMapper;
import com.linjiajun.equipmentledger.service.EquipmentService;
import com.linjiajun.equipmentledger.mapper.EquipmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
* @author 21983
* @description 针对表【equipment】的数据库操作Service实现
* @createDate 2026-01-17 15:01:17
*/
@Service
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper, Equipment>
    implements EquipmentService {

    private final EquipmentMapper equipmentMapper;
    private final MaintenanceColMapper maintenanceMapper;

    public EquipmentServiceImpl(EquipmentMapper equipmentMapper, MaintenanceColMapper maintenanceMapper) {
        this.equipmentMapper = equipmentMapper;
        this.maintenanceMapper = maintenanceMapper;
    }

    @Override
    @Transactional
    public Equipment create(EquipmentCreateDTO dto) {
        // 基本校验
        if (dto.getPurchasePrice() != null && dto.getPurchasePrice().doubleValue() < 0) {
            throw new IllegalArgumentException("purchasePrice must be >= 0");
        }
        Equipment e = Equipment.builder()
                .deviceNo(dto.getDeviceNo())
                .model(dto.getModel())
                .manufacturer(dto.getManufacturer())
                .manufactureDate(dto.getManufactureDate())
                .purchaseDate(dto.getPurchaseDate())
                .purchasePrice(dto.getPurchasePrice())
                .workshopId(dto.getWorkshopId())
                .owner(dto.getOwner())
                .status(dto.getStatus() == null ? EquipmentStatusConstants.IN_SERVICE : dto.getStatus())
                .remark(dto.getRemark())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        equipmentMapper.insert(e);
        return e;
    }

    @Override
    @Transactional
    public Equipment update(EquipmentUpdateDTO dto) {
        if (!StringUtils.hasText(dto.getDeviceNo())) {
            throw new IllegalArgumentException("deviceNo required");
        }
        Equipment existent = equipmentMapper.selectById(dto.getDeviceNo());
        if (existent == null) {
            throw new IllegalArgumentException("equipment not found: " + dto.getDeviceNo());
        }

        // 更新允许项（可以根据需求限制哪些字段能被更新）
        if (dto.getModel() != null) {
            existent.setModel(dto.getModel());
        }
        if (dto.getManufacturer() != null) {
            existent.setManufacturer(dto.getManufacturer());
        }
        if (dto.getManufactureDate() != null) {
            existent.setManufactureDate(dto.getManufactureDate());
        }
        if (dto.getPurchaseDate() != null) {
            existent.setPurchaseDate(dto.getPurchaseDate());
        }
        if (dto.getPurchasePrice() != null) {
            if (dto.getPurchasePrice().doubleValue() < 0) {
                throw new IllegalArgumentException("purchasePrice must be >=0");
            }
            existent.setPurchasePrice(dto.getPurchasePrice());
        }
        if (dto.getWorkshopId() != null) {
            existent.setWorkshopId(dto.getWorkshopId());
        }
        if (dto.getOwner() != null) {
            existent.setOwner(dto.getOwner());
        }
        if (dto.getStatus() != null) {
            existent.setStatus(dto.getStatus());
        }
        if (dto.getRemark() != null) {
            existent.setRemark(dto.getRemark());
        }
        existent.setUpdatedAt(LocalDateTime.now());

        equipmentMapper.updateById(existent);
        return existent;
    }

    @Override
    @Transactional
    public void delete(String deviceNo) {
        if (!StringUtils.hasText(deviceNo)) {
            throw new IllegalArgumentException("deviceNo required");
        }
        Equipment e = equipmentMapper.selectById(deviceNo);
        if (e == null) {
            throw new IllegalArgumentException("equipment not found: " + deviceNo);
        }
        // 只允许 SCRAPPED 删除（按你的要求）
        if (!EquipmentStatusConstants.SCRAPPED.equals(e.getStatus())) {
            throw new IllegalStateException("only SCRAPPED equipment can be deleted");
        }
        // 如果你还想检查是否有维修记录，可启用下面的检查（可选）
        // long cnt = maintenanceMapper.selectCount(new QueryWrapper<Maintenance>().eq("device_no", deviceNo));
        // if (cnt > 0) throw new IllegalStateException("cannot delete equipment that has maintenance records");

        equipmentMapper.deleteById(deviceNo);
    }

    @Override
    public Equipment getById(String deviceNo) {
        return equipmentMapper.selectById(deviceNo);
    }

    @Override
    public IPage<Equipment> search(int page, int size, String keyword) {
        Page<Equipment> pg = new Page<>(page, size);
        QueryWrapper<Equipment> qw = new QueryWrapper<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            String k = "%" + keyword.trim() + "%";
            qw.and(wrapper -> wrapper
                    .like("device_no", keyword)
                    .or()
                    .like("model", keyword)
                    .or()
                    .like("workshop_id", keyword)
            );
        }
        return equipmentMapper.selectPage(pg, qw);
    }
}




