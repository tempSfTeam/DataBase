package com.linjiajun.equipmentledger.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linjiajun.equipmentledger.entity.Workshop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 21983
* @description 针对表【workshop】的数据库操作Mapper
* @createDate 2026-01-17 15:01:17
* @Entity com.linjiajun.equipmentledger.entity.Workshop
*/
public interface WorkshopMapper extends BaseMapper<Workshop> {
    /**
     * 分页 + 四字段模糊搜索（workshop_id / name / manager / location）
     * - page: MyBatis-Plus Page 对象
     * - 参数可为空，表示不做该项筛选
     */
    IPage<Workshop> searchByFields(
            Page<Workshop> page,
            @Param("workshopId") String workshopId,
            @Param("name") String name,
            @Param("manager") String manager,
            @Param("location") String location
    );
}




