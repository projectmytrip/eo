package com.eni.enhop.be.shifthandoverlogbook.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eni.enhop.be.shifthandoverlogbook.model.LogbookWhitelist;
import com.eni.enhop.be.shifthandoverlogbook.repository.LogbookWhitelistRepository;

@Service
public class LogbookWhitelistServiceImpl implements LogbookWhitelistService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogbookWhitelistService.class);

	@Autowired
	private LogbookWhitelistRepository logbookWhitelistRepository;

	@Override
	public List<LogbookWhitelist> getWhitelists(){
		return logbookWhitelistRepository.findAll();
	}

	public List<LogbookWhitelist> getWhitelistByName(String name){
		return logbookWhitelistRepository.findByName(name);
	}

}
