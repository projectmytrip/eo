package com.eni.ioc.dailyworkplan.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.eni.ioc.dailyworkplan.entities.UserRelProfile;

public interface UserRelProfileRepository extends CrudRepository<UserRelProfile, Long> {

	List<UserRelProfile> findByUidAndAsset(String uid, String asset);

}
