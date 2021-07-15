package com.eni.ioc.assetintegrity.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.eni.ioc.assetintegrity.api.DERegistryFilter;
import com.eni.ioc.assetintegrity.api.MocRegistryInput;
import com.eni.ioc.assetintegrity.dto.AssetTable;
import com.eni.ioc.assetintegrity.dto.BacklogOperationalKpi;
import com.eni.ioc.assetintegrity.dto.CorrosionBacteriaDto;
import com.eni.ioc.assetintegrity.dto.CorrosionCNDElement;
import com.eni.ioc.assetintegrity.dto.CorrosionCNDTable;
import com.eni.ioc.assetintegrity.dto.CorrosionCouponDto;
import com.eni.ioc.assetintegrity.dto.CorrosionPiggingDto;
import com.eni.ioc.assetintegrity.dto.CorrosionProtectionDto;
import com.eni.ioc.assetintegrity.dto.CriticalSignalsOperationalKpi;
import com.eni.ioc.assetintegrity.dto.EWPDto;
import com.eni.ioc.assetintegrity.dto.KpiDataDto;
import com.eni.ioc.assetintegrity.dto.RegistroDeDto;
import com.eni.ioc.assetintegrity.dto.RegistroMocDto;
import com.eni.ioc.assetintegrity.enitities.AreasDto;
import com.eni.ioc.assetintegrity.enitities.CountCriticalDto;
import com.eni.ioc.assetintegrity.enitities.CriticalCount;
import com.eni.ioc.assetintegrity.enitities.CriticalSignalDto;
import com.eni.ioc.assetintegrity.enitities.DetailListDto;
import com.eni.ioc.assetintegrity.entities.Backlog;
import com.eni.ioc.assetintegrity.entities.CriticalSignal;
import com.eni.ioc.assetintegrity.entities.EVPMSData;
import com.eni.ioc.assetintegrity.entities.JacketedPipes;
import com.eni.ioc.assetintegrity.entities.TemplateFile;
import com.eni.ioc.assetintegrity.entities.WellAlarm;

/**
 * @author a.menolascina
 *
 */
public interface AssetIntegrityService {

	void updateKpiData(List<KpiDataDto> data, String table);

	CriticalCount getDashboard(String asset);

	CountCriticalDto countCriticalHigh(String table, String asset);

	DetailListDto showDetails(String table, String asset);

	CriticalCount countOthers(String tab);

	List<Backlog> backloglist(String tab, String area, String parentArea) throws ParseException;

	List<CriticalSignal> signcriticilist(String area, String parentArea);

	List<CriticalSignal> getAllSignals(String asset, Integer active);

	List<CriticalSignal> getAllSignals(String asset, Integer active, boolean fullSearch);

	List<WellAlarm> getWellAlarms(String asset);

	public Map<String, Map<String, Integer>> getEWPCounts(String asset);

	public Map<String, String> getEWPTags(String asset, String parentMap);

	List<EWPDto> getEwp(String asset, String area, String parentArea);

	AreasDto getEWPAreas(String asset,String parentMap,String parentArea) throws ParseException;

	AreasDto getAreas(String asset, Long id, String tab);

	List<CriticalSignalDto> signcriticilistDto(String area, String parentArea);

	CriticalSignalsOperationalKpi getCriticalSignalsKpi(String asset);

	BacklogOperationalKpi getBacklogKpi(String asset);

	public AssetTable<CorrosionBacteriaDto> getCorrosionBacteria(String asset, String area, String parentArea);

	public CorrosionCNDTable getCorrosionCNDCount(String asset, String area, String year, String parentArea);

	public List<CorrosionCNDElement> getCorrosionCND(String asset, String element, String year, String area, String parentArea);

	public AssetTable<CorrosionCouponDto> getCorrosionCoupon(String asset, String area, String parentArea);

	public AssetTable<CorrosionPiggingDto> getCorrosionPigging(String asset, String area, String parentArea);

	public AssetTable<CorrosionProtectionDto> getCorrosionProtection(String asset, String area, String parentArea);

	public List<CorrosionCouponDto> getCorrosionCouponElements(String asset, String code, String year, String area, String parentArea);

	public AssetTable<EVPMSData> getEVPMSData(String asset, String area, String parentArea);

	public AssetTable<JacketedPipes> getJacketedPipes(String asset, String area, String parentArea);

	public String parentArea(String subArea, String asset);

	public List<RegistroDeDto> getRegistroDE(DERegistryFilter data, String asset);

    public List<RegistroMocDto> getRegistroMoc(MocRegistryInput data);

	public ByteArrayOutputStream generateMOCRegistryExcel(MocRegistryInput data, String asset);

	public ByteArrayOutputStream generateDERegistryExcel(DERegistryFilter filter, String asset);

	public AreasDto getEWPSubAreas(String asset,String parentArea);

	TemplateFile getTemplate(String idPdf, String asset) throws IOException;

	DetailListDto showDetailsByDate(String table, String asset, String from, String to) throws ParseException;
}