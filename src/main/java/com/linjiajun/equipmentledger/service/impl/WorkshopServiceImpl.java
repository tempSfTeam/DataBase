package com.linjiajun.equipmentledger.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linjiajun.equipmentledger.entity.Workshop;
import com.linjiajun.equipmentledger.service.WorkshopService;
import com.linjiajun.equipmentledger.mapper.WorkshopMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
* @author 21983
* @description 针对表【workshop】的数据库操作Service实现
* @createDate 2026-01-17 15:01:17
*/
@Service
public class WorkshopServiceImpl extends ServiceImpl<WorkshopMapper, Workshop>
    implements WorkshopService {
    private final WorkshopMapper workshopMapper;

    public WorkshopServiceImpl(WorkshopMapper workshopMapper) {
        this.workshopMapper = workshopMapper;
    }

    @Override
    @Transactional
    public Workshop create(Workshop w) {
        w.setCreatedAt(LocalDateTime.now());
        w.setUpdatedAt(LocalDateTime.now());
        workshopMapper.insert(w);
        return w;
    }

    @Override
    @Transactional
    public Workshop update(Workshop w) {
        Workshop old = workshopMapper.selectById(w.getWorkshopId());
        if (old == null) {
            throw new IllegalArgumentException("workshop not found");
        }
        if (w.getName() != null) {
            old.setName(w.getName());
        }
        if (w.getManager() != null) {
            old.setManager(w.getManager());
        }
        if (w.getLocation() != null) {
            old.setLocation(w.getLocation());
        }
        if (w.getDescription() != null) {
            old.setDescription(w.getDescription());
        }
        old.setUpdatedAt(LocalDateTime.now());
        workshopMapper.updateById(old);
        return old;
    }

    @Override
    @Transactional
    public void delete(String workshopId) {
        // 若你需要检查是否有设备属于该车间，建议在此做检查
        workshopMapper.deleteById(workshopId);
    }

    @Override
    public Workshop getById(String workshopId) {
        return workshopMapper.selectById(workshopId);
    }

    @Override
    public IPage<Workshop> page(int page, int size, String keyword) {
        Page<Workshop> pg = new Page<>(page, size);
        QueryWrapper<Workshop> qw = new QueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            qw.and(w -> w.like("workshop_id", keyword).or().like("name", keyword));
        }
        return workshopMapper.selectPage(pg, qw);
    }
}




