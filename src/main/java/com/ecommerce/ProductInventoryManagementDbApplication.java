package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing//for Auditing created and updated data
public class ProductInventoryManagementDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductInventoryManagementDbApplication.class, args);
	}

}
