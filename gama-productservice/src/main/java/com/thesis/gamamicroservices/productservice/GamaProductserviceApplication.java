package com.thesis.gamamicroservices.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//ctirei a dependcia eureka client

@SpringBootApplication
//@EnableDiscoveryClient
public class GamaProductserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamaProductserviceApplication.class, args);
	}

}
