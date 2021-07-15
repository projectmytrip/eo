package com.eni.ioc.assetintegrity.service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eni.ioc.assetintegrity.dao.AssetIntegrityDao;

@Service
public class ExcelServiceImpl implements ExcelService {

	private static final Logger logger = LoggerFactory.getLogger(ExcelServiceImpl.class);

	@Autowired
	AssetIntegrityDao assetIntegrityDao;

	@Override
	public ByteArrayOutputStream generateExcel(String title, Map<String, String> data, String asset, Class genClass,
			List<?> items) {
		return generateExcel(title, data, asset, genClass, items, null);
	}
	
	@Override
	public ByteArrayOutputStream generateExcel(String title, Map<String, String> data, String asset, Class genClass,
			List<?> items, Map<Integer, Map<String, Map<Integer, Integer>>> excelHeader) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(title);
		createCellStyles(workbook);
		fillData(sheet, data, asset, genClass, items, excelHeader);

		int colCount = data.size();

		// dobbiamo prendere la colonna avente tutti i valori, non gli header complessi!
		Integer rowToUse = (excelHeader != null && !excelHeader.isEmpty()) ? excelHeader.size() : 0;
		for (int j = 0; j < sheet.getRow(rowToUse).getLastCellNum(); j++) {
			//sheet.autoSizeColumn(j);
			sheet.setColumnWidth(j, sheet.getRow(rowToUse).getCell(j).getStringCellValue().length() * 350);
		}

		sheet.createFreezePane(0, colCount);
		try {
			workbook.write(baos);
		} catch (Exception e) {
			logger.error("could not write excel", e);
		} finally {
			closeAll(workbook);
		}

		return baos;

	}

	private void fillData(XSSFSheet sheet, Map<String, String> data, String asset, Class genClass, List<?> items, Map<Integer, Map<String, Map<Integer, Integer>>> excelHeader) {
		fillHeader(sheet, data, genClass, excelHeader);
		fillBody(sheet, data, asset, genClass, items, excelHeader);
	}

	private void fillHeader(XSSFSheet sheet, Map<String, String> data,  Class genClass, Map<Integer, Map<String, Map<Integer, Integer>>> excelHeader) {

		int currRow = 0;
		if(excelHeader != null && !excelHeader.isEmpty()){
			currRow = fillComplexHeader(sheet, excelHeader);
		} 
		
		Map<String, String> mapOrder = data;
		Row header = sheet.createRow(currRow);
		int i = 0;
		for (String key : mapOrder.keySet()) {
			Cell cell = header.createCell(i);
			cell.setCellValue(key);
			cell.setCellStyle(sheet.getWorkbook().getCellStyleAt(0));
			i++;
		}

	}

	private void fillBody(XSSFSheet sheet, Map<String, String> data, String asset, Class genClass, List<?> items, Map<Integer, Map<String, Map<Integer, Integer>>> excelHeader) {
		
		int rowCount = 1;
		if(excelHeader != null && !excelHeader.isEmpty()){
			// if complex header, start after it
			rowCount = excelHeader.size() + 1;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Map<String, String> mapOrder = data;
		// List<Object> registro = assetIntegrityDao.getRegistroMoc(data);

		for (Object moc : items) {
			Row row = sheet.createRow(rowCount);

			int cellCount = 0;

			for (String key : mapOrder.keySet()) {
				Cell cell = row.createCell(cellCount);
				Object value = null;
				try {
					value = genClass.getMethod(mapOrder.get(key)).invoke(moc);
					if (value != null) {
						if (value instanceof String) {
							cell.setCellValue((String) value);
						} else if (value instanceof Date) {
							String dateString = sdf.format((Date) value);
							cell.setCellValue(dateString);
						} else if (value instanceof Long) {
							cell.setCellValue((Long)value);
						} else {
							logger.warn("unhandled value type for {} = {}", key, value);
						}
						cell.setCellStyle(sheet.getWorkbook().getCellStyleAt(0));
					}
				} catch (Exception e) {
					logger.error("key {}", key, e);
				}

				cellCount++;
			}
			rowCount++;
		}

	}
	

	private int fillComplexHeader(XSSFSheet sheet, Map<Integer, Map<String, Map<Integer, Integer>>> excelHeader) {
		int currRow = 0;
		for(Map.Entry<Integer, Map<String, Map<Integer, Integer>>> entryRow : excelHeader.entrySet()){
			// per ogni row della configurazione
			currRow = entryRow.getKey();
			Row complexHeader = sheet.createRow(currRow);
			for(Map.Entry<String, Map<Integer, Integer>> entryField : entryRow.getValue().entrySet()){
				// prendiamo label e cordinate delle aree
				String label = entryField.getKey();
				Map<Integer, Integer> coords = entryField.getValue();
				Integer startingPos = null;
				Integer endingPos = null;
				if(coords != null && !coords.isEmpty()){
					// prendiamo inizio e fine
					for(Map.Entry<Integer, Integer> entryCoords : coords.entrySet()){
						startingPos = entryCoords.getKey();
						endingPos = entryCoords.getValue();
					}
				}
				
				if(startingPos != null && endingPos != null){
					// creiamo celle
					Cell complexCell = complexHeader.createCell(startingPos);
					complexCell.setCellStyle(sheet.getWorkbook().getCellStyleAt(0));
					complexCell.setCellValue(label);
					if(startingPos < endingPos){						
						sheet.addMergedRegion(new CellRangeAddress(currRow,currRow,startingPos,endingPos));
					}
				}
			}
		}
		return excelHeader.size();
	}

	private void createCellStyles(XSSFWorkbook workbook) {
		XSSFFont normal11Calibri = workbook.createFont();
		normal11Calibri.setFontHeightInPoints((short) 11);
		normal11Calibri.setColor(IndexedColors.BLACK.getIndex());
		normal11Calibri.setBold(false);
		normal11Calibri.setItalic(false);

		//index 0
		XSSFCellStyle style = workbook.getCellStyleAt(0);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setFont(normal11Calibri);
		
	}

	private void closeAll(XSSFWorkbook workbook) {
		try {
			workbook.close();
		} catch (Exception e) {
			logger.error("could not close workbook", e);
		}
	}
}
