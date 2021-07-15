package com.eni.ioc.assetintegrity.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eni.ioc.assetintegrity.enitities.DetailDto;
import com.eni.ioc.assetintegrity.entities.TemplateFile;
import com.eni.ioc.assetintegrity.utils.StatusEnum;

/**
 * @author a.menolascina
 */
@Service
public class AssetIntegrityExcelServiceImpl implements AssetIntegrityExcelService {

	private static final Logger logger = LoggerFactory.getLogger(AssetIntegrityExcelServiceImpl.class);
	private static final String COVA = "COVA";

	private static int idIdx = 0;
	private static int valueIdx = 2;
	private static int statusIdx = 3;
	private static int dateIdx = 5;

	@Override
	public TemplateFile populateTemplate(TemplateFile templateFile, List<DetailDto> list) throws IOException {

		//Create input stream from byte array
		ByteArrayInputStream bin = new ByteArrayInputStream(templateFile.getAttachment());

		//Create workbook object from byte input stream
		Workbook workbook = new XSSFWorkbook(bin);
		Sheet sheet = workbook.getSheetAt(0);

		Map<Integer, List<String>> data = new HashMap<>();
		for (Row row : sheet) {
			if(row.getRowNum() == 0) {
				continue;
			}
			List<DetailDto> filteredList = list.stream()
					.filter((d) -> d.getId().equals(row.getCell(idIdx).getStringCellValue()))
					.collect(Collectors.toList());
			if(!filteredList.isEmpty()) {
				Cell valueCell = row.getCell(valueIdx);
				if(filteredList.get(0).getValore() != null) {
					valueCell.setCellValue(Double.parseDouble(filteredList.get(0).getValore()));
				}
				Cell statusCell = row.getCell(statusIdx);
				statusCell.setCellValue(StatusEnum.getEnum(filteredList.get(0).getStatus()) != null ?
						StatusEnum.getEnum(filteredList.get(0).getStatus()).getLabel() :
						null);
				Cell dateCell = row.getCell(dateIdx);
				if(filteredList.get(0).getLastUpdate() != null && !filteredList.get(0).getLastUpdate().isEmpty()) {
					LocalDate d = LocalDate.parse(filteredList.get(0).getLastUpdate());
					dateCell.setCellValue(d.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				}
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			workbook.write(bos);
		} finally {
			bos.close();
		}
		templateFile.setAttachment(bos.toByteArray());

		return templateFile;
	}
}
