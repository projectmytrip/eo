package com.eni.ioc.assetintegrity.dao;

import java.util.List;

import com.eni.ioc.assetintegrity.api.DERegistry;
import com.eni.ioc.assetintegrity.entities.BadJacketedPipes;
import com.eni.ioc.assetintegrity.entities.CorrosionBacteria;
import com.eni.ioc.assetintegrity.entities.CorrosionCND;
import com.eni.ioc.assetintegrity.entities.CorrosionCoupon;
import com.eni.ioc.assetintegrity.entities.CorrosionKpi;
import com.eni.ioc.assetintegrity.entities.CorrosionPigging;
import com.eni.ioc.assetintegrity.entities.CorrosionProtection;
import com.eni.ioc.assetintegrity.entities.EVPMSAlert;
import com.eni.ioc.assetintegrity.entities.EVPMSStation;
import com.eni.ioc.assetintegrity.entities.EWP;
import com.eni.ioc.assetintegrity.entities.IntegrityOperatingWindowKpi;
import com.eni.ioc.assetintegrity.entities.JacketedPipes;
import com.eni.ioc.assetintegrity.entities.OperatingWindowKpi;
import com.eni.ioc.assetintegrity.entities.RegistroMoc;
import com.eni.ioc.assetintegrity.entities.SegnaleCritico;
import com.eni.ioc.assetintegrity.entities.WeekWellAlarm;
import com.eni.ioc.assetintegrity.entities.WellAlarm;

/**
 * An Example DAO for JPA access
 * 
 * @author generated automatically by eni-msa-mw-archetype
 * 
 */
public interface AssetIntegrityDao {

	void insertSegnaliCritici(List<SegnaleCritico> segnaliCritici);

	void insertWellAlarm(List<WellAlarm> wellAlarm);

	boolean updateCriticalSignWF(String asset, List<String> element, String tmpKey, String wf, Boolean approved,
			Boolean closed, String start, String end);

	void insertWeekWellAlarm(List<WeekWellAlarm> wellAlarm);

	int callInsertBacklog();

	void insertFile801(List<String[]> file801);

	void insertFile701(List<String[]> file701);

	void updateCriticalSignalsActivationDates();

	public void insertBacklogTotalCount(List<String[]> parseCsvFile);

	public void insertOperatingWindow(List<OperatingWindowKpi> data);

	public void insertIntegrityOperatingWindow(List<IntegrityOperatingWindowKpi> data);

	public List<SegnaleCritico> getCriticalsByHashKey(String hashKey);

	public void insertCorrosionKpi(List<CorrosionKpi> data);

	public void insertCorrosionBacteria(List<CorrosionBacteria> data);

	public void insertCorrosionCND(List<CorrosionCND> input);

	public void insertCorrosionCoupon(List<CorrosionCoupon> input);

	public void insertCorrosionProtection(List<CorrosionProtection> input);

	public void insertCorrosionPigging(List<CorrosionPigging> input);

	public void truncateCND();

	public void truncateTable(String tableName);

	public void truncateCorrosionCoupon(String tab);

	public void saveEVPMSStations(List<EVPMSStation> data);

	public void saveEVPMSAlerts(List<EVPMSAlert> data);

	public void insertJackedPipes(List<JacketedPipes> data);

	public void insertEWP(EWP ewp);
	
	String getPreviousEWPState(String ewp);
	
	void insertMoc(RegistroMoc input);
	
	void insertDE(DERegistry input);
	
	List<SegnaleCritico> getCriticalByNameList(List<String> signals);
	
	void updateRegistroDe();
	
	void saveBadJackedPipes(List<BadJacketedPipes> badPipes);
	
	List<BadJacketedPipes> getBadJackedPipes(String asset);
}
