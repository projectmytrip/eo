package com.eni.ioc.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eni.ioc.common.CovaFlaringAD;
import com.eni.ioc.common.CovaFlaringEvent;
import com.eni.ioc.common.CovaSo2Average;
import com.eni.ioc.common.CovaSo2Impact;
import com.eni.ioc.common.CovaSo2Prediction;
import com.eni.ioc.common.CovaRootCause;
import com.eni.ioc.dao.DaoUtils;
import com.eni.ioc.emission.dto.soredataserviceanomaly.StoreDataServiceAnomalyRequest;
import com.eni.ioc.emission.dto.soredataserviceaverage.StoreDataServiceAverageRequest;
import com.eni.ioc.emission.dto.soredataserviceflaringevent.StoreDataServiceFlaringEventsRequest;
import com.eni.ioc.emission.dto.soredataserviceimpacts.StoreDataServiceImpactsRequest;
import com.eni.ioc.emission.dto.soredataservicerootcause.StoreDataServiceRootCause;
import com.eni.ioc.emission.dto.storedataservicepredictiveprobabilities.StoreDataServiceProbabilitiesRequest;
import com.eni.ioc.properties.util.ApplicationProperties;

public class ServiceUtils {

	private static final ZoneId CLOUDERA_ZONE_ID = ZoneId.of("UTC");
	private static final String SO2_KEYNAME = "SO2";
	
	private static final Map<String, String> TABLENAMEPRED;
	
	static {
		TABLENAMEPRED = new HashMap<>();
		TABLENAMEPRED.put("E13", "pmc_l1_cova_e13_predictive_output");
		TABLENAMEPRED.put("E14", "pmc_l1_cova_e14_predictive_output");
		TABLENAMEPRED.put("E15", "pmc_l1_cova_e15_predictive_output");
    }
	
	private static final Map<String, String> TABLENAMEAD;
	
	static {
		TABLENAMEAD = new HashMap<>();
		TABLENAMEAD.put("E13", "pmc_l1_cova_e13_anomaly_tag_event_register");
		TABLENAMEAD.put("E14", "pmc_l1_cova_e14_anomaly_tag_event_register");
		TABLENAMEAD.put("E15", "pmc_l1_cova_e15_anomaly_tag_event_register");
    }
	
	private static final Map<String, String> TABLENAMEEVENTS;
	
	static {
		TABLENAMEEVENTS = new HashMap<>();
		TABLENAMEEVENTS.put("E13", "pmc_l1_cova_e13_anomaly_flaring_register");
		TABLENAMEEVENTS.put("E14", "pmc_l1_cova_e14_anomaly_flaring_register");
		TABLENAMEEVENTS.put("E15", "pmc_l1_cova_e15_anomaly_flaring_register");
    }

	private static final Logger logger = LoggerFactory.getLogger(ServiceUtils.class);
	
	private ServiceUtils() {
	}

	public static StoreDataServiceProbabilitiesRequest retrieveSO2PredictiveProbabilities() {
		
		int historyDepth = Integer.parseInt(ApplicationProperties.getInstance().getHistoryDepthHoursPredictive());
		
		LocalDateTime localDateTimeEnd = LocalDateTime.now();
		LocalDateTime localDateTimeStart = localDateTimeEnd.plusHours(historyDepth);

		logger.debug("I'm converting start=" + localDateTimeStart + ", end=" + localDateTimeEnd
				+ " with and historyDepth=" + historyDepth);
		Date end = Date.from(localDateTimeEnd.atZone(CLOUDERA_ZONE_ID).toInstant());
		Date start = Date.from(localDateTimeStart.atZone(CLOUDERA_ZONE_ID).toInstant());
		
		List<CovaSo2Prediction> predictiveProbabilities = DaoUtils.retrieveSo2PredictiveProbabilities(start, end);
		
		return Converter.convertToPredictiveProbabilitiesRequest(predictiveProbabilities, SO2_KEYNAME);
	}

	public static StoreDataServiceImpactsRequest retrieveSO2PredictiveImpacts() {
		
		int historyDepth = Integer.parseInt(ApplicationProperties.getInstance().getHistoryDepthHoursPredictive());
		
		LocalDateTime localDateTimeEnd = LocalDateTime.now();
		LocalDateTime localDateTimeStart = localDateTimeEnd.plusHours(historyDepth);

		logger.debug("I'm converting start=" + localDateTimeStart + ", end=" + localDateTimeEnd
				+ " with and historyDepth=" + historyDepth);
		Date end = Date.from(localDateTimeEnd.atZone(CLOUDERA_ZONE_ID).toInstant());
		Date start = Date.from(localDateTimeStart.atZone(CLOUDERA_ZONE_ID).toInstant());
		
		List<CovaSo2Impact> predictiveImpacts = DaoUtils.retrieveSo2PredictiveImpacts(start, end);
		
		return Converter.convertToPredictiveImpactsRequest(predictiveImpacts);
	}

	public static StoreDataServiceAverageRequest retrieveSO2PredictiveAverage() {
		int historyDepth = Integer.parseInt(ApplicationProperties.getInstance().getHistoryDepthHoursAverage());
		
		LocalDateTime localDateTimeEnd = LocalDateTime.now();
		LocalDateTime localDateTimeStart = localDateTimeEnd.plusHours(historyDepth);

		logger.debug("I'm converting start=" + localDateTimeStart + ", end=" + localDateTimeEnd
				+ " with and historyDepth=" + historyDepth);
		Date end = Date.from(localDateTimeEnd.atZone(CLOUDERA_ZONE_ID).toInstant());
		Date start = Date.from(localDateTimeStart.atZone(CLOUDERA_ZONE_ID).toInstant());
		
		List<CovaSo2Average> predictiveAverages = DaoUtils.retrieveSo2Average(start, end);
		
		return Converter.convertToPredictiveAverageRequest(predictiveAverages);
	} 
	
	public static StoreDataServiceAnomalyRequest retrieveFlaringAnomaly(String keyName) {
		
		int historyDepth = Integer.parseInt(ApplicationProperties.getInstance().getHistoryDepthHoursAnomaly());
		
		LocalDateTime localDateTimeEnd = LocalDateTime.now();
		LocalDateTime localDateTimeStart = localDateTimeEnd.plusHours(historyDepth);

		logger.debug("I'm converting start=" + localDateTimeStart + ", end=" + localDateTimeEnd
				+ " with and historyDepth=" + historyDepth);
		Date end = Date.from(localDateTimeEnd.atZone(CLOUDERA_ZONE_ID).toInstant());
		Date start = Date.from(localDateTimeStart.atZone(CLOUDERA_ZONE_ID).toInstant());
		
		List<CovaFlaringAD> anomaly = DaoUtils.retrieveFlaringAnomaly(start, end,  TABLENAMEAD.get(keyName), keyName);


		return Converter.convertToFlaringAnomalyRequest(anomaly, keyName);
	}
	
	public static StoreDataServiceProbabilitiesRequest retrieveFlaringPredictiveProbabilities(String keyName) {
		
		int historyDepth = Integer.parseInt(ApplicationProperties.getInstance().getHistoryDepthHoursPredictive());
		
		LocalDateTime localDateTimeEnd = LocalDateTime.now();
		LocalDateTime localDateTimeStart = localDateTimeEnd.plusHours(historyDepth);

		logger.debug("I'm converting start=" + localDateTimeStart + ", end=" + localDateTimeEnd
				+ " with and historyDepth=" + historyDepth);
		Date end = Date.from(localDateTimeEnd.atZone(CLOUDERA_ZONE_ID).toInstant());
		Date start = Date.from(localDateTimeStart.atZone(CLOUDERA_ZONE_ID).toInstant());
				
		List<CovaSo2Prediction> predictiveProbabilities = DaoUtils.retrieveFlaringPredictive(start, end,  TABLENAMEPRED.get(keyName), keyName);

		return Converter.convertToPredictiveProbabilitiesRequest(predictiveProbabilities, keyName);
	}

	public static StoreDataServiceFlaringEventsRequest retrieveFlaringEvent(String keyName) {
		int historyDepth = Integer.parseInt(ApplicationProperties.getInstance().getHistoryDepthHoursAnomaly());
		
		LocalDateTime localDateTimeEnd = LocalDateTime.now();
		LocalDateTime localDateTimeStart = localDateTimeEnd.plusHours(historyDepth);

		logger.debug("I'm converting start=" + localDateTimeStart + ", end=" + localDateTimeEnd
				+ " with and historyDepth=" + historyDepth);
		Date end = Date.from(localDateTimeEnd.atZone(CLOUDERA_ZONE_ID).toInstant());
		Date start = Date.from(localDateTimeStart.atZone(CLOUDERA_ZONE_ID).toInstant());
		
		List<CovaFlaringEvent> events = DaoUtils.retrieveFlaringEvent(start, end,  TABLENAMEEVENTS.get(keyName), keyName);

		return Converter.convertToFlaringEventRequest(events, keyName);
	}

	public static StoreDataServiceRootCause retrieveRootCause() {
		int historyDepth = Integer.parseInt(ApplicationProperties.getInstance().getHistoryDepthHoursRoot());
		
		LocalDateTime localDateTimeEnd = LocalDateTime.now();
		LocalDateTime localDateTimeStart = localDateTimeEnd.plusHours(historyDepth);

		logger.debug("I'm converting start=" + localDateTimeStart + ", end=" + localDateTimeEnd
				+ " with and historyDepth=" + historyDepth);
		Date end = Date.from(localDateTimeEnd.atZone(CLOUDERA_ZONE_ID).toInstant());
		Date start = Date.from(localDateTimeStart.atZone(CLOUDERA_ZONE_ID).toInstant());
		
		List<CovaRootCause> tda = DaoUtils.retrieveRootCause(start, end);

		return Converter.convertToTdaRequest(tda);
	}
}
