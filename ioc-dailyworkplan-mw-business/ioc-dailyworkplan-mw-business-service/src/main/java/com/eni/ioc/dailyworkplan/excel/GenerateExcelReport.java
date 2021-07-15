package com.eni.ioc.dailyworkplan.excel;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eni.ioc.dailyworkplan.entities.Activity;
import com.eni.ioc.dailyworkplan.entities.Planning;

public class GenerateExcelReport {

	private static final Logger logger = LoggerFactory.getLogger(GenerateExcelReport.class);

	public static ByteArrayOutputStream getExportExcel(Planning planning, List<Activity> activityList) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XSSFWorkbook workbook = new XSSFWorkbook();
		// createSheetPlanning(workbook, planning);
		createSheetActivity(workbook, activityList, planning.getReferenceDate());

		try {
			workbook.write(baos);
		} catch (Exception e) {
			logger.error("could not write excel", e);
		} finally {
			try {
				workbook.close();
			} catch (Exception e) {
				logger.error("could not close workbook", e);
			}
		}
		return baos;
	}

	private static void createSheetPlanning(XSSFWorkbook workbook, Planning planning) {
		XSSFSheet sheet = workbook.createSheet("Programmazione");

		XSSFCellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		style.setFont(font);

		Map<Integer, String> headerSheet = new HashMap<Integer, String>();
		headerSheet.put(0, "DATA RIFERIMENTO");
		headerSheet.put(1, "INSERITO DA");
		headerSheet.put(2, "STATO");

		int rowCount = 0;
		int columnCount = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

		Row rowHeader = sheet.createRow(rowCount);
		for (Integer position : headerSheet.keySet()) {
			Cell cell = rowHeader.createCell(columnCount);
			cell.setCellValue(headerSheet.get(position));
			cell.setCellStyle(style);
			columnCount++;
		}

		rowCount++;
		Row row = sheet.createRow(rowCount);

		Cell cell1 = row.createCell(0);
		cell1.setCellValue(sdf.format(planning.getReferenceDate()));

		Cell cell2 = row.createCell(1);
		cell2.setCellValue(planning.getInsertionBy());

		Cell cell3 = row.createCell(2);
		cell3.setCellValue(planning.getState().getState());

	}

	private static void createSheetActivity(XSSFWorkbook workbook, List<Activity> activityList, Date referenceDate) {
		
		logger.info("createSheetActivity: inizio esecuzione metodo");
		
		try{
			
			for(Activity act : activityList) {
				if(act.getSociety() == null) {
					act.setSociety("");
				}
			}
			activityList.sort(Comparator.comparing(Activity::getSociety));
			
			// Lista per la lunghezza massima di caratteri
			HashMap<Integer, Integer> maxColumnChars = new HashMap<Integer, Integer>();

			XSSFSheet sheet = workbook.createSheet("Attivit\u00e0");
			sheet.setZoom(50);
			sheet.setDefaultRowHeightInPoints((float) 31.50);
			
			int rowCount = 0;
			int columnCount = 0;
			String fontName = "Serif";
			
			Map<Integer, String> tableHeaderSheet = new TreeMap<Integer, String>();
			tableHeaderSheet.put(0, "SOCIET\u00c0");
			tableHeaderSheet.put(1, "SUPERVISORE");
			tableHeaderSheet.put(2, "ATTIVATORE");
			tableHeaderSheet.put(3, "DISCIPLINA");
			tableHeaderSheet.put(4, "AREA");
			tableHeaderSheet.put(5, "NOTE");
			tableHeaderSheet.put(6, "ODM");
			tableHeaderSheet.put(7, "ORARIO");
			tableHeaderSheet.put(8, "DESCRIZIONE ATTIVIT\u00c0");
			tableHeaderSheet.put(9, "NO MANUTENZIONE");
			tableHeaderSheet.put(10, "SQUADRA");
			tableHeaderSheet.put(11, "XSN");
			tableHeaderSheet.put(12, "ASSISTENZA DATA");
			tableHeaderSheet.put(13, "MEZZO");
			tableHeaderSheet.put(14, "PRIORIT\u00c0 06_14");
			tableHeaderSheet.put(15, "PRIORIT\u00c0 14_22");
			tableHeaderSheet.put(16, "ITEM");
			tableHeaderSheet.put(17, "AVANZAMENTO (%)");
			
			
			//Inserimento lunghezza caratteri header colonne come valore base di partenza
			for (Integer position : tableHeaderSheet.keySet()) {
				maxColumnChars.put(position, tableHeaderSheet.get(position).length()); 
			}
			
			// Creazione dei font custom con colori differenti
			XSSFFont redFont = creaFont(workbook, true, IndexedColors.RED.getIndex(), (short) 14, fontName);
			XSSFFont greenFont = creaFont(workbook, true, IndexedColors.GREEN.getIndex(), (short) 14, fontName);
			XSSFFont blueFont = creaFont(workbook, true, IndexedColors.DARK_BLUE.getIndex(), (short) 14, fontName);
	
			// Creazione style per header della tabella dello sheet
			XSSFFont font = creaFont(workbook, true, IndexedColors.BLACK.getIndex(), (short) 14, fontName);
			XSSFCellStyle style = creaStyle(workbook, font);
			setAlignments(style, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			setBorders(style, BorderStyle.THICK, BorderStyle.THIN, BorderStyle.THICK, BorderStyle.THIN);
			setForeGround(style, IndexedColors.WHITE.getIndex(), FillPatternType.SOLID_FOREGROUND);
	
			// Creo lo style per la cella header dello sheet
			XSSFCellStyle sheetHeaderCell = creaStyle(workbook, font);
			setAlignments(sheetHeaderCell, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			setBorders(sheetHeaderCell, BorderStyle.THICK, BorderStyle.THICK, BorderStyle.THICK, BorderStyle.THICK);
			setForeGround(sheetHeaderCell, IndexedColors.GREY_25_PERCENT.getIndex(), FillPatternType.SOLID_FOREGROUND);
			
			// Creazione style custom per celle dell'header tabella
			// Priorita1422 Table Header
			XSSFCellStyle priorita1422HeaderStyle = creaStyle(workbook, greenFont);
			setAlignments(priorita1422HeaderStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			setBorders(priorita1422HeaderStyle, BorderStyle.THICK, BorderStyle.THIN, BorderStyle.THICK, BorderStyle.THIN);
			setForeGround(priorita1422HeaderStyle, IndexedColors.CORAL.getIndex(), FillPatternType.SOLID_FOREGROUND);
			
			// Priorita0614 Table Header
			XSSFCellStyle priorita0614HeaderStyle = creaStyle(workbook, redFont);
			setAlignments(priorita0614HeaderStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			setBorders(priorita0614HeaderStyle, BorderStyle.THICK, BorderStyle.THIN, BorderStyle.THICK, BorderStyle.THIN);
			setForeGround(priorita0614HeaderStyle, IndexedColors.SKY_BLUE.getIndex(), FillPatternType.SOLID_FOREGROUND);
					
			// Attivatore Table Header
			XSSFCellStyle attivatoreHeaderStyle = creaStyle(workbook, redFont);
			setAlignments(attivatoreHeaderStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			setBorders(attivatoreHeaderStyle, BorderStyle.THICK, BorderStyle.THIN, BorderStyle.THICK, BorderStyle.THIN);
	
			// Note Table Header
			XSSFCellStyle noteHeaderStyle = creaStyle(workbook, redFont);
			setAlignments(noteHeaderStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			setBorders(noteHeaderStyle, BorderStyle.THICK, BorderStyle.THIN, BorderStyle.THICK, BorderStyle.THIN);
			
			// Item Table Header
			XSSFCellStyle itemHeaderStyle = creaStyle(workbook, font);
			setAlignments(itemHeaderStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			setBorders(itemHeaderStyle, BorderStyle.THICK, BorderStyle.THIN, BorderStyle.THICK, BorderStyle.THIN);
			setForeGround(itemHeaderStyle, IndexedColors.ROYAL_BLUE.getIndex(), FillPatternType.SOLID_FOREGROUND);
			
			// Avanzamento Table Header
			XSSFCellStyle avanzamentoHeaderStyle = creaStyle(workbook, font);
			setAlignments(avanzamentoHeaderStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			setBorders(avanzamentoHeaderStyle, BorderStyle.THICK, BorderStyle.THICK, BorderStyle.THICK, BorderStyle.THIN);
			
			
			// Creo la stringa per la data da inserire nella cella header dello sheet
			String pattern = "EEEEE dd/MM/yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("it", "IT"));
			String sheetHeaderDate = simpleDateFormat.format(referenceDate);
			sheetHeaderDate = sheetHeaderDate.substring(0, 1).toUpperCase() + sheetHeaderDate.substring(1);
	
			// Merge di tutte le colonne, inserimento testo e aggiunta style
			Row rowSheetHeader = sheet.createRow(rowCount);
			Cell bigCell = rowSheetHeader.createCell(columnCount);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, tableHeaderSheet.size() - 1));
			bigCell.setCellValue("MAESTRANZE PRESENTI IN IMPIANTO - " + sheetHeaderDate);
			bigCell.setCellStyle(sheetHeaderCell);
			rowSheetHeader.setHeightInPoints((float) 34.50);
	
			rowCount++;
			
			// Inserisco l'header della tabella
			Row rowHeader = sheet.createRow(rowCount);
			rowHeader.setHeightInPoints((float) 34.50);
			for (Integer position : tableHeaderSheet.keySet()) {
				Cell cell = rowHeader.createCell(columnCount);
				cell.setCellValue(tableHeaderSheet.get(position));
				switch(position) {
					case 2:
						cell.setCellStyle(attivatoreHeaderStyle);
						break;
					case 5:
						cell.setCellStyle(noteHeaderStyle);
						break;
					case 14:
						cell.setCellStyle(priorita0614HeaderStyle);
						break;
					case 15:
						cell.setCellStyle(priorita1422HeaderStyle);
						break;
					case 16:
						cell.setCellStyle(itemHeaderStyle);
						break;
					case 17:
						cell.setCellStyle(avanzamentoHeaderStyle);
						break;
					default:
						cell.setCellStyle(style);
						break;
				}
				columnCount++;
			}
			
			// Creazione font standard da utilizzare nelle celle della tabella
			XSSFFont cellFont = creaFont(workbook, false, IndexedColors.BLACK.getIndex(), (short) 14, fontName);
			
			// Variabili per logica di merge delle righe
			//String societa = activityList.get(0).getSociety();
			//int rigaInizio = 2;
			int rigaFine = 1;
			
			// Inserimento dati all'interno della tabella e applicazione style alle celle
			for (Activity activity : activityList) {
				rowCount++;
				Row row = sheet.createRow(rowCount);
				
				// Cella societa
				Cell cell1 = row.createCell(0);
				cell1.setCellValue(activity.getSociety());
				XSSFCellStyle societyCellStyle = creaStyle(workbook, cellFont);
				setBorders(societyCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.NONE, BorderStyle.NONE);
				setForeGround(societyCellStyle, IndexedColors.LIGHT_ORANGE.getIndex(), FillPatternType.SOLID_FOREGROUND);
				setAlignments(societyCellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
				
				// Cella supervisore
				Cell cell2 = row.createCell(1);
				cell2.setCellValue(activity.getSupervisor());
				XSSFCellStyle supervisorCellStyle = creaStyle(workbook, cellFont);
				setBorders(supervisorCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.NONE, BorderStyle.NONE);
				setForeGround(supervisorCellStyle, IndexedColors.LIME.getIndex(), FillPatternType.SOLID_FOREGROUND);
				setAlignments(supervisorCellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
	
				// Cella attivatore 
				Cell cell3 = row.createCell(2);
				cell3.setCellValue(activity.getActivator());
				XSSFCellStyle activatorCellStyle = creaStyle(workbook, cellFont);
				setBorders(activatorCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.NONE, BorderStyle.NONE);
				setAlignments(activatorCellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
	
				// Cella disciplina
				Cell cell4 = row.createCell(3);
				cell4.setCellValue(activity.getDiscipline());
				XSSFCellStyle disciplineCellStyle = creaStyle(workbook, cellFont);
				setBorders(disciplineCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.NONE, BorderStyle.NONE);
				setAlignments(disciplineCellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
	
				// Cella area
				Cell cell5 = row.createCell(4);
				cell5.setCellValue(activity.getArea());
				XSSFCellStyle areaCellStyle = creaStyle(workbook, blueFont);
				setBorders(areaCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.NONE, BorderStyle.NONE);
				setAlignments(areaCellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
	
				// Cella note
				Cell cell6 = row.createCell(5);
				cell6.setCellValue(activity.getNote());
				XSSFCellStyle noteCellStyle = creaStyle(workbook, redFont);
				setBorders(noteCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.NONE, BorderStyle.NONE);
				setAlignments(noteCellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
				
				// Cella odm
				Cell cell7 = row.createCell(6);
				String odm = activity.getOdm();
				if(odm != null && (odm.contains(" ") || odm.contains(","))) {
					odm = odm.replace(" ", " \n");
					odm = odm.replace(",", ",\n");
				}
				cell7.setCellValue(odm);
				XSSFCellStyle odmCellStyle = creaStyle(workbook, blueFont);
				odmCellStyle.setWrapText(true);
				setBorders(odmCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.NONE, BorderStyle.NONE);
				setAlignments(odmCellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
				
				
				// Cella orario
				Cell cell8 = row.createCell(7);
				cell8.setCellValue(activity.getHours());
				XSSFCellStyle hoursCellStyle = creaStyle(workbook, cellFont);
				setBorders(hoursCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.NONE, BorderStyle.NONE);
				setAlignments(hoursCellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
				if(activity.getHours() != null) {
					switch (activity.getHours()) { // Style differenziato per orario
						case "06:00 - 14:00":
							setForeGround(hoursCellStyle, IndexedColors.SKY_BLUE.getIndex(), FillPatternType.SOLID_FOREGROUND);
							break;
						case "14:00 - 22:00":
							setForeGround(hoursCellStyle, IndexedColors.LIGHT_ORANGE.getIndex(), FillPatternType.SOLID_FOREGROUND);
							break;
						case "22:00 - 06:00":
							setForeGround(hoursCellStyle, IndexedColors.BLUE_GREY.getIndex(), FillPatternType.SOLID_FOREGROUND);
							break;
						default:
							break;
					}
				}
	
				// Cella descrizione
				Cell cell9 = row.createCell(8);
				cell9.setCellValue(activity.getDescription());
				XSSFCellStyle descriptionCellStyle = creaStyle(workbook, cellFont);
				descriptionCellStyle.setWrapText(true);
				setBorders(descriptionCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.NONE, BorderStyle.NONE);
				setAlignments(descriptionCellStyle, HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
	
				// Cella no manutenzione
				Cell cell10 = row.createCell(9);
				cell10.setCellValue("1".equals(activity.getNoMaintenance()) ? "SI" : "");
				XSSFCellStyle noManutenzioneCellStyle = creaStyle(workbook, cellFont);
				noManutenzioneCellStyle.setWrapText(true);
				setBorders(noManutenzioneCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.NONE, BorderStyle.NONE);
				setAlignments(noManutenzioneCellStyle, HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
	
				// Cella squadra
				Cell cell11 = row.createCell(10);
				cell11.setCellValue(activity.getTeam());
				XSSFCellStyle teamCellStyle = creaStyle(workbook, cellFont);
				setBorders(teamCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.NONE, BorderStyle.NONE);
				setAlignments(teamCellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
				setForeGround(teamCellStyle, IndexedColors.LIGHT_ORANGE.getIndex(), FillPatternType.SOLID_FOREGROUND);
	
				// Cella XSN
				Cell cell12 = row.createCell(11);
				cell12.setCellValue(activity.getXsn());
				XSSFCellStyle xsnCellStyle = creaStyle(workbook, cellFont);
				setBorders(xsnCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.NONE, BorderStyle.NONE);
				setAlignments(xsnCellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
	
				// Cella assistenza data
				Cell cell13 = row.createCell(12);
				cell13.setCellValue(activity.getSupport());
				XSSFCellStyle supportCellStyle = creaStyle(workbook, cellFont);
				setBorders(supportCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.NONE, BorderStyle.NONE);
				if(activity.getSupport() != null) {
					setForeGround(supportCellStyle, IndexedColors.LIGHT_ORANGE.getIndex(), FillPatternType.SOLID_FOREGROUND);
				}
				
				// Cella mezzo
				Cell cell14 = row.createCell(13);
				cell14.setCellValue(activity.getTransport());
				XSSFCellStyle transportCellStyle = creaStyle(workbook, cellFont);
				setBorders(transportCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.NONE, BorderStyle.NONE);
				setAlignments(transportCellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
				if(activity.getTransport() != null) {
					setForeGround(transportCellStyle, IndexedColors.LIGHT_ORANGE.getIndex(), FillPatternType.SOLID_FOREGROUND);
				}
	
				// Cella priorita0614
				Cell cell15 = row.createCell(14);
				cell15.setCellValue(activity.getPriority0614());
				XSSFCellStyle priority0614CellStyle = creaStyle(workbook, redFont);
				setBorders(priority0614CellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.NONE, BorderStyle.NONE);
				setAlignments(priority0614CellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
				if(activity.getPriority0614() != null) { // Style applicato solo se dato valorizzato
					setForeGround(priority0614CellStyle, IndexedColors.YELLOW.getIndex(), FillPatternType.SOLID_FOREGROUND);
				}
	
				// Cella priorita1422
				Cell cell16 = row.createCell(15);
				cell16.setCellValue(activity.getPriority1422());
				XSSFCellStyle priority1422CellStyle = creaStyle(workbook, greenFont);
				setBorders(priority1422CellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.NONE, BorderStyle.NONE);
				setAlignments(priority1422CellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
				if(activity.getPriority1422() != null) { // Style applicato solo se dato valorizzato
					setForeGround(priority1422CellStyle, IndexedColors.YELLOW.getIndex(), FillPatternType.SOLID_FOREGROUND);
				}
	
				// Cella item
				Cell cell17 = row.createCell(16);
				cell17.setCellValue(activity.getItem());
				XSSFCellStyle itemCellStyle = creaStyle(workbook, blueFont);
				setBorders(itemCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.NONE, BorderStyle.NONE);
				setAlignments(itemCellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
	
				// Cella avanzamento
				Cell cell18 = row.createCell(17);
				cell18.setCellValue(activity.getProgress().toPlainString());
				XSSFCellStyle progressCellStyle = creaStyle(workbook, blueFont);
				setBorders(progressCellStyle, BorderStyle.THIN, BorderStyle.THICK, BorderStyle.NONE, BorderStyle.NONE);
				setAlignments(progressCellStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
				
				/*
				boolean flag = false;
				// Logica per merge dati di colonna
				if(societa.equals(activity.getSociety())) {
					rigaFine++;
					row.setHeightInPoints((short)-1);
					if(!(rigaFine == rigaInizio) && (activity.getOdm() == null || !activity.getOdm().contains("\n"))) {
						row.setHeightInPoints(row.getHeightInPoints()+6);
						flag = true;
					}
					if(!flag && !(rigaFine == rigaInizio) && (activity.getDescription() != null && !activity.getDescription().contains("\n"))) {
						row.setHeightInPoints(row.getHeightInPoints()+6);
					}
				} else {
					if(rigaFine > rigaInizio) { // Merge applicabile solo se vengono mergiate 2 o piu' celle
						sheet.addMergedRegion(new CellRangeAddress(rigaInizio, rigaFine, 0, 0));
						sheet.addMergedRegion(new CellRangeAddress(rigaInizio, rigaFine, 1, 1));
						// Quindi applico border thick per distaccare il blocco di societa'
						societyCellStyle.setBorderTop(BorderStyle.THICK);
						supervisorCellStyle.setBorderTop(BorderStyle.THICK);
						activatorCellStyle.setBorderTop(BorderStyle.THICK);
						disciplineCellStyle.setBorderTop(BorderStyle.THICK);
						areaCellStyle.setBorderTop(BorderStyle.THICK);
						noteCellStyle.setBorderTop(BorderStyle.THICK);
						odmCellStyle.setBorderTop(BorderStyle.THICK);
						hoursCellStyle.setBorderTop(BorderStyle.THICK);
						descriptionCellStyle.setBorderTop(BorderStyle.THICK);
						teamCellStyle.setBorderTop(BorderStyle.THICK);
						xsnCellStyle.setBorderTop(BorderStyle.THICK);
						supportCellStyle.setBorderTop(BorderStyle.THICK);
						transportCellStyle.setBorderTop(BorderStyle.THICK);
						priority0614CellStyle.setBorderTop(BorderStyle.THICK);
						priority1422CellStyle.setBorderTop(BorderStyle.THICK);
						itemCellStyle.setBorderTop(BorderStyle.THICK);
						progressCellStyle.setBorderTop(BorderStyle.THICK);
						row.setHeightInPoints((short)-1);
						if(activity.getOdm() == null || !activity.getOdm().contains("\n")) {
							row.setHeightInPoints(row.getHeightInPoints()+6);
							flag = true;
						}
						if(!flag && (activity.getDescription() != null && !activity.getDescription().contains("\n"))) {
							row.setHeightInPoints(row.getHeightInPoints()+6);
						}
					} else if (rigaFine == rigaInizio) { // Logica per la quale viene applicato il border thick se una sola cella
						societyCellStyle.setBorderTop(BorderStyle.THICK);
						supervisorCellStyle.setBorderTop(BorderStyle.THICK);
						activatorCellStyle.setBorderTop(BorderStyle.THICK);
						disciplineCellStyle.setBorderTop(BorderStyle.THICK);
						areaCellStyle.setBorderTop(BorderStyle.THICK);
						noteCellStyle.setBorderTop(BorderStyle.THICK);
						odmCellStyle.setBorderTop(BorderStyle.THICK);
						hoursCellStyle.setBorderTop(BorderStyle.THICK);
						descriptionCellStyle.setBorderTop(BorderStyle.THICK);
						teamCellStyle.setBorderTop(BorderStyle.THICK);
						xsnCellStyle.setBorderTop(BorderStyle.THICK);
						supportCellStyle.setBorderTop(BorderStyle.THICK);
						transportCellStyle.setBorderTop(BorderStyle.THICK);
						priority0614CellStyle.setBorderTop(BorderStyle.THICK);
						priority1422CellStyle.setBorderTop(BorderStyle.THICK);
						itemCellStyle.setBorderTop(BorderStyle.THICK);
						progressCellStyle.setBorderTop(BorderStyle.THICK);
						row.setHeightInPoints((float) 31.50);
					}
					
					rigaInizio = rigaFine + 1;
					
					societa = activity.getSociety();
				}*/
	
				rigaFine++;
				
				// Logica per applicare il border thick alla fine della tabella
				if(activityList.size() < rigaFine) {
					societyCellStyle.setBorderBottom(BorderStyle.THICK);
					supervisorCellStyle.setBorderBottom(BorderStyle.THICK);
					activatorCellStyle.setBorderBottom(BorderStyle.THICK);
					disciplineCellStyle.setBorderBottom(BorderStyle.THICK);
					areaCellStyle.setBorderBottom(BorderStyle.THICK);
					noteCellStyle.setBorderBottom(BorderStyle.THICK);
					odmCellStyle.setBorderBottom(BorderStyle.THICK);
					hoursCellStyle.setBorderBottom(BorderStyle.THICK);
					descriptionCellStyle.setBorderBottom(BorderStyle.THICK);
					noManutenzioneCellStyle.setBorderBottom(BorderStyle.THICK);
					teamCellStyle.setBorderBottom(BorderStyle.THICK);
					xsnCellStyle.setBorderBottom(BorderStyle.THICK);
					supportCellStyle.setBorderBottom(BorderStyle.THICK);
					transportCellStyle.setBorderBottom(BorderStyle.THICK);
					priority0614CellStyle.setBorderBottom(BorderStyle.THICK);
					priority1422CellStyle.setBorderBottom(BorderStyle.THICK);
					itemCellStyle.setBorderBottom(BorderStyle.THICK);
					progressCellStyle.setBorderBottom(BorderStyle.THICK);
				}
				
				// Setto lo stile a tutte le celle
				cell1.setCellStyle(societyCellStyle);
				cell2.setCellStyle(supervisorCellStyle);
				cell3.setCellStyle(activatorCellStyle);
				cell4.setCellStyle(disciplineCellStyle);
				cell5.setCellStyle(areaCellStyle);
				cell6.setCellStyle(noteCellStyle);
				cell7.setCellStyle(odmCellStyle);
				cell8.setCellStyle(hoursCellStyle);
				cell9.setCellStyle(descriptionCellStyle);
				cell10.setCellStyle(noManutenzioneCellStyle);
				cell11.setCellStyle(teamCellStyle);
				cell12.setCellStyle(xsnCellStyle);
				cell13.setCellStyle(supportCellStyle);
				cell14.setCellStyle(transportCellStyle);
				cell15.setCellStyle(priority0614CellStyle);
				cell16.setCellStyle(priority1422CellStyle);
				cell17.setCellStyle(itemCellStyle);
				cell18.setCellStyle(progressCellStyle);
				
				// Aggiorno eventuale valore massimo di caratteri massimo della colonna
				for (Integer position : tableHeaderSheet.keySet()) {
					switch(position) {
						case 0:
						    if(activity.getSociety() != null && maxColumnChars.get(position) < activity.getSociety().length()) {
						        maxColumnChars.put(position, activity.getSociety().length());
						    }
						    break;
						case 1:
						    if(activity.getSupervisor() != null && maxColumnChars.get(position) < activity.getSupervisor().length()) {
						        maxColumnChars.put(position, activity.getSupervisor().length());
						    }
						    break;
						case 2:
						    if(activity.getActivator() != null && maxColumnChars.get(position) < activity.getActivator().length()) {
						        maxColumnChars.put(position, activity.getActivator().length());
						    }
						    break;
						case 3:
						    if(activity.getDiscipline() != null && maxColumnChars.get(position) < activity.getDiscipline().length()) {
						        maxColumnChars.put(position, activity.getDiscipline().length());
						    }
						    break;
						case 4:
						    if(activity.getArea() != null && maxColumnChars.get(position) < activity.getArea().length()) {
						        maxColumnChars.put(position, activity.getArea().length());
						    }
						    break;
						case 5:
						    if(activity.getNote() != null && maxColumnChars.get(position) < activity.getNote().length()) {
						        maxColumnChars.put(position, activity.getNote().length());
						    }
						    break;
						case 6:
						    if(activity.getOdm() != null && maxColumnChars.get(position) < activity.getOdm().length()) {
						        maxColumnChars.put(position, activity.getOdm().length());
						    }
						    break;
						case 7:
						    if(activity.getHours() != null && maxColumnChars.get(position) < activity.getHours().length()) {
						        maxColumnChars.put(position, activity.getHours().length());
						    }
						    break;
						case 8:
						    if(activity.getDescription() != null && maxColumnChars.get(position) < activity.getDescription().length()) {
						        maxColumnChars.put(position, activity.getDescription().length());
						    }
						    break;
						case 9:
						    if(activity.getNoMaintenance() != null && maxColumnChars.get(position) < activity.getNoMaintenance().length()) {
						        maxColumnChars.put(position, activity.getNoMaintenance().length());
						    }
						    break;
						case 10:
						    if(activity.getTeam() != null && maxColumnChars.get(position) < activity.getTeam().length()) {
						        maxColumnChars.put(position, activity.getTeam().length());
						    }
						    break;
						case 11:
						    if(activity.getXsn() != null && maxColumnChars.get(position) < activity.getXsn().length()) {
						        maxColumnChars.put(position, activity.getXsn().length());
						    }
						    break;
						case 12:
						    if(activity.getSupport() != null && maxColumnChars.get(position) < activity.getSupport().length()) {
						        maxColumnChars.put(position, activity.getSupport().length());
						    }
						    break;
						case 13:
						    if(activity.getTransport() != null && maxColumnChars.get(position) < activity.getTransport().length()) {
						        maxColumnChars.put(position, activity.getTransport().length());
						    }
						    break;
						case 14:
						    if(activity.getPriority0614() != null && maxColumnChars.get(position) < activity.getPriority0614().length()) {
						        maxColumnChars.put(position, activity.getPriority0614().length());
						    }
						    break;
						case 15:
						    if(activity.getPriority1422() != null && maxColumnChars.get(position) < activity.getPriority1422().length()) {
						        maxColumnChars.put(position, activity.getPriority1422().length());
						    }
						    break;
						case 16:
						    if(activity.getItem() != null && maxColumnChars.get(position) < activity.getItem().length()) {
						        maxColumnChars.put(position, activity.getItem().length());
						    }
						    break;
						case 17:
						    if(activity.getProgress() != null && maxColumnChars.get(position) < String.valueOf(activity.getProgress()).length()) {
						        maxColumnChars.put(position, String.valueOf(activity.getProgress()).length());
						    }
						    break;
                        default:
                        	break;
					}
				}
			}
	
			// Autosizing di tutte le colonne
			for (Integer position : tableHeaderSheet.keySet()) {
				if(sheet != null) {
					int width = ((int)((maxColumnChars.get(position) > 255 ? 200 : maxColumnChars.get(position)) * 1.14388)) * 256;
					sheet.setColumnWidth(position, width+1536);
				}
			}
			
		} catch (NullPointerException e) {
			logger.error("Errore variabile null", e);
		} catch (Exception e) {
			logger.error("Eccezione generica", e);
		}
		
		logger.info("createSheetActivity: fine esecuzione metodo");
	}

	public static XSSFFont creaFont(XSSFWorkbook workbook, boolean bold, short color, short points, String fontName) {
		XSSFFont font = workbook.createFont();
		font.setBold(bold);
		font.setColor(color);
		font.setFontHeightInPoints(points);
		font.setFontName(fontName);
		return font;
	}
	
	public static XSSFCellStyle creaStyle(XSSFWorkbook workbook, XSSFFont font) {
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);
		return style;
	}
	
	public static void setBorders(XSSFCellStyle style, BorderStyle top, BorderStyle right, BorderStyle bottom, BorderStyle left) {
		style.setBorderTop(top);
		style.setBorderRight(right);
		style.setBorderBottom(bottom);
		style.setBorderLeft(left);
	}
	
	private static void setAlignments(XSSFCellStyle style, HorizontalAlignment horizontal, VerticalAlignment vertical) {
		style.setAlignment(horizontal);
		style.setVerticalAlignment(vertical);
	}
	
	private static void setForeGround(XSSFCellStyle style, short indexedColor, FillPatternType fillPatternType) {
		style.setFillForegroundColor(indexedColor);
		style.setFillPattern(fillPatternType);
	}
	
	public static ByteArrayOutputStream getEmptyTemplate() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XSSFWorkbook workbook = new XSSFWorkbook();
		// createSheetPlanning(workbook, planning);
		createEmptySheet(workbook);

		try {
			workbook.write(baos);
		} catch (Exception e) {
			logger.error("could not write excel", e);
		} finally {
			try {
				workbook.close();
			} catch (Exception e) {
				logger.error("could not close workbook", e);
			}
		}
		return baos;
	}
	
	
	
	private static void createEmptySheet(XSSFWorkbook workbook) {
		
		logger.info("createSheetActivity: inizio esecuzione metodo");
		
		try{
			XSSFSheet sheet = workbook.createSheet("Attivit\u00e0");
			sheet.setZoom(50);
			sheet.setDefaultRowHeightInPoints((float) 31.50);
			
			int rowCount = 0;
			int columnCount = 0;
			String fontName = "Serif";
			
			// Lista per la lunghezza massima di caratteri
			HashMap<Integer, Integer> maxColumnChars = new HashMap<Integer, Integer>();
			
			Map<Integer, String> tableHeaderSheet = new TreeMap<Integer, String>();
			tableHeaderSheet.put(0, "SOCIET\u00c0");
			tableHeaderSheet.put(1, "SUPERVISORE");
			tableHeaderSheet.put(2, "ATTIVATORE");
			tableHeaderSheet.put(3, "DISCIPLINA");
			tableHeaderSheet.put(4, "AREA");
			tableHeaderSheet.put(5, "NOTE");
			tableHeaderSheet.put(6, "ODM");
			tableHeaderSheet.put(7, "ORARIO");
			tableHeaderSheet.put(8, "DESCRIZIONE ATTIVIT\u00c0");
			tableHeaderSheet.put(9, "SQUADRA");
			tableHeaderSheet.put(10, "XSN");
			tableHeaderSheet.put(11, "ASSISTENZA DATA");
			tableHeaderSheet.put(12, "MEZZO");
			tableHeaderSheet.put(13, "PRIORIT\u00c0 06_14");
			tableHeaderSheet.put(14, "PRIORIT\u00c0 14_22");
			tableHeaderSheet.put(15, "ITEM");
			
			//Inserimento lunghezza caratteri header colonne come valore base di partenza
			for (Integer position : tableHeaderSheet.keySet()) {
				maxColumnChars.put(position, tableHeaderSheet.get(position).length()); 
			}
			
			// Creazione dei font custom con colori differenti
			XSSFFont redFont = creaFont(workbook, true, IndexedColors.RED.getIndex(), (short) 14, fontName);
			XSSFFont greenFont = creaFont(workbook, true, IndexedColors.GREEN.getIndex(), (short) 14, fontName);
	
			// Creazione style per header della tabella dello sheet
			XSSFFont font = creaFont(workbook, true, IndexedColors.BLACK.getIndex(), (short) 14, fontName);
			XSSFCellStyle style = creaStyle(workbook, font);
			setAlignments(style, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			setBorders(style, BorderStyle.THICK, BorderStyle.THIN, BorderStyle.THICK, BorderStyle.THIN);
			setForeGround(style, IndexedColors.WHITE.getIndex(), FillPatternType.SOLID_FOREGROUND);
	
			// Creo lo style per la cella header dello sheet
			XSSFCellStyle sheetHeaderCell = creaStyle(workbook, font);
			setAlignments(sheetHeaderCell, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			setBorders(sheetHeaderCell, BorderStyle.THICK, BorderStyle.THICK, BorderStyle.THICK, BorderStyle.THICK);
			setForeGround(sheetHeaderCell, IndexedColors.GREY_25_PERCENT.getIndex(), FillPatternType.SOLID_FOREGROUND);
			
			// Creazione style custom per celle dell'header tabella
			// Priorita1422 Table Header
			XSSFCellStyle priorita1422HeaderStyle = creaStyle(workbook, greenFont);
			setAlignments(priorita1422HeaderStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			setBorders(priorita1422HeaderStyle, BorderStyle.THICK, BorderStyle.THIN, BorderStyle.THICK, BorderStyle.THIN);
			setForeGround(priorita1422HeaderStyle, IndexedColors.CORAL.getIndex(), FillPatternType.SOLID_FOREGROUND);
			
			// Priorita0614 Table Header
			XSSFCellStyle priorita0614HeaderStyle = creaStyle(workbook, redFont);
			setAlignments(priorita0614HeaderStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			setBorders(priorita0614HeaderStyle, BorderStyle.THICK, BorderStyle.THIN, BorderStyle.THICK, BorderStyle.THIN);
			setForeGround(priorita0614HeaderStyle, IndexedColors.SKY_BLUE.getIndex(), FillPatternType.SOLID_FOREGROUND);
					
			// Attivatore Table Header
			XSSFCellStyle attivatoreHeaderStyle = creaStyle(workbook, redFont);
			setAlignments(attivatoreHeaderStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			setBorders(attivatoreHeaderStyle, BorderStyle.THICK, BorderStyle.THIN, BorderStyle.THICK, BorderStyle.THIN);
	
			// Note Table Header
			XSSFCellStyle noteHeaderStyle = creaStyle(workbook, redFont);
			setAlignments(noteHeaderStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			setBorders(noteHeaderStyle, BorderStyle.THICK, BorderStyle.THIN, BorderStyle.THICK, BorderStyle.THIN);
			
			// Item Table Header
			XSSFCellStyle itemHeaderStyle = creaStyle(workbook, font);
			setAlignments(itemHeaderStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			setBorders(itemHeaderStyle, BorderStyle.THICK, BorderStyle.THICK, BorderStyle.THICK, BorderStyle.THIN);
			setForeGround(itemHeaderStyle, IndexedColors.ROYAL_BLUE.getIndex(), FillPatternType.SOLID_FOREGROUND);

			// Merge di tutte le colonne, inserimento testo e aggiunta style
			Row rowSheetHeader = sheet.createRow(rowCount);
			Cell bigCell = rowSheetHeader.createCell(columnCount);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, tableHeaderSheet.size() - 1));
			bigCell.setCellValue("MAESTRANZE PRESENTI IN IMPIANTO - Giorno dd/MM/yyyy");
			bigCell.setCellStyle(sheetHeaderCell);
			rowSheetHeader.setHeightInPoints((float) 34.50);
	
			rowCount++;
			
			// Inserisco l'header della tabella
			Row rowHeader = sheet.createRow(rowCount);
			rowHeader.setHeightInPoints((float) 34.50);
			for (Integer position : tableHeaderSheet.keySet()) {
				Cell cell = rowHeader.createCell(columnCount);
				cell.setCellValue(tableHeaderSheet.get(position));
				switch(position) {
					case 2:
						cell.setCellStyle(attivatoreHeaderStyle);
						break;
					case 5:
						cell.setCellStyle(noteHeaderStyle);
						break;
					case 13:
						cell.setCellStyle(priorita0614HeaderStyle);
						break;
					case 14:
						cell.setCellStyle(priorita1422HeaderStyle);
						break;
					case 15:
						cell.setCellStyle(itemHeaderStyle);
						break;
					default:
						cell.setCellStyle(style);
						break;
				}
				columnCount++;
			}
			
			
			
			// No Manutenzione Cell Style
			XSSFCellStyle noManutenzioneStyle = creaStyle(workbook, font);
			setAlignments(noManutenzioneStyle, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
			//setBorders(noManutenzioneStyle, BorderStyle.THICK, BorderStyle.THICK, BorderStyle.THICK, BorderStyle.THICK);
			setForeGround(noManutenzioneStyle, IndexedColors.GREY_50_PERCENT.getIndex(), FillPatternType.SOLID_FOREGROUND);
			
			rowCount += 3;
			Row row = sheet.createRow(rowCount);
			Cell noManutenzioneCell = row.createCell(8);
			//sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, tableHeaderSheet.size() - 1));
			noManutenzioneCell.setCellValue("NO MANUTENZIONE");
			noManutenzioneCell.setCellStyle(noManutenzioneStyle);
			
			// Autosizing di tutte le colonne
			for (Integer position : tableHeaderSheet.keySet()) {
				if(sheet != null) {
					int width = ((int)((maxColumnChars.get(position)*2 > 255 ? 200 : maxColumnChars.get(position)*2) * 1.14388)) * 256;
					sheet.setColumnWidth(position, width+1536);
				}
			}
			
		} catch (NullPointerException e) {
			logger.error("Errore variabile null", e);
		} catch (Exception e) {
			logger.error("Eccezione generica", e);
		}
		
		logger.info("createSheetActivity: fine esecuzione metodo");
	}

}
