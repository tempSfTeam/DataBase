package com.linjiajun.equipmentledger.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EquipmentUpdateDTO {
    // 前端传
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
}