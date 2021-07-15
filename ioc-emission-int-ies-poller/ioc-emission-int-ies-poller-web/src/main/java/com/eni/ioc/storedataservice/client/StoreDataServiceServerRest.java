package com.eni.ioc.storedataservice.client;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

import com.eni.ioc.pojo.server.export.ServerExportIndex;
import com.eni.ioc.pojo.server.export.ServerExportMeasurements;
import com.eni.ioc.pojo.server.export.ServerExportPojo;
import com.eni.ioc.pojo.server.hour.ServerHourIndex;
import com.eni.ioc.pojo.server.hour.ServerHourParam;
import com.eni.ioc.pojo.server.hour.ServerHourPojo;
import com.eni.ioc.pojo.server.minute.ServerMinuteParam;
import com.eni.ioc.pojo.server.minute.ServerMinutePojo;
import com.eni.ioc.properties.util.ApplicationProperties;
import com.eni.ioc.storedataservice.request.server.Element;
import com.eni.ioc.storedataservice.request.server.StoreDataServiceServerRequest;
import com.eni.ioc.storedataservice.request.server.SubElement;
import com.eni.ioc.utils.FlaringElement;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StoreDataServiceServerRest {

	private static final Logger logger = LoggerFactory.getLogger(StoreDataServiceServerRest.class);

	private static DateTimeFormatter dateTimeFormatterIes = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final String IES_TIMEZONE = "Europe/Rome";
	private static final ZoneId italyZoneId = ZoneId.of(IES_TIMEZONE);
	private static final String SERVER_MINUTE_INDEX_NAME = "MEDIA_MINUTO";
	private static final String SERVER_HOUR_INDEX_NAME = "MEDIA_ORA";
	private static final String SERVER_DAY_INDEX_NAME = "MEDIA_GIORNO";

	private static final String SUP_LDL_ORA_INDEX_KEY = "SUP_LDL_ORA";
	private static final String SUP_LDL_GIORNO_INDEX_KEY = "SUP_LDL_GIORNO";

	private static final String ALERT_KEY = "ALERT";
	private static final String NO_ALERT_KEY = "NONE";
	private static final String VIEW_DATA_KEY = "VIEW_DATA";

	private StoreDataServiceServerRest() {
	}

	public static StoreDataServiceServerRequest createMinuteRequest(String asset, String event,
			Map<String, ServerMinutePojo> serverMinutePojo, boolean actualValue) {

		StoreDataServiceServerRequest request = new StoreDataServiceServerRequest();
		request.setAsset(asset);
		request.setActualValue(actualValue);
		request.setEvent(event);

		List<Element> elements = new ArrayList<>();
		for (Entry<String, ServerMinutePojo> entryElement : serverMinutePojo.entrySet()) {
			Element element = new Element();
			element.setName(entryElement.getKey());
			String hour = entryElement.getValue().getData().substring(entryElement.getValue().getData().length() - 2);
			hour = Integer.parseInt(hour) < 11 ? "0" + (Integer.parseInt(hour) - 1) : "" + (Integer.parseInt(hour) - 1);
			String dateWithoutHour = entryElement.getValue().getData().substring(0,
					entryElement.getValue().getData().length() - 2);
			String date = dateWithoutHour + hour + ":"
					+ (entryElement.getValue().getMinuto() < 11 ? "0" + (entryElement.getValue().getMinuto() - 1)
							: (entryElement.getValue().getMinuto() - 1))
					+ ":00";
			
			LocalDateTime localtDateAndTime = LocalDateTime.parse(date, dateTimeFormatterIes);
			ZonedDateTime dateAndTimeInItaly = ZonedDateTime.of(localtDateAndTime, italyZoneId);
			ZonedDateTime utcDate = dateAndTimeInItaly.withZoneSameInstant(ZoneOffset.UTC);
			
			List<SubElement> subElements = new ArrayList<>();
			for (ServerMinuteParam param : entryElement.getValue().getParametri()) {
				SubElement subElement = new SubElement();
				subElement.setName(param.getParametro());
				subElement.setDate(utcDate.toLocalDateTime());
				subElement.setUnitsAbbreviation(param.getUm());
				subElement.setValue(param.getValore());
				subElement.setValidita(param.getValidita());
				subElements.add(subElement);
			}
			element.setSubElements(subElements);
			elements.add(element);
		}
		request.setElements(elements);
		return request;
	}

	public static StoreDataServiceServerRequest createHourRequest(String asset, String event,
			Map<String, ServerHourPojo> serverHourPojo, boolean actualValue) {

		StoreDataServiceServerRequest request = new StoreDataServiceServerRequest();
		request.setAsset(asset);
		request.setActualValue(actualValue);
		request.setEvent(event);

		List<Element> elements = new ArrayList<>();
		for (Entry<String, ServerHourPojo> entryElement : serverHourPojo.entrySet()) {
			Element element = new Element();
			element.setName(entryElement.getKey());
			element.setAlert(FlaringElement.isElement(entryElement.getKey()) ? VIEW_DATA_KEY : NO_ALERT_KEY);
			String date = entryElement.getValue().getData() + " "
					+ (entryElement.getValue().getOra() < 11 ? "0" + (entryElement.getValue().getOra() - 1)
							: (entryElement.getValue().getOra() - 1))
					+ ":00:00";
			
			LocalDateTime localtDateAndTime = LocalDateTime.parse(date, dateTimeFormatterIes);
			ZonedDateTime dateAndTimeInItaly = ZonedDateTime.of(localtDateAndTime, italyZoneId);
			ZonedDateTime utcDate = dateAndTimeInItaly.withZoneSameInstant(ZoneOffset.UTC);
			
			List<SubElement> subElements = new ArrayList<>();
			for (ServerHourParam param : entryElement.getValue().getParametri()) {
				SubElement subElement = new SubElement();
				subElement.setAlert(FlaringElement.isElement(entryElement.getKey()) ? VIEW_DATA_KEY : NO_ALERT_KEY);
				boolean persist = false;
				for (ServerHourIndex index : param.getIndici()) {
					if (SERVER_HOUR_INDEX_NAME.equals(index.getIndice())) {
						subElement.setName(param.getParametro());
						subElement.setDate(utcDate.toLocalDateTime());
						subElement.setUnitsAbbreviation(index.getUm());
						subElement.setValue(index.getValore());
						subElement.setValidita(index.getValidita());
						persist = true;
					}
					if (SUP_LDL_ORA_INDEX_KEY.equals(index.getIndice()) && !FlaringElement.isElement(entryElement.getKey())) {
						element.setAlert(ALERT_KEY);
						subElement.setAlert(ALERT_KEY);
					}
				}
				if (persist) {					
					subElements.add(subElement);
				}
			}
			element.setSubElements(subElements);
			elements.add(element);
		}
		request.setElements(elements);
		return request;
	}

	public static StoreDataServiceServerRequest createDayRequest(String asset, String event,
			Map<String, Map<String, ServerExportPojo>> serverDayPojo, boolean actualValue) {

		StoreDataServiceServerRequest request = new StoreDataServiceServerRequest();
		request.setAsset(asset);
		request.setActualValue(actualValue);
		request.setEvent(event);

		List<Element> elements = new ArrayList<>();
		for (Entry<String, Map<String, ServerExportPojo>> entryElement : serverDayPojo.entrySet()) {
			for (Entry<String, ServerExportPojo> entrySubElement : entryElement.getValue().entrySet()) {
				Element element = new Element();
				element.setAlert(FlaringElement.isElement(entryElement.getKey()) ? VIEW_DATA_KEY : NO_ALERT_KEY);
				List<SubElement> subElements = new ArrayList<>();
				SubElement subElement = new SubElement();
				subElement.setAlert(FlaringElement.isElement(entryElement.getKey()) ? VIEW_DATA_KEY : NO_ALERT_KEY);
				boolean persist = false;
				for (ServerExportIndex param : entrySubElement.getValue().getIndici()) {
					if (SERVER_DAY_INDEX_NAME.equals(param.getIndice())) {
						for (ServerExportMeasurements measure : param.getMisure()) {

							element.setName(entryElement.getKey());
							String date = measure.getData() + " 00:00:00";
							LocalDateTime localtDateAndTime = LocalDateTime.parse(date, dateTimeFormatterIes);
							ZonedDateTime dateAndTimeInItaly = ZonedDateTime.of(localtDateAndTime, italyZoneId);
							ZonedDateTime utcDate = dateAndTimeInItaly.withZoneSameInstant(ZoneOffset.UTC);

							subElement.setName(entrySubElement.getValue().getParametro());
							subElement.setDate(utcDate.toLocalDateTime());
							subElement.setUnitsAbbreviation(param.getUm());
							subElement.setValue(measure.getValore());
							subElement.setValidita(measure.getValidita());
							persist = true;
						}
					}
					if (SUP_LDL_GIORNO_INDEX_KEY.equals(param.getIndice()) && !FlaringElement.isElement(entryElement.getKey())) {
						element.setAlert(ALERT_KEY);
						subElement.setAlert(ALERT_KEY);
					}
				}
				if (persist) {					
					subElements.add(subElement);
				}
				element.setSubElements(subElements);
				elements.add(element);
			}
		}
		request.setElements(elements);
		return request;
	}

	public static StoreDataServiceServerRequest postHistory(String asset, String event,
			List<ServerExportPojo> serverHistoryPojos, boolean actualValue) {

		logger.debug("Persisisting Histoy");
		
		StoreDataServiceServerRequest request = new StoreDataServiceServerRequest();
		request.setAsset(asset);
		request.setActualValue(actualValue);
		request.setEvent(event);
		List<Element> elements = new ArrayList<>();
		Element element = new Element();
		List<SubElement> subElements = new ArrayList<>();
		if (serverHistoryPojos != null && !serverHistoryPojos.isEmpty()) {
			element.setName(serverHistoryPojos.get(0).getStazione());
		}
		
		for (ServerExportPojo serverExportPojo : serverHistoryPojos) {
			for (ServerExportIndex param : serverExportPojo.getIndici()) {
				if (SERVER_MINUTE_INDEX_NAME.equals(param.getIndice())) {
					for (ServerExportMeasurements measure : param.getMisure()) {

						
						String hour = measure.getData().substring(measure.getData().length() - 2);
						hour = Integer.parseInt(hour) < 11 ? "0" + (Integer.parseInt(hour) - 1)
								: "" + (Integer.parseInt(hour) - 1);
						String dateWithoutHour = measure.getData().substring(0, measure.getData().length() - 2);
						String date = dateWithoutHour + hour + ":"
								+ (measure.getMinuto() < 11 ? "0" + (measure.getMinuto() - 1)
										: (measure.getMinuto() - 1))
								+ ":00";
						LocalDateTime localtDateAndTime = LocalDateTime.parse(date, dateTimeFormatterIes);
						ZonedDateTime dateAndTimeInItaly = ZonedDateTime.of(localtDateAndTime, italyZoneId);
						ZonedDateTime utcDate = dateAndTimeInItaly.withZoneSameInstant(ZoneOffset.UTC);
						
						SubElement subElement = new SubElement();
						subElement.setName(serverExportPojo.getParametro());
						subElement.setUnitsAbbreviation(param.getUm());
						subElement.setDate(utcDate.toLocalDateTime());
						subElement.setValue(measure.getValore());
						subElement.setValidita(measure.getValidita());
						subElements.add(subElement);
					}
				}
			}
			
			element.setSubElements(subElements);
		}
		elements.add(element);
		request.setElements(elements);
		
		return request;
	}

	@SuppressWarnings("unchecked")
	public static <T> T post(String url, StoreDataServiceServerRequest storeDataServiceServerRequest) {
		Response<T> resp = new Response<>();
		ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		HttpEntity<StoreDataServiceServerRequest> request = new HttpEntity<>(storeDataServiceServerRequest);

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
}
