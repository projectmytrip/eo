package com.eni.enhop.be.shifthandoverlogbook.repository;

import java.util.Locale;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.eni.enhop.be.shifthandoverlogbook.model.EmailTemplate;

public interface EmailTemplateRepository  extends PagingAndSortingRepository<EmailTemplate, Long>{
	
	EmailTemplate findByNameAndLocale(String name, Locale locale);
	
	EmailTemplate findByNameAndIsDefault(String name, boolean isDefault);
}
