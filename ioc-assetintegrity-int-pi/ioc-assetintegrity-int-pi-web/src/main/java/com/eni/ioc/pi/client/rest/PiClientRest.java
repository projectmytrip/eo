package com.eni.ioc.pi.client.rest;

import com.eni.ioc.common.CorrosionCNDDeserializer;
import com.eni.ioc.common.ItemDeserializer;
import com.eni.ioc.pi.rest.response.ActualResponse;
import com.eni.ioc.pi.rest.response.ActualResponseEvpmAlerts;
import com.eni.ioc.pi.rest.response.CorrosionBacteriaDto;
import com.eni.ioc.pi.rest.response.CorrosionCNDAuth;
import com.eni.ioc.pi.rest.response.CorrosionCNDItem;
import com.eni.ioc.pi.rest.response.CorrosionCNDModel;
import com.eni.ioc.pi.rest.response.CorrosionCNDResponse;
import com.eni.ioc.pi.rest.response.CorrosionCouponItem;
import com.eni.ioc.pi.rest.response.CorrosionCouponResponse;
import com.eni.ioc.pi.rest.response.CorrosionKpiItem;
import com.eni.ioc.pi.rest.response.CorrosionKpiResponse;
import com.eni.ioc.pi.rest.response.EVPMSAlertDto;
import com.eni.ioc.pi.rest.response.EVPMSStationDto;
import com.eni.ioc.pi.rest.response.Item;
import com.eni.ioc.pi.rest.response.ItemsParent;
import com.eni.ioc.pi.rest.response.JacketedPipesDto;
import com.eni.ioc.pi.rest.response.WebIdItem;
import com.eni.ioc.pi.rest.response.WebIdResponse;

import static com.eni.ioc.properties.util.CorrosionConstants.CND_FILTER_STRUCTURE;
import static com.eni.ioc.properties.util.CorrosionConstants.CND_MODELS;
import static com.eni.ioc.properties.util.CorrosionConstants.CORROSION_CND_PLANT;
import com.eni.ioc.properties.util.CustomConfigurations;
import com.eni.ioc.request.CorrosionKpi;
import com.eni.ioc.request.IntegrityOperatingWindows;
import com.eni.ioc.request.OperatingWindows;
import com.eni.ioc.request.SegnaleCritico;
import com.eni.ioc.request.SegnaleCriticoValues;
import com.eni.ioc.request.StoreDataServiceRequest;
import com.eni.ioc.storedataservice.client.StoreDataServiceClientRest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class PiClientRest {

	private static final Logger logger = LoggerFactory.getLogger(PiClientRest.class);

	public static HttpHeaders setHeaders(boolean isPost) {

		HttpHeaders headers = new HttpHeaders();
		if (isPost) {
			headers.set("Host", "edof-af-webapi.eni.com");
			headers.set("Cache-control", "no-cache");
			headers.set("Accept", "application/json");
			headers.set("X-Requested-With", "XMLHttpRequest");
			headers.set("Content-Type", "application/json");

		}
		String auth = CustomConfigurations.getProperty("credential.pi.wss.username") + ":"
				+ CustomConfigurations.getProperty("credential.pi.wss.password");
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
		String authHeader = "Basic " + new String(encodedAuth);
		headers.set("Authorization", authHeader);

		return headers;

	}

	private static <T> T getBatchResponse(String batchBody, Class<T> responseClass) {
		String urlPostRequest = CustomConfigurations.getProperty("pi.url.batch");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = PiClientRest.setHeaders(true);

		// Note the body object as first parameter!
		HttpEntity<String> httpRequestEntity = new HttpEntity<>(batchBody, headers);
		T resp = null;
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("start call");
				logger.debug(headers.toString());
				logger.debug(urlPostRequest);
				logger.debug(batchBody);
			}
			ResponseEntity<String> response = restTemplate.exchange(urlPostRequest, HttpMethod.POST, httpRequestEntity,
					String.class);
			String r = response.getBody();
			logger.debug("Received {}", r);
			ObjectMapper mapper = new ObjectMapper();
			SimpleModule module = new SimpleModule();
			module.addDeserializer(Item.class, new ItemDeserializer());
			mapper.registerModule(module);
			resp = mapper.readValue(r, responseClass);

		} catch (Exception e) {
			logger.error("Problem invoking post Actuals with error : " + e.getMessage());
		}
		return resp;
	}
	
	private static <T> T getRestResponse(Class<T> responseClass) {
		String urlGetRequest = CustomConfigurations.getProperty("pi.url.rest.signals");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = PiClientRest.setHeaders(true);

		T resp = null;
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("start call");
				logger.debug(headers.toString());
				logger.debug(urlGetRequest);
			}
			ResponseEntity<String> response = restTemplate.exchange(urlGetRequest, HttpMethod.GET, new HttpEntity<>(headers),
					String.class);
			String r = response.getBody();
			logger.debug("Received {}", r);
			ObjectMapper mapper = new ObjectMapper();
			SimpleModule module = new SimpleModule();
			module.addDeserializer(Item.class, new ItemDeserializer());
			mapper.registerModule(module);
			resp = mapper.readValue(r, responseClass);

		} catch (Exception e) {
			logger.error("Problem invoking post Actuals with error : " + e.getMessage());
		}
		return resp;
	}

	public static void senderSegnaliProcesso() {

		logger.info("senderSegnaliProcesso REST : ---- IN ");

		WebIdResponse webResponse = getRestResponse(WebIdResponse.class);
		if(webResponse == null || webResponse.getItems() == null || webResponse.getItems().length == 0) return;
		
		int counter = 0;
		for (WebIdItem webId : webResponse.getItems()) {
			
			ActualResponse resp = getBatchResponse(CustomConfigurations.getProperty("pi.url.batch.body.signals").replace("{webId}", webId.getWebId()),
					ActualResponse.class);

			if (resp == null || !resp.getBodyBatchCall().getStatus().equals("207")) continue;
	
			ArrayList<SegnaleCritico> segnaleCriticoList = new ArrayList<>();
			SegnaleCritico sCritico = null;

			for (ItemsParent item : resp.getBodyBatchCall().getContent().getItems()) {
				if (!"200".equals(resp.getBodyBatchCall().getContent().getItems()[0].getStatus())) {
					logger.warn("scartato segnale critico: status != 200"
							+ resp.getBodyBatchCall().getContent().getItems()[0].getStatus());
					continue;
				}

				sCritico = new SegnaleCritico();

				// subItem -> ogni field
				// ciclicamo e valorizziamo i vari campi
				for (Item subItem : item.getContent().getItems()) {
					String valueToAdd = "";

					if (subItem.getGood()) {
						valueToAdd = subItem.getValue();
					} else {
						logger.warn("good = false segnali critici: " + subItem.getName());
						valueToAdd = null;
					}

					SegnaleCriticoValues value = SegnaleCriticoValues.getByField(subItem.getName());
					if (value == null) {
						logger.warn("field scartato segnali critici: " + subItem.getName());
						continue;
					}

					switch (value) {
					case AREA:
						sCritico.setArea(valueToAdd);
						break;
					case BLOCKINPUT:
						sCritico.setBlockInput(valueToAdd);
						break;
					case LASTSTATUSCHANGEDATE:
						sCritico.setLastStatusChangeDate(valueToAdd);
						break;
					case BLOCKINPUTSTATUS:
						sCritico.setBlockInputStatus(valueToAdd);
						break;
					case CATEGORY:
						sCritico.setCategory(valueToAdd);
						break;
					case DESCRIPTION:
						sCritico.setDescription(valueToAdd);
						break;
					case NAME:
						sCritico.setName(valueToAdd);
						break;
					case SEVERITY:
						sCritico.setSeverity(valueToAdd);
						break;
					case VALUE:
						sCritico.setValue(valueToAdd);
						break;
					default:
						logger.warn("field scartato segnali critici: " + subItem.getName());
						break;
					}
				}
				segnaleCriticoList.add(sCritico);

			}

			boolean lastInBatch = (counter == (webResponse.getItems().length - 1));

			if(lastInBatch){
				logger.info("last in batch!!!");
			}
			
			StoreDataServiceRequest<SegnaleCritico> req = StoreDataServiceClientRest.createRequest("COVA", segnaleCriticoList, lastInBatch);

			StoreDataServiceClientRest.postStoreDataService(CustomConfigurations.getProperty("persistence.signals.endpoint"), req);

			logger.info("senderSegnaliProcesso REST : ---- OUT ");
			counter++;
		}
		
		StoreDataServiceClientRest.updateSegnaliCritici(CustomConfigurations.getProperty("persistence.update.signals.endpoint"), "COVA");
	}

	public static List<CorrosionKpi> getCorrosionKpi() {
		try {
			logger.debug("getCorrosionKpi start");
			String urlRequest = CustomConfigurations.getProperty("pi.corrosion.url");
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity entity = new HttpEntity(PiClientRest.setHeaders(false));
			ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.GET, entity, String.class);
			logger.debug("Received " + response.getBody());
			List<CorrosionKpi> data = new LinkedList();
			ObjectMapper mapper = new ObjectMapper();
			SimpleModule module = new SimpleModule();
			module.addDeserializer(Item.class, new ItemDeserializer());
			mapper.registerModule(module);
			CorrosionKpiResponse resp = mapper.readValue(response.getBody(), CorrosionKpiResponse.class);
			for (CorrosionKpiItem item : resp.getItems()) {
				CorrosionKpi kpi = new CorrosionKpi();
				kpi.setName(item.getName());
				if (item.getValue() != null && item.getValue().getGood()) {
					kpi.setValue(item.getValue().getNameValue());
				} else {
					kpi.setValue("BAD DATA");
				}
				data.add(kpi);
			}
			logger.debug("getCorrosionKpi end - recieved {} elements.", data.size());
			return data;
		} catch (IOException ex) {
			logger.error("Problem invoking getCorrosionKpi.", ex);
			return null;
		}
	}

	public static List<OperatingWindows> getOperatingWindowsKpi() {
		ActualResponse resp = getBatchResponse(CustomConfigurations.getProperty("pi.url.batch.body.windows"),
				ActualResponse.class);
		List<OperatingWindows> operatingWindows = new LinkedList();

		if (resp != null && resp.getBodyBatchCall() != null && resp.getBodyBatchCall().getContent() != null
				&& resp.getBodyBatchCall().getContent().getItems() != null
				&& resp.getBodyBatchCall().getContent().getItems().length > 0) {

			for (ItemsParent item : resp.getBodyBatchCall().getContent().getItems()) {
				if (!"200".equals(resp.getBodyBatchCall().getContent().getItems()[0].getStatus())) {
					logger.warn("scartato Kpi Window: status != 200"
							+ resp.getBodyBatchCall().getContent().getItems()[0].getStatus());
					continue;
				}

				OperatingWindows opWindow = new OperatingWindows();
				BeanWrapper wrapper = new BeanWrapperImpl(opWindow);
				for (Item subItem : item.getContent().getItems()) {
					String valueToAdd = "";
					if (subItem.getGood()) {
						valueToAdd = subItem.getValue();
					} else {
						logger.warn("getWindowsKpi: good = false " + subItem.getName());
						valueToAdd = null;
					}

					try {
						// INTEGER PARSE INT?
						wrapper.setPropertyValue(subItem.getName(), valueToAdd);
					} catch (InvalidPropertyException e) {
						logger.warn("getWindowsKpi: Skipping field {} as not mapped. ", subItem.getName());
					}

				}
				operatingWindows.add((OperatingWindows) wrapper.getWrappedInstance());
			}
		}
		return operatingWindows;
	}

	public static List<IntegrityOperatingWindows> getIntegrityOperatingWindowsKpi() {
		logger.debug("getIntegrityOperatingWindowsKpi start");
		ActualResponse resp = getBatchResponse(CustomConfigurations.getProperty("pi.url.batch.body.iowindows"),
				ActualResponse.class);
		List<IntegrityOperatingWindows> operatingWindows = new LinkedList();

		if (resp != null && resp.getBodyBatchCall() != null && resp.getBodyBatchCall().getContent() != null
				&& resp.getBodyBatchCall().getContent().getItems() != null
				&& resp.getBodyBatchCall().getContent().getItems().length > 0) {

			for (ItemsParent item : resp.getBodyBatchCall().getContent().getItems()) {
				if (!"200".equals(resp.getBodyBatchCall().getContent().getItems()[0].getStatus())) {
					logger.warn("scartato Kpi Window: status != 200"
							+ resp.getBodyBatchCall().getContent().getItems()[0].getStatus());
					continue;
				}

				IntegrityOperatingWindows opWindow = new IntegrityOperatingWindows();
				BeanWrapper wrapper = new BeanWrapperImpl(opWindow);
				for (Item subItem : item.getContent().getItems()) {
					String valueToAdd = "";
					if (subItem.getGood()) {
						valueToAdd = subItem.getValue();
					} else {
						logger.warn("getIntegrityOperatingWindowsKpi: good = false " + subItem.getName());
						valueToAdd = null;
					}

					try {
						// INTEGER PARSE INT?
						wrapper.setPropertyValue(subItem.getName(), valueToAdd);
					} catch (InvalidPropertyException e) {
						logger.warn("getIntegrityOperatingWindowsKpi: Skipping field {} as not mapped. ", subItem.getName());
					}

				}
				operatingWindows.add((IntegrityOperatingWindows) wrapper.getWrappedInstance());
			}
		}
		logger.debug("getIntegrityOperatingWindowsKpi end - received {} elements.", operatingWindows.size());
		return operatingWindows;
	}

	public static List<CorrosionBacteriaDto> getCorrosionBacteria() {
		ActualResponse resp = getBatchResponse(CustomConfigurations.getProperty("pi.url.batch.body.bacteria"),
				ActualResponse.class);
		List<CorrosionBacteriaDto> outputList = new LinkedList<>();
		if (resp != null && resp.getBodyBatchCall() != null && resp.getBodyBatchCall().getContent() != null
				&& resp.getBodyBatchCall().getContent().getItems() != null
				&& resp.getBodyBatchCall().getContent().getItems().length > 0) {

			for (ItemsParent item : resp.getBodyBatchCall().getContent().getItems()) {
				if (!"200".equals(item.getStatus())) {
					logger.warn("scartato Corrosion bacteria: status != 200"
							+ resp.getBodyBatchCall().getContent().getItems()[0].getStatus());
					continue;
				}
				CorrosionBacteriaDto bacteria = new CorrosionBacteriaDto();
				for (Item subItem : item.getContent().getItems()) {
					String valueToAdd;
					if (subItem.getGood()) {
						valueToAdd = subItem.getValue();
					} else {
						logger.warn("good = false Corrosion bacteria: " + subItem.getName());
						valueToAdd = null;
					}
					switch (subItem.getName()) {
					case "Type":
						if (valueToAdd != null) {
							String[] type = valueToAdd.split("_");
							if (type.length == 2) {
								valueToAdd = type[1];
								bacteria.setType(valueToAdd);
							} else {
								bacteria.setType(null);
							}
						}
						break;
					case "AnalisysPoint":
						bacteria.setAnalysisPoint(valueToAdd);
						break;
					case "Area":
						bacteria.setArea(valueToAdd);
						break;
					case "AnalisysValue":
						bacteria.setValue(valueToAdd);
						bacteria.setCountingEndDate(subItem.getTimestamp());
						break;
					case "AnalisysName":
						if (valueToAdd != null) {
							String[] type = valueToAdd.split("--");
							if (type.length == 2) {
								valueToAdd = type[1];
								bacteria.setAnalysisName(valueToAdd);
							} else {
								bacteria.setAnalysisName(null);
							}
						}
						break;
					case "UdM":
						bacteria.setUdM(valueToAdd);
						break;
					default:
						logger.warn("Skipping value " + subItem.getName() + " for corrosion bacteria");
					}
				}
				outputList.add(bacteria);
			}
		}
		return outputList;
	}

	public static List<CorrosionCNDItem> getCorrosionCND() {
		// Obtain authentication cookie
		String urlRequest = CustomConfigurations.getProperty("pi.corrosion.cnd.auth.url");
		RestTemplate restTemplate = new RestTemplate();
		CorrosionCNDAuth auth = new CorrosionCNDAuth();
		auth.setPassword(CustomConfigurations.getProperty("pi.corrosion.cnd.auth.password"));
		auth.setUsername(CustomConfigurations.getProperty("pi.corrosion.cnd.auth.username"));
		HttpEntity entity = new HttpEntity(auth);
		ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, entity, String.class);
		HttpHeaders headers = response.getHeaders();
		String setCookie = headers.getFirst(HttpHeaders.SET_COOKIE);

		// Add cookie to header
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Cookie", setCookie);
		entity = new HttpEntity(requestHeaders);

		// Retrieve actua data for each model
		List<CorrosionCNDItem> data = new LinkedList();
		for (CorrosionCNDModel model : CND_MODELS) {
			// Retrieve scheduleList
			urlRequest = CustomConfigurations.getProperty("pi.corrosion.cnd.schedulelist.url");
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlRequest)
					.queryParam("plant", CORROSION_CND_PLANT).queryParam("pUnitArea", "0") // Valori
																							// fissi
					.queryParam("pUnit", "0") // Valori fissi
					.queryParam("modelType", model.getModelType()).queryParam("componentType", model.getComponentType())
					.queryParam("modelId", model.getModelId());

			response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
			ObjectMapper mapper = new ObjectMapper();
			SimpleModule module = new SimpleModule();
			module.addDeserializer(CorrosionCNDItem.class,
					new CorrosionCNDDeserializer(model.getModelName(), model.getKeyList()));
			TypeFactory typeFactory = mapper.getTypeFactory();
			CollectionType collectionType = typeFactory.constructCollectionType(List.class, CorrosionCNDResponse.class);
			mapper.registerModule(module);

			try {
				List<CorrosionCNDResponse> resp = mapper.readValue(response.getBody(), collectionType);
				// Filter response
				List<CorrosionCNDItem> filteredResp = resp.stream()
						.filter(item -> CND_FILTER_STRUCTURE.contains(item.getItems().getPlantAcronym()))
						.map(item -> item.getItems()).collect(Collectors.toList());
				data.addAll(filteredResp);
			} catch (IOException ex) {
				logger.error("Error while reading corrosion CND response for model " + model.getModelName(), ex);
			}
		}
		return data;
	}

	public static List<CorrosionCouponItem> getCorrosionCoupon() {
		// Obtain authentication cookie
		String urlRequest = CustomConfigurations.getProperty("pi.corrosion.cnd.auth.url");
		RestTemplate restTemplate = new RestTemplate();
		CorrosionCNDAuth auth = new CorrosionCNDAuth();
		auth.setPassword(CustomConfigurations.getProperty("pi.corrosion.cnd.auth.password"));
		auth.setUsername(CustomConfigurations.getProperty("pi.corrosion.cnd.auth.username"));
		HttpEntity entity = new HttpEntity(auth);

		ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, entity, String.class);
		HttpHeaders headers = response.getHeaders();
		String setCookie = headers.getFirst(HttpHeaders.SET_COOKIE);

		// Add cookie to header
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Cookie", setCookie);
		entity = new HttpEntity(requestHeaders);

		// Retrieve actua data for each model
		List<CorrosionCouponItem> data = new LinkedList();

		// Retrieve scheduleList
		urlRequest = CustomConfigurations.getProperty("pi.corrosion.cnd.schedulelist.url");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlRequest)
				.queryParam("plant", CORROSION_CND_PLANT).queryParam("pUnitArea", "0") // Valori
																						// fissi
				.queryParam("pUnit", "0") // Valori fissi
				.queryParam("modelType", "1").queryParam("componentType", "29").queryParam("modelId", "1");

		response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
		ObjectMapper mapper = new ObjectMapper();
		CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class,
				CorrosionCouponResponse.class);

		try {
			List<CorrosionCouponResponse> resp = mapper.readValue(response.getBody(), collectionType);

			// Filter response
			List<CorrosionCouponItem> filteredResp = resp.stream()
					.filter(item -> CND_FILTER_STRUCTURE.contains(item.getItems().getPlantAcronym())).map((item) -> {
						CorrosionCouponItem element = item.getItems();
						if (element.getArea() == null) {
							element.setArea("Z");
						}
						return element;
					}).collect(Collectors.toList());
			data.addAll(filteredResp);
		} catch (IOException ex) {
			logger.error("Error while reading corrosion Coupon response for model ", ex);
		}

		return data;
	}

	public static List<EVPMSStationDto> getEVPMSStations() {
		ActualResponse stationJson = getBatchResponse(
				CustomConfigurations.getProperty("pi.url.batch.body.EVPMS.station"), ActualResponse.class);

		List<EVPMSStationDto> evpmsStations = new LinkedList<>();

		if (stationJson != null && stationJson.getBodyBatchCall() != null
				&& stationJson.getBodyBatchCall().getContent() != null
				&& stationJson.getBodyBatchCall().getContent().getItems() != null
				&& stationJson.getBodyBatchCall().getContent().getItems().length > 0) {

			for (ItemsParent item : stationJson.getBodyBatchCall().getContent().getItems()) {
				if (!"200".equals(stationJson.getBodyBatchCall().getContent().getItems()[0].getStatus())) {
					logger.warn("scartato evpms: status != 200"
							+ stationJson.getBodyBatchCall().getContent().getItems()[0].getStatus());
					continue;
				}

				EVPMSStationDto evpms = new EVPMSStationDto();
				BeanWrapper wrapper = new BeanWrapperImpl(evpms);
				for (Item subItem : item.getContent().getItems()) {
					String valueToAdd = "";
					if (subItem.getGood()) {
						valueToAdd = subItem.getValue();
					} else {
						logger.warn("getEVPMS station: good = false " + subItem.getName());
						valueToAdd = null;
					}

					try {
						wrapper.setPropertyValue(subItem.getName(), valueToAdd);
					} catch (InvalidPropertyException e) {
						logger.warn("getEVPMS station: Skipping field {} as not mapped. ", subItem.getName());
					}

				}
				evpmsStations.add((EVPMSStationDto) wrapper.getWrappedInstance());
			}
		}
		return evpmsStations;
	}

	public static List<EVPMSAlertDto> getEVPMSAlerts() {
		ActualResponseEvpmAlerts stationAlert = getBatchResponse(
				CustomConfigurations.getProperty("pi.url.batch.body.EVPMS.alert"), ActualResponseEvpmAlerts.class);
		List<EVPMSAlertDto> evpmsAlerts = new LinkedList<>();

		if (stationAlert != null && stationAlert.getBodyBatchAlert() != null
				&& stationAlert.getBodyBatchAlert().getContent() != null
				&& stationAlert.getBodyBatchAlert().getContent().getItems() != null
				&& stationAlert.getBodyBatchAlert().getContent().getItems().length > 0) {

			for (int i = 0; i < stationAlert.getBodyBatchAlert().getContent().getItems().length; i++) {
				ItemsParent item = stationAlert.getBodyBatchAlert().getContent().getItems()[i];
				if (!"200".equals(stationAlert.getBodyBatchAlert().getContent().getItems()[0].getStatus())) {
					logger.warn("scartato evpms: status != 200"
							+ stationAlert.getBodyBatchAlert().getContent().getItems()[0].getStatus());
					continue;
				}

				ItemsParent[] coordinates = stationAlert.getBodyBatchCoordinates().getContent().getItems();

				EVPMSAlertDto evpms = new EVPMSAlertDto();
				BeanWrapper wrapper = new BeanWrapperImpl(evpms);
				for (Item subItem : item.getContent().getItems()) {
					String valueToAdd = "";
					if (subItem.getGood()) {
						valueToAdd = subItem.getValue();
					} else {
						logger.warn("getEVPMS alert: good = false " + subItem.getName());
						valueToAdd = null;
					}

					try {
						if ("Coordinates".equals(subItem.getName())) {
							ItemsParent coordinate = coordinates[i];
							if (!"200".equals(coordinate.getStatus())) {
								logger.warn(
										"scartate coordinates evpms: status != 200 per station " + evpms.getAlertID());
							} else {
								for (Item coordItem : coordinate.getContent().getItems()) {
									if (subItem.getGood()) {
										wrapper.setPropertyValue(coordItem.getName(), coordItem.getValue());
									} else {
										wrapper.setPropertyValue(coordItem.getName(), null);
									}
								}
							}
						} else {
							wrapper.setPropertyValue(subItem.getName(), valueToAdd);
						}
					} catch (InvalidPropertyException e) {
						logger.warn("getEVPMSalert: Skipping field " + subItem.getName() + " as not mapped. ");
					}

				}
				evpmsAlerts.add((EVPMSAlertDto) wrapper.getWrappedInstance());
			}
		}
		return evpmsAlerts;
	}
     
    public static <T> List<T> parseBatchResponse(ActualResponse resp, Class<T> resultClass) {
        List<T> resultList = new LinkedList();

        if(resp == null ) return resultList;
        
        for (ItemsParent item : resp.getBodyBatchCall().getContent().getItems()) {
            if (!"200".equals(resp.getBodyBatchCall().getContent().getItems()[0].getStatus())) {
                logger.warn("scartata risposta: status != 200" + resp.getBodyBatchCall().getContent().getItems()[0].getStatus());
                continue;
            }

            T resultItem;
            try {
                resultItem = resultClass.newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                logger.error("Can't create object " + resultClass, ex );
                throw new IllegalArgumentException();
            }
            BeanWrapper wrapper = new BeanWrapperImpl(resultItem);
            for (Item subItem : item.getContent().getItems()) {
                String valueToAdd = "";
                if (subItem.getGood()) {
                    valueToAdd = subItem.getValue();
                } else {
                    logger.warn("{}: Scartato elemento good = false {}", resultClass, subItem.getName());
                    valueToAdd = null;
                }

                try {
                    wrapper.setPropertyValue(subItem.getName(), valueToAdd);
                } catch (InvalidPropertyException e) {
                    logger.warn(resultClass + "- parseBatchResponse: Skipping field " + subItem.getName() + " as not mapped. ");
                }

            }
            resultList.add((T) wrapper.getWrappedInstance());
        }

        return resultList;
    } 

    public static List<JacketedPipesDto>  senderJacketedPipes() {
        ActualResponse json = getBatchResponse(CustomConfigurations.getProperty("pi.url.batch.body.jacketedPipes"), ActualResponse.class);
        return parseBatchResponse(json, JacketedPipesDto.class);
    }

}
