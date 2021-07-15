package com.eni.ioc;

import static com.eni.ioc.utils.EventConstants.MINUTE_EVENT;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eni.ioc.bean.HttpClientBean;
import com.eni.ioc.pojo.emission.EmissionPojo;
import com.eni.ioc.pojo.flaring.FlaringPojo;
import com.eni.ioc.pojo.history.HistoryPojo;
import com.eni.ioc.pojo.server.export.ServerExportPojo;
import com.eni.ioc.pojo.server.hour.ServerHourPojo;
import com.eni.ioc.pojo.server.minute.ServerMinutePojo;
import com.eni.ioc.pojo.thresholds.ThresholdsPojo;
import com.eni.ioc.properties.util.ApplicationProperties;
import com.eni.ioc.storedataservice.client.StoreDataServiceServerRest;
import com.eni.ioc.storedataservice.request.server.StoreDataServiceServerRequest;
import com.eni.ioc.utils.EmissionElement;
import com.eni.ioc.utils.EmissionKpiElement;
import com.eni.ioc.utils.FlaringElement;
import com.eni.ioc.utils.FlaringKpiElement;
import com.eni.ioc.utils.HttpClientUtils;
import com.eni.ioc.utils.MapperUtils;

public class ApplicationHelper {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationHelper.class);

	private static final String EMISSION = "Emission";
	private static final String FLARING = "Flaring";
	private static final String JOB_SERVER_MINUTE = "JobServerMinute";
	private static final String JOB_SERVER_HOUR = "JobServerHour";
	private static final String JOB_SERVER_DAY = "JobServerDay";
	
	private ApplicationHelper() {
	}

	public static EmissionPojo retrieveEmissionFromSource() {
		HttpClientBean httpConfig = new HttpClientBean();
		httpConfig.setUsername(ApplicationProperties.getApiIesUsername());
		httpConfig.setPassword(ApplicationProperties.getApiIesPassword());
		httpConfig.setEndpoint(
				ApplicationProperties.getApiIesEmissionEndpoint() + "&site=" + ApplicationProperties.getEmissionSite());
		HttpResponse response = HttpClientUtils.callApiGet(httpConfig, EMISSION);
		String jsonOutput = HttpClientUtils.getOutput(response);
		
		// TODO: mettere qui l'invio 
		
		return MapperUtils.<EmissionPojo>convertJsonToPojo(jsonOutput, EmissionPojo.class);
	}

	public static FlaringPojo retrieveFlaringFromSource() {
		HttpClientBean httpConfig = new HttpClientBean();
		httpConfig.setUsername(ApplicationProperties.getApiIesUsername());
		httpConfig.setPassword(ApplicationProperties.getApiIesPassword());
		httpConfig.setEndpoint(
				ApplicationProperties.getApiIesFlaringEndpoint() + "&site=" + ApplicationProperties.getEmissionSite());
		HttpResponse response = HttpClientUtils.callApiGet(httpConfig, FLARING);
		String jsonOutput = HttpClientUtils.getOutput(response);
		return MapperUtils.<FlaringPojo>convertJsonToPojo(jsonOutput, FlaringPojo.class);
	}

	public static List<ThresholdsPojo> retrieveThresholdsFromSource() {
		HttpClientBean httpConfig = new HttpClientBean();
		httpConfig.setUsername(ApplicationProperties.getApiIesUsername());
		httpConfig.setPassword(ApplicationProperties.getApiIesPassword());
		httpConfig.setEndpoint(ApplicationProperties.getApiIesThresholdsEndpoint() + "?site="
				+ ApplicationProperties.getEmissionSite());
		HttpResponse response = HttpClientUtils.callApiGet(httpConfig, EMISSION);
		String jsonOutput = HttpClientUtils.getOutput(response);
		return MapperUtils.convertJsonArrayToThresholdsPojo(jsonOutput);
	}

	/**
	 * Recupare lo storico di tutte le stazioni
	 * 
	 * @return
	 */
	public static Map<String, HistoryPojo> retrieveHistoryFromSource() {

		Map<String, HistoryPojo> historyPojos = new HashMap<>();

		Calendar now = Calendar.getInstance();
		Calendar nowSO2 = Calendar.getInstance();
		long timeMilliNow = now.getTimeInMillis();
		now.add(Calendar.MINUTE, -ApplicationProperties.getEmissionHistoryParamter());
		nowSO2.add(Calendar.MINUTE, -ApplicationProperties.getEmissionSO2HistoryParamter());

		// now.add(Calendar.DAY_OF_YEAR, -1);
		long timeMilliHalfPast = now.getTimeInMillis();
		long timeMilliSO2 = nowSO2.getTimeInMillis();


		
		for (EmissionElement e : EmissionElement.values()) {
			
			if(!e.equals(EmissionElement.EXX)) {

				HttpClientBean httpConfig = new HttpClientBean();
				httpConfig.setUsername(ApplicationProperties.getApiIesUsername());
				httpConfig.setPassword(ApplicationProperties.getApiIesPassword());
				

				httpConfig.setEndpoint(ApplicationProperties.getApiIesHistoryEndpoint() + "&site="
						+ ApplicationProperties.getEmissionSite() + "&from=" + timeMilliHalfPast + "&to=" + timeMilliNow
						+ "&stazione=" + e.name());
				
				HttpResponse response = HttpClientUtils.callApiGet(httpConfig);
				String jsonOutput = HttpClientUtils.getOutput(response);
	
				historyPojos.put(e.name(), MapperUtils.<HistoryPojo>convertJsonToPojo(jsonOutput, HistoryPojo.class));
			} else {
				
				Calendar start = Calendar.getInstance();
				start.setTime(nowSO2.getTime());
				

				
				Calendar end = Calendar.getInstance();
				end.setTime(nowSO2.getTime());
				end.add(Calendar.MINUTE, ApplicationProperties.getEmissionHistoryParamter());
				
				long timeMilliSO2Start =  start.getTimeInMillis();
				long timeMilliSO2End = end.getTimeInMillis();
				while(timeMilliSO2End <= timeMilliNow) {
					
					HttpClientBean httpConfig = new HttpClientBean();
					httpConfig.setUsername(ApplicationProperties.getApiIesUsername());
					httpConfig.setPassword(ApplicationProperties.getApiIesPassword());					
					
					httpConfig.setEndpoint(ApplicationProperties.getApiIesHistoryEndpoint() + "&site="
							+ ApplicationProperties.getEmissionSite() + "&from=" + timeMilliSO2Start + "&to=" + timeMilliSO2End
							+ "&stazione=" + e.name());
					
					logger.debug("Calling ies for " + EmissionElement.EXX.toString() + " from " + timeMilliSO2Start + " to " + timeMilliSO2End);
					HttpResponse response = HttpClientUtils.callApiGet(httpConfig);
					String jsonOutput = HttpClientUtils.getOutput(response);
		
					historyPojos.put(e.name(), MapperUtils.<HistoryPojo>convertJsonToPojo(jsonOutput, HistoryPojo.class));
					
					start.add(Calendar.MINUTE, ApplicationProperties.getEmissionHistoryParamter());
					end.add(Calendar.MINUTE, ApplicationProperties.getEmissionHistoryParamter());

					timeMilliSO2Start =  start.getTimeInMillis();
					timeMilliSO2End = end.getTimeInMillis();
				}
			}
		}

		for (FlaringElement f : FlaringElement.values()) {

			HttpClientBean httpConfig = new HttpClientBean();
			httpConfig.setUsername(ApplicationProperties.getApiIesUsername());
			httpConfig.setPassword(ApplicationProperties.getApiIesPassword());
			
			httpConfig.setEndpoint(ApplicationProperties.getApiIesHistoryEndpoint() + "&site="
					+ ApplicationProperties.getEmissionSite() + "&from=" + timeMilliSO2 + "&to=" + timeMilliNow
					+ "&stazione=" + f.name());
			HttpResponse response = HttpClientUtils.callApiGet(httpConfig);
			String jsonOutput = HttpClientUtils.getOutput(response);

			historyPojos.put(f.name(), MapperUtils.<HistoryPojo>convertJsonToPojo(jsonOutput, HistoryPojo.class));
		}

		return historyPojos;
	}

	public static Map<String, ServerMinutePojo> retrieveEmissionServerMinuteFromSource() {

		Map<String, ServerMinutePojo> pojos = new HashMap<>();

		for (EmissionElement e : EmissionElement.values()) {

			HttpClientBean httpConfig = new HttpClientBean();
			httpConfig.setUsername(ApplicationProperties.getApiIesUsername());
			httpConfig.setPassword(ApplicationProperties.getApiIesPassword());
			httpConfig.setEndpoint(ApplicationProperties.getApiIesServerMinuteEndpoint() + e.name());
			HttpResponse response = HttpClientUtils.callApiGet(httpConfig, JOB_SERVER_MINUTE);
			String jsonOutput = HttpClientUtils.getOutput(response);
			ServerMinutePojo pojo = MapperUtils.<ServerMinutePojo>convertJsonToPojo(jsonOutput, ServerMinutePojo.class);
			if (pojo != null) {
				pojos.put(e.name(), pojo);
			}
		}

		for (FlaringElement f : FlaringElement.values()) {

			HttpClientBean httpConfig = new HttpClientBean();
			httpConfig.setUsername(ApplicationProperties.getApiIesUsername());
			httpConfig.setPassword(ApplicationProperties.getApiIesPassword());
			httpConfig.setEndpoint(ApplicationProperties.getApiIesServerMinuteEndpoint() + f.name());
			HttpResponse response = HttpClientUtils.callApiGet(httpConfig, JOB_SERVER_MINUTE);
			String jsonOutput = HttpClientUtils.getOutput(response);
			
			for (FlaringKpiElement ke: FlaringKpiElement.values()) {				
				jsonOutput = jsonOutput.replaceAll(ke.getRealName(), ke.name());
			}
			
			ServerMinutePojo pojo = MapperUtils.<ServerMinutePojo>convertJsonToPojo(jsonOutput, ServerMinutePojo.class);
			if (pojo != null) {
				pojos.put(f.name(), pojo);
			}
		}

		return pojos;
	}

	public static Map<String, ServerHourPojo> retrieveEmissionServerHourFromSource() {

		Map<String, ServerHourPojo> pojos = new HashMap<>();

		for (EmissionElement e : EmissionElement.values()) {

			HttpClientBean httpConfig = new HttpClientBean();
			httpConfig.setUsername(ApplicationProperties.getApiIesUsername());
			httpConfig.setPassword(ApplicationProperties.getApiIesPassword());
			httpConfig.setEndpoint(ApplicationProperties.getApiIesServerHourEndpoint() + e.name());
			HttpResponse response = HttpClientUtils.callApiGet(httpConfig, JOB_SERVER_HOUR);
			String jsonOutput = HttpClientUtils.getOutput(response);
			ServerHourPojo pojo = MapperUtils.<ServerHourPojo>convertJsonToPojo(jsonOutput, ServerHourPojo.class);
			if (pojo != null) {
				pojos.put(e.name(), pojo);
			}
		}

		for (FlaringElement f : FlaringElement.values()) {

			HttpClientBean httpConfig = new HttpClientBean();
			httpConfig.setUsername(ApplicationProperties.getApiIesUsername());
			httpConfig.setPassword(ApplicationProperties.getApiIesPassword());
			httpConfig.setEndpoint(ApplicationProperties.getApiIesServerHourEndpoint() + f.name());
			HttpResponse response = HttpClientUtils.callApiGet(httpConfig, JOB_SERVER_HOUR);
			String jsonOutput = HttpClientUtils.getOutput(response);
			
			for (FlaringKpiElement ke: FlaringKpiElement.values()) {				
				jsonOutput = jsonOutput.replaceAll(ke.getRealName(), ke.name());
			}
			
			ServerHourPojo pojo = MapperUtils.<ServerHourPojo>convertJsonToPojo(jsonOutput, ServerHourPojo.class);
			if (pojo != null) {
				pojos.put(f.name(), pojo);
			}
		}

		return pojos;
	}

	public static Map<String, Map<String, ServerExportPojo>> retrieveEmissionServerDayFromSource() {

		Map<String, Map<String, ServerExportPojo>> pojos = new HashMap<>();

		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR, -24);
		long timeMilliNow = now.getTimeInMillis();
		now.add(Calendar.HOUR, -24);
		long timeMilliHalfPast = now.getTimeInMillis();

		for (EmissionElement e : EmissionElement.values()) {
			Map<String, ServerExportPojo> internalMap = new HashMap<>();
			for (EmissionKpiElement ke : EmissionKpiElement.values()) {
				HttpClientBean httpConfig = new HttpClientBean();
				httpConfig.setUsername(ApplicationProperties.getApiIesUsername());
				httpConfig.setPassword(ApplicationProperties.getApiIesPassword());
				httpConfig.setEndpoint(
						ApplicationProperties.getApiIesServerDayEndpoint().replace("<STATION_KEY>", e.name())
								.replace("<KPI_KEY>", ke.name()).replace("<FROM_MILLIS>", String.valueOf(timeMilliHalfPast))
								.replace("<TO_MILLIS>", String.valueOf(timeMilliNow)));
				HttpResponse response = HttpClientUtils.callApiGet(httpConfig, JOB_SERVER_DAY);
				String jsonOutput = HttpClientUtils.getOutput(response);
				ServerExportPojo internalPojo = MapperUtils.<ServerExportPojo>convertJsonToPojo(jsonOutput, ServerExportPojo.class);
				if (internalPojo != null) {
					internalMap.put(ke.name(), internalPojo);
				}
			}
			if (!internalMap.isEmpty()) {
				pojos.put(e.name(), internalMap);
			}
		}

		for (FlaringElement f : FlaringElement.values()) {
			Map<String, ServerExportPojo> internalMap = new HashMap<>();
			for (FlaringKpiElement kf : FlaringKpiElement.values()) {
				HttpClientBean httpConfig = new HttpClientBean();
				httpConfig.setUsername(ApplicationProperties.getApiIesUsername());
				httpConfig.setPassword(ApplicationProperties.getApiIesPassword());
				httpConfig.setEndpoint(
						ApplicationProperties.getApiIesServerDayEndpoint().replace("<STATION_KEY>", f.name())
								.replace("<KPI_KEY>", kf.getRealName()).replace("<FROM_MILLIS>", String.valueOf(timeMilliHalfPast))
								.replace("<TO_MILLIS>", String.valueOf(timeMilliNow)));
				HttpResponse response = HttpClientUtils.callApiGet(httpConfig, JOB_SERVER_DAY);
				String jsonOutput = HttpClientUtils.getOutput(response);
				ServerExportPojo internalPojo = MapperUtils.<ServerExportPojo>convertJsonToPojo(jsonOutput, ServerExportPojo.class);
				if (internalPojo != null) {
					internalPojo.setParametro(kf.name());
					internalMap.put(kf.name(), internalPojo);
				}
			}
			if (!internalMap.isEmpty()) {
				pojos.put(f.name(), internalMap);
			}
		}

		return pojos;
	}
	
	public static void retrieveEmissionServerHistoryFromSourceAndPost() {

		Calendar now = Calendar.getInstance();
		long timeMilliNow = now.getTimeInMillis();
		now.add(Calendar.HOUR, -24);
		long timeMilliHalfPast = now.getTimeInMillis();

		for (EmissionElement e : EmissionElement.values()) {
			persistEmissionHistoryServer(e.name(), timeMilliHalfPast, timeMilliNow);
		}

		for (FlaringElement f : FlaringElement.values()) {
			persistFlaringHistoryServer(f.name(), timeMilliHalfPast, timeMilliNow);
		}
	}
	
	private static void persistEmissionHistoryServer(String station, long from, long to) {
		List<ServerExportPojo> kpi1 = new ArrayList<>();
		List<ServerExportPojo> kpi2 = new ArrayList<>();
		List<ServerExportPojo> kpi3 = new ArrayList<>();
		for (EmissionKpiElement ke : EmissionKpiElement.values()) {
			HttpClientBean httpConfig = new HttpClientBean();
			httpConfig.setUsername(ApplicationProperties.getApiIesUsername());
			httpConfig.setPassword(ApplicationProperties.getApiIesPassword());
			httpConfig.setEndpoint(
					ApplicationProperties.getApiIesServerHistoryEndpoint().replace("<STATION_KEY>", station)
							.replace("<KPI_KEY>", ke.name()).replace("<FROM_MILLIS>", String.valueOf(from))
							.replace("<TO_MILLIS>", String.valueOf(to)));
			HttpResponse response = HttpClientUtils.callApiGet(httpConfig);
			String jsonOutput = HttpClientUtils.getOutput(response);
			ServerExportPojo internalPojo = MapperUtils.<ServerExportPojo>convertJsonToPojo(jsonOutput, ServerExportPojo.class);
			if (internalPojo != null && ke.getIndex() == 1) {					
				kpi1.add(internalPojo);
			}
			if (internalPojo != null && ke.getIndex() == 2) {					
				kpi2.add(internalPojo);
			}
			if (internalPojo != null && ke.getIndex() == 3) {					
				kpi3.add(internalPojo);
			}
		}
		
		if (!kpi1.isEmpty()) {
			StoreDataServiceServerRequest request1 = StoreDataServiceServerRest.postHistory(ApplicationProperties.getEmissionSite(), MINUTE_EVENT, kpi1, false);
			logger.debug("Post request on persistence (PART 1)...");
			String respServerHistory1 = StoreDataServiceServerRest
					.post(ApplicationProperties.getEmissionPersistenceStoredataserviceServerEndpoint(), request1);
			logger.info("Server History Emission: " + respServerHistory1);
		} else {
			logger.info("No data to persist for " + station + " in part 1");
		}
		
		if (!kpi2.isEmpty()) {
			StoreDataServiceServerRequest request2 = StoreDataServiceServerRest.postHistory(ApplicationProperties.getEmissionSite(), MINUTE_EVENT, kpi2, false);
			logger.debug("Post request on persistence (PART 2)...");
			String respServerHistory2 = StoreDataServiceServerRest
					.post(ApplicationProperties.getEmissionPersistenceStoredataserviceServerEndpoint(), request2);
			logger.info("Server History Emission: " + respServerHistory2);
		} else {
			logger.info("No data to persist for " + station + " in part 2");
		}
		
		if (!kpi3.isEmpty()) {
			StoreDataServiceServerRequest request3 = StoreDataServiceServerRest.postHistory(ApplicationProperties.getEmissionSite(), MINUTE_EVENT, kpi3, false);
			logger.debug("Post request on persistence (PART 3)...");
			String respServerHistory3 = StoreDataServiceServerRest
					.post(ApplicationProperties.getEmissionPersistenceStoredataserviceServerEndpoint(), request3);
			logger.info("Server History Emission: " + respServerHistory3);
		} else {
			logger.info("No data to persist for " + station + " in part 3");
		}
	}
	
	private static void persistFlaringHistoryServer(String station, long from, long to) {
		List<ServerExportPojo> kpi = new ArrayList<>();
		for (FlaringKpiElement kf : FlaringKpiElement.values()) {
			HttpClientBean httpConfig = new HttpClientBean();
			httpConfig.setUsername(ApplicationProperties.getApiIesUsername());
			httpConfig.setPassword(ApplicationProperties.getApiIesPassword());
			httpConfig.setEndpoint(
					ApplicationProperties.getApiIesServerHistoryEndpoint().replace("<STATION_KEY>", station)
							.replace("<KPI_KEY>", kf.getRealName()).replace("<FROM_MILLIS>", String.valueOf(from))
							.replace("<TO_MILLIS>", String.valueOf(to)));
			HttpResponse response = HttpClientUtils.callApiGet(httpConfig);
			String jsonOutput = HttpClientUtils.getOutput(response);
			ServerExportPojo internalPojo = MapperUtils.<ServerExportPojo>convertJsonToPojo(jsonOutput, ServerExportPojo.class);
			if (internalPojo != null) {	
				internalPojo.setParametro(kf.name());
				kpi.add(internalPojo);
			}
		}
		
		if (!kpi.isEmpty()) {
			StoreDataServiceServerRequest request = StoreDataServiceServerRest.postHistory(ApplicationProperties.getEmissionSite(), MINUTE_EVENT, kpi, false);
			logger.debug("Post request on persistence...");
			String respServerHistory = StoreDataServiceServerRest
					.post(ApplicationProperties.getEmissionPersistenceStoredataserviceServerEndpoint(), request);
			logger.info("Server History Flaring: " + respServerHistory);
		} else {
			logger.info("No data to persist for " + station);
		}
	}
}
