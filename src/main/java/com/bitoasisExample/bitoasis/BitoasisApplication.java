package com.bitoasisExample.bitoasis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableSwagger2
public class BitoasisApplication {

	private static final Logger logger = LoggerFactory.getLogger(BitoasisApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(BitoasisApplication.class, args);
		logger.info("Spring boot application started");
	}


}
