package com.eni.ioc.be.email;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan({ "com.eni.ioc" })
public class ApplicationMain {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationMain.class);

	public static void main(String[] args) {
		SpringApplication.run(ApplicationMain.class, args);
		logger.debug("-- Application Started --");
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("Let inspect the beans provided by Spring Boot:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
		};
	}
}
