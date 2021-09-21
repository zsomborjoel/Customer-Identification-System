package com.company.customeridentificationsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.goodid.customeridentificationsystem.model.properties")
public class CustomerIdentificationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerIdentificationSystemApplication.class, args);
	}

}
