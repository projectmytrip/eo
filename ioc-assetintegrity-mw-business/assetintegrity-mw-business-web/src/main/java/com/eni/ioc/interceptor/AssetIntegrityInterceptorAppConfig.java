package com.eni.ioc.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AssetIntegrityInterceptorAppConfig implements WebMvcConfigurer {
	@Autowired
	AssetIntegrityServiceInterceptor offspecServiceInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(offspecServiceInterceptor).excludePathPatterns("/liveness/**");
	}
}
