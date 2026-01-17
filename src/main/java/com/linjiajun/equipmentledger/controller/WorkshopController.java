package com.linjiajun.equipmentledger.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.linjiajun.equipmentledger.entity.Workshop;
import com.linjiajun.equipmentledger.service.WorkshopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workshops")
public class WorkshopController {

    private final WorkshopService service;

    public WorkshopController(WorkshopService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<Workshop> create(@RequestBody Workshop w) {
        return ResponseEntity.ok(service.create(w));
    }

    @PostMapping("/update")
    public ResponseEntity<Workshop> update(@RequestBody Workshop w) {
        return ResponseEntity.ok(service.update(w));
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody Workshop w) {
        service.delete(w.getWorkshopId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get")
    public ResponseEntity<Workshop> get(@RequestParam String workshopId) {
        Workshop w = service.getById(workshopId);
        return w == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(w);
    }

    @GetMapping("/page")
    public ResponseEntity<IPage<Workshop>> page(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(service.page(page, size, keyword));
    }
}