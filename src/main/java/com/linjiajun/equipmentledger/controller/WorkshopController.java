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

    /**
     * 分页 + 四字段模糊搜索：
     * 参数：
     *  - page, size
     *  - workshopId, name, manager, location （任意可选，空则不作为筛选）
     *
     * 示例：
     * GET /api/workshops/search?page=1&size=10&workshopId=W001&name=装配&manager=张&location=北区
     */
    @GetMapping("/search")
    public ResponseEntity<IPage<Workshop>> search(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String workshopId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String manager,
            @RequestParam(required = false) String location
    ) {
        IPage<Workshop> res = service.searchByFields(page, size, workshopId, name, manager, location);
        return ResponseEntity.ok(res);
    }
}