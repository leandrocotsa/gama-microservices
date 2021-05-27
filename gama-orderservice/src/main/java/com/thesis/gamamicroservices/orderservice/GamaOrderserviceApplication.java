package com.thesis.gamamicroservices.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableDiscoveryClient
public class GamaOrderserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamaOrderserviceApplication.class, args);
	}

}
