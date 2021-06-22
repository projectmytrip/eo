package com.eni.enhop.be.shifthandoverlogbook.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eni.enhop.be.shifthandoverlogbook.model.WorkShift;
import com.eni.enhop.be.shifthandoverlogbook.repository.WorkShiftRepository;

@Service
public class WorkShiftServiceImpl implements WorkShiftService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkShiftService.class);

	@Autowired
	private WorkShiftRepository workShiftRepository;

	@Override
	public List<WorkShift> getLocationWorkShifts(long locationId) {
		LOGGER.info("Get all work shifts for the location {}", locationId);
		List<WorkShift> workShifts = workShiftRepository.findByLocationIdAndIsActiveTrue(locationId);
		LOGGER.debug("Work Shifts: {}", workShifts);
		return workShifts;
	}

}
