package com.eni.ioc.service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.eni.ioc.common.CovaFlaringAD;
import com.eni.ioc.common.CovaFlaringEvent;
import com.eni.ioc.common.CovaSo2Average;
import com.eni.ioc.common.CovaSo2Impact;
import com.eni.ioc.common.CovaSo2Prediction;
import com.eni.ioc.common.CovaRootCause;
import com.eni.ioc.emission.dto.soredataserviceanomaly.StoreDataServiceAnomalyRequest;
import com.eni.ioc.emission.dto.soredataserviceaverage.StoreDataServiceAverageRequest;
import com.eni.ioc.emission.dto.soredataserviceflaringevent.StoreDataServiceFlaringEventsRequest;
import com.eni.ioc.emission.dto.soredataserviceimpacts.StoreDataServiceImpactsRequest;
import com.eni.ioc.emission.dto.soredataservicerootcause.StoreDataServiceRootCause;
import com.eni.ioc.emission.dto.storedataservicepredictiveprobabilities.StoreDataServiceProbabilitiesRequest;

public class Converter {

	private static final String ASSET_COVA = "COVA";
	private static final String SO2_KEYNAME = "SO2";
	private static final String FLAR_KEYNAME = "FLARING";
	
	private Converter() {	
	}
	
	public static StoreDataServiceAnomalyRequest convertToFlaringAnomalyRequest (List<CovaFlaringAD> anomaly, String keyName) {
		
		StoreDataServiceAnomalyRequest request = new StoreDataServiceAnomalyRequest();
		
		List<com.eni.ioc.emission.dto.soredataserviceanomaly.DetailElement> detailElements = new ArrayList<>();
		for (CovaFlaringAD a: anomaly) {
			com.eni.ioc.emission.dto.soredataserviceanomaly.DetailElement detailElement = new com.eni.ioc.emission.dto.soredataserviceanomaly.DetailElement();
			detailElement.setDescription(a.getDescription());
			if(a.geteAlarm() != null) {
				detailElement.seteAlarm(a.geteAlarm().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			}
			detailElement.setFrom(a.getFrom());
			detailElement.setImportance(a.getImportance());
			detailElement.setKeyName(a.getKeyName());
			detailElement.setsAlarm(a.getsAlarm().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			detailElement.setTag(a.getTag());
			detailElement.setTo(a.getTo());
			detailElement.setUnit(a.getUnit());
			detailElement.setMeasureUnit(a.getMeasureUnit());

			detailElements.add(detailElement);
		}
		
		List<com.eni.ioc.emission.dto.soredataserviceanomaly.Element> elements = new ArrayList<>();
		com.eni.ioc.emission.dto.soredataserviceanomaly.Element flarElement = new com.eni.ioc.emission.dto.soredataserviceanomaly.Element();
		flarElement.setName(keyName);
		flarElement.setDetailElement(detailElements);
		elements.add(flarElement);
		
		request.setAsset(ASSET_COVA);
		//request.setActualValue(true);
		request.setElement(elements);
	
		return request;
	}
	
	public static StoreDataServiceProbabilitiesRequest convertToPredictiveProbabilitiesRequest (List<CovaSo2Prediction> predictiveProbabilities, String keyName) {
		
		StoreDataServiceProbabilitiesRequest request = new StoreDataServiceProbabilitiesRequest();
		
		List<com.eni.ioc.emission.dto.storedataservicepredictiveprobabilities.DetailElement> detailElements = new ArrayList<>();
		for (CovaSo2Prediction predictiveProbability: predictiveProbabilities) {
			com.eni.ioc.emission.dto.storedataservicepredictiveprobabilities.DetailElement detailElement = new com.eni.ioc.emission.dto.storedataservicepredictiveprobabilities.DetailElement();
			detailElement.setValue(predictiveProbability.getProba());
			detailElement.setKeyName(predictiveProbability.getKeyName());
			detailElement.setDatetime(predictiveProbability.getDatetime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			detailElements.add(detailElement);
		}
		
		List<com.eni.ioc.emission.dto.storedataservicepredictiveprobabilities.Element> elements = new ArrayList<>();
		com.eni.ioc.emission.dto.storedataservicepredictiveprobabilities.Element so2Element = new com.eni.ioc.emission.dto.storedataservicepredictiveprobabilities.Element();

		so2Element.setName(keyName);
		
		so2Element.setDetailElement(detailElements);
		elements.add(so2Element);
		
		request.setAsset(ASSET_COVA);
		//request.setActualValue(true);
		request.setElement(elements);
	
		return request;
	}


	public static StoreDataServiceImpactsRequest convertToPredictiveImpactsRequest (List<CovaSo2Impact> predictiveImpacts) {
		
		StoreDataServiceImpactsRequest request = new StoreDataServiceImpactsRequest();
		
		List<com.eni.ioc.emission.dto.soredataserviceimpacts.DetailElement> detailElements = new ArrayList<>();
		for (CovaSo2Impact predictiveImpact: predictiveImpacts) {
			com.eni.ioc.emission.dto.soredataserviceimpacts.DetailElement detailElement = new com.eni.ioc.emission.dto.soredataserviceimpacts.DetailElement();
			detailElement.setValue(predictiveImpact.getValue());
			detailElement.setFeature(predictiveImpact.getFeature());
			detailElement.setMedian(predictiveImpact.getMedian());
			detailElement.setImpact(predictiveImpact.getImpact());
			detailElement.setDescription(predictiveImpact.getDescriptor());
			detailElement.setEngUnits(predictiveImpact.getEngunits());
			detailElement.setDatetime(predictiveImpact.getDatetime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			detailElements.add(detailElement);
		}
		
		List<com.eni.ioc.emission.dto.soredataserviceimpacts.Element> elements = new ArrayList<>();
		com.eni.ioc.emission.dto.soredataserviceimpacts.Element so2Element = new com.eni.ioc.emission.dto.soredataserviceimpacts.Element();
		so2Element.setName(SO2_KEYNAME);
		so2Element.setDetailElement(detailElements);
		elements.add(so2Element);
		
		request.setAsset(ASSET_COVA);
		//request.setActualValue(true);
		request.setElement(elements);
	
		return request;
	}

	public static StoreDataServiceAverageRequest convertToPredictiveAverageRequest(List<CovaSo2Average> predictiveAverages) {
		StoreDataServiceAverageRequest request = new StoreDataServiceAverageRequest();
		
		List<com.eni.ioc.emission.dto.soredataserviceaverage.DetailElement> detailElements = new ArrayList<>();
		for (CovaSo2Average predictiveAverage: predictiveAverages) {
			com.eni.ioc.emission.dto.soredataserviceaverage.DetailElement detailElement = new com.eni.ioc.emission.dto.soredataserviceaverage.DetailElement();
			detailElement.setMape(predictiveAverage.getMape());
			detailElement.setP(predictiveAverage.getP());
			detailElement.setPrediction(predictiveAverage.getPrediction());			
			detailElement.setDatetime(predictiveAverage.getDatetime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			detailElements.add(detailElement);
		}
		
		List<com.eni.ioc.emission.dto.soredataserviceaverage.Element> elements = new ArrayList<>();
		com.eni.ioc.emission.dto.soredataserviceaverage.Element so2Element = new com.eni.ioc.emission.dto.soredataserviceaverage.Element();
		so2Element.setName(SO2_KEYNAME);
		so2Element.setDetailElement(detailElements);
		elements.add(so2Element);
		
		request.setAsset(ASSET_COVA);
		//request.setActualValue(true);
		request.setElement(elements);
	
		return request;
	}

	public static StoreDataServiceFlaringEventsRequest convertToFlaringEventRequest(List<CovaFlaringEvent> events, String keyName) {
		StoreDataServiceFlaringEventsRequest request = new StoreDataServiceFlaringEventsRequest();
		
		List<com.eni.ioc.emission.dto.soredataserviceflaringevent.DetailElement> detailElements = new ArrayList<>();
		for (CovaFlaringEvent a: events) {
			com.eni.ioc.emission.dto.soredataserviceflaringevent.DetailElement detailElement = new com.eni.ioc.emission.dto.soredataserviceflaringevent.DetailElement();
			
			detailElement.setAvgEmission(a.getAvgEmission());			
			detailElement.setEventLength(a.getEventLength());
			detailElement.setId(a.getId());
			detailElement.setMaxEmission(a.getMaxEmission());
			detailElement.setTotalEmission(a.getTotalEmission());
			detailElement.setKeyname(a.getKeyName());
			
			if(a.getEndEvent() != null) {
				detailElement.setEndEvent(a.getEndEvent().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			}
			detailElement.setStartEvent(a.getStartEvent().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

			detailElements.add(detailElement);
		}
		
		List<com.eni.ioc.emission.dto.soredataserviceflaringevent.Element> elements = new ArrayList<>();
		com.eni.ioc.emission.dto.soredataserviceflaringevent.Element flarElement = new com.eni.ioc.emission.dto.soredataserviceflaringevent.Element();
		flarElement.setName(keyName);
		flarElement.setDetailElement(detailElements);
		elements.add(flarElement);
		
		request.setAsset(ASSET_COVA);
		//request.setActualValue(true);
		request.setElement(elements);
	
		return request;
	}

	public static StoreDataServiceRootCause convertToTdaRequest(List<CovaRootCause> tda) {
		StoreDataServiceRootCause request = new StoreDataServiceRootCause();
		
		List<com.eni.ioc.emission.dto.soredataservicerootcause.DetailElement> detailElements = new ArrayList<>();
		for (CovaRootCause tdaStat: tda) {
			com.eni.ioc.emission.dto.soredataservicerootcause.DetailElement detailElement = new com.eni.ioc.emission.dto.soredataservicerootcause.DetailElement();
			detailElement.setCLAUS200(tdaStat.getCLAUS200());			
			detailElement.setCLAUS300(tdaStat.getCLAUS300());
			detailElement.setCLAUS400(tdaStat.getCLAUS400());
			detailElement.setRunid(tdaStat.getRunid());
			detailElement.setTDA(tdaStat.getTDA());
			detailElement.setTDB(tdaStat.getTDB());
			detailElement.setDatetime(tdaStat.getDatetime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			detailElements.add(detailElement);
		}
		
		List<com.eni.ioc.emission.dto.soredataservicerootcause.Element> elements = new ArrayList<>();
		com.eni.ioc.emission.dto.soredataservicerootcause.Element tdaElement = new com.eni.ioc.emission.dto.soredataservicerootcause.Element();

		tdaElement.setName(FLAR_KEYNAME);
		
		tdaElement.setDetailElement(detailElements);
		elements.add(tdaElement);
		
		request.setAsset(ASSET_COVA);
		//request.setActualValue(true);
		request.setElement(elements);
	
		return request;
	}

}
