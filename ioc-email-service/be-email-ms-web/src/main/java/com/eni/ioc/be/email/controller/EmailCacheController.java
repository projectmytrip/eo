package com.eni.ioc.be.email.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class EmailCacheController {

	private static final Logger logger = LoggerFactory.getLogger(EmailCacheController.class);

	@CacheEvict(cacheNames="MailinglistEmail", allEntries=true)
	@GetMapping(value = "/evictCache/{asset}")
	public @ResponseBody String evictCache(HttpServletRequest request) {
		logger.info("evincting cache...");
		return "OK";
	}

}
