package com.eni.ioc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eni.ioc.pi.client.rest.PiClientRest;

/*
 * Do not delete this controller.
 * 
 * */
@RestController
@RequestMapping("/senderjob")
public class CrontabSenderJobController {
	
	private static final Logger logger = LoggerFactory.getLogger(CrontabSenderJobController.class);
	
	@GetMapping(value = "/ping")
	public String ping() {
		logger.debug(" -- Controller ping Called-- ");
		return "OK";
	}
    
	@GetMapping(value = "/getSegnaliCritici")
	public @ResponseBody String getSegnaliCritici() {
		logger.debug(" ---Controller getSegnaliCritici Called-- ");
		PiClientRest.senderSegnaliProcesso();
		return "OK";
		
	}
}
