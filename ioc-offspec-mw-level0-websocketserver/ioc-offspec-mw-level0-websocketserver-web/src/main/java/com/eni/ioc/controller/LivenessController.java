package com.eni.ioc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
 * Do not delete this controller.
 * 
 * */
@RestController
@RequestMapping("/liveness")
public class LivenessController {
	
	private static final Logger logger = LoggerFactory.getLogger(LivenessController.class);
	
    @RequestMapping(value = "/ping",method = RequestMethod.GET)
	public String ping() {
		logger.debug(" -- Controller ping Called -- ");
		return "OK";
	}
	
}
