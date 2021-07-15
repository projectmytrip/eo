package com.eni.ioc.dailyworkplan.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.eni.ioc.dailyworkplan.entities.Activity;

public interface ActivityRepository extends CrudRepository<Activity, Long> {

	List<Activity> findAll();

	List<Activity> findActivityByPlanningIdAndAsset(Long id, String asset);
	
	Activity findActivityByIdAndAsset(Long id, String asset);

}
