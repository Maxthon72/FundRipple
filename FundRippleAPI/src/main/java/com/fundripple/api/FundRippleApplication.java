package com.fundripple.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FundRippleApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundRippleApplication.class, args);
	}

}
