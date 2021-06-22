package com.eni.enhop.be.shifthandoverlogbook.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eni.enhop.be.shifthandoverlogbook.model.PlantSection;
import com.eni.enhop.be.shifthandoverlogbook.repository.PlantSectionRepository;

@Service
public class PlantSectionServiceImpl implements PlantSectionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogbookPageService.class);

	@Autowired
	private PlantSectionRepository PlantSectionRepository;

	@Override
	public List<PlantSection> getByLocationId(long locationId) {
		LOGGER.info("Get the plant sections by locationId: {}", locationId);

		List<PlantSection> plantSections = PlantSectionRepository.findByLocationId(locationId);

		LOGGER.debug("The plant sections {} was found", plantSections);
		return plantSections;
	}

	@Override
	public List<PlantSection> getByLocationIdAndLogbookType(long locationId, String logbookType) {
		LOGGER.info("Get the plant sections by locationId: {}, logbookType: {}", locationId, logbookType);

		List<PlantSection> plantSections = PlantSectionRepository.findByLocationIdAndLogbookType(locationId,
				logbookType);

		LOGGER.debug("The plant sections {} was found", plantSections);
		return plantSections;
	}

}
