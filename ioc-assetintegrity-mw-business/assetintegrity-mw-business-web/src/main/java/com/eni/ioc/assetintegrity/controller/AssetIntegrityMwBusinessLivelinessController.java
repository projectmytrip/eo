package com.eni.ioc.assetintegrity.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/liveness")
public class AssetIntegrityMwBusinessLivelinessController{

	@GetMapping(value = "/ping")
	public @ResponseBody String ping(HttpServletRequest request){
		return "OK";
	}

	
}
