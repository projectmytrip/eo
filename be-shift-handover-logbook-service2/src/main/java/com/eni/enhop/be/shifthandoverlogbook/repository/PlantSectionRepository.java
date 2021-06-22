package com.eni.enhop.be.shifthandoverlogbook.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.eni.enhop.be.shifthandoverlogbook.model.PlantSection;

public interface PlantSectionRepository extends PagingAndSortingRepository<PlantSection, Long> {

	List<PlantSection> findByLocationId(Long locationId);

	List<PlantSection> findByLocationIdAndLogbookType(Long locationId, String logbookType);
}
