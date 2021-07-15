package com.eni.ioc.dailyworkplan.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.eni.ioc.dailyworkplan.entities.PlanningState;

public interface PlanningStateRepository extends CrudRepository<PlanningState, Long> {

	List<PlanningState> findAll();

	PlanningState findByIdAndAsset(Long id, String asset);

	PlanningState findByStateAndAsset(String state, String asset);

	PlanningState findByCodeAndAsset(String code, String asset);

}
