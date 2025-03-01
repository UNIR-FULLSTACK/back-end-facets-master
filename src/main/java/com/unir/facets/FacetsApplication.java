package com.unir.facets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FacetsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacetsApplication.class, args);
	}

}
