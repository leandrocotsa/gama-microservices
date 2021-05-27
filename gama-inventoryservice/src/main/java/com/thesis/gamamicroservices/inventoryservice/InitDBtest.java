package com.thesis.gamamicroservices.inventoryservice;

import com.thesis.gamamicroservices.inventoryservice.model.Warehouse;
import com.thesis.gamamicroservices.inventoryservice.repository.WarehouseRepository;
import com.thesis.gamamicroservices.inventoryservice.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
@Configuration
public class InitDBtest {
    private static final Logger log = LoggerFactory.getLogger(InitDBtest.class);

    @Bean
    CommandLineRunner initDatabase(WarehouseRepository warehouseRepository, InventoryService inventoryService) {

        return args -> {
            log.info("Preloading ");

            Warehouse w1 = new Warehouse("W1", "One warehouse");
            warehouseRepository.save(w1);
            Warehouse w2 = new Warehouse("W2", "Two warehouse");
            warehouseRepository.save(w2);


            //inventoryService.addStock(1,1, 5);
            //inventoryService.addStock(1,2, 10);


        };
    }
}
**/