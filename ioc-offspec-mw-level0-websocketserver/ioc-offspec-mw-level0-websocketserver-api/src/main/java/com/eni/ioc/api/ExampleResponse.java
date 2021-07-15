package com.eni.ioc.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExampleResponse implements Serializable {

	private static final long serialVersionUID = 10000L;
	
	private ExampleDTO result;
	private List<ExampleDTO> results = new ArrayList<ExampleDTO>();

	public ExampleDTO getResult() {
		return result;
	}

	public void setResult(ExampleDTO result) {
		this.result = result;
	}

	public List<ExampleDTO> getResults() {
		return results;
	}

	public void setResults(List<ExampleDTO> results) {
		this.results = results;
	}
	
}
