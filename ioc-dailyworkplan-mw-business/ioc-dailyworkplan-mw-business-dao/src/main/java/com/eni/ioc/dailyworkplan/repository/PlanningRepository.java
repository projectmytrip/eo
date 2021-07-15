package com.eni.ioc.dailyworkplan.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.eni.ioc.dailyworkplan.entities.Planning;

public interface PlanningRepository extends CrudRepository<Planning, Long> {

	List<Planning> findAll();
	
	Planning findByIdAndAsset(Long id, String asset);
	
	List<Planning> findByAsset(String asset);

}
