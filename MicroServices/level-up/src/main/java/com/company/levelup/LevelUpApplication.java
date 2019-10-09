package com.company.levelup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LevelUpApplication {

	public static void main(String[] args) {
		SpringApplication.run(LevelUpApplication.class, args);
	}

}
