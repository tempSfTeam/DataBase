package com.linjiajun.equipmentledger.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName maintenance_no_lock
 */
@TableName(value ="maintenance_no_lock")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceNoLock implements Serializable {
    /**
     * 
     */
    @TableId
    private String maintenanceNo;

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
        MaintenanceNoLock other = (MaintenanceNoLock) that;
        return (this.getMaintenanceNo() == null ? other.getMaintenanceNo() == null : this.getMaintenanceNo().equals(other.getMaintenanceNo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMaintenanceNo() == null) ? 0 : getMaintenanceNo().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", maintenanceNo=").append(maintenanceNo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}