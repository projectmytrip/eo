package com.eni.enhop.be.shifthandoverlogbook.service;

import java.util.List;

import com.eni.enhop.be.shifthandoverlogbook.model.LogbookWhitelist;

public interface LogbookWhitelistService {
	List<LogbookWhitelist> getWhitelists();

	List<LogbookWhitelist> getWhitelistByName(String name);
}
