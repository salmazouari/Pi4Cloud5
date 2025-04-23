package com.example.skillbridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SkillbridgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillbridgeApplication.class, args);
	}

}
