package com.eni.ioc.dailyworkplan.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.eni.ioc.dailyworkplan.entities.UserWhitelist;

import org.springframework.data.jpa.repository.Query;


public interface UserWhitelistRepository extends CrudRepository<UserWhitelist, Long> {

	@Query("select u from UserWhitelist u where u.to = 1 and u.cc = 0")
	List<UserWhitelist> findToUser();

	@Query("select u from UserWhitelist u where u.to = 0 and u.cc = 1")
	List<UserWhitelist> findCcUser();

}
