package com.linjiajun.equipmentledger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableTransactionManagement
public class EquipmentLedgerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EquipmentLedgerApplication.class, args);
    }

}
