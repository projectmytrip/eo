package com.eni.enhop.be.shifthandoverlogbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.eni.enhop.be.shifthandoverlogbook.auditing.AuditorAwareImpl;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistenceConfig {

	@Bean
	public AuditorAware<String> auditorProvider() {
		return new AuditorAwareImpl();
	}
}
