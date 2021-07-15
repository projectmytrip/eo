package com.eni.ioc;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.eni.ioc.controller.WebsocketThread;

@SpringBootApplication
public class ApplicationMain {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationMain.class);

	public static void main(String[] args) {
		SpringApplication.run(ApplicationMain.class, args);

		logger.debug("-- Application Started --");
		
		logger.debug("Starting connection");
		WebsocketThread websocketThread = new WebsocketThread(0);
		websocketThread.start();
		logger.debug("Starting connection Process 1");
		WebsocketThread websocketThread1 = new WebsocketThread(1);
		websocketThread1.start();
		logger.debug("Starting connection Process 2");
		WebsocketThread websocketThread2 = new WebsocketThread(2);
		websocketThread2.start();
		logger.debug("Starting connection Process 3");
		WebsocketThread websocketThread3 = new WebsocketThread(3);
		websocketThread3.start();
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			logger.info("Let inspect the beans provided by Spring Boot:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				logger.info(beanName);
			}
		};
	}
}
