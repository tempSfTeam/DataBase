package com.linjiajun.equipmentledger.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.linjiajun.equipmentledger.dto.EquipmentCreateDTO;
import com.linjiajun.equipmentledger.dto.EquipmentUpdateDTO;
import com.linjiajun.equipmentledger.entity.Equipment;
import com.linjiajun.equipmentledger.service.EquipmentService;
import com.linjiajun.equipmentledger.vo.EquipmentWithCountVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/equipments")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Equipment> create(@RequestBody EquipmentCreateDTO dto) {
        Equipment created = equipmentService.create(dto);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/update")
    public ResponseEntity<Equipment> update(@RequestBody EquipmentUpdateDTO dto) {
        Equipment updated = equipmentService.update(dto);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody Equipment equipment) {
        equipmentService.delete(equipment.getDeviceNo());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getById")
    public ResponseEntity<Equipment> getById(@RequestParam String deviceNo) {
        Equipment e = equipmentService.getById(deviceNo);
        return e == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(e);
    }

    /**
     * 分页 + 多条件搜索（返回设备信息 + 维修次数）
     */
    @GetMapping("/searchWithCount")
    public ResponseEntity<IPage<EquipmentWithCountVO>> searchWithCount(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String workshopId,
            @RequestParam(required = false) String owner,
            @RequestParam(required = false) String status
    ) {
        IPage<EquipmentWithCountVO> res = equipmentService.searchWithMaintenanceCount(page, size, model, workshopId, owner, status);
        return ResponseEntity.ok(res);
    }
}