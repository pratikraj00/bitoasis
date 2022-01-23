package com.bitoasisExample.bitoasis;

import com.bitoasisExample.bitoasis.servicesImpl.AuditorAwareImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableSwagger2
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class BitoasisApplication {

	private static final Logger logger = LoggerFactory.getLogger(BitoasisApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(BitoasisApplication.class, args);
		logger.info("Spring boot application started");
	}

	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}


}
