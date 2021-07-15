package com.eni.ioc.dailyworkplan.excel;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.eni.ioc.dailyworkplan.dto.ActivityDto;
import com.eni.ioc.dailyworkplan.dto.PlanningDto;

public class LoadExcelTemplate {

	private static final Logger logger = LoggerFactory.getLogger(LoadExcelTemplate.class);
	
	private static final String NO_MAINTENANCE = "NO MANUTENZIONE";
	private static final int FIRST_COLUMN = 0;
	private static final int LAST_COLUMN = 16;
	private static final int FIRST_ROW = 2;

	public static PlanningDto savePlanningFromTemplate(String asset, MultipartFile file, String referenceDate,
			String uid, String stateCode, String state) {
		
		logger.info("savePlanningFromTemplate: inizio esecuzione metodo");

		PlanningDto planningDto = new PlanningDto();

		ReadableWorkbook workbook = null;

		try {

			logger.info("savePlanningFromTemplate: inizio creazione workbook");
			
			workbook = new ReadableWorkbook(file.getInputStream());
			
			logger.info("savePlanningFromTemplate: fine creazione workbook");
			
			Sheet sheet = workbook.getFirstSheet();

			List<ActivityDto> activityListDto = new ArrayList<ActivityDto>();
			
			// nell'excel bisogna settare il flag noMaintenance a tutte le attività che seguono dopo la riga NO MANUTENZIONE
			// la lista flagNoMaintenance funge da wrapper, in questo modo è possibile modificare il valore all'interno della funzione lambda forEach
			// se flagNoMaintenance fosse una variabile normale, non potrebbe essere acceduta dalla forEach
			List<Boolean> flagNoMaintenance = new ArrayList<Boolean>();
			
			try (Stream<org.dhatim.fastexcel.reader.Row> rows = sheet.openStream()) {
                rows.forEach(currentRow -> {
					
					int rowNumber = currentRow.getRowNum();
					
					logger.info("savePlanningFromTemplate: elaborazione riga numero {}", rowNumber);
	
					if (rowNumber > FIRST_ROW && currentRow != null) {
	
						ActivityDto activityDto = new ActivityDto();
						
						boolean firstNoMaintenance = false; // quando si incontra la riga NO MANUTENZIONE, bisogna saltare la creazione del dto
						
						if(flagNoMaintenance != null && !flagNoMaintenance.contains(true)){
							boolean flag = searchNoMaintenance(currentRow);
							if(flag){
								flagNoMaintenance.add(true);
								
								// serve per non memorizzare la riga NO MANUTENZIONE nel dto e per passare alla successiva riga dell'excel
								// in questo if si entra al massimo una volta
								firstNoMaintenance = true;
							}
						}
	
						if(!firstNoMaintenance){
							activityDto.setSociety(getValueFromCell(currentRow,0));
							activityDto.setSupervisor(getValueFromCell(currentRow,1));
							activityDto.setActivator(getValueFromCell(currentRow,2));
							activityDto.setDiscipline(getValueFromCell(currentRow,3));
							activityDto.setArea(getValueFromCell(currentRow,4));
							activityDto.setNote(getValueFromCell(currentRow,5));
							activityDto.setOdm(getValueFromCell(currentRow,6));
							activityDto.setHours(getValueFromCell(currentRow,7));
							activityDto.setDescription(getValueFromCell(currentRow,8));
							activityDto.setNoMaintenance(flagNoMaintenance.contains(true) ? true : false);
							activityDto.setTeam(getValueFromCell(currentRow,9));
							activityDto.setXsn(getValueFromCell(currentRow,10));
							activityDto.setSupport(getValueFromCell(currentRow,11));
							activityDto.setTransport(getValueFromCell(currentRow,12));
							activityDto.setPriority0614(getValueFromCell(currentRow,13));
							activityDto.setPriority1422(getValueFromCell(currentRow,14));
							activityDto.setItem(getValueFromCell(currentRow,15));
							activityDto.setProgress(BigDecimal.ZERO);
							activityDto.setAsset(asset);
							activityDto.setFlagAdd(true);
							activityDto.setFlagEdit(false);
							activityDto.setFlagRemove(false);
							activityDto.setFlagHidden(false);
							
							boolean checkActivityDtoIsEmpty = checkActivityDtoIsEmpty(activityDto); // se la riga è tutta vuota e se crea comunque un dto, non va inserito
							if(checkActivityDtoIsEmpty == false){
								activityListDto.add(activityDto);
							}
						}
					}
				});
				
			}catch(Exception e){
				logger.error("Errore durante l'elaborazione delle righe del template", e);
			}
			

			// creazione PlanningDto
			// valorizzo solo i campi utili che verranno poi visualizzati sul
			// front-end

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			cal.setTime(sdf.parse(referenceDate));

			Date refDate = cal.getTime();

			planningDto.setActivityList(activityListDto);
			planningDto.setAsset(asset);
			planningDto.setInsertionBy(uid);
			planningDto.setReferenceDate(refDate);
			planningDto.setStateCode(stateCode);
			planningDto.setState(state);

		} catch (IOException e) {
			logger.error("Errore nella lettura del file", e);
		} catch (Exception e) {
			logger.error("Errore uploadTemplateFile", e);
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				logger.error("Errore nella chiusura del file", e);
			} catch (Exception e) {
				logger.error("Errore nella chiusura del file, uploadTemplateFile", e);
			}
		}
		
		logger.info("savePlanningFromTemplate: fine esecuzione metodo");

		return planningDto;
	}

	private static boolean searchNoMaintenance(Row row){
		// si cerca la scritta NO MANUTENZIONE e tutte le righe dopo tale scritta avranno il flag settato a TRUE
		boolean noMaintenance = false;
		for(int cellIndex=FIRST_COLUMN; cellIndex<LAST_COLUMN; cellIndex++){
			String cellValue = getValueFromCell(row, cellIndex);
			if(cellValue != null && NO_MAINTENANCE.equals(cellValue.toUpperCase())){
				noMaintenance = true;
				break;
			}
		}
		return noMaintenance;
	}
	
	private static String getValueFromCell(Row row, int cellIndex) {
		String value = null;
		if(row != null && row.hasCell(cellIndex) == true){
			Cell cell = row.getCell(cellIndex);
			if (cell != null) {
				switch (cell.getType()) {
				case STRING:
					value = getStringValueFromCell(cell);
					break;
				case NUMBER:
					value = getNumericValueFromCell(cell);
					break;
				default:
					break;
				}
			}
		}
		return value;
	}

	private static String getStringValueFromCell(Cell cell) {
		String value = null;
		if (cell != null && !"".equals(cell.asString())) {
			value = cell.asString();
		}
		return value;
	}

	private static String getNumericValueFromCell(Cell cell) {
		String value = null;
		if (cell != null && !"".equals(cell.asNumber())) {
			value = cell.asNumber().toPlainString();
			if (value.contains(".")) {
				// se la parte decimale è composta da soli zeri, si può
				// rimuovere
				String[] splittedValue = value.split("\\.");
				String decimalPart = splittedValue[1];
				boolean isDecimalPart = false;
				for (int i = 0; i < decimalPart.length(); i++) {
					if (decimalPart.charAt(i) != '0') {
						isDecimalPart = true;
						break;
					}
					if (isDecimalPart == false) {
						value = splittedValue[0];
					}
				}
			}
		}
		return value;
	}
	
	private static boolean checkActivityDtoIsEmpty(ActivityDto activityDto){
		boolean toReturn = false;
		if(activityDto.getSociety() == null &&
				activityDto.getSupervisor() == null &&
				activityDto.getActivator() == null &&
				activityDto.getDiscipline() == null &&
				activityDto.getArea() == null &&
				activityDto.getNote() == null &&
				activityDto.getOdm() == null &&
				activityDto.getHours() == null &&
				activityDto.getDescription() == null &&
				activityDto.getTeam() == null &&
				activityDto.getXsn() == null &&
				activityDto.getSupport() == null &&
				activityDto.getTransport() == null &&
				activityDto.getPriority0614() == null &&
				activityDto.getPriority1422() == null &&
				activityDto.getItem() == null){
			toReturn = true;
		}
		return toReturn;
	}

}
