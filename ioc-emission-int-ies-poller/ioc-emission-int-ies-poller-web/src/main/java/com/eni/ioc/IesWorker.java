package com.eni.ioc;

import static com.eni.ioc.utils.EventConstants.DAY_EVENT;
import static com.eni.ioc.utils.EventConstants.HOUR_EVENT;
import static com.eni.ioc.utils.EventConstants.MINUTE_EVENT;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eni.ioc.bean.RequestElementsBean;
import com.eni.ioc.pojo.emission.EmissionPojo;
import com.eni.ioc.pojo.flaring.FlaringPojo;
import com.eni.ioc.pojo.history.HistoryPojo;
import com.eni.ioc.pojo.server.export.ServerExportPojo;
import com.eni.ioc.pojo.server.hour.ServerHourPojo;
import com.eni.ioc.pojo.server.minute.ServerMinutePojo;
import com.eni.ioc.pojo.thresholds.ThresholdsPojo;
import com.eni.ioc.properties.util.ApplicationProperties;
import com.eni.ioc.storedataservice.client.StoreDataServiceClientRest;
import com.eni.ioc.storedataservice.client.StoreDataServiceServerRest;
import com.eni.ioc.storedataservice.request.StoreDataServiceRequest;
import com.eni.ioc.storedataservice.request.server.StoreDataServiceServerRequest;

public class IesWorker {

	private static final Logger logger = LoggerFactory.getLogger(IesWorker.class);
	
	private IesWorker() {
	}
	
	public static void doClient() {
		logger.debug("INIT CLIENT WORK");
		retrieveEmissionClient();
		retrieveFlaringClient();
		retrieveHistoryClient();
		logger.debug("CLIENT WORK FINISHED");
	}
	
	public static void doServerMinute() {
		logger.debug("INIT SERVER MINUTE WORK");
		retrieveMinuteServer();
		retrieveHistoryServer();
		logger.debug("SERVER MINUTE WORK FINISHED");
	}
	
	public static void doServerOther() {
		
		retrieveHourServer();
		retrieveDayServer();
	}
	
	private static void retrieveEmissionClient() {
		logger.info("-- Retrieving first actual emission --");
		EmissionPojo emissionPojo = ApplicationHelper.retrieveEmissionFromSource();
		List<ThresholdsPojo> thresholdsPojoEmission = ApplicationHelper.retrieveThresholdsFromSource();
		RequestElementsBean requestElementsBeanEmission = new RequestElementsBean();
		requestElementsBeanEmission.setEmission(emissionPojo);
		requestElementsBeanEmission.setThresholds(thresholdsPojoEmission);
		StoreDataServiceRequest requestEmission = StoreDataServiceClientRest.createRequest(ApplicationProperties.getEmissionSite(), requestElementsBeanEmission, true, false);
		String respEmission = StoreDataServiceClientRest.post(ApplicationProperties.getEmissionPersistenceStoredataserviceEndpoint(),requestEmission);
		logger.info("Emission: " + respEmission);
	}
	
	private static void retrieveFlaringClient() {
		logger.info("-- Retrieving first actual flaring --");
		FlaringPojo flaringPojo = ApplicationHelper.retrieveFlaringFromSource();
		List<ThresholdsPojo> thresholdsPojoFlaring = ApplicationHelper.retrieveThresholdsFromSource();
		RequestElementsBean requestElementsBeanFlaring = new RequestElementsBean();
		requestElementsBeanFlaring.setFlaring(flaringPojo);
		requestElementsBeanFlaring.setThresholds(thresholdsPojoFlaring);
		StoreDataServiceRequest requestFlaring = StoreDataServiceClientRest.createRequest(ApplicationProperties.getEmissionSite(), requestElementsBeanFlaring, true, true);
		String respFlaring = StoreDataServiceClientRest.post(ApplicationProperties.getEmissionPersistenceStoredataserviceEndpoint(),requestFlaring);
		logger.info("Flaring: " + respFlaring);
	}
	
	private static void retrieveHistoryClient() {
		logger.info("-- Retrieving History --");
		Map<String, HistoryPojo> historyPojos = ApplicationHelper.retrieveHistoryFromSource();
		StoreDataServiceClientRest.postHistory(ApplicationProperties.getEmissionSite(), historyPojos, false, false);
	}
	
	private static void retrieveMinuteServer() {
		logger.info("-- Retrieving server minute data --");
		Map<String, ServerMinutePojo> serverMinutePojo = ApplicationHelper.retrieveEmissionServerMinuteFromSource();
		StoreDataServiceServerRequest requestServerMinute = StoreDataServiceServerRest.createMinuteRequest(ApplicationProperties.getEmissionSite(), MINUTE_EVENT, serverMinutePojo, true);
		String respServerMinute = StoreDataServiceServerRest.post(ApplicationProperties.getEmissionPersistenceStoredataserviceServerEndpoint(),requestServerMinute);
		logger.info("Server Minute: " + respServerMinute);
	}
	
	private static void retrieveHistoryServer() {
		logger.info("-- Retrieving server History data --");
		ApplicationHelper.retrieveEmissionServerHistoryFromSourceAndPost();
	}
	
	private static void retrieveHourServer() {
		logger.info("-- Retrieving server hour data --");
		Map<String, ServerHourPojo> serverHourPojo = ApplicationHelper.retrieveEmissionServerHourFromSource();
		StoreDataServiceServerRequest requestServerHour = StoreDataServiceServerRest.createHourRequest(ApplicationProperties.getEmissionSite(), HOUR_EVENT, serverHourPojo, true);
		String respServerHour = StoreDataServiceServerRest.post(ApplicationProperties.getEmissionPersistenceStoredataserviceServerEndpoint(),requestServerHour);
		logger.info("Server Hour: " + respServerHour);
	}
	
	private static void retrieveDayServer() {
		logger.info("-- Retrieving server day data --");
		Map<String, Map<String, ServerExportPojo>> serverDayPojo = ApplicationHelper.retrieveEmissionServerDayFromSource();
		StoreDataServiceServerRequest requestServerDay = StoreDataServiceServerRest.createDayRequest(ApplicationProperties.getEmissionSite(), DAY_EVENT, serverDayPojo, true);
		String respServerDay = StoreDataServiceServerRest.post(ApplicationProperties.getEmissionPersistenceStoredataserviceServerEndpoint(),requestServerDay);
		logger.info("Server Day: " + respServerDay);
	}
}
