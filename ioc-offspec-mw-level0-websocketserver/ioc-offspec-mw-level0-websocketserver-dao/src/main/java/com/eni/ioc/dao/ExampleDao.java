package com.eni.ioc.dao;

import java.util.List;

import com.eni.ioc.api.ExampleRequest;
import com.eni.ioc.entities.ExampleEntity;


/**
 * An Example DAO for JPA access
 * 
 * @author generated automatically by eni-msa-mw-archetype 
 * 
 */
public interface ExampleDao {

	// example method
	List<ExampleEntity> list(int start, int end, ExampleRequest parameters);

	// example method
	int count(ExampleRequest parameters);
}
