package com.eni.enhop.be.shifthandoverlogbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eni.enhop.be.shifthandoverlogbook.model.Operator;
import com.eni.enhop.be.shifthandoverlogbook.service.OperatorService;

@RestController
@RequestMapping("locations/{locationId}/operators")
public class OperatorController {
	
	@Autowired
	private OperatorService operatorService;
	
	@GetMapping
	@PreAuthorize("isMember(#locationId)")
	public ResponseEntity<List<Operator>> getLocationOperators(@PathVariable long locationId) {
		return ResponseEntity.ok(operatorService.getLocationOperators(locationId));
	}

}
