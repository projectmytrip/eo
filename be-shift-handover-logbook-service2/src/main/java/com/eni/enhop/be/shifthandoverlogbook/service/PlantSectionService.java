package com.eni.enhop.be.shifthandoverlogbook.service;

import java.util.List;

import com.eni.enhop.be.shifthandoverlogbook.model.PlantSection;

public interface PlantSectionService {
	List<PlantSection> getByLocationId(long locationId);

	List<PlantSection> getByLocationIdAndLogbookType(long locationId, String logbookType);
}
