package com.eni.ioc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/*
 * Do not delete this controller.
 * 
 * */
@RestController
@RequestMapping("/liveness")
public class LivenessController {
	
	private static final Logger logger = LoggerFactory.getLogger(LivenessController.class);
    private static final String UP = "UP";
    private static final String DOWN = "DOWN";

	
	
    @GetMapping(value = "/ping")
	public String ping() {
		logger.debug(" -- Controller ping Called-- ");
		return "OK";
	}

}
