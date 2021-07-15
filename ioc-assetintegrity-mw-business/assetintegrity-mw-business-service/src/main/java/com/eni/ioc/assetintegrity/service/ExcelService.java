package com.eni.ioc.assetintegrity.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

public interface ExcelService {
	ByteArrayOutputStream generateExcel(String title, Map<String, String> data, String asset, Class genClass, List<?> items);
	ByteArrayOutputStream generateExcel(String title, Map<String, String> data, String asset, Class genClass, List<?> items, Map<Integer, Map<String, Map<Integer, Integer>>> excelHeader);
}
