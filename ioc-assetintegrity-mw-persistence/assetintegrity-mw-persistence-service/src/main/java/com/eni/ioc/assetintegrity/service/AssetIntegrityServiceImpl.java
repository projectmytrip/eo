package com.eni.ioc.assetintegrity.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eni.ioc.assetintegrity.api.DERegistry;
import com.eni.ioc.assetintegrity.dao.AssetIntegrityDao;
import com.eni.ioc.assetintegrity.dao.MocDao;
import com.eni.ioc.assetintegrity.dto.storedataservice.CorrosionBacteriaDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.CorrosionCNDItem;
import com.eni.ioc.assetintegrity.dto.storedataservice.CorrosionCouponDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.CorrosionKpiDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.CorrosionPiggingDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.CorrosionProtectionDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.EVPMSAlertDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.EVPMSStationDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.Feature;
import com.eni.ioc.assetintegrity.dto.storedataservice.IntegrityOperatingWindowsDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.JacketedPipesDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.OperatingWindowsDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.SegnaleCriticoApi;
import com.eni.ioc.assetintegrity.dto.storedataservice.StoreCriticalSignalsWfMOCRequest;
import com.eni.ioc.assetintegrity.dto.storedataservice.StoreCriticalSignalsWfRequest;
import com.eni.ioc.assetintegrity.dto.storedataservice.StoreDataServiceEVPMS;
import com.eni.ioc.assetintegrity.dto.storedataservice.StoreDataServiceRequest;
import com.eni.ioc.assetintegrity.dto.storedataservice.StoreDataServiceWellRequest;
import com.eni.ioc.assetintegrity.dto.storedataservice.WellAlarmDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.ewpBarrierPanel;
import com.eni.ioc.assetintegrity.entities.BadJacketedPipes;
import com.eni.ioc.assetintegrity.entities.CorrosionBacteria;
import com.eni.ioc.assetintegrity.entities.CorrosionCND;
import com.eni.ioc.assetintegrity.entities.CorrosionCoupon;
import com.eni.ioc.assetintegrity.entities.CorrosionKpi;
import com.eni.ioc.assetintegrity.entities.CorrosionPigging;
import com.eni.ioc.assetintegrity.entities.CorrosionProtection;
import com.eni.ioc.assetintegrity.entities.EVPMSAlert;
import com.eni.ioc.assetintegrity.entities.EVPMSStation;
import com.eni.ioc.assetintegrity.entities.EWP;
import com.eni.ioc.assetintegrity.entities.IntegrityOperatingWindowKpi;
import com.eni.ioc.assetintegrity.entities.JacketedPipes;
import com.eni.ioc.assetintegrity.entities.OperatingWindowKpi;
import com.eni.ioc.assetintegrity.entities.RegistroMoc;
import com.eni.ioc.assetintegrity.entities.SegnaleCritico;
import com.eni.ioc.assetintegrity.entities.WeekWellAlarm;
import com.eni.ioc.assetintegrity.entities.WellAlarm;
import com.eni.ioc.assetintegrity.service.sender.MessageService;
import com.eni.ioc.assetintegrity.utils.EWPConstants;
import com.eni.ioc.assetintegrity.utils.Esito;

@Service
public class AssetIntegrityServiceImpl implements AssetIntegrityService {

	private static final Logger logger = LoggerFactory.getLogger(AssetIntegrityServiceImpl.class);
	private static final String EMPTY = "";

	@Autowired
	AssetIntegrityDao assetIntegrityDao;

	@Autowired
	MocDao mocDao;

	@Autowired
	private Environment env;

	@Autowired
	private MessageService messageService;

	@Override
	public void updateSignalsMOC_DE(StoreCriticalSignalsWfRequest input, String asset) {
		if (input.getElement() != null && !input.getElement().isEmpty()) {
			mocDao.updateSignalsMOC_DE(input.getAsset(), input.getElement(), input.getUid());

			List<SegnaleCritico> signals = assetIntegrityDao.getCriticalByNameList(input.getElement());
			Set<String> areas = new HashSet<>();
			signals.forEach(item -> areas.add(item.getArea()));
			areas.forEach(area -> notifyWs(input.getAsset(), area));
		}
	}

	@Override
	public void insertUpdateSignalsMOC(StoreCriticalSignalsWfMOCRequest input, String asset) {
		if (input.getElement() != null && !input.getElement().isEmpty()) {
			mocDao.insertUpdateSignalsMOC(input.getAsset(), input.getElement(), input.getUid(), input.getMoc(), input.getWf());

			List<SegnaleCritico> signals = assetIntegrityDao.getCriticalByNameList(input.getElement());
			Set<String> areas = new HashSet<>();
			signals.forEach(item -> areas.add(item.getArea()));
			areas.forEach(area -> notifyWs(input.getAsset(), area));
		}
	}

	@Override
	@Transactional
	public void insertSegnaliCritici(StoreDataServiceRequest<SegnaleCriticoApi> input) {

		List<SegnaleCritico> elts = new ArrayList<>();
		if (input != null && input.getElement() != null && !input.getElement().isEmpty()) {
			for (SegnaleCriticoApi element : input.getElement()) {
				SegnaleCritico elt = new SegnaleCritico();

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

				try {
					if (element.getLastStatusChangeDate() != null && !"".equals(element.getLastStatusChangeDate())) {
						Date d = sdf.parse(element.getLastStatusChangeDate());
						elt.setLastStatusChangeDate(sdf.format(d));
					} else {
						elt.setLastStatusChangeDate(EMPTY);
					}

				} catch (ParseException e) {
					logger.error("error parsing blockinputdate", e);
					elt.setLastStatusChangeDate(EMPTY);
				}

				elt.setAsset(emptyIfNull(input.getAsset()));
				elt.setName(emptyIfNull(element.getName()));
				elt.setArea(emptyIfNull(element.getArea()));
				elt.setBlockInput(emptyIfNull(element.getBlockInput()));
				elt.setBlockInputStatus(emptyIfNull(element.getBlockInputStatus()));
				elt.setCategory(emptyIfNull(element.getCategory()));
				elt.setDescription(emptyIfNull(element.getDescription()));
				elt.setSeverity(emptyIfNull(element.getSeverity()));
				elt.setValue(emptyIfNull(element.getValue()));
				elts.add(elt);

			}
			if (!elts.isEmpty()) {
				assetIntegrityDao.insertSegnaliCritici(elts);

				if(input.getLastInBatch() != null && input.getLastInBatch()){
					logger.info("notifying websocket, last in batch");
					notifyWsSimple(input.getAsset(), env.getProperty("spring.event.type.map"));
				}
			}
		}
	}

	@Override
	public void insertRegistroMoc(RegistroMoc input) {
		assetIntegrityDao.insertMoc(input);
	}

	@Override
	@Transactional
	public void insertWellAlarm(StoreDataServiceWellRequest input) {
		List<WellAlarm> wellAlarms = new ArrayList<>();
		LocalDateTime insertDate = LocalDateTime.now();
		if (input != null && input.getElement() != null && !input.getElement().isEmpty()) {
			for (WellAlarmDto element : input.getElement()) {
				WellAlarm wellAlarm = new WellAlarm();
				wellAlarm.setCompany(emptyIfNull(element.getCompany()));
				wellAlarm.setField(emptyIfNull(element.getField()));
				wellAlarm.setFieldCD(emptyIfNull(element.getFieldCD()));
				wellAlarm.setWellCode(emptyIfNull(element.getWellCode()));
				wellAlarm.setWellName(emptyIfNull(element.getWellName()));
				wellAlarm.setGeneralAlarm(emptyIfNull(element.getGeneralAlarm()));
				wellAlarm.setGeneralAlarmDescription(emptyIfNull(element.getGeneralAlarmDescription()));
				wellAlarm.setSafetyValve(emptyIfNull(element.getSafetyValve()));
				wellAlarm.setSafetyValveDescription(emptyIfNull(element.getSafetyValveDescription()));
				wellAlarm.setWellheadTest(emptyIfNull(element.getWellheadTest()));
				wellAlarm.setWellheadTestDescription(emptyIfNull(element.getWellheadTestDescription()));
				wellAlarm.setAnnulusPressure(emptyIfNull(element.getAnnulusPressure()));
				wellAlarm.setAnnulusPressureDescription(emptyIfNull(element.getAnnulusPressureDescription()));
				wellAlarm.setRefDate(element.getRefDate());
				wellAlarm.setAsset(emptyIfNull(input.getAsset()));
				wellAlarm.setInsertionDate(insertDate);
				wellAlarms.add(wellAlarm);
			}
			if (!wellAlarms.isEmpty()) {
				assetIntegrityDao.insertWellAlarm(wellAlarms);
			}
		}
	}

	@Override
	public boolean insertSegnaliCriticiWf(StoreCriticalSignalsWfRequest input) {
		if (input.getElement() == null || input.getWf() == null || input.getTmpKey() == null) {
			logger.error("elements empty? " + (input.getElement() == null || input.getElement().isEmpty())
					+ "wf null ? " + (input.getWf() == null) + "hashkey null? " + (input.getTmpKey() == null));
			return false;
		}
		if (logger.isDebugEnabled()) {
			logger.debug(input.toString());
		}
		boolean res = assetIntegrityDao.updateCriticalSignWF(input.getAsset(), input.getElement(), input.getTmpKey(),
				input.getWf(), input.isApproved(), input.isClosed(), input.getStart(), input.getEnd());

		List<SegnaleCritico> signals = assetIntegrityDao.getCriticalsByHashKey(input.getTmpKey());
		Set<String> areas = new HashSet<>();
		signals.forEach(item -> areas.add(item.getArea()));
		areas.forEach(area -> notifyWs(input.getAsset(), area));

		return res;
	}

	@Override
	@Transactional
	public void insertWeekWellAlarm(StoreDataServiceWellRequest input) {
		List<WeekWellAlarm> wellAlarms = new ArrayList<>();
		LocalDateTime insertDate = LocalDateTime.now();
		if (input != null && input.getElement() != null && !input.getElement().isEmpty()) {
			for (WellAlarmDto element : input.getElement()) {
				WeekWellAlarm wellAlarm = new WeekWellAlarm();
				wellAlarm.setCompany(emptyIfNull(element.getCompany()));
				wellAlarm.setField(emptyIfNull(element.getField()));
				wellAlarm.setFieldCD(emptyIfNull(element.getFieldCD()));
				wellAlarm.setWellCode(emptyIfNull(element.getWellCode()));
				wellAlarm.setWellName(emptyIfNull(element.getWellName()));
				wellAlarm.setGeneralAlarm(emptyIfNull(element.getGeneralAlarm()));
				wellAlarm.setGeneralAlarmDescription(emptyIfNull(element.getGeneralAlarmDescription()));
				wellAlarm.setSafetyValve(emptyIfNull(element.getSafetyValve()));
				wellAlarm.setSafetyValveDescription(emptyIfNull(element.getSafetyValveDescription()));
				wellAlarm.setWellheadTest(emptyIfNull(element.getWellheadTest()));
				wellAlarm.setWellheadTestDescription(emptyIfNull(element.getWellheadTestDescription()));
				wellAlarm.setAnnulusPressure(emptyIfNull(element.getAnnulusPressure()));
				wellAlarm.setAnnulusPressureDescription(emptyIfNull(element.getAnnulusPressureDescription()));
				wellAlarm.setRefDate(element.getRefDate());
				wellAlarm.setCurrQuarter(emptyIfNull(element.getCurrQuarter()));
				wellAlarm.setAsset(emptyIfNull(input.getAsset()));
				wellAlarm.setInsertionDate(insertDate);
				wellAlarms.add(wellAlarm);
			}
			if (!wellAlarms.isEmpty()) {
				assetIntegrityDao.insertWeekWellAlarm(wellAlarms);
			}
		}
	}

	private String emptyIfNull(String toCheck) {
		return toCheck != null ? toCheck : EMPTY;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void insertSapDocument(byte[] file701, byte[] file801) {
		logger.debug("start insertFile701");
		assetIntegrityDao.insertFile701(parseCsvFile(file701));
		logger.debug("end insertFile701");
		logger.debug("start insertFile801");
		assetIntegrityDao.insertFile801(parseCsvFile(file801));
		logger.debug("end insertFile801");
		logger.debug("start callInsertBacklog");
		assetIntegrityDao.callInsertBacklog();
		logger.debug("end callInsertBacklog");
	}

	@Override
	@Transactional
	public void insertBacklogTotalCount(byte[] file) {
		assetIntegrityDao.insertBacklogTotalCount(parseCsvFile(file));
	}

	@Override
	@Transactional
	public void insertOperatingWindow(StoreDataServiceRequest<OperatingWindowsDto> input) {
		List<OperatingWindowKpi> data = new ArrayList<>();
		for (OperatingWindowsDto dto : input.getElement()) {
			OperatingWindowKpi op = new OperatingWindowKpi();
			op.setAsset(input.getAsset());
			op.setName(dto.getName());
			op.setState(dto.getState());
			op.setInsertionDate(LocalDateTime.now());
			op.setLinkVision(dto.getLinkVision());
			op.setOutHour(dto.getOutHour());
			data.add(op);
		}
		assetIntegrityDao.insertOperatingWindow(data);
	}

	@Override
	@Transactional
	public void insertIntegrityOperatingWindow(StoreDataServiceRequest<IntegrityOperatingWindowsDto> input) {
		List<IntegrityOperatingWindowKpi> data = new ArrayList<>();
		for (IntegrityOperatingWindowsDto dto : input.getElement()) {
			IntegrityOperatingWindowKpi op = new IntegrityOperatingWindowKpi();
			op.setAsset(input.getAsset());
			op.setName(dto.getZoneName());
			op.setIow(dto.getN() != null ? dto.getN().getIOW() : null);
			op.setIowOk(dto.getN() != null ? dto.getN().getIOW_OK() : null);
			op.setIowOut(dto.getN() != null ? dto.getN().getIOW_OUT() : null);
			op.setIowWarning(dto.getN() != null ? dto.getN().getIOW_WARNING() : null);
			op.setInsertionDate(LocalDateTime.now());
			op.setLinkVision(dto.getPIVisionURL());
			data.add(op);
		}
		assetIntegrityDao.insertIntegrityOperatingWindow(data);
	}

	@Override
	@Transactional
	public void insertCorrosionKpi(StoreDataServiceRequest<CorrosionKpiDto> input) {
		List<CorrosionKpi> data = new ArrayList<>();

		for (CorrosionKpiDto dto : input.getElement()) {
			CorrosionKpi op = new CorrosionKpi();
			op.setAsset(input.getAsset());
			op.setName(dto.getName());
			op.setValue(dto.getValue());
			op.setInsertionDate(LocalDateTime.now());
			data.add(op);
		}
		assetIntegrityDao.insertCorrosionKpi(data);
	}

	@Override
	@Transactional
	public void insertCorrosionBacteria(StoreDataServiceRequest<CorrosionBacteriaDto> input) {
		Map<String, CorrosionBacteria> bacteriaMap = new HashMap<>();

		LocalDateTime insertDate = LocalDateTime.now();
		String asset = input.getAsset();
		for (CorrosionBacteriaDto dto : input.getElement()) {
			String analysisPoint = dto.getAnalysisPoint();
			if (analysisPoint == null) {
				logger.warn("Skipping value for analysisPoint null.");
				continue;
			}

			CorrosionBacteria bacteria = bacteriaMap.get(analysisPoint);
			if (bacteria == null) {
				bacteria = new CorrosionBacteria();
			}
			bacteria.setAnalysisPoint(analysisPoint);
			bacteria.setInsertionDate(insertDate);
			String actualArea = null;
			if (dto.getArea() != null) {
				if (dto.getArea().startsWith("COVA")) {
					int index = dto.getArea().indexOf("-");
					if (index < 0 || dto.getArea().length() <= index + 1) {
						logger.warn("Receved erroneous PLANT area value from corrosion bacteria "
								+ dto.getAnalysisPoint() + ": " + dto.getArea());
						messageService.sendNotificationError(
								"Ricevuta da integrazione PI, per il punto d'analisi " + dto.getAnalysisPoint()
										+ " un'area non valida:  " + dto.getArea(),
								"AssetIntegrity integrazione analisi batteriche: area invalida",
								env.getProperty("spring.rabbitmq.notification.recipients"));

					} else {
						actualArea = dto.getArea().substring(index + 1);
					}
				} else if (dto.getArea().startsWith("AP")) {
					actualArea = dto.getArea();
				} else if (dto.getArea().equals("NA")) {
					actualArea = "NA";
				}
			} else {
				logger.error("Receved a null PLANT area value from corrosion bacteria " + dto.getAnalysisPoint());
				messageService.sendNotificationError(
						"Ricevuta da integrazione PI un'area nulla per il punto d'analisi " + dto.getAnalysisPoint(),
						"AssetIntegrity integrazione analisi batteriche: valore area nullo",
						env.getProperty("spring.rabbitmq.notification.recipients"));
			}
			bacteria.setArea(actualArea);
			bacteria.setProcessingUnit(null);
			bacteria.setProduct(dto.getType());
			bacteria.setUdM(dto.getUdM());
			try {
				bacteria.setCountingEndDate(
						(LocalDateTime) parseDate(dto.getCountingEndDate(), "yyyy-MM-dd'T'HH:mm:ssVV", true));
			} catch (Exception e) {
				logger.warn("Skipping counting date for corrosion bacteria " + analysisPoint);
			}
			bacteria.setAsset(asset);
			switch (dto.getAnalysisName()) {
			case "APB":
				bacteria.setApbValue(dto.getValue() != null ? Float.parseFloat(dto.getValue()) : null);
				break;
			case "SRB":
				bacteria.setSrbValue(dto.getValue() != null ? Float.parseFloat(dto.getValue()) : null);
				break;
			default:
				logger.warn("Skipping value for point " + dto.getAnalysisPoint() + ": analysisName not recognized "
						+ dto.getAnalysisName());
			}
			bacteria.setAsset(input.getAsset());
			bacteriaMap.put(analysisPoint, bacteria);
		}
		assetIntegrityDao.insertCorrosionBacteria(new LinkedList(bacteriaMap.values()));
	}

	/**
	 * Parse CSV file into a list containing each CSV line divided into an array
	 * representing the columns. The maxColumnPosition indicate how many column
	 * of the CSV to take, while the subsequent are ignored.
	 *
	 * @param csvContent
	 * @param maxColumnPosition
	 * @return
	 */
	private List<String[]> parseCsvFile(byte[] csvContent) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(csvContent)));
		List<String[]> parsedFile = new ArrayList();
		try {
			// Skip header line
			String firstLine = reader.readLine();
			logger.debug("throwing away " + firstLine);
			String line = null;

			// Iterate over each line
			while ((line = reader.readLine()) != null) {
				String[] columns = line.split(";", -1);
				logger.debug(Arrays.toString(columns));
				logger.debug(Arrays.toString(Arrays.copyOfRange(columns, 0, columns.length - 1)));
				// Fix for handling possible csv empty line
				parsedFile.add(Arrays.copyOfRange(columns, 0, columns.length - 1));
			}
		} catch (IOException ex) {
			logger.error("Error while parsing csv ", ex);
		}

		return parsedFile;
	}

	@Override
	public void updateCriticalSignalsActivationDates() {
		assetIntegrityDao.updateCriticalSignalsActivationDates();
	}

	@Override
	public void updateRegistroDe(){
		assetIntegrityDao.updateRegistroDe();
	}

	@Override
	@Transactional
	public void insertMoc(RegistroMoc input) {
		if (input == null) {
			logger.error("registro moc was null");
			return;
		}
		mocDao.insertMoc(input);
	}

	@Override
	@Transactional
	public void insertCorrosionCND(StoreDataServiceRequest<CorrosionCNDItem> input) {
		List<CorrosionCND> corrosionCNDs = new LinkedList();
		LocalDateTime insertionDate = LocalDateTime.now();
		for (CorrosionCNDItem item : input.getElement()) {
			CorrosionCND corrosionCND = new CorrosionCND();
			corrosionCND.setArea(item.getArea() != null ? item.getArea() : "Z");
			corrosionCND.setModelName(item.getModelName());
			corrosionCND.setPlantAcronym(item.getPlantAcronym());
			corrosionCND.setComponentName(item.getComponentName());
			corrosionCND.setDateType(item.getDateType());

			corrosionCND.setLastDate((LocalDate) parseDate(item.getLastDate(), "dd-MM-yyyy", false));
			corrosionCND.setNextDate((LocalDate) parseDate(item.getNextDate(), "dd-MM-yyyy", false));
			corrosionCND.setFrequency(item.getFrequency());

			corrosionCND.setAsset(input.getAsset());
			corrosionCND.setInsertionDate(insertionDate);
			corrosionCNDs.add(corrosionCND);
		}
		int batchSize = 1000;
		int insertCount = (corrosionCNDs.size() / batchSize);
		int i = 0;
		assetIntegrityDao.truncateCND();
		for (; i <= insertCount; i++) {
			assetIntegrityDao.insertCorrosionCND(
					corrosionCNDs.subList(i * batchSize, Integer.min(i * batchSize + batchSize, corrosionCNDs.size())));
		}
	}

	@Override
	@Transactional
	public void insertCorrosionCoupon(StoreDataServiceRequest<CorrosionCouponDto> input, String tab) {
		if (input.getElement() != null && !input.getElement().isEmpty()) {
			assetIntegrityDao.truncateCorrosionCoupon(tab);
		}
		List<CorrosionCoupon> data = new LinkedList();
		LocalDateTime insertDate = LocalDateTime.now();
		for (CorrosionCouponDto dto : input.getElement()) {
			CorrosionCoupon coupon = new CorrosionCoupon();
			coupon.setAsset(input.getAsset());
			coupon.setInsertionDate(insertDate);
			coupon.setCode(dto.getCode());
			coupon.setDorsal(dto.getDorsal());
			coupon.setWellArea(dto.getWellArea());
			coupon.setType(dto.getType());
			coupon.setLine(dto.getLine());
			coupon.setTab(tab);
			if (dto.getUmy() != null) {
				try {
					coupon.setUmy(Float.parseFloat(dto.getUmy()));
				} catch (NumberFormatException ex) {
					logger.error("Cannot parse MPY for coupon " + dto.getCode() + " with value: " + dto.getUmy());
				}
			}
			if (dto.getMpy() != null) {
				try {
					coupon.setMpy(Float.parseFloat(dto.getMpy()));
				} catch (NumberFormatException ex) {
					logger.error("Cannot parse MPY for coupon " + dto.getCode() + " with value: " + dto.getMpy());
				}
			}
			coupon.setLastDate((LocalDate) parseDate(dto.getLastDate(), "dd-MM-yyyy", false));
			coupon.setNextDate((LocalDate) parseDate(dto.getNextDate(), "dd-MM-yyyy", false));
			if (dto.getArea() != null && dto.getArea().toUpperCase().startsWith("AREA")) {
				int index = dto.getArea().indexOf(" ");
				if (index != -1 && (index + 1) < dto.getArea().length()) {
					coupon.setArea(dto.getArea().substring(index + 1));
				} else {
					logger.error("Rececived erroneous area corrosion coupon with code " + dto.getCode() + " and area "
							+ dto.getArea());
				}
			} else {
				if (dto.getArea() != null) {
					coupon.setArea(dto.getArea());
				} else {
					messageService.sendNotificationError(
							"Ricevuta da integrazione PI un'area nulla per il coupon " + dto.getCode(),
							"AssetIntegrity integrazione corrosion coupon: valore area nullo",
							env.getProperty("spring.rabbitmq.notification.recipients"));
				}
			}
			data.add(coupon);
		}
		if (!data.isEmpty()) {
			assetIntegrityDao.insertCorrosionCoupon(data);
		}
	}

	@Override
	@Transactional
	public void insertCorrosionProtection(StoreDataServiceRequest<CorrosionProtectionDto> input) {
		if (input.getElement() != null && !input.getElement().isEmpty()) {
			assetIntegrityDao.truncateTable("CORROSION_PROTECTION");
		}
		List<CorrosionProtection> data = new LinkedList();
		LocalDateTime insertDate = LocalDateTime.now();
		for (CorrosionProtectionDto dto : input.getElement()) {
			CorrosionProtection protection = new CorrosionProtection();
			protection.setAsset(input.getAsset());
			protection.setInsertionDate(insertDate);
			protection.setDescription(dto.getDescription());
			protection.setDorsal(dto.getDorsal());
			protection.setSection(dto.getSection());
			protection.setTag(dto.getTag());
			protection.setExternalConduitOff(parseFloatValue(dto.getExternalConduitOff()));
			protection.setExternalConduitOn(parseFloatValue(dto.getExternalConduitOn()));
			protection.setNotProtectedSideOff(parseFloatValue(dto.getNotProtectedSideOff()));
			protection.setNotProtectedSideOn(parseFloatValue(dto.getNotProtectedSideOn()));
			protection.setProtectedSideOff(parseFloatValue(dto.getProtectedSideOff()));
			protection.setProtectedSideOn(parseFloatValue(dto.getProtectedSideOn()));
			protection.setTuebOff(parseFloatValue(dto.getTuebOff()));
			protection.setTuebOn(parseFloatValue(dto.getTuebOn()));
			protection.setLastDate((LocalDate) parseDate(dto.getLastDate(), "dd-MM-yyyy", false));
			protection.setNextDate((LocalDate) parseDate(dto.getNextDate(), "dd-MM-yyyy", false));
			protection.setArea(dto.getSection());
			data.add(protection);
		}
		if (!data.isEmpty()) {
			assetIntegrityDao.insertCorrosionProtection(data);
		}
	}

	public Float parseFloatValue(String value) {
		if (value != null) {
			try {
				return Float.parseFloat(value);
			} catch (NumberFormatException ex) {
				logger.error("Cannot parse value for corrosionProtection " + value);
			}
		}
		return null;
	}

	@Override
	@Transactional
	public void insertEVPMS(StoreDataServiceEVPMS input) {
		if (input == null) {
			return;
		}

		LocalDateTime insertDate = LocalDateTime.now();

		// INSERT STATIONS
		if (input.getStations() != null && !input.getStations().isEmpty()) {
			List<EVPMSStation> data = new LinkedList();
			assetIntegrityDao.truncateTable("EVPMS_STATIONS");

			for (EVPMSStationDto dto : input.getStations()) {
				if (dto.getAcPresence() == null || dto.getAcquisition() == null || dto.getDejitter() == null
						|| dto.getGps() == null || dto.getGsm() == null || dto.getIntrusion() == null
						|| dto.getUsbConnection() == null) {
					messageService.sendNotificationError(
							"Ricevuto un valore di diagnostica nullo per la stazione EVPMS " + dto.getIDStation(),
							"AssetIntegrity integrazione EVPMS: valore diagnostica null per la stazione " + dto.getStationName() + " nell'area " + dto.getWellArea(),
							env.getProperty("spring.rabbitmq.notification.recipients"));
				}

				EVPMSStation station = new EVPMSStation();
				station.setAsset(input.getAsset());
				station.setInsertionDate(insertDate);
				station.setAcPresence(dto.getAcPresence());
				station.setAcquisition(dto.getAcquisition());
				station.setArea(dto.getWellArea());
				station.setDejitter(dto.getDejitter());
				station.setDorsal(dto.getDorsal());
				station.setGps(dto.getGps());
				station.setGsm(dto.getGsm());
				station.setIntrusion(dto.getIntrusion());
				station.setStationId(dto.getIDStation());
				station.setStationName(dto.getStationName());
				station.setUsbConnection(dto.getUsbConnection());
				data.add(station);
			}
			assetIntegrityDao.saveEVPMSStations(data);
		}

		// INSERT ALERTS
		if (input.getAlerts() != null && !input.getAlerts().isEmpty()) {
			List<EVPMSAlert> data = new LinkedList();

			for (EVPMSAlertDto dto : input.getAlerts()) {
				if (dto.getAlertID() == null || dto.getAlertDate() == null) {
					logger.warn("Received an empty alertId/date - alertId: " + dto.getAlertID() + ", alertDate:"
							+ dto.getAlertDate() + ", station:" + dto.getIDStation());
				}
				EVPMSAlert alert = new EVPMSAlert();
				alert.setAsset(input.getAsset());
				alert.setInsertionDate(insertDate);
				alert.setAlertDate((LocalDateTime) parseDate(dto.getAlertDate(), "dd-MM-yyyy HH:mm:ss", true));
				alert.getAlertKey().setAlertID(dto.getAlertID());
				alert.setAlertType(dto.getAlertType());
				alert.setChainage(dto.getChainage());
				alert.setIDStation(dto.getIDStation());
				alert.setLatitude(dto.getLatitude());
				alert.setLongitude(dto.getLongitude());
				data.add(alert);
			}
			assetIntegrityDao.saveEVPMSAlerts(data);
		}
	}

	@Override
	@Transactional
	public void insertCorrosionPigging(StoreDataServiceRequest<CorrosionPiggingDto> input) {
		if (input.getElement() == null) {
			return;
		}

		if (input.getElement() != null && !input.getElement().isEmpty()) {
			assetIntegrityDao.truncateTable("CORROSION_PIGGING");
		}
		LocalDateTime insertDate = LocalDateTime.now();
		for (CorrosionPiggingDto dto : input.getElement()) {
			List<CorrosionPigging> data = new LinkedList();
			for (Feature feature : dto.getFeatures()) {
				CorrosionPigging pigging = new CorrosionPigging();
				pigging.setAsset(input.getAsset());
				pigging.setInsertionDate(insertDate);
				pigging.setDorsal(dto.getDorsal());
				if (feature.getErf() != null) {
					try {
						pigging.setErf(Float.parseFloat(feature.getErf()));
					} catch (NumberFormatException ex) {
						logger.error("Cannot parse ERF for feature " + feature.getDescrizione() + " with value: "
								+ feature.getErf());
					}
				}
				if (feature.getKp() != null) {
					try {
						pigging.setKp(Float.parseFloat(feature.getKp()));
					} catch (NumberFormatException ex) {
						logger.error("Cannot parse KP for feature " + feature.getDescrizione() + " with value: "
								+ feature.getKp());
					}
				}
				pigging.setFeature(feature.getDescrizione());
				pigging.setSection(dto.getSection());
				pigging.setTechnicalSite(dto.getTechnicalSite());
				pigging.setDescription(dto.getDescription());
				pigging.setModel(dto.getModel());
				pigging.setLastDate((LocalDate) parseDate(dto.getLastDate(), "dd-MM-yyyy", false));
				pigging.setNextDate((LocalDate) parseDate(dto.getNextDate(), "dd-MM-yyyy", false));
				pigging.setArea(dto.getSection());
				data.add(pigging);
			}
			if (!data.isEmpty()) {
				assetIntegrityDao.insertCorrosionPigging(data);
			}
		}
	}

	@Override
	@Transactional
	public void insertJacketedPipes(StoreDataServiceRequest<JacketedPipesDto> input) {
		if (input.getElement() == null) {
			return;
		}

		LocalDateTime insertDate = LocalDateTime.now();
		List<JacketedPipes> data = new LinkedList<>();
		List<BadJacketedPipes> nullElements = new LinkedList<>();
		for (JacketedPipesDto dto : input.getElement()) {
			if (dto.getBadActive() == null) {
				nullElements.add(generateBadJacketedPipe(dto, input.getAsset(), insertDate));
				continue;
			}
			data.add(generateJacketedPipe(dto, input.getAsset(), insertDate));
		}

		if (!data.isEmpty()) {
			assetIntegrityDao.truncateTable("JACKETED_PIPES");
			assetIntegrityDao.insertJackedPipes(data);
		}

		if (!nullElements.isEmpty()) {
			// HIDING SCHEDULED JACKETED PIPES -> delete this code when unhiding
			StringBuilder message = new StringBuilder(
					"Ricevuto un valore di BadActive nullo per i seguenti tag: <br> ");
			nullElements.forEach((BadJacketedPipes el) -> message.append(el.getTag()).append("<br>"));
			messageService.sendNotificationError(message.toString(),
					"AssetIntegrity integrazione JacketedPipes: valore BadActive null",
					env.getProperty("spring.rabbitmq.notification.recipients"));
			
			// HIDING SCHEDULED JACKETED PIPES -> uncomment this code when unhiding
			//assetIntegrityDao.truncateTable("BAD_JACKETED_PIPES");
			//assetIntegrityDao.saveBadJackedPipes(nullElements);
		}
	}

	private JacketedPipes generateJacketedPipe(JacketedPipesDto dto, String asset, LocalDateTime insertDate){
		JacketedPipes pipe = new JacketedPipes();
		pipe.setAsset(asset);
		pipe.setInsertionDate(insertDate);
		pipe.setArea(dto.getArea());
		pipe.setDescription(dto.getDescription());
		pipe.setTag(dto.getName());
		pipe.setBadActive(dto.getBadActive());
		pipe.setAlarm(dto.getAlarm());

		if (dto.getBadActive() != null && dto.getBadActive() == 0) {
			pipe.setSubArea(dto.getSubArea());
			pipe.setDeltaP(dto.getDeltaP());
		}

		return pipe;
	}

	private BadJacketedPipes generateBadJacketedPipe(JacketedPipesDto dto, String asset, LocalDateTime insertDate){
		BadJacketedPipes pipe = new BadJacketedPipes();
		pipe.setAsset(asset);
		pipe.setInsertionDate(insertDate);
		pipe.setArea(dto.getArea());
		pipe.setDescription(dto.getDescription());
		pipe.setTag(dto.getName());
		pipe.setBadActive(dto.getBadActive());
		pipe.setAlarm(dto.getAlarm());

		if (dto.getBadActive() != null && dto.getBadActive() == 0) {
			pipe.setSubArea(dto.getSubArea());
			pipe.setDeltaP(dto.getDeltaP());
		}

		return pipe;
	}

	@Override
	public void sendBadJackedPipesMail(String asset){

		List<BadJacketedPipes> nullElements = assetIntegrityDao.getBadJackedPipes(asset);

		StringBuilder message = new StringBuilder(
				"Ricevuto un valore di BadActive nullo per i seguenti tag: <br> ");
		nullElements.forEach((BadJacketedPipes el) -> message.append(el.getTag()).append("<br>"));
		messageService.sendNotificationError(message.toString(),
				"AssetIntegrity integrazione JacketedPipes: valore BadActive null",
				env.getProperty("spring.rabbitmq.notification.recipients"));
	}

	public Temporal parseDate(String date, String pattern, boolean hasTime) {
		if (date == null || pattern == null) {
			return null;
		}
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
			if (hasTime) {
				return LocalDateTime.parse(date, formatter);
			} else {
				return LocalDate.parse(date, formatter);
			}
		} catch (DateTimeParseException ex) {
			logger.error("Cannot parse date " + date, ex);
			return null;
		}
	}

	public void notifyWs(String asset, Object param) {
		String requestJson = "{\"asset\":\"" + asset + "\", \"event\":\""
				+ env.getProperty("spring.event.type.criticalsignals") + "\", \"keyname\":\"" + param + "\"}";

		sendToWss(requestJson);
	}

	public void notifyWsSimple(String asset, String event) {
		String requestJson = "{\"asset\":\"" + asset + "\", \"event\":\"" + event + "\"}";

		sendToWss(requestJson);
	}

	private void sendToWss(String requestJson) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

		logger.info("Notify web server socket with payload: {}", requestJson);

		String wssUrls = env.getProperty("spring.ws.urls");
		logger.info(wssUrls);
		String[] wsUrlArr = wssUrls.split("\\|");

		for (String wsUrl : wsUrlArr) {
			logger.info("Notify web server socket to: {}", wsUrl);
			ResponseEntity<Esito> resultEntity = restTemplate.exchange(wsUrl, HttpMethod.POST, entity, Esito.class);
			if (logger.isDebugEnabled()) {
				logger.debug(resultEntity.toString());
			}
		}
	}

	@Override
	public void insertEWP(StoreDataServiceRequest<ewpBarrierPanel> input) {
		if (input.getElement() == null || input.getElement().isEmpty()) {
			logger.warn("no elements in insertEWP");
			return;
		}

		LocalDateTime insertDate = LocalDateTime.now();
		for (ewpBarrierPanel dto : input.getElement()) {
			EWP workPermit = new EWP();
			workPermit.setAsset(input.getAsset());
			workPermit.setInsertionDate(insertDate);
			workPermit.setAppaltatore(dto.getAppaltatore());
			workPermit.setAutorizzanteAnalisiDiRischio(dto.getAutorizzanteAnalisiDiRischio());

			workPermit.setDescrizioneLavoro(dto.getDescrizioneLavoro());
			workPermit.setImpresaEsecutrice(dto.getImpresaEsecutrice());
			workPermit.setIndicazioneSCE(dto.getIndicazioneSCE());
			workPermit.setIndicazioneTipologiaAttivita(dto.getIndicazioneTipologiaAttivita());
			workPermit.setNumeroEWP(dto.getNumeroEWP());
			workPermit.setPrepostoAiLavori(dto.getPrepostoAiLavori());
			workPermit.setRichiedente(dto.getRichiedente());
			workPermit.setSorvegliante(dto.getSorvegliante());
			workPermit.setSottoArea("|"+dto.getSottoArea()+"|");
			workPermit.setUnitaAutorizzante(dto.getUnitaAutorizzante());
			workPermit.setUnitaRichiedente(dto.getUnitRichiedente());
			workPermit.setDescrizioneSCE(dto.getDescrizioneSCE());
			workPermit.setSedeTecnica(dto.getSedeTecnica());
			workPermit.setStatus(dto.getStatus().name());
			if(dto.getSort() != null && !"".equals(dto.getSort())){
				workPermit.setTags("|"+dto.getSort()+"|");
			}

			if (dto.getDataApertura() != null) {
				workPermit.setDataApertura(parsedDate(dto.getDataApertura(), "dd-MM-yyyy HH:mm:ss", true));
			}

			if (dto.getDataChiusura() != null) {
				workPermit.setDataChiusura(parsedDate(dto.getDataChiusura(), "dd-MM-yyyy HH:mm:ss", true));
			}

			if (dto.getDataOraInizioAutorizzazione() != null) {
				workPermit.setDataOraInizioAutorizzazione(
						parsedDate(dto.getDataOraInizioAutorizzazione(), "dd-MM-yyyy HH:mm:ss", true));
			}

			if (dto.getDataOraScadenzaAutorizzazione() != null) {
				workPermit.setDataOraScadenzaAutorizzazione(
						parsedDate(dto.getDataOraScadenzaAutorizzazione(), "dd-MM-yyyy HH:mm:ss", true));
			}

			if (dto.getDataScadenza() != null) {
				workPermit.setDataScadenza(parsedDate(dto.getDataScadenza(), "dd-MM-yyyy", false));
			}

			checkStateCoherence(workPermit);

			assetIntegrityDao.insertEWP(workPermit);

			notifyWsSimple(input.getAsset(), env.getProperty("spring.event.type.ewp"));
		}
	}

	@Override
	public void insertRegistroDe(DERegistry input) {
		if(input != null && input.getHashKey() != null){
			assetIntegrityDao.insertDE(input);
		} else {
			logger.error("input non valido per insertRegistroDe");
		}
	}

	private void checkStateCoherence(EWP ewp) {
		String nEwp = ewp.getNumeroEWP();
		String newStatus = ewp.getStatus();
		String prevStatus = assetIntegrityDao.getPreviousEWPState(nEwp);

		if (prevStatus != null && isIncoherent(prevStatus, newStatus)) {
			StringBuilder sb = new StringBuilder();
			sb.append("Abbiamo ricevuto un permesso di lavoro con stato non coerente con quanto presente a DB: ");
			sb.append("<br/>Vecchio stato -> ").append(prevStatus);
			sb.append("<br/>Nuovo stato -> ").append(newStatus);
			sb.append("<br/>EWP ricevuto: ").append(ewp.toString());

			logger.warn("Passaggio di stato incoerente per ewp: {} -> {}, numero {}", prevStatus, newStatus, nEwp);
			messageService.sendNotificationError(sb.toString(), "AssetIntegrity integrazione EWP: Stati incoerenti",
					env.getProperty("spring.rabbitmq.notification.recipients"));
		}

	}

	private boolean isIncoherent(String oldS, String newS) {
		// check != null
		if (oldS == null || newS == null)
			return true;

		// prendo lista passaggi validi da mappa statica
		List<String> statiConsentiti = EWPConstants.getEwpStateMap().get(oldS);
		if (statiConsentiti == null || statiConsentiti.isEmpty())
			return true;

		// newS presente in lista? se no, passaggio non valido
		boolean found = statiConsentiti.stream().anyMatch(value -> value.equalsIgnoreCase(newS));
		if (!found)
			return true;

		// tutti controlli passati, passaggio valido
		return false;
	}

	private Date parsedDate(String source, String pattern, boolean doConvertion) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if (source == null || source == "")
			return null;
		try {
			Date dateUTCPlus1 = sdf.parse(source);

			// TODO trovare soluzione migliore...
			Calendar calendar = Calendar.getInstance();
			int rawOff = TimeZone.getTimeZone("CET").getRawOffset();
			calendar.setTimeInMillis(dateUTCPlus1.getTime() - rawOff);
			Date currentDate = calendar.getTime();

			logger.info("{} -> {}", source, currentDate);

			return currentDate;
		} catch (Exception e) {
			logger.error("could not parse " + source, e);
		}
		return null;
	}
}
