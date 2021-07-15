package com.eni.ioc.assetintegrity.service;

import java.io.IOException;
import java.util.List;

import com.eni.ioc.assetintegrity.enitities.DetailDto;
import com.eni.ioc.assetintegrity.entities.TemplateFile;

public interface AssetIntegrityExcelService {

	TemplateFile populateTemplate(TemplateFile templateFile, List<DetailDto> list) throws IOException;
}