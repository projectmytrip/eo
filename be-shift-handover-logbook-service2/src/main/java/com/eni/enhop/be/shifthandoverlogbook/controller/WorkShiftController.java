package com.eni.enhop.be.shifthandoverlogbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eni.enhop.be.shifthandoverlogbook.model.WorkShift;
import com.eni.enhop.be.shifthandoverlogbook.service.WorkShiftService;

@RestController
@RequestMapping("locations/{locationId}/work-shifts")
public class WorkShiftController {

	@Autowired
	private WorkShiftService workShiftService;

	@GetMapping
	@PreAuthorize("isMember(#locationId)")
	public ResponseEntity<List<WorkShift>> getLocationWorkShifts(@PathVariable long locationId) {
		return ResponseEntity.ok(workShiftService.getLocationWorkShifts(locationId));
	}
}
