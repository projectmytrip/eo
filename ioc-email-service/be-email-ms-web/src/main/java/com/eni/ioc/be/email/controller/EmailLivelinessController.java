package com.eni.ioc.be.email.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/liveness")
public class EmailLivelinessController {

	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public @ResponseBody String ping(HttpServletRequest request) {
		return "OK";
	}

}
