package com.team_quddy.quddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class QuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuddyApplication.class, args);
	}

}
