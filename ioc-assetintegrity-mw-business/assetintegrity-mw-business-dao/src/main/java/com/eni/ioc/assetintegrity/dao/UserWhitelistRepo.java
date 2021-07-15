package com.eni.ioc.assetintegrity.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import com.eni.ioc.assetintegrity.entities.UserWhitelist;

public interface UserWhitelistRepo extends CrudRepository<UserWhitelist, Long> {

	@Query("select u from UserWhitelist u where u.to = 1 and u.cc = 0")
	List<UserWhitelist> findToUser();

	@Query("select u from UserWhitelist u where u.to = 0 and u.cc = 1")
	List<UserWhitelist> findCcUser();

}
