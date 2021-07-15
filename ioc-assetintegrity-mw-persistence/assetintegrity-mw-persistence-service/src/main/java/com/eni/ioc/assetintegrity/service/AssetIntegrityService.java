package com.eni.ioc.assetintegrity.service;

import com.eni.ioc.assetintegrity.api.DERegistry;
import com.eni.ioc.assetintegrity.dto.storedataservice.CorrosionBacteriaDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.CorrosionCNDItem;
import com.eni.ioc.assetintegrity.dto.storedataservice.CorrosionCouponDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.CorrosionKpiDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.CorrosionPiggingDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.CorrosionProtectionDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.IntegrityOperatingWindowsDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.JacketedPipesDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.OperatingWindowsDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.SegnaleCriticoApi;
import com.eni.ioc.assetintegrity.dto.storedataservice.StoreCriticalSignalsWfMOCRequest;
import com.eni.ioc.assetintegrity.dto.storedataservice.StoreCriticalSignalsWfRequest;
import com.eni.ioc.assetintegrity.dto.storedataservice.StoreDataServiceEVPMS;
import com.eni.ioc.assetintegrity.dto.storedataservice.StoreDataServiceRequest;
import com.eni.ioc.assetintegrity.dto.storedataservice.StoreDataServiceWellRequest;
import com.eni.ioc.assetintegrity.dto.storedataservice.ewpBarrierPanel;
import com.eni.ioc.assetintegrity.entities.RegistroMoc;

public interface AssetIntegrityService {

	void insertWellAlarm(StoreDataServiceWellRequest input);

	void insertWeekWellAlarm(StoreDataServiceWellRequest input);

	void insertSapDocument(byte[] file701, byte[] file801);

	boolean insertSegnaliCriticiWf(StoreCriticalSignalsWfRequest input);

	void updateCriticalSignalsActivationDates();

	void insertSegnaliCritici(StoreDataServiceRequest<SegnaleCriticoApi> input);

	void insertBacklogTotalCount(byte[] file);

	public void insertOperatingWindow(StoreDataServiceRequest<OperatingWindowsDto> input);

	public void insertIntegrityOperatingWindow(StoreDataServiceRequest<IntegrityOperatingWindowsDto> input);

	public void insertCorrosionKpi(StoreDataServiceRequest<CorrosionKpiDto> input);

	public void insertCorrosionBacteria(StoreDataServiceRequest<CorrosionBacteriaDto> input);

	public void insertCorrosionCND(StoreDataServiceRequest<CorrosionCNDItem> input);

	public void insertCorrosionCoupon(StoreDataServiceRequest<CorrosionCouponDto> input, String tab);

	public void insertCorrosionProtection(StoreDataServiceRequest<CorrosionProtectionDto> input);

	public void insertCorrosionPigging(StoreDataServiceRequest<CorrosionPiggingDto> input);

	public void insertEVPMS(StoreDataServiceEVPMS input);

	public void insertJacketedPipes(StoreDataServiceRequest<JacketedPipesDto> input);

	public void insertMoc(RegistroMoc input);

	public void insertEWP(StoreDataServiceRequest<ewpBarrierPanel> input);

	public void insertRegistroMoc(RegistroMoc input);

	public void insertRegistroDe(DERegistry input);

	void updateSignalsMOC_DE(StoreCriticalSignalsWfRequest input, String asset);

	void insertUpdateSignalsMOC(StoreCriticalSignalsWfMOCRequest input, String asset);

	void updateRegistroDe();

	void sendBadJackedPipesMail(String asset);
}
