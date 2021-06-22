package com.eni.enhop.be.shifthandoverlogbook.service;

import java.util.List;

import com.eni.enhop.be.shifthandoverlogbook.model.WorkShift;

public interface WorkShiftService {

	List<WorkShift> getLocationWorkShifts(long locationId);

}
