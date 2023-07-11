package com.shop.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ShopServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopServiceApiApplication.class, args);
	}

}
