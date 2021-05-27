package com.thesis.gamamicroservices.orderservice;

import com.thesis.gamamicroservices.orderservice.model.ShippingReferenceValue;
import com.thesis.gamamicroservices.orderservice.repository.ShippingRepository;
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
    CommandLineRunner initDatabase(ShippingRepository shippingRepository) {

        return args -> {
            log.info("Preloading ");



            ShippingReferenceValue s1 = new ShippingReferenceValue(5.0, "Portugal");
            shippingRepository.save(s1);



        };
    }
}

**/
