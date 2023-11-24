package com.lcwd.electronic.store.electronicStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.lcwd.electronic.store.electronicStore")
public class ElectronicStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
	}

}
