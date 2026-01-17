package com.linjiajun.equipmentledger.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.linjiajun.equipmentledger.entity.Workshop;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 21983
* @description 针对表【workshop】的数据库操作Service
* @createDate 2026-01-17 15:01:17
*/
public interface WorkshopService extends IService<Workshop> {
    Workshop create(Workshop w);
    Workshop update(Workshop w);
    void delete(String workshopId);
    Workshop getById(String workshopId);

    /**
     * 分页 + 四字段模糊搜索：
     * - workshopId: 车间编号模糊匹配（可为空）
     * - name: 车间名模糊匹配（可为空）
     * - manager: 负责人模糊匹配（可为空）
     * - location: 地点模糊匹配（可为空）
     * 若某项为空则不作为筛选条件。
     */
    IPage<Workshop> searchByFields(int page, int size,
                                   String workshopId, String name,
                                   String manager, String location);
}
