package com.eni.ioc.assetintegrity.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.eni.ioc.assetintegrity.entities.TemplateFile;

public interface TemplateFileRepo extends CrudRepository<TemplateFile,Long> {

	List<TemplateFile> findByCode(String code);

}
