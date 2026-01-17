package com.linjiajun.equipmentledger.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName maintenance_col
 */
@TableName(value ="maintenance_col")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceCol implements Serializable {
    /**
     * 
     */
    @TableId
    private Long maintenanceId;

    /**
     * 
     */
    private String maintenanceNo;

    /**
     * 
     */
    private String deviceNo;

    /**
     * 
     */
    private LocalDateTime maintenanceTime;

    /**
     * 
     */
    private String faultType;

    /**
     * 
     */
    private String faultDesc;

    /**
     * 
     */
    private String repairAction;

    /**
     * 
     */
    private String repairPerson;

    /**
     * 
     */
    private BigDecimal cost;

    /**
     * 
     */
    private Integer durationMinutes;

    /**
     * 
     */
    private LocalDateTime createdAt;

    /**
     * 
     */
    private LocalDateTime updatedAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MaintenanceCol other = (MaintenanceCol) that;
        return (this.getMaintenanceId() == null ? other.getMaintenanceId() == null : this.getMaintenanceId().equals(other.getMaintenanceId()))
            && (this.getMaintenanceNo() == null ? other.getMaintenanceNo() == null : this.getMaintenanceNo().equals(other.getMaintenanceNo()))
            && (this.getDeviceNo() == null ? other.getDeviceNo() == null : this.getDeviceNo().equals(other.getDeviceNo()))
            && (this.getMaintenanceTime() == null ? other.getMaintenanceTime() == null : this.getMaintenanceTime().equals(other.getMaintenanceTime()))
            && (this.getFaultType() == null ? other.getFaultType() == null : this.getFaultType().equals(other.getFaultType()))
            && (this.getFaultDesc() == null ? other.getFaultDesc() == null : this.getFaultDesc().equals(other.getFaultDesc()))
            && (this.getRepairAction() == null ? other.getRepairAction() == null : this.getRepairAction().equals(other.getRepairAction()))
            && (this.getRepairPerson() == null ? other.getRepairPerson() == null : this.getRepairPerson().equals(other.getRepairPerson()))
            && (this.getCost() == null ? other.getCost() == null : this.getCost().equals(other.getCost()))
            && (this.getDurationMinutes() == null ? other.getDurationMinutes() == null : this.getDurationMinutes().equals(other.getDurationMinutes()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMaintenanceId() == null) ? 0 : getMaintenanceId().hashCode());
        result = prime * result + ((getMaintenanceNo() == null) ? 0 : getMaintenanceNo().hashCode());
        result = prime * result + ((getDeviceNo() == null) ? 0 : getDeviceNo().hashCode());
        result = prime * result + ((getMaintenanceTime() == null) ? 0 : getMaintenanceTime().hashCode());
        result = prime * result + ((getFaultType() == null) ? 0 : getFaultType().hashCode());
        result = prime * result + ((getFaultDesc() == null) ? 0 : getFaultDesc().hashCode());
        result = prime * result + ((getRepairAction() == null) ? 0 : getRepairAction().hashCode());
        result = prime * result + ((getRepairPerson() == null) ? 0 : getRepairPerson().hashCode());
        result = prime * result + ((getCost() == null) ? 0 : getCost().hashCode());
        result = prime * result + ((getDurationMinutes() == null) ? 0 : getDurationMinutes().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", maintenanceId=").append(maintenanceId);
        sb.append(", maintenanceNo=").append(maintenanceNo);
        sb.append(", deviceNo=").append(deviceNo);
        sb.append(", maintenanceTime=").append(maintenanceTime);
        sb.append(", faultType=").append(faultType);
        sb.append(", faultDesc=").append(faultDesc);
        sb.append(", repairAction=").append(repairAction);
        sb.append(", repairPerson=").append(repairPerson);
        sb.append(", cost=").append(cost);
        sb.append(", durationMinutes=").append(durationMinutes);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}