package com.eni.ioc.assetintegrity.dao;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eni.ioc.assetintegrity.enitities.CriticalSignalDto;
import com.eni.ioc.assetintegrity.entities.Shift;

/**
 * An Example DAO for JPA access
 *
 * @author generated automatically by eni-msa-mw-archetype
 */
public interface AssetIntegrityDaoWf {

	void insertSegnaliCriticiWf(String hash, List<com.eni.ioc.assetintegrity.enitities.CriticalSignal> signals,
			Date start, Date end);

	void storeWFRequest(String list, String asset, String duration, String emergency, String notes, String requestType,
			String sap, Date startDate);

	List<CriticalSignalDto> getCriticalSignalsWarnings(String asset);

	List<CriticalSignalDto> getSignalStatus(String asset, String wfId);

	void updateSignalMoc(CriticalSignalDto input, String asset, String uid) throws Exception;

	Map<String, List<CriticalSignalDto>> getCriticalSignalsWithOpenWF(String asset);

	void storeMocRequest(String title, String number, Date date, String reasons, String otherReason, String priority,
			String type, Date typeDate, String description, String documentation, String signals, String user,
			String uid);

	List<CriticalSignalDto> pollingMOC(List<String> input, String asset);

	//List<Shift> getShifts();

	List<CriticalSignalDto> getCriticalSignalsWarningsByDatetimeRange(String asset, LocalDateTime startRange, LocalDateTime endRange);

}
