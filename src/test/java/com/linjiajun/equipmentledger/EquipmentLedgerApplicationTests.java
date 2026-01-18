package com.linjiajun.equipmentledger;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EquipmentLedgerApplicationTests {

    @Test
    void contextLoads() {

            String[] devices = new String[50];
            String[] persons = {"张三", "李四", "王五", "赵六", "刘七"};

            // 先生成设备编号
            for (int i = 0; i < 50; i++) {
                devices[i] = String.format("E2025%03d", i+1);
            }

            System.out.println("INSERT INTO maintenance_col (maintenance_id, maintenance_no, device_no, maintenance_time, fault_type, fault_desc, repair_action, repair_person, cost, duration_minutes) VALUES");

            for (int i = 1; i <= 300; i++) {
                int deviceIdx = (i-1) % 50; // 每台设备6条
                String deviceNo = devices[deviceIdx];
                String maintenanceId = Integer.toString(i);
                String maintenanceNo = String.format("M2025%05d", i);
                String maintenanceTime = String.format("2025-%02d-%02d %02d:00:00",
                        ((i-1)/50)+1, ((i-1)%28)+1, 8+(i%8)); // 月份&日&小时自动生成
                String faultType = "故障类型" + ((i-1)%5+1);
                String faultDesc = "描述" + i;
                String repairAction = "处理" + ((i-1)%10+1);
                String repairPerson = persons[(i-1)%5];
                int cost = 200 + ((i*13)%900);
                int mins = 30 + ((i*7)%70);

                String sql = String.format("(%s, '%s', '%s', '%s', '%s', '%s', '%s', '%s', %.2f, %d)%s",
                        maintenanceId, maintenanceNo, deviceNo, maintenanceTime, faultType, faultDesc, repairAction, repairPerson, (float)cost, mins, (i==300)? ";":",");

                System.out.println(sql);
            }
    }

}
