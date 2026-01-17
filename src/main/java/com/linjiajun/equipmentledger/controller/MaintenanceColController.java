package com.linjiajun.equipmentledger.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.linjiajun.equipmentledger.entity.MaintenanceCol;
import com.linjiajun.equipmentledger.service.MaintenanceColService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/maintenance-col")
public class MaintenanceColController {

    private final MaintenanceColService maintenanceColService;

    public MaintenanceColController(MaintenanceColService maintenanceColService) {
        this.maintenanceColService = maintenanceColService;
    }


    @PostMapping("/create")
    public ResponseEntity<Long> create(@RequestBody MaintenanceCol dto) {
        Long id = maintenanceColService.create(dto);
        return ResponseEntity.ok(id);
    }


    @PostMapping("/update")
    public ResponseEntity<Void> update(@RequestBody MaintenanceCol dto) {
        maintenanceColService.update(dto);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody MaintenanceCol dto) {
        maintenanceColService.delete(dto.getMaintenanceId());
        return ResponseEntity.ok().build();
    }


    @GetMapping("/getById")
    public ResponseEntity<MaintenanceCol> getById(@RequestParam("id") Long maintenanceId) {
        MaintenanceCol m = maintenanceColService.getById(maintenanceId);
        return m == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(m);
    }


    @GetMapping("/page")
    public ResponseEntity<IPage<MaintenanceCol>> page(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String deviceNo,
            @RequestParam(required = false) String faultType
    ) {
        IPage<MaintenanceCol> res = maintenanceColService.page(page, size, deviceNo, faultType);
        return ResponseEntity.ok(res);
    }
}