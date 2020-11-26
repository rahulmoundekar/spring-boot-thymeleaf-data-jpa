package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySource({ "classpath:messages.properties" })
public class SpringBootThymeleafDataJpaCurdApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootThymeleafDataJpaCurdApplication.class, args);
	}

}
