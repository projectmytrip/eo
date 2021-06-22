package com.eni.enhop.be.shifthandoverlogbook.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eni.enhop.be.shifthandoverlogbook.model.Operator;
import com.eni.enhop.be.shifthandoverlogbook.repository.OperatorRepository;

@Service
public class OperatorServiceImpl implements OperatorService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OperatorServiceImpl.class);

	@Autowired
	private OperatorRepository operatorRepository;

	@Override
	public List<Operator> getLocationOperators(long locationId) {
		LOGGER.info("Get all operators for the location {}", locationId);
		List<Operator> operators = operatorRepository.findByLocationId(locationId);
		LOGGER.debug("Operators: {}", operators);
		return operators;
	}

}
