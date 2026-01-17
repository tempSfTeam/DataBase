package com.linjiajun.equipmentledger.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.linjiajun.equipmentledger.dto.EquipmentCreateDTO;
import com.linjiajun.equipmentledger.dto.EquipmentUpdateDTO;
import com.linjiajun.equipmentledger.entity.Equipment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linjiajun.equipmentledger.vo.EquipmentWithCountVO;

/**
* @author 21983
* @description 针对表【equipment】的数据库操作Service
* @createDate 2026-01-17 15:01:17
*/
public interface EquipmentService extends IService<Equipment> {
    Equipment create(EquipmentCreateDTO dto);

    Equipment update(EquipmentUpdateDTO dto);

    void delete(String deviceNo);

    Equipment getById(String deviceNo);

    /**
     * 分页 + 多条件模糊搜索（返回设备信息 + maintenanceCount）
     */
    IPage<EquipmentWithCountVO> searchWithMaintenanceCount(int page, int size,String deviceNo,
                                                           String model, String workshopId,
                                                           String owner, String status);
}
