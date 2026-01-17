package com.linjiajun.equipmentledger.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * VO: 设备信息 + 维修次数（用于前端展示）
 */
@Data
public class EquipmentWithCountVO {
    private String deviceNo;
    private String model;
    private String manufacturer;
    private LocalDate manufactureDate;
    private LocalDate purchaseDate;
    private BigDecimal purchasePrice;
    private String workshopId;
    private String owner;
    private String status;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 派生字段：维修次数
    private Long maintenanceCount;
}