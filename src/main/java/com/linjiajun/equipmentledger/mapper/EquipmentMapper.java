package com.linjiajun.equipmentledger.mapper;

import com.linjiajun.equipmentledger.entity.Equipment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linjiajun.equipmentledger.vo.EquipmentWithCountVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 21983
* @description 针对表【equipment】的数据库操作Mapper
* @createDate 2026-01-17 15:01:17
* @Entity com.linjiajun.equipmentledger.entity.Equipment
*/
public interface EquipmentMapper extends BaseMapper<Equipment> {
    /**
     * 手动分页查询：返回带维修次数的设备列表（limit/offset 分页）
     *
     * @param deviceNo 模糊匹配 device_no（可空）
     * @param model    模糊匹配 model（可空）
     * @param workshopId 模糊匹配 workshop_id（可空）
     * @param owner    模糊匹配 owner（可空）
     * @param status   精确或模糊匹配 status（可空；此处按精确匹配示例，可根据需求改为模糊）
     * @param limit    页大小
     * @param offset   偏移量
     * @return 当前页记录列表
     */
    List<EquipmentWithCountVO> searchWithMaintenanceCount(
            @Param("deviceNo") String deviceNo,
            @Param("model") String model,
            @Param("workshopId") String workshopId,
            @Param("owner") String owner,
            @Param("status") String status,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    /**
     * 计数：返回满足条件的不同设备数量（用于分页 total）
     */
    long countByFields(
            @Param("deviceNo") String deviceNo,
            @Param("model") String model,
            @Param("workshopId") String workshopId,
            @Param("owner") String owner,
            @Param("status") String status
    );
}




