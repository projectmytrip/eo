package com.eni.enhop.be.shifthandoverlogbook.service;

import java.util.List;

import com.eni.enhop.be.shifthandoverlogbook.model.Operator;

public interface OperatorService {
	List<Operator> getLocationOperators(long locationId);
}
