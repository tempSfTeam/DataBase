package com.linjiajun.equipmentledger.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.linjiajun.equipmentledger.dto.EquipmentCreateDTO;
import com.linjiajun.equipmentledger.dto.EquipmentUpdateDTO;
import com.linjiajun.equipmentledger.entity.Equipment;
import com.linjiajun.equipmentledger.service.EquipmentService;
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

    @GetMapping("/search")
    public ResponseEntity<IPage<Equipment>> search(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword
    ) {
        IPage<Equipment> res = equipmentService.search(page, size, keyword);
        return ResponseEntity.ok(res);
    }
}