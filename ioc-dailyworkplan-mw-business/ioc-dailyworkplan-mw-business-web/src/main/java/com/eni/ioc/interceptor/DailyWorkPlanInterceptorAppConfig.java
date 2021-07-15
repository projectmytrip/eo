package com.eni.ioc.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DailyWorkPlanInterceptorAppConfig implements WebMvcConfigurer {
	@Autowired
	DailyWorkPlanServiceInterceptor serviceInterceptor;

	private static final Logger logger = LoggerFactory.getLogger(DailyWorkPlanInterceptorAppConfig.class);

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		logger.info("registering interceptor...");
		registry.addInterceptor(serviceInterceptor).excludePathPatterns("/liveness/**");
	}
}
