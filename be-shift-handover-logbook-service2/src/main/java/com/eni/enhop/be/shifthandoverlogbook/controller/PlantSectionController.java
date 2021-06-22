package com.eni.enhop.be.shifthandoverlogbook.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eni.enhop.be.shifthandoverlogbook.model.PlantSection;
import com.eni.enhop.be.shifthandoverlogbook.service.PlantSectionService;

@RestController
@RequestMapping("locations/{locationId}/logbook/")
public class PlantSectionController {

	@Autowired
	private PlantSectionService plantSectionService;

	private static final Logger LOGGER = LoggerFactory.getLogger(PlantSectionController.class);

	@GetMapping("plant-sections/")
	@PreAuthorize("isMember(#locationId)")
	public ResponseEntity<List<PlantSection>> getByLocationId(@PathVariable long locationId) {
		return ResponseEntity.ok(plantSectionService.getByLocationId(locationId));
	}

	@GetMapping("{logbookType}/plant-sections/")
	@PreAuthorize("isMember(#locationId)")
	public ResponseEntity<List<PlantSection>> getByLocationIdAndLogbookType(@PathVariable long locationId,
			@PathVariable String logbookType) {
		return ResponseEntity.ok(plantSectionService.getByLocationIdAndLogbookType(locationId, logbookType));
	}
}
