package com.creativeoh.keyword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KeywordSubscriptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeywordSubscriptionApplication.class, args);
	}
}
