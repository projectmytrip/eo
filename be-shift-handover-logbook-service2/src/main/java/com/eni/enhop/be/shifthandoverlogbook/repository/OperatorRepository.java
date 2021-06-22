package com.eni.enhop.be.shifthandoverlogbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.eni.enhop.be.shifthandoverlogbook.model.Operator;

public interface OperatorRepository
		extends PagingAndSortingRepository<Operator, Long>, QuerydslPredicateExecutor<Operator> {

	@Query("select distinct o from Operator o join o.roles r where o.isActive = true and r.isActive = true and r.locationId = :locationId")
	List<Operator> findByLocationId(@Param("locationId") Long locationId);
}
