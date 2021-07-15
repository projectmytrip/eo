package com.eni.ioc.assetintegrity.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eni.ioc.assetintegrity.api.DERegistryFilter;
import com.eni.ioc.assetintegrity.api.MocRegistryInput;
import com.eni.ioc.assetintegrity.enitities.AreaDto;
import com.eni.ioc.assetintegrity.enitities.CountCriticalDto;
import com.eni.ioc.assetintegrity.enitities.CriticalCount;
import com.eni.ioc.assetintegrity.enitities.CriticalSignalDto;
import com.eni.ioc.assetintegrity.enitities.DetailListDto;
import com.eni.ioc.assetintegrity.entities.*;

/**
 * An Example DAO for JPA access
 *
 * @author generated automatically by eni-msa-mw-archetype
 *
 */
public interface AssetIntegrityDao {

	void updateKpiData(List<KpiData> dataList);

	CriticalCount getDashboard(String asset);

	CountCriticalDto countCriticalHigh(String table, String asset);

	CountCriticalDto countCriticalHighWell(String table, String asset);

	DetailListDto showDetailsPlant(String table, String asset);

	DetailListDto showDetailsNetwork(String table, String asset);

	DetailListDto showDetailsOil(String table, String asset);

	DetailListDto showDetailsReservoir(String table, String asset);

	DetailListDto showDetailsWell(String table, String asset);

	CriticalCount countOthers(String tab);

	List<Backlog> backloglist(String tab, String area, String parentArea);

	public Long getBacklogODMCount(String asset);

	List<CriticalSignal> signcriticilist(String area, String parentArea);

	List<WellAlarm> getWellAlarms(String asset);

	List<EWP> getEwp(String asset);

	List<EWP> getEwp(String asset, String area, String parentArea);

	List<AreaDto> getAreas(String asset, Long id);

	List<AreaDto> getEWPAreasWithCriticalCount(String asset, String parentMap,String parentArea);

	List<AreaDto> getAreasWithCriticalCount(String asset, Long id, String tab);

	List<CriticalSignal> getSegnaliCriticiPerWorkflow(String asset, Integer active, boolean fullSearch);

	void insertSegnaliCriticiWf(String hash, List<com.eni.ioc.assetintegrity.enitities.CriticalSignal> signals,
			Date start, Date end);

	List<CriticalSignalDto> signcriticilistDto(String area, String parentArea);

	void storeWFRequest(String list, String asset, String duration, String emergency, String notes, String requestType,
			String sap, Date startDate);

	public Map<String, Long> getCriticalSignalsSeverityCount(String asset);

	public long getCriticalSignalsTotalCount(String asset);

	public Map<String, Long> getMocTotalCount(String asset);

	public Map<String, Long> getBacklogTypeCount(String asset);

	public String getLastInsertionDate(String asset);

	public List<OperatingWindowKpi> getOperatingWindowKpi(String asset);

	public List<IntegrityOperatingWindowKpi> getIntegrityOperatingWindowKpi(String asset);

	public List<CorrosionKpi> getCorrosionKpi(String asset);

	public List<CorrosionBacteria> getCorrosionBacteria(String asset, String area, String parentArea);

	public Map<String, Long[]> getCorrosionCND(String asset, String area, String year, String parentArea);

	public Long getCriticalCountCorrosionCND(String asset, String area, String parentArea);

	public List<CorrosionCND> getCorrosionCNDElements(String asset, String element, String year, String area, String parentArea);

	public List<CorrosionProtection> getCorrosionProtection(String asset, String area, String parentArea);

	public List<CorrosionPigging> getCorrosionPigging(String asset, String area, String parentArea);

	public List<CorrosionCoupon> getCorrosionCoupon(String asset, String area, String parentArea);

	public String[] getYearsCorrosionCoupon(String asset, String area, String parentArea);

	public String[] getYearsCorrosionCND(String asset, String area, String parentArea);

	public List<CorrosionCoupon> getCorrosionCoupoElements(String asset, String code, String year, String area, String parentArea);

	public List<EVPMSData> getEVPMSData(String asset, String area, String parentArea);

	public List<JacketedPipes> getJacketedPipes(String asset, String area, String parentArea);

	public String getParentArea(String subArea, String asset);

	List<RegistroMoc> getRegistroMoc(MocRegistryInput data);

	List<RegistroDe> getRegistroDE(DERegistryFilter filter, String asset);

	public List<AreaDto> getEWPSubAreas(String asset, String parentArea);

	public Map<String, List<String>> getAreasMap(String asset);

	List<EWP> getEwpWithTags(String asset, String parentMap);

	DetailListDto showDetailsNetworkByDate(String table, String asset, String fromDate, String toDate);

	DetailListDto showDetailsPlantByDate(String table, String asset, String fromDate, String toDate);

	DetailListDto showDetailsReservoirByDate(String table, String asset, String fromDate, String toDate);

	DetailListDto showDetailsOilByDate(String table, String asset, String fromDate, String toDate);

	List<BacklogStateHistory> getBacklogStateHistory(List<String> odmList);

}
