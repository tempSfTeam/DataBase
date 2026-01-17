package com.linjiajun.equipmentledger.constant;

public final class EquipmentStatusConstants {
    private EquipmentStatusConstants() {}

    // 在用
    public static final String IN_SERVICE = "IN_SERVICE";
    // 维修中
    public static final String UNDER_MAINT = "UNDER_MAINT";
    // 报废 / 未使用（你指定的“未使用”语义）
    public static final String SCRAPPED = "SCRAPPED";
}