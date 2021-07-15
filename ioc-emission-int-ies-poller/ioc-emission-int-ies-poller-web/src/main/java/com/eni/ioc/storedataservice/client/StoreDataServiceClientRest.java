package com.eni.ioc.storedataservice.client;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.eni.ioc.bean.RequestElementsBean;
import com.eni.ioc.pojo.history.HistoryData;
import com.eni.ioc.pojo.history.HistoryKpi;
import com.eni.ioc.pojo.history.HistoryPojo;
import com.eni.ioc.pojo.thresholds.ThresholdsPojo;
import com.eni.ioc.properties.util.ApplicationProperties;
import com.eni.ioc.storedataservice.request.Element;
import com.eni.ioc.storedataservice.request.History;
import com.eni.ioc.storedataservice.request.StoreDataServiceRequest;
import com.eni.ioc.storedataservice.request.SubElement;
import com.eni.ioc.utils.EmissionElement;
import com.eni.ioc.utils.EmissionKpiElement;
import com.eni.ioc.utils.FlaringElement;
import com.eni.ioc.utils.FlaringKpiElement;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StoreDataServiceClientRest {

	private static final Logger logger = LoggerFactory.getLogger(StoreDataServiceClientRest.class);

	private static final String ALERT_KEY = "ALERT";
	private static final String NO_ALERT_KEY = "NONE";
	private static final String WARNING_KEY = "WARN";

	private StoreDataServiceClientRest() {
	}

	@SuppressWarnings("unchecked")
	public static <T> T post(String url, StoreDataServiceRequest storeDataServiceRequest) {
		Response<T> resp = new Response<>();
		ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		HttpEntity<StoreDataServiceRequest> request = new HttpEntity<>(storeDataServiceRequest);

		try {
			ResponseEntity<String> respEntity = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
			String r = respEntity.getBody();
			ObjectMapper mapper = new ObjectMapper();
			resp = mapper.readValue(r, Response.class);
		} catch (Exception e) {
			logger.error("Problem invoking post on " + url + " with error : " + e.getMessage(), e);
		}
		return resp.getData();
	}

	private static ClientHttpRequestFactory getClientHttpRequestFactory() {
		int timeout = ApplicationProperties.getEmissionPersistenceStoredataserviceTimeout();
		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).setConnectionRequestTimeout(timeout)
				.setSocketTimeout(timeout).build();
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		return new HttpComponentsClientHttpRequestFactory(client);
	}

	public static StoreDataServiceRequest createRequest(String asset, RequestElementsBean requestElementsBean,
			boolean actualValue, boolean flaring) {

		StoreDataServiceRequest request = new StoreDataServiceRequest();
		request.setAsset(asset);
		request.setActualValue(actualValue);
		if (flaring) {
			request.setFlgIsFlaring(true);
		}
		request.setElements(getElements(requestElementsBean, flaring));
		return request;
	}

	public static void postHistory(String asset, Map<String, HistoryPojo> historyPojos, boolean actualValue,
			boolean flaring) {

		logger.info("-- Persisitng History --");

		if (!actualValue && historyPojos != null) {
			for (Entry<String, HistoryPojo> h : historyPojos.entrySet()) {
				if (h.getValue() != null) {
					StoreDataServiceRequest request = new StoreDataServiceRequest();
					request.setAsset(asset);
					request.setActualValue(actualValue);
					List<Element> elements = new ArrayList<>();
					Element element = new Element();
					element.setName(h.getValue().getStazione());
					element.setFlaring(flaring);
					element.setFrom(h.getValue().getFrom());
					element.setTo(h.getValue().getTo());
					element.setSubElements(getHistorySubElements(h.getValue().getData()));
					elements.add(element);
					request.setElements(elements);

					String respHistory = StoreDataServiceClientRest
							.post(ApplicationProperties.getEmissionPersistenceStoredataserviceEndpoint(), request);
					logger.info("History Client for element " + element.getName() + ": " + respHistory);
				}
			}
		}
	}

	private static List<Element> getElements(RequestElementsBean requestElementsBean, boolean flaring) {

		List<Element> elementList = new ArrayList<>();

		if (!flaring && requestElementsBean.getEmission() != null) {
			Map<String, com.eni.ioc.pojo.emission.Station> emissionStations = retrieveEmissionStations(
					requestElementsBean.getEmission().getData());

			// retrive a timestamp of element
			Timestamp ts = new Timestamp(requestElementsBean.getEmission().getAt());

			for (Entry<String, com.eni.ioc.pojo.emission.Station> s : emissionStations.entrySet()) {

				if (s.getValue() != null) {
					Element element = new Element();
					element.setName(s.getKey());
					element.setAlert(s.getValue().getAlert());
					element.setState(s.getValue().getState());
					element.setFlaring(false);
					element.setSubElements(getEmissionSubElements(s, requestElementsBean));
					element.setFrom(ts);
					element.setTo(ts);
					elementList.add(element);
				}
			}
		}

		if (flaring && requestElementsBean.getFlaring() != null) {
			Map<String, com.eni.ioc.pojo.flaring.Station> flaringStations = retrieveFlaringStations(
					requestElementsBean.getFlaring().getData());
			// retrive a timestamp of element
			Timestamp ts = new Timestamp(requestElementsBean.getFlaring().getAt());

			for (Entry<String, com.eni.ioc.pojo.flaring.Station> s : flaringStations.entrySet()) {

				if (s.getValue() != null) {
					List<SubElement> flaringSubElements = getFlaringSubElements(s, requestElementsBean);
					Element element = new Element();
					element.setName(s.getKey());
					element.setState(s.getValue().getState());
					element.setFlaring(true);
					element.setSubElements(flaringSubElements);
					element.setFrom(ts);
					element.setTo(ts);
					element.setAlert(buildFlaringAlert(flaringSubElements));
					elementList.add(element);
				}

			}
		}

		return elementList;
	}

	private static List<SubElement> getEmissionSubElements(Entry<String, com.eni.ioc.pojo.emission.Station> station,
			RequestElementsBean requestElementsBean) {

		List<SubElement> subElementList = new ArrayList<>();

		Map<String, com.eni.ioc.pojo.emission.Kpi> flaringKpi = retrieveEmissionKpi(station.getValue().getParameters());
		for (Entry<String, com.eni.ioc.pojo.emission.Kpi> k : flaringKpi.entrySet()) {
			SubElement subElement = new SubElement();
			subElement.setName(k.getKey());
			if (k.getValue() != null) {

				subElement.setAlert(k.getValue().getAlert());

				if (k.getValue().getMinute() != null) {
					subElement.setmValue(k.getValue().getMinute().getValue());
				}
				if (k.getValue().getHour() != null) {
					subElement.sethValue(k.getValue().getHour().getValue());
				}
				if (k.getValue().getGiorno() != null) {
					subElement.setdValue(k.getValue().getGiorno().getValue());
				}
				subElement.setMaxHourAverage(k.getValue().getMaxhouraverage());
				// subElement.setTimestamp(timestamp);
				subElement
						.setHourlimit(getHourLimit(station.getKey(), k.getKey(), requestElementsBean.getThresholds()));
				subElement.setDaylimit(getDayLimit(station.getKey(), k.getKey(), requestElementsBean.getThresholds()));
				subElement.setUnitsAbbreviation(k.getValue().getUm());
				subElement.setValidData(checkIsValidDataEmission(k.getValue().getMinute()));

				subElementList.add(subElement);

			}

		}

		return subElementList;
	}

	private static List<SubElement> getFlaringSubElements(Entry<String, com.eni.ioc.pojo.flaring.Station> station,
			RequestElementsBean requestElementsBean) {

		List<SubElement> subElementList = new ArrayList<>();

		Map<String, com.eni.ioc.pojo.flaring.Kpi> flaringKpi = retrieveFlaringKpi(station.getValue().getParameters());
		for (Entry<String, com.eni.ioc.pojo.flaring.Kpi> k : flaringKpi.entrySet()) {

			SubElement subElement = new SubElement();
			subElement.setName(k.getKey());

			if (FlaringKpiElement.PORTATAMASSICA.name().equals(k.getKey())) {
				subElement.setAlert(k.getValue().getMinute().getValue() > ApplicationProperties.getflaringClientAlertThreshold() ? ALERT_KEY
						: (k.getValue().getMinute().getValue() > ApplicationProperties.getflaringClientWarningThreshold() ? WARNING_KEY : NO_ALERT_KEY));
				logger.debug("PORTATAMASSICA: value->" + k.getValue().getMinute().getValue() + ", alert: " + subElement.getAlert());
			} else {
				subElement.setAlert(k.getValue().getAlert());
			}

			if (k.getValue().getMinute() != null) {
				subElement.setmValue(k.getValue().getMinute().getValue());
			}
			if (k.getValue().getHour() != null) {
				subElement.sethValue(k.getValue().getHour().getValue());
			}
			if (k.getValue().getGiorno() != null) {
				subElement.setdValue(k.getValue().getGiorno().getValue());
			}
			subElement.setMaxHourAverage(k.getValue().getMaxhouraverage());
			// subElement.setTimestamp(timestamp);
			subElement.setHourlimit(getHourLimit(station.getKey(), k.getKey(), requestElementsBean.getThresholds()));
			subElement.setDaylimit(getDayLimit(station.getKey(), k.getKey(), requestElementsBean.getThresholds()));
			subElement.setUnitsAbbreviation(k.getValue().getUm());
			subElement.setValidData(checkIsValidDataFlaring(k.getValue().getMinute()));

			subElementList.add(subElement);
		}

		return subElementList;
	}

	private static String buildFlaringAlert(List<SubElement> flaringSubElements) {
		String alert = NO_ALERT_KEY;
		for (SubElement s : flaringSubElements) {
			if (FlaringKpiElement.PORTATAMASSICA.name().equals(s.getName())) {
				alert = s.getmValue() > ApplicationProperties.getflaringClientAlertThreshold() ? ALERT_KEY
						: (s.getmValue() > ApplicationProperties.getflaringClientWarningThreshold() ? WARNING_KEY : NO_ALERT_KEY);
			}
		}
		
		return alert;
	}

	private static List<SubElement> getHistorySubElements(HistoryData historyData) {

		List<SubElement> subElementList = new ArrayList<>();

		Map<String, HistoryKpi> historyKpi = retrieveHistoryKpi(historyData);
		for (Entry<String, HistoryKpi> k : historyKpi.entrySet()) {
			SubElement subElement = new SubElement();
			subElement.setName(k.getKey());

			List<History> history = new ArrayList<>();
			int numHistory = 0;
			if (k.getValue() != null && k.getValue().getTime() != null)
				numHistory = k.getValue().getTime().size();
			else if (k.getValue() != null && k.getValue().getValue() != null)
				numHistory = k.getValue().getValue().size();
			else if (k.getValue() != null && k.getValue().getMaintenance() != null)
				numHistory = k.getValue().getMaintenance().size();
			else if (k.getValue() != null && k.getValue().getError() != null)
				numHistory = k.getValue().getError().size();
			else if (k.getValue() != null && k.getValue().getEstimate() != null)
				numHistory = k.getValue().getEstimate().size();

			for (int i = 0; i < numHistory; i++) {
				History h = new History();
				h.setTime(k.getValue().getTime().get(i));
				h.setValue(k.getValue().getValue().get(i));
				h.setMaintenance(k.getValue().getMaintenance().get(i));
				h.setError(k.getValue().getError().get(i));
				h.setEstimate(k.getValue().getEstimate().get(i));
				h.setValidData(checkIsValidDataHistory(k.getValue(), i));
				history.add(h);
			}
			subElement.setHistory(history);

			subElementList.add(subElement);
		}

		return subElementList;
	}

	private static Map<String, com.eni.ioc.pojo.emission.Station> retrieveEmissionStations(
			com.eni.ioc.pojo.emission.EmissionData emissionData) {
		Map<String, com.eni.ioc.pojo.emission.Station> map = new HashMap<>();

		map.put(EmissionElement.E03.name(), emissionData.getE03());
		map.put(EmissionElement.E04.name(), emissionData.getE04());
		map.put(EmissionElement.E04BIS.name(), emissionData.getE04bis());
		map.put(EmissionElement.E04RISEXX.name(), emissionData.getE04risexx());
		map.put(EmissionElement.E11A.name(), emissionData.getE11a());
		map.put(EmissionElement.E11B.name(), emissionData.getE11b());
		map.put(EmissionElement.E11C.name(), emissionData.getE11c());
		map.put(EmissionElement.E12A.name(), emissionData.getE12a());
		map.put(EmissionElement.E12B.name(), emissionData.getE12b());
		map.put(EmissionElement.E12C.name(), emissionData.getE12c());
		map.put(EmissionElement.E12D.name(), emissionData.getE12d());
		map.put(EmissionElement.E20.name(), emissionData.getE20());
		map.put(EmissionElement.EXX.name(), emissionData.getExx());

		return map;
	}

	private static Map<String, com.eni.ioc.pojo.flaring.Station> retrieveFlaringStations(
			com.eni.ioc.pojo.flaring.FlaringData flaringData) {
		Map<String, com.eni.ioc.pojo.flaring.Station> map = new HashMap<>();

		map.put(FlaringElement.E13.name(), flaringData.getE13());
		map.put(FlaringElement.E14.name(), flaringData.getE14());
		map.put(FlaringElement.E15.name(), flaringData.getE15());

		return map;
	}

	private static Map<String, com.eni.ioc.pojo.emission.Kpi> retrieveEmissionKpi(
			com.eni.ioc.pojo.emission.StationParameters stationParameters) {
		Map<String, com.eni.ioc.pojo.emission.Kpi> map = new HashMap<>();

		map.put(EmissionKpiElement.CO.name(), stationParameters.getCo());
		map.put(EmissionKpiElement.CO_CORR.name(), stationParameters.getCoCorr());
		map.put(EmissionKpiElement.COT.name(), stationParameters.getCot());
		map.put(EmissionKpiElement.COT_CORR.name(), stationParameters.getCotCorr());
		map.put(EmissionKpiElement.DIGIT.name(), stationParameters.getDigit());
		map.put(EmissionKpiElement.FLUSSO_MASSA.name(), stationParameters.getFlussoMassa());
		map.put(EmissionKpiElement.FLUSSO_MASSA_CO.name(), stationParameters.getFlussoMassaCo());
		map.put(EmissionKpiElement.FLUSSO_MASSA_COT.name(), stationParameters.getFlussoMassaCot());
		map.put(EmissionKpiElement.FLUSSO_MASSA_NOX.name(), stationParameters.getFlussoMassaNox());
		map.put(EmissionKpiElement.FLUSSO_MASSA_POLVERI.name(), stationParameters.getFlussoMassaPolveri());
		map.put(EmissionKpiElement.FLUSSO_MASSA_SO2.name(), stationParameters.getFlussoMassaSo2());
		map.put(EmissionKpiElement.FLUSSO_VOLUME.name(), stationParameters.getFlussoVolume());
		map.put(EmissionKpiElement.H2S.name(), stationParameters.getH2s());
		map.put(EmissionKpiElement.HUM.name(), stationParameters.getHum());
		map.put(EmissionKpiElement.NOX.name(), stationParameters.getNox());
		map.put(EmissionKpiElement.NOX_CORR.name(), stationParameters.getNoxCorr());
		map.put(EmissionKpiElement.O2.name(), stationParameters.getO2());
		map.put(EmissionKpiElement.POLVERI.name(), stationParameters.getPolveri());
		map.put(EmissionKpiElement.POLVERI_CORR.name(), stationParameters.getPolveriCorr());
		map.put(EmissionKpiElement.PORTATA_HUM.name(), stationParameters.getPortataHum());
		map.put(EmissionKpiElement.PORTATA_HUM_NORM.name(), stationParameters.getPortataHumNorm());
		map.put(EmissionKpiElement.PORTATA_SECCA.name(), stationParameters.getPortataSecca());
		map.put(EmissionKpiElement.PORTATA_SECCA_NORM.name(), stationParameters.getPortataSeccaNorm());
		map.put(EmissionKpiElement.PORTATA_SECCA_NORM_CORR.name(), stationParameters.getPortataSeccaNormCorr());
		map.put(EmissionKpiElement.PRESS.name(), stationParameters.getPress());
		map.put(EmissionKpiElement.SO2.name(), stationParameters.getSo2());
		map.put(EmissionKpiElement.SO2_CORR.name(), stationParameters.getSo2Corr());
		map.put(EmissionKpiElement.TEMP_FUMI.name(), stationParameters.getTempFumi());

		return map;
	}

	private static Map<String, com.eni.ioc.pojo.flaring.Kpi> retrieveFlaringKpi(
			com.eni.ioc.pojo.flaring.StationParameters stationParameters) {
		Map<String, com.eni.ioc.pojo.flaring.Kpi> map = new HashMap<>();

		map.put(FlaringKpiElement.PORTATAMASSICA.name(), stationParameters.getFlussoMassa()); // .getPortataMassica());
		map.put(FlaringKpiElement.PORTATAVOLUMETRICA.name(), stationParameters.getFlussoVolume()); // .getPortataVolumetrica());

		return map;
	}

	private static Map<String, HistoryKpi> retrieveHistoryKpi(HistoryData historyData) {
		Map<String, HistoryKpi> map = new HashMap<>();

		map.put(EmissionKpiElement.CO.name(), historyData.getCo());
		map.put(EmissionKpiElement.CO_CORR.name(), historyData.getCoCorr());
		map.put(EmissionKpiElement.COT.name(), historyData.getCot());
		map.put(EmissionKpiElement.COT_CORR.name(), historyData.getCotCorr());
		map.put(EmissionKpiElement.DIGIT.name(), historyData.getDigit());
		// map.put(EmissionKpiElement.FLUSSO_MASSA.name(),
		// historyData.getFlussoMassa());
		map.put(EmissionKpiElement.FLUSSO_MASSA_CO.name(), historyData.getFlussoMassaCo());
		map.put(EmissionKpiElement.FLUSSO_MASSA_COT.name(), historyData.getFlussoMassaCot());
		map.put(EmissionKpiElement.FLUSSO_MASSA_NOX.name(), historyData.getFlussoMassaNox());
		map.put(EmissionKpiElement.FLUSSO_MASSA_POLVERI.name(), historyData.getFlussoMassaPolveri());
		map.put(EmissionKpiElement.FLUSSO_MASSA_SO2.name(), historyData.getFlussoMassaSo2());
		// map.put(EmissionKpiElement.FLUSSO_VOLUME.name(),
		// historyData.getFlussoVolume());
		map.put(EmissionKpiElement.H2S.name(), historyData.getH2s());
		map.put(EmissionKpiElement.HUM.name(), historyData.getHum());
		map.put(EmissionKpiElement.NOX.name(), historyData.getNox());
		map.put(EmissionKpiElement.NOX_CORR.name(), historyData.getNoxCorr());
		map.put(EmissionKpiElement.O2.name(), historyData.getO2());
		map.put(EmissionKpiElement.POLVERI.name(), historyData.getPolveri());
		map.put(EmissionKpiElement.POLVERI_CORR.name(), historyData.getPolveriCorr());
		map.put(EmissionKpiElement.PORTATA_HUM.name(), historyData.getPortataHum());
		map.put(EmissionKpiElement.PORTATA_HUM_NORM.name(), historyData.getPortataHumNorm());
		map.put(EmissionKpiElement.PORTATA_SECCA.name(), historyData.getPortataSecca());
		map.put(EmissionKpiElement.PORTATA_SECCA_NORM.name(), historyData.getPortataSeccaNorm());
		map.put(EmissionKpiElement.PORTATA_SECCA_NORM_CORR.name(), historyData.getPortataSeccaNormCorr());
		map.put(EmissionKpiElement.PRESS.name(), historyData.getPress());
		map.put(EmissionKpiElement.SO2.name(), historyData.getSo2());
		map.put(EmissionKpiElement.SO2_CORR.name(), historyData.getSo2Corr());
		map.put(EmissionKpiElement.TEMP_FUMI.name(), historyData.getTempFumi());
		map.put(FlaringKpiElement.PORTATAMASSICA.name(), historyData.getFlussoMassa());
		map.put(FlaringKpiElement.PORTATAVOLUMETRICA.name(), historyData.getFlussoVolume());

		return map;
	}

	private static Double getDayLimit(String stationName, String kpiName, List<ThresholdsPojo> thresholds) {

		Double dayLimit = null;

		if (thresholds != null) {
			for (ThresholdsPojo threshold : thresholds) {
				if (stationName.equals(threshold.getStazione()) && kpiName.equals(threshold.getParameter())) {
					dayLimit = threshold.getDaylimit();
				}
			}
		}

		return dayLimit;
	}

	private static Double getHourLimit(String stationName, String kpiName, List<ThresholdsPojo> thresholds) {
		Double hourLimit = null;

		if (thresholds != null) {
			for (ThresholdsPojo threshold : thresholds) {
				if (stationName.equals(threshold.getStazione()) && kpiName.equals(threshold.getParameter())) {
					hourLimit = threshold.getHourlimit();
				}
			}
		}

		return hourLimit;
	}

	private static boolean checkIsValidDataEmission(com.eni.ioc.pojo.emission.KpiMinute minute) {
		String maintenance = minute.getMaintenance();
		boolean error = minute.getError();
		return !(!"O".equals(maintenance) || error);
	}

	private static boolean checkIsValidDataFlaring(com.eni.ioc.pojo.flaring.KpiMinute minute) {
		String maintenance = minute.getMaintenance();
		boolean error = minute.getError();
		return !(!"O".equals(maintenance) || error);
	}

	private static boolean checkIsValidDataHistory(HistoryKpi h, int i) {
		String maintenance = h.getMaintenance().get(i);
		boolean error = h.getError().get(i);
		return !(!"O".equals(maintenance) || error);
	}
}
