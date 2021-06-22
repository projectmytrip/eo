package com.eni.enhop.be.shifthandoverlogbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.eni.enhop.be.shifthandoverlogbook.model.LogbookWhitelist;

public interface LogbookWhitelistRepository extends PagingAndSortingRepository<LogbookWhitelist, Long>{
	List<LogbookWhitelist> findAll();
	
	@Query("select un from LogbookWhitelist un where un.name = :name and un.isActive = true")
	List<LogbookWhitelist> findByName(@Param("name") String name);
}
