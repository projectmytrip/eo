package com.eni.ioc.dao;

import java.util.ArrayList;
import java.util.List;

import com.eni.ioc.api.ExampleRequest;
import com.eni.ioc.entities.ExampleEntity;

/**
 * The Example DAO
 * 
 * @author generated automatically by eni-msa-mw-archetype 
 * 
 */
public class ExampleDaoImpl implements ExampleDao {

	@Override
	public List<ExampleEntity> list(int start, int end, ExampleRequest parameters) {
		List<ExampleEntity> list = new ArrayList<ExampleEntity>();
		// Implementation here
		return list;
	}

	@Override
	public int count(ExampleRequest parameters) {
		// Implementation here
		return 1;
	}

}
