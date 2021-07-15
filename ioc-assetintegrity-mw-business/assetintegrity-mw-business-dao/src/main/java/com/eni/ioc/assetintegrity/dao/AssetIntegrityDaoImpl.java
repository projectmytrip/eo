package com.eni.ioc.assetintegrity.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eni.ioc.assetintegrity.api.DERegistryFilter;
import com.eni.ioc.assetintegrity.api.MocRegistryInput;
import com.eni.ioc.assetintegrity.enitities.AreaDto;
import com.eni.ioc.assetintegrity.enitities.AssetIntegrityTable;
import com.eni.ioc.assetintegrity.enitities.CountCriticalDto;
import com.eni.ioc.assetintegrity.enitities.CriticalCount;
import com.eni.ioc.assetintegrity.enitities.CriticalSignalDto;
import com.eni.ioc.assetintegrity.enitities.DetailDto;
import com.eni.ioc.assetintegrity.enitities.DetailListDto;
import com.eni.ioc.assetintegrity.entities.Backlog;
import com.eni.ioc.assetintegrity.entities.BacklogStateHistory;
import com.eni.ioc.assetintegrity.entities.CorrosionBacteria;
import com.eni.ioc.assetintegrity.entities.CorrosionCND;
import com.eni.ioc.assetintegrity.entities.CorrosionCoupon;
import com.eni.ioc.assetintegrity.entities.CorrosionKpi;
import com.eni.ioc.assetintegrity.entities.CorrosionPigging;
import com.eni.ioc.assetintegrity.entities.CorrosionProtection;
import com.eni.ioc.assetintegrity.entities.CriticalSignal;
import com.eni.ioc.assetintegrity.entities.EVPMSData;
import com.eni.ioc.assetintegrity.entities.EWP;
import com.eni.ioc.assetintegrity.entities.IntegrityOperatingWindowKpi;
import com.eni.ioc.assetintegrity.entities.JacketedPipes;
import com.eni.ioc.assetintegrity.entities.KpiData;
import com.eni.ioc.assetintegrity.entities.OperatingWindowKpi;
import com.eni.ioc.assetintegrity.entities.RegistroDe;
import com.eni.ioc.assetintegrity.entities.RegistroMoc;
import com.eni.ioc.assetintegrity.entities.WellAlarm;
import com.eni.ioc.assetintegrity.entities.WfRequest;
import com.eni.ioc.assetintegrity.utils.AssetIntegrityException;

/**
 *
 * @author generated automatically by eni-msa-mw-archetype
 *
 */

@Repository
@Transactional
public class AssetIntegrityDaoImpl implements AssetIntegrityDao {

	private static final Logger logger = LoggerFactory.getLogger(AssetIntegrityDaoImpl.class);
	private static final String DATABASE_TIMEZONE = "UTC";
	private static final String APPLICATION_TIMEZONE = "CET";
	private static final String WARNING_LEVEL = "WARNING";

	private static final String CHIUSURA = "CHIUSURA";

	private static final String OK_LEVEL = "OK";
    private static final String BUCKET_AREA = "BULK";


	private static final String NOW_APP_TIMEZONE = "CONVERT_TZ(NOW(), '" + DATABASE_TIMEZONE + "', '"
			+ APPLICATION_TIMEZONE + "')";

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void updateKpiData(List<KpiData> dataList) {
		for (KpiData data : dataList) {
			entityManager.persist(data);
		}
	}

	@Override
	public Map<String, List<String>> getAreasMap(String asset){
		logger.debug("Called getAreasMap");
		try {
			Map<String, List<String>> res = new HashMap<>();
			StringBuilder querySql = new StringBuilder();
            querySql.append(" SELECT ea.PARENT_AREA as PARENT_AREA, ta.DECODED_VALUE as CODE, ea.NAME as NAME FROM ASSETINT.EWP_AREAS ea LEFT JOIN ASSETINT.Transcodifica_Aree ta ON (ea.NAME = ta.AREA) where PARENT_AREA not in (select distinct NAME from ASSETINT.EWP_AREAS)  and PARENT_MAP = 1");
			Query sql = entityManager.createNativeQuery(querySql.toString());
			logger.info(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					String parent = (String)object[0];
					String subArea = (String)object[1];
					List<String> subAreas = res.get(parent);
					if(subAreas == null) subAreas = new ArrayList<>();

					subAreas.add(subArea);
					res.put(parent, subAreas);
				}
			}
			return res;
		} catch (Exception e) {
			logger.error("error during getAreasMap ", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public List<AreaDto> getEWPSubAreas(String asset, String parentArea) {
		List<AreaDto> res = new ArrayList<>();

		// TODO: AGGIUNGERE JOIN CON "sort" di 801 e 701 per logica dei colori

		logger.debug("Called getEWPSubAreas");
		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append(" SELECT DISTINCT MAP_COORDS, COLOR, COLOR2, ");
			querySql.append(" b.Descrizione as DESCRIPTION, NAME, SHAPE, PARENT_MAP, PARENT_AREA, ");
			querySql.append(" ta.DECODED_VALUE as CODE ");
			querySql.append(" FROM ASSETINT.EWP_AREAS a LEFT JOIN ASSETINT.Transcodifica_Aree b on b.Area IN (a.NAME, a.PARENT_AREA) ");
			querySql.append(" LEFT JOIN ASSETINT.Transcodifica_Aree ta on ta.Area = a.NAME ");
			querySql.append(" WHERE ASSET = '").append(asset).append("'")
					.append(" AND a.ACTIVE = 'Y' and a.PARENT_AREA = '").append(parentArea).append("'")
					.append(" GROUP BY NAME, MAP_COORDS, COLOR, COLOR2,   b.Descrizione,  SHAPE, PARENT_MAP, PARENT_AREA, CODE");


			logger.info(querySql.toString());
			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					AreaDto o = new AreaDto();
					o.setMapCoords((String)object[0]);
					o.setColor((String)object[1]);
					o.setColor2((String)object[2]);
					o.setDescription((String)object[3]);
					o.setName((String)object[4]);
					o.setShape((String)object[5]);
					o.setParentMap((String)object[6]);
					o.setParentArea((String)object[7]);
					o.setCode((String)object[8]);
					res.add(o);
				}
			}
			return res;
		} catch (Exception e) {
			logger.error("error during getEWPSubAreas ", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public CriticalCount getDashboard(String asset) {
		CriticalCount res = new CriticalCount();
		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append("SELECT ");
			querySql.append(
					" (SELECT COUNT(DISTINCT ODM) FROM ASSETINT.BACKLOG WHERE STR_TO_DATE(DATA_CHIUSURA_PREV, '%d/%m/%Y') < DATE(now()) and TAB is not null) as a, "); // crit
																																										// BACKLOG
			querySql.append(
					" (SELECT COUNT(DISTINCT ODM) FROM ASSETINT.BACKLOG WHERE STR_TO_DATE(DATA_CHIUSURA_PREV, '%d/%m/%Y') >= DATE(now()) and TAB is not null) as a2, "); // high
																																											// BACKLOG
			querySql.append(

					" (SELECT count(distinct SC.NAME) FROM ASSETINT.`SEGNALI CRITICI` SC left join ASSETINT.SEGNALI_WF SWF ON SC.NAME = SWF.NAME "); // crit
			// SIGNALS
			querySql.append(
					" WHERE BlockInput = 'TRUE' and `BlockInput Status` = '1' and (SWF.CHIUSO = 1 or SWF.CHIUSO is NULL) and (DATA_INIZIO is null and DATA_FINE is null || DATA_INIZIO <= "
							+ NOW_APP_TIMEZONE + " and DATA_FINE >= " + NOW_APP_TIMEZONE + ")) as b, ");
			querySql.append(
					" (SELECT count(distinct SC2.NAME) FROM ASSETINT.`SEGNALI CRITICI` SC2 left join ASSETINT.SEGNALI_WF SWF2 ON SC2.NAME = SWF2.NAME "); // high
			// SIGNALS
			querySql.append(
					" WHERE BlockInput = 'TRUE' and `BlockInput Status` = '0' and (SWF2.CHIUSO = 1 or SWF2.CHIUSO is NULL) and (DATA_INIZIO is null and DATA_FINE is null || DATA_INIZIO <= "
							+ NOW_APP_TIMEZONE + " and DATA_FINE >= " + NOW_APP_TIMEZONE + ")) as b2, ");

			querySql.append(
					" ( ( SELECT  count(*) count FROM ASSETINT.CORROSION_CND_VIEW CCV JOIN ASSETINT.Transcodifica_Aree ta on ta.decoded_value = CCV.area  where ASSET = '")
					.append(asset).append("' AND NEXT_DATE IS NOT NULL AND NEXT_DATE < DATE(NOW()) )") // CND
																										// COUNT
					.append(" + ")
					.append(" ( SELECT  count(DISTINCT(ANALYSIS_POINT)) count from ASSETINT.CORROSION_BACTERIA CB JOIN ASSETINT.Transcodifica_Aree ta on ta.decoded_value = CB.area  where ASSET = '")
					.append(asset).append("' AND (SRB_VALUE > 100 OR APB_VALUE > 100) ) ") // BACTERIA
																							// COUNT
					.append(" + ").append(" (  SELECT  count(*) count FROM ASSETINT.CORROSION_PROTECTION CP ")
					.append(" JOIN ASSETINT.Transcodifica_Aree ta on ta.decoded_value = CP.area ")
					.append(" JOIN (select  SECTION, TAG, max(last_date) max ")
					.append(" from ASSETINT.CORROSION_PROTECTION ")
					.append(" group by SECTION, TAG) as max_date on max_date.section =  CP.section AND CP.TAG = max_date.tag AND CP.last_date = max_date.max ")
					.append(" where ASSET = '").append(asset).append("' AND (PROTECTED_SIDE_OFF >= -0.85")
					.append(" OR NOT_PROTECTED_SIDE_OFF >= -0.85 OR EXTERNAL_CONDUIT_OFF >= -0.85 OR TUBE_OFF >= -0.85  ) ) ")
					.append(" + ").append(" ( SELECT  count(*) count FROM ASSETINT.CORROSION_PIGGING CP ")
					.append(" JOIN ASSETINT.Transcodifica_Aree ta on ta.decoded_value = CP.area ")
					.append(" JOIN (select SECTION, FEATURE, max(last_date) max ")
					.append(" from ASSETINT.CORROSION_PIGGING ").append(" where erf is not null ")
					.append(" group by SECTION, FEATURE) as max_date on max_date.section =  CP.section AND CP.feature = max_date.feature")
					.append(" where ASSET = '").append(asset).append("' AND ERF >= 1 )").append(" + ")
					.append("  ( SELECT  count(*) count FROM ASSETINT.CORROSION_COUPON CC ")
					.append(" JOIN ASSETINT.Transcodifica_Aree ta on ta.decoded_value = CC.area ")
					.append(" JOIN (select CODE, max(last_date) max ").append(" from ASSETINT.CORROSION_COUPON ")
					.append(" group by CODE) as max_date on max_date.code =  CC.code and CC.last_date = max_date.max ")
					.append(" where ASSET = '").append(asset).append("' AND MPY >= 1 )").append(" ) as c, "); // crit
																												// CORROSION

			querySql.append(" (SELECT  count(*) count FROM ASSETINT.CORROSION_COUPON CC ")
					.append(" JOIN ASSETINT.Transcodifica_Aree ta on ta.decoded_value = CC.area ");
			querySql.append(" JOIN (select CODE, max(last_date) max ");
			querySql.append(" from ASSETINT.CORROSION_COUPON ");
			querySql.append(" group by CODE) as max_date on max_date.code =  CC.code and CC.last_date = max_date.max ");
			querySql.append(" where ASSET = '").append(asset)
					.append("' AND MPY < 1 AND NEXT_DATE IS NOT NULL AND NEXT_DATE < DATE(NOW()) ");
			querySql.append(" ) as c2, "); // high CORROSION

			querySql.append(" (SELECT null) as d, "); // crit INSPECT
			querySql.append(" (SELECT null) as d2 "); // high INSPECT

			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] obj : lista) {
					res.setBacklogCrit((Long) obj[0]);
					res.setBacklogHigh((Long) obj[1]);
					res.setCriticalCrit((Long) obj[2]);
					res.setCriticalHigh((Long) obj[3]);
					res.setCorrosionCrit((Long) obj[4]);
					res.setCorrosionHigh((Long) obj[5]);
					res.setInspectionCrit((Long) obj[6]);
					res.setInspectionHigh((Long) obj[7]);
				}
			}
			return res;
		} catch (Exception e) {
			logger.error("error during showDetailsPlant", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EWP> getEwp(String asset, String area, String parentArea) {
		// in teoria non useremo mai parentArea negli EWP

		Query sql = null;
		if(area.equals(BUCKET_AREA)) {
			sql = entityManager.createQuery("select e from EWP e where e.asset = :asset and e.status != '"+CHIUSURA+"'", EWP.class);
		} else {
			sql = entityManager.createQuery("select e from EWP e where e.asset = :asset and ((e.sottoArea like :area) or (e.sottoArea like :parentArea)) and e.status != '"+CHIUSURA+"'",
				EWP.class);
			sql.setParameter("area", "%|"+area+"|%");
			sql.setParameter("parentArea", "%|"+parentArea+"|%" );
		}

		sql.setParameter("asset", asset);

		return sql.getResultList();

	}


	@SuppressWarnings("unchecked")
	@Override
	public List<EWP> getEwp(String asset) {
		logger.debug("Called getEwp");
		List<EWP> res = new ArrayList<>();
		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append(" select TIPOLOGIA_ATTIVITA, AREA, SCE from ASSETINT.EWP where FASE != '").append(CHIUSURA)
					.append("' and ASSET = '").append(asset).append("'");

			logger.debug(querySql.toString());

			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					EWP o = new EWP();
					o.setIndicazioneTipologiaAttivita((String) object[0]);
					o.setSottoArea((String) object[1]);
					o.setIndicazioneSCE((String) object[2]);
					res.add(o);
				}
			}
			return res;
		} catch (Exception e) {
			logger.error(String.format("error during showDetailsPlant %s", asset), e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<EWP> getEwpWithTags(String asset, String parentMap) {
		logger.debug("Called getEwp");
		List<EWP> res = new ArrayList<>();
		try {
			StringBuilder querySql = new StringBuilder();
			querySql
			.append("SELECT e.SORT as TAGS, e.TIPOLOGIA_ATTIVITA as LAVORI, e.SCE as SCE, ta.DECODED_VALUE AS AREA, eareas.PARENT_AREA as PARENT_AREA FROM ASSETINT.EWP e ")
			.append("LEFT JOIN ASSETINT.Transcodifica_Aree ta ON (ta.DECODED_VALUE = '")
			.append(parentMap).append("') ")
			.append("LEFT JOIN ASSETINT.EWP_AREAS eareas ON (eareas.NAME = ta.AREA) where ( e.AREA LIKE '%|")
			.append(parentMap).append("|%' OR e.AREA LIKE CONCAT('%|', eareas.PARENT_AREA, '|%'))")
			.append(" and e.FASE != '").append(CHIUSURA).append("' and e.SORT != ''")
			.append(" and e.ASSET = '").append(asset).append("'");

			logger.debug(querySql.toString());

			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					EWP o = new EWP();
					o.setTags((String) object[0]);
					o.setIndicazioneTipologiaAttivita((String) object[1]);
					o.setIndicazioneSCE((String) object[2]);
					res.add(o);
				}
			}
			return res;
		} catch (Exception e) {
			logger.error(String.format("error during showDetailsPlant %s", asset), e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public DetailListDto showDetailsNetworkByDate(String table, String asset, String fromDate, String toDate) {
		logger.debug("Called showDetailsNetworkByDate");
		DetailListDto res = new DetailListDto();
		List<DetailDto> results = new ArrayList<>();
		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append(
					" SELECT P.ID AS ID, P.DESCRIZIONE AS DESCRIZIONE, P.TOOLTIP_MSG AS TOOLTIP_MSG, P.TIPO_KPI AS TIPO_KPI,  ");
			querySql.append(" D.INSERTION_DATE ");
			querySql.append(" AS INSERTION_DATE, P.FREQ_REGISTRAZIONE AS FREQ_REGISTRAZIONE, P.ASSET AS ASSET, ");
			querySql.append(" D.VALORE AS VALORE, D.STATO AS STATO, D.ULTIMO_UPDATE AS ULTIMO_UPDATE ");
			querySql.append(" FROM ASSETINT.KPI_NETWORK P LEFT JOIN (");
			querySql.append(" SELECT * FROM ASSETINT.KPI_NETWORKDATA ");
			querySql.append(" WHERE  (ID, INSERTION_DATE) IN ");
			querySql.append(" (SELECT ID, MAX(INSERTION_DATE) MAX ");
			querySql.append(" FROM ASSETINT.KPI_NETWORKDATA K ");
			querySql.append(" WHERE (K.ID, STR_TO_DATE(K.ULTIMO_UPDATE, '%Y-%m-%d')) IN ");
			querySql.append(" (SELECT ID, MAX(STR_TO_DATE(ULTIMO_UPDATE, '%Y-%m-%d')) FROM ASSETINT.KPI_NETWORKDATA ");
			querySql.append(" WHERE STR_TO_DATE(ULTIMO_UPDATE, '%Y-%m-%d') >= '").append(fromDate).append("'");
			querySql.append(" AND STR_TO_DATE(ULTIMO_UPDATE, '%Y-%m-%d') <= '").append(toDate).append("'");
			querySql.append(" GROUP BY ID) ");
			querySql.append(" GROUP BY ID) ");
			querySql.append(" ) D ON (P.ID = D.ID) ");
			querySql.append(" WHERE P.ASSET = '").append(asset).append("' ");
			querySql.append("ORDER BY LENGTH(P.ID), P.ID ");

			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					DetailDto o = new DetailDto();
					o.setId((String) object[0]);
					o.setDescrizione((String) object[1]);
					o.setTooltip((String) object[2]);
					o.setTipoKpi((String) object[3]);
					o.setInsertionDate((Date)object[4]);
					o.setFreqReg((String) object[5]);
					o.setAsset((String) object[6]);
					o.setValore((String) object[7]);
					o.setStatus((Integer) object[8]);
					o.setLastUpdate((String) object[9]);
					results.add(o);
				}
			}
			res.setList(results);
			res.setName(table.toUpperCase());

			return res;
		} catch (Exception e) {
			logger.error(String.format("error during showDetailsNetworkByDate %s", asset), e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public DetailListDto showDetailsPlantByDate(String table, String asset, String fromDate, String toDate) {
		logger.debug("Called showDetailsPlantByDate");
		DetailListDto res = new DetailListDto();
		List<DetailDto> results = new ArrayList<>();
		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append(
					" SELECT P.ID AS ID, P.DESCRIZIONE AS DESCRIZIONE, P.TOOLTIP_MSG AS TOOLTIP_MSG, P.TIPO_KPI AS TIPO_KPI, ");
			querySql.append(" D.INSERTION_DATE ");
			querySql.append(" AS INSERTION_DATE, P.FREQ_REGISTRAZIONE AS FREQ_REGISTRAZIONE, P.ASSET AS ASSET, ");
			querySql.append(" D.VALORE AS VALORE, D.STATO AS STATO, D.ULTIMO_UPDATE AS ULTIMO_UPDATE ");
			querySql.append(" FROM ASSETINT.KPI_PLANT P LEFT JOIN (");
			querySql.append(" SELECT * FROM ASSETINT.KPI_PLANTDATA ");
			querySql.append(" WHERE  (ID, INSERTION_DATE) IN ");
			querySql.append(" (SELECT ID, MAX(INSERTION_DATE) MAX ");
			querySql.append(" FROM ASSETINT.KPI_PLANTDATA K ");
			querySql.append(" WHERE (K.ID, STR_TO_DATE(K.ULTIMO_UPDATE, '%Y-%m-%d')) IN ");
			querySql.append(" (SELECT ID, MAX(STR_TO_DATE(ULTIMO_UPDATE, '%Y-%m-%d')) FROM ASSETINT.KPI_PLANTDATA ");
			querySql.append(" WHERE STR_TO_DATE(ULTIMO_UPDATE, '%Y-%m-%d') >= '").append(fromDate).append("'");
			querySql.append(" AND STR_TO_DATE(ULTIMO_UPDATE, '%Y-%m-%d') <= '").append(toDate).append("'");
			querySql.append(" GROUP BY ID) ");
			querySql.append(" GROUP BY ID) ");
			querySql.append(" ) D ON (P.ID = D.ID) ");
			querySql.append(" WHERE P.ASSET = '").append(asset).append("' ");
			querySql.append("ORDER BY LENGTH(P.ID), P.ID ");

			logger.debug(querySql.toString());

			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					DetailDto o = new DetailDto();
					o.setId((String) object[0]);
					o.setDescrizione((String) object[1]);
					o.setTooltip((String) object[2]);
					o.setTipoKpi((String) object[3]);
					o.setInsertionDate((Date)object[4]);
					o.setFreqReg((String) object[5]);
					o.setAsset((String) object[6]);
					o.setValore((String) object[7]);
					o.setStatus((Integer) object[8]);
					o.setLastUpdate((String) object[9]);
					results.add(o);
				}
				res.setList(results);
				res.setName(table.toUpperCase());
			}
			return res;
		} catch (Exception e) {
			logger.error(String.format("error during showDetailsPlantByDate %s", asset), e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public DetailListDto showDetailsReservoirByDate(String table, String asset, String fromDate, String toDate) {
		logger.debug("Called showDetailsReservoirByDate");
		DetailListDto res = new DetailListDto();
		List<DetailDto> results = new ArrayList<>();
		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append(" SELECT P.ID AS ID, P.DESCRIZIONE AS DESCRIZIONE, P.TOOLTIP_MSG AS TOOLTIP_MSG, ");
			querySql.append(" D.INSERTION_DATE ");
			querySql.append(" AS INSERTION_DATE,P.FREQ_REGISTRAZIONE AS FREQ_REGISTRAZIONE, P.ASSET AS ASSET, ");
			querySql.append(" D.VALORE AS VALORE, D.STATO AS STATO, D.ULTIMO_UPDATE AS ULTIMO_UPDATE ");
			querySql.append(" FROM ASSETINT.KPI_RESERVOIR P LEFT JOIN (");
			querySql.append(" SELECT * FROM ASSETINT.KPI_RESERVOIRDATA ");
			querySql.append(" WHERE  (ID, INSERTION_DATE) IN ");
			querySql.append(" (SELECT ID, MAX(INSERTION_DATE) MAX ");
			querySql.append(" FROM ASSETINT.KPI_RESERVOIRDATA K ");
			querySql.append(" WHERE (K.ID, STR_TO_DATE(K.ULTIMO_UPDATE, '%Y-%m-%d')) IN ");
			querySql.append(" (SELECT ID, MAX(STR_TO_DATE(ULTIMO_UPDATE, '%Y-%m-%d')) FROM ASSETINT.KPI_RESERVOIRDATA");
			querySql.append(" WHERE STR_TO_DATE(ULTIMO_UPDATE, '%Y-%m-%d') >= '").append(fromDate).append("'");
			querySql.append(" AND STR_TO_DATE(ULTIMO_UPDATE, '%Y-%m-%d') <= '").append(toDate).append("'");
			querySql.append(" GROUP BY ID) ");
			querySql.append(" GROUP BY ID) ");
			querySql.append(" ) D ON (P.ID = D.ID) ");
			querySql.append(" WHERE P.ASSET = '").append(asset).append("' ");
			querySql.append("ORDER BY LENGTH(P.ID), P.ID ");

			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					DetailDto o = new DetailDto();
					o.setId((String) object[0]);
					o.setDescrizione((String) object[1]);
					o.setTooltip((String) object[2]);
					o.setInsertionDate((Date)object[3]);
					o.setFreqReg((String) object[4]);
					o.setAsset((String) object[5]);
					o.setValore((String) object[6]);
					o.setStatus((Integer) object[7]);
					o.setLastUpdate((String) object[8]);
					results.add(o);
				}
			}
			res.setList(results);
			res.setName(table.toUpperCase());
			return res;
		} catch (Exception e) {
			logger.error(String.format("error during showDetailsReservoirByDate %s", asset), e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public DetailListDto showDetailsOilByDate(String table, String asset, String fromDate, String toDate) {
		logger.debug("Called showDetailsOilByDate");
		DetailListDto res = new DetailListDto();
		List<DetailDto> results = new ArrayList<>();
		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append(
					" SELECT P.ID AS ID, P.DESCRIZIONE AS DESCRIZIONE, P.TOOLTIP_MSG AS TOOLTIP_MSG, P.TIPO_KPI AS TIPO_KPI,  ");
			querySql.append(" D.INSERTION_DATE ");
			querySql.append(" AS INSERTION_DATE, P.FREQ_REGISTRAZIONE AS FREQ_REGISTRAZIONE, P.ASSET AS ASSET, ");
			querySql.append(" D.VALORE AS VALORE, D.STATO AS STATO, D.ULTIMO_UPDATE AS ULTIMO_UPDATE ");
			querySql.append(" FROM ASSETINT.KPI_OIL P LEFT JOIN (");
			querySql.append(" SELECT * FROM ASSETINT.KPI_OILDATA ");
			querySql.append(" WHERE  (ID, INSERTION_DATE) IN ");
			querySql.append(" (SELECT ID, MAX(INSERTION_DATE) MAX ");
			querySql.append(" FROM ASSETINT.KPI_OILDATA K ");
			querySql.append(" WHERE (K.ID, STR_TO_DATE(K.ULTIMO_UPDATE, '%Y-%m-%d')) IN ");
			querySql.append(" (SELECT ID, MAX(STR_TO_DATE(ULTIMO_UPDATE, '%Y-%m-%d')) FROM ASSETINT.KPI_OILDATA ");
			querySql.append(" WHERE STR_TO_DATE(ULTIMO_UPDATE, '%Y-%m-%d') >= '").append(fromDate).append("'");
			querySql.append(" AND STR_TO_DATE(ULTIMO_UPDATE, '%Y-%m-%d') <= '").append(toDate).append("'");
			querySql.append(" GROUP BY ID) ");
			querySql.append(" GROUP BY ID) ");
			querySql.append(" ) D ON (P.ID = D.ID) ");
			querySql.append(" WHERE P.ASSET = '").append(asset).append("' ");
			querySql.append("ORDER BY LENGTH(P.ID), P.ID ");

			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					DetailDto o = new DetailDto();
					o.setId((String) object[0]);
					o.setDescrizione((String) object[1]);
					o.setTooltip((String) object[2]);
					o.setTipoKpi((String) object[3]);
					o.setInsertionDate((Date)object[4]);
					o.setFreqReg((String) object[5]);
					o.setAsset((String) object[6]);
					o.setValore((String) object[7]);
					o.setStatus((Integer) object[8]);
					o.setLastUpdate((String) object[9]);
					results.add(o);
				}
			}
			res.setList(results);
			res.setName(table.toUpperCase());

			return res;
		} catch (Exception e) {
			logger.error(String.format("error during showDetailsOilByDate %s", asset), e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public CountCriticalDto countCriticalHigh(String table, String asset) {
		CountCriticalDto res = new CountCriticalDto();
		try {
			StringBuilder querySql = new StringBuilder();
			switch (AssetIntegrityTable.valueOf(table.toUpperCase())) {
			case PLANT:
				querySql.append("SELECT COUNT(*), STATO FROM ASSETINT.KPI_PLANTDATA D");
				querySql.append(" JOIN (SELECT ID, MAX(INSERTION_DATE) MAX FROM ASSETINT.KPI_PLANTDATA GROUP BY ID)");
				querySql.append(" MAX_DATE ON MAX_DATE.ID = D.ID AND MAX_DATE.MAX = D.INSERTION_DATE ");
				querySql.append(" GROUP BY STATO ORDER BY STATO");
				break;
			case OIL:
				querySql.append("SELECT COUNT(*), STATO FROM ASSETINT.KPI_OILDATA D");
				querySql.append(" JOIN (SELECT ID, MAX(INSERTION_DATE) MAX FROM ASSETINT.KPI_OILDATA GROUP BY ID)");
				querySql.append(" MAX_DATE ON MAX_DATE.ID = D.ID AND MAX_DATE.MAX = D.INSERTION_DATE ");
				querySql.append(" GROUP BY STATO ORDER BY STATO");
				break;
			case NETWORK:
				querySql.append("SELECT COUNT(*), STATO FROM ASSETINT.KPI_NETWORKDATA D");
				querySql.append(" JOIN (SELECT ID, MAX(INSERTION_DATE) MAX FROM ASSETINT.KPI_NETWORKDATA GROUP BY ID)");
				querySql.append(" MAX_DATE ON MAX_DATE.ID = D.ID AND MAX_DATE.MAX = D.INSERTION_DATE ");
				querySql.append(" GROUP BY STATO ORDER BY STATO");
				break;
			case RESERVOIR:
				querySql.append("SELECT COUNT(*), STATO FROM ASSETINT.KPI_RESERVOIRDATA D");
				querySql.append(" JOIN (SELECT ID, MAX(INSERTION_DATE) MAX FROM ASSETINT.KPI_RESERVOIRDATA GROUP BY ID)");
				querySql.append(" MAX_DATE ON MAX_DATE.ID = D.ID AND MAX_DATE.MAX = D.INSERTION_DATE ");
				querySql.append(" GROUP BY STATO ORDER BY STATO");
				break;

			case WELL:
				querySql.append(
						"SELECT WD.N, W.STATUS FROM ASSETINT.KPI_WELL W LEFT JOIN ASSETINT.KPI_WELLDATA WD ON (W.ID = WD.ID) GROUP BY WD.N, W.STATUS ORDER BY W.STATUS");
				break;
			default:
				logger.warn(String.format("no valid table %s", table));
				break;
			}

			logger.debug(querySql.toString());

			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					Integer status = (Integer) object[1];
					if (status != null && status == 3) {
						if (object[0] instanceof String) {
							String crit = (String) object[0];
							if (crit != null)
								res.setCritical(Long.valueOf(crit));
						} else if (object[0] instanceof Long) {
							res.setCritical((Long) object[0]);
						}

					}
					if (status != null && status == 2) {
						if (object[0] instanceof String) {
							String high = (String) object[0];
							if (high != null)
								res.setHigh(Long.valueOf(high));
						} else if (object[0] instanceof Long) {
							res.setHigh((Long) object[0]);
						}
					}
				}
			}
			res.setName(table.toUpperCase());
			return res;
		} catch (Exception e) {
			logger.error("error during showDetailsPlant", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public CountCriticalDto countCriticalHighWell(String table, String asset) {
		CountCriticalDto res = new CountCriticalDto();
		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append("SELECT WELL_WEEK_ALARM_COVA_VIEW.GENERAL_ALARM, "
					+ " COUNT(WELL_WEEK_ALARM_COVA_VIEW.WELL_CODE ) AS WELLS " + "FROM ASSETINT.KPI_WELL "
					+ "LEFT JOIN ASSETINT.WELL_WEEK_ALARM_COVA_VIEW ON WELL_WEEK_ALARM_COVA_VIEW.GENERAL_ALARM = KPI_WELL.GENERAL_ALARM "
					+ "WHERE WELL_WEEK_ALARM_COVA_VIEW.REF_DATE = (SELECT MAX(WELL_WEEK_ALARM_COVA_VIEW.REF_DATE) "
					+ " FROM ASSETINT.WELL_WEEK_ALARM_COVA_VIEW) AND WELL_WEEK_ALARM_COVA_VIEW.GENERAL_ALARM != 'OK' "
					+ "GROUP BY WELL_WEEK_ALARM_COVA_VIEW.GENERAL_ALARM, KPI_WELL.GENERAL_ALARM,"
					+ "WELL_WEEK_ALARM_COVA_VIEW.REF_DATE, KPI_WELL.ID ORDER BY KPI_WELL.ID LIMIT 2; " + "");

			logger.debug(querySql.toString());

			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();

			if (lista != null && !lista.isEmpty()) {
				res.setCritical(0L);
				res.setHigh(0L);

				for (Object[] object : lista) {
					if (object[0] instanceof String && ((String) object[0]).equals(WARNING_LEVEL)) {
						Long crit = (Long) object[1];
						if (crit != null)
							res.setHigh(crit);
					} else if (object[0] instanceof String && !((String) object[0]).equals(WARNING_LEVEL)) {
						Long crit = (Long) object[1];
						if (crit != null)
							res.setCritical(crit);
					}
				}

			}
			res.setName(table.toUpperCase());
			return res;
		} catch (Exception e) {
			logger.error("error during showDetailsPlant", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public DetailListDto showDetailsPlant(String table, String asset) {
		logger.debug("Called showDetailsPlant");
		DetailListDto res = new DetailListDto();
		List<DetailDto> results = new ArrayList<>();
		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append(
					" SELECT P.ID AS ID, P.DESCRIZIONE AS DESCRIZIONE, P.TOOLTIP_MSG AS TOOLTIP_MSG, P.TIPO_KPI AS TIPO_KPI, ");
			// ambiente
			// querySql.append("
			// CONVERT_TZ(INSERTION_DATE,'"+DATABASE_TIMEZONE+"','"+APPLICATION_TIMEZONE+"')
			// ");
			// locale
			querySql.append(" D.INSERTION_DATE ");
			querySql.append(" AS INSERTION_DATE, P.FREQ_REGISTRAZIONE AS FREQ_REGISTRAZIONE, P.ASSET AS ASSET, ");
			querySql.append(" D.VALORE AS VALORE, D.STATO AS STATO, D.ULTIMO_UPDATE AS ULTIMO_UPDATE ");
			querySql.append(" FROM ASSETINT.KPI_PLANT P LEFT JOIN ASSETINT.KPI_PLANTDATA D ON (P.ID = D.ID) ");
			querySql.append(" JOIN (SELECT ID, MAX(INSERTION_DATE) MAX ");
			querySql.append("FROM ASSETINT.KPI_PLANTDATA GROUP BY ID) ");
			querySql.append("MAX_DATE ON MAX_DATE.ID = D.ID AND MAX_DATE.MAX = D.INSERTION_DATE ");
			querySql.append(" WHERE P.ASSET = '").append(asset).append("' ");
			querySql.append("ORDER BY LENGTH(P.ID), P.ID ");

			logger.debug(querySql.toString());

			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					DetailDto o = new DetailDto();
					o.setId((String) object[0]);
					o.setDescrizione((String) object[1]);
					o.setTooltip((String) object[2]);
					o.setTipoKpi((String) object[3]);
					o.setInsertionDate((Date)object[4]);
					o.setFreqReg((String) object[5]);
					o.setAsset((String) object[6]);
					o.setValore((String) object[7]);
					o.setStatus((Integer) object[8]);
					o.setLastUpdate((String) object[9]);
					results.add(o);
				}
				res.setList(results);
				res.setName(table.toUpperCase());
			}
			return res;
		} catch (Exception e) {
			logger.error(String.format("error during showDetailsPlant %s", asset), e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public DetailListDto showDetailsNetwork(String table, String asset) {
		logger.debug("Called showDetailsNetwork");
		DetailListDto res = new DetailListDto();
		List<DetailDto> results = new ArrayList<>();
		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append(
					" SELECT P.ID AS ID, P.DESCRIZIONE AS DESCRIZIONE, P.TOOLTIP_MSG AS TOOLTIP_MSG, P.TIPO_KPI AS TIPO_KPI,  ");
			// ambiente
			// querySql.append("
			// CONVERT_TZ(INSERTION_DATE,'"+DATABASE_TIMEZONE+"','"+APPLICATION_TIMEZONE+"')
			// ");
			// locale
			querySql.append(" D.INSERTION_DATE ");
			querySql.append(" AS INSERTION_DATE, P.FREQ_REGISTRAZIONE AS FREQ_REGISTRAZIONE, P.ASSET AS ASSET, ");
			querySql.append(" D.VALORE AS VALORE, D.STATO AS STATO, D.ULTIMO_UPDATE AS ULTIMO_UPDATE ");
			querySql.append(" FROM ASSETINT.KPI_NETWORK P LEFT JOIN ASSETINT.KPI_NETWORKDATA D ON (P.ID = D.ID) ");
			querySql.append(" JOIN (SELECT ID, MAX(INSERTION_DATE) MAX ");
			querySql.append("FROM ASSETINT.KPI_NETWORKDATA GROUP BY ID) ");
			querySql.append("MAX_DATE ON MAX_DATE.ID = D.ID AND MAX_DATE.MAX = D.INSERTION_DATE ");
			querySql.append(" WHERE P.ASSET = '").append(asset).append("' ");
			querySql.append("ORDER BY LENGTH(P.ID), P.ID ");

			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					DetailDto o = new DetailDto();
					o.setId((String) object[0]);
					o.setDescrizione((String) object[1]);
					o.setTooltip((String) object[2]);
					o.setTipoKpi((String) object[3]);
					o.setInsertionDate((Date)object[4]);
					o.setFreqReg((String) object[5]);
					o.setAsset((String) object[6]);
					o.setValore((String) object[7]);
					o.setStatus((Integer) object[8]);
					o.setLastUpdate((String) object[9]);
					results.add(o);
				}
			}
			res.setList(results);
			res.setName(table.toUpperCase());

			return res;
		} catch (Exception e) {
			logger.error(String.format("error during showDetailsNetwork %s", asset), e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public DetailListDto showDetailsOil(String table, String asset) {
		logger.debug("Called showDetailsOil");
		DetailListDto res = new DetailListDto();
		List<DetailDto> results = new ArrayList<>();
		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append(
					" SELECT P.ID AS ID, P.DESCRIZIONE AS DESCRIZIONE, P.TOOLTIP_MSG AS TOOLTIP_MSG, P.TIPO_KPI AS TIPO_KPI,  ");
			// ambiente
			// querySql.append("
			// CONVERT_TZ(INSERTION_DATE,'"+DATABASE_TIMEZONE+"','"+APPLICATION_TIMEZONE+"')
			// ");
			// locale
			querySql.append(" D.INSERTION_DATE ");
			querySql.append(" AS INSERTION_DATE, P.FREQ_REGISTRAZIONE AS FREQ_REGISTRAZIONE, P.ASSET AS ASSET, ");
			querySql.append(" D.VALORE AS VALORE, D.STATO AS STATO, D.ULTIMO_UPDATE AS ULTIMO_UPDATE ");
			querySql.append(" FROM ASSETINT.KPI_OIL P LEFT JOIN ASSETINT.KPI_OILDATA D ON (P.ID = D.ID) ");
			querySql.append(" JOIN (SELECT ID, MAX(INSERTION_DATE) MAX ");
			querySql.append("FROM ASSETINT.KPI_OILDATA GROUP BY ID) ");
			querySql.append("MAX_DATE ON MAX_DATE.ID = D.ID AND MAX_DATE.MAX = D.INSERTION_DATE ");
			querySql.append(" WHERE P.ASSET = '").append(asset).append("' ");
			querySql.append("ORDER BY LENGTH(P.ID), P.ID ");

			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					DetailDto o = new DetailDto();
					o.setId((String) object[0]);
					o.setDescrizione((String) object[1]);
					o.setTooltip((String) object[2]);
					o.setTipoKpi((String) object[3]);
					o.setInsertionDate((Date)object[4]);
					o.setFreqReg((String) object[5]);
					o.setAsset((String) object[6]);
					o.setValore((String) object[7]);
					o.setStatus((Integer) object[8]);
					o.setLastUpdate((String) object[9]);
					results.add(o);
				}
			}
			res.setList(results);
			res.setName(table.toUpperCase());

			return res;
		} catch (Exception e) {
			logger.error(String.format("error during showDetailsOil %s", asset), e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public DetailListDto showDetailsReservoir(String table, String asset) {
		logger.debug("Called showDetailsReservoir");
		DetailListDto res = new DetailListDto();
		List<DetailDto> results = new ArrayList<>();
		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append(" SELECT P.ID AS ID, P.DESCRIZIONE AS DESCRIZIONE, P.TOOLTIP_MSG AS TOOLTIP_MSG, ");
			// ambiente
			// querySql.append("
			// CONVERT_TZ(INSERTION_DATE,'"+DATABASE_TIMEZONE+"','"+APPLICATION_TIMEZONE+"')
			// ");
			// locale
			querySql.append(" D.INSERTION_DATE ");
			querySql.append(" AS INSERTION_DATE,P.FREQ_REGISTRAZIONE AS FREQ_REGISTRAZIONE, P.ASSET AS ASSET, ");
			querySql.append(" D.VALORE AS VALORE, D.STATO AS STATO, D.ULTIMO_UPDATE AS ULTIMO_UPDATE ");
			querySql.append(" FROM ASSETINT.KPI_RESERVOIR P LEFT JOIN ASSETINT.KPI_RESERVOIRDATA D ON (P.ID = D.ID) ");
			querySql.append(" JOIN (SELECT ID, MAX(INSERTION_DATE) MAX ");
			querySql.append("FROM ASSETINT.KPI_RESERVOIRDATA GROUP BY ID) ");
			querySql.append("MAX_DATE ON MAX_DATE.ID = D.ID AND MAX_DATE.MAX = D.INSERTION_DATE ");
			querySql.append(" WHERE P.ASSET = '").append(asset).append("' ");
			querySql.append("ORDER BY LENGTH(P.ID), P.ID ");

			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					DetailDto o = new DetailDto();
					o.setId((String) object[0]);
					o.setDescrizione((String) object[1]);
					o.setTooltip((String) object[2]);
					o.setInsertionDate((Date)object[3]);
					o.setFreqReg((String) object[4]);
					o.setAsset((String) object[5]);
					o.setValore((String) object[6]);
					o.setStatus((Integer) object[7]);
					o.setLastUpdate((String) object[8]);
					results.add(o);
				}
			}
			res.setList(results);
			res.setName(table.toUpperCase());
			return res;
		} catch (Exception e) {
			logger.error(String.format("error during showDetailsReservoir %s", asset), e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public DetailListDto showDetailsWell(String table, String asset) {
		logger.debug("Called showDetailsWell");
		DetailListDto res = new DetailListDto();
		List<DetailDto> results = new ArrayList<>();
		try {
			StringBuilder querySql = new StringBuilder();

			/*
			 * querySql.
			 * append(" SELECT P.ID AS ID, P.WELL_ALARM_STATUS AS WELL_ALARM_STATUS, P.STATUS AS STATUS, "
			 * ); //ambiente //querySql.append(" CONVERT_TZ(INSERTION_DATE,'"
			 * +DATABASE_TIMEZONE+"','"+ APPLICATION_TIMEZONE+"') "); //locale
			 * querySql.append(" P.INSERTION_DATE "); querySql.
			 * append(" AS INSERTION_DATE, P.FREQ_REGISTRAZIONE AS FREQ_REGISTRAZIONE, P.ASSET AS ASSET,  "
			 * ); querySql.
			 * append(" D.N AS N, D.PERC AS PERC, D.ULTIMO_UPDATE AS ULTIMO_UPDATE "
			 * ); querySql.
			 * append(" FROM ASSETINT.KPI_WELL P LEFT JOIN ASSETINT.KPI_WELLDATA D ON (P.ID = D.ID)"
			 * );
			 * querySql.append(" WHERE P.ASSET = '").append(asset).append("' ");
			 * querySql.append("ORDER BY LENGTH(P.ID), P.ID ");
			 */

			querySql.append(
					"SELECT KPI_WELL.ID, KPI_WELL.WELL_ALARM_STATUS AS DESCRIZIONE, KPI_WELL.STATUS, KPI_WELL.FREQ_REGISTRAZIONE, KPI_WELL.ASSET, ");
			querySql.append(
					" COUNT(WELL_WEEK_ALARM_COVA_VIEW.WELL_CODE ) AS WELLS , CAST(WELL_WEEK_ALARM_COVA_VIEW.REF_DATE " +
							"AS CHAR) AS LAST_UPDATE, MAX(WELL_WEEK_ALARM_COVA_VIEW.INSERTION_DATE) AS INSERTION_DATE ");
			querySql.append(" FROM ASSETINT.KPI_WELL  ");
			querySql.append(
					" LEFT JOIN ASSETINT.WELL_WEEK_ALARM_COVA_VIEW ON WELL_WEEK_ALARM_COVA_VIEW.GENERAL_ALARM = KPI_WELL.GENERAL_ALARM  ");
			querySql.append(
					"WHERE WELL_WEEK_ALARM_COVA_VIEW.REF_DATE = (SELECT MAX(WELL_WEEK_ALARM_COVA_VIEW.REF_DATE) FROM ASSETINT.WELL_WEEK_ALARM_COVA_VIEW) ");
			querySql.append(" OR WELL_WEEK_ALARM_COVA_VIEW.REF_DATE IS NULL  ");
			querySql.append(
					"GROUP BY WELL_WEEK_ALARM_COVA_VIEW.GENERAL_ALARM, KPI_WELL.GENERAL_ALARM, WELL_WEEK_ALARM_COVA_VIEW.REF_DATE,  KPI_WELL.ID ORDER BY KPI_WELL.ID LIMIT 3  ");

			logger.info(querySql.toString());
			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			Integer totalNumberOfWells = 0;
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					totalNumberOfWells = totalNumberOfWells + ((Long) object[5]).intValue();
				}
			}

			String lastUpdate = "";

			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					DetailDto o = new DetailDto();
					o.setId((String) object[0]);
					o.setDescrizione((String) object[1]);
					o.setStatus((Integer) object[2]);
					o.setFreqReg((String) object[3]);
					o.setAsset((String) object[4]);
					Long valore = ((Long) object[5]);
					o.setValore("" + valore);
					Float perc = (float) (valore * 100f / totalNumberOfWells);
					String strPerc = String.format("%.2f", perc);
					strPerc = strPerc.replace(',', '.');
					o.setPerc(strPerc + "%");
					if (object[6] != null && !((String) object[6]).equals("null")) {
						lastUpdate = (String) object[6];
					}
					o.setLastUpdate(lastUpdate);
					o.setInsertionDate((Date)object[7]);
					results.add(o);
				}
			}
			res.setList(results);
			res.setName(table.toUpperCase());
			return res;
		} catch (Exception e) {
			logger.error(String.format("error during showDetailsWell %s", asset), e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public CriticalCount countOthers(String tab) {
		CriticalCount res = new CriticalCount();

		try {
			StringBuilder querySql = new StringBuilder();

			querySql.append("SELECT ");
			querySql.append(
					" (SELECT COUNT(DISTINCT ODM) FROM ASSETINT.BACKLOG WHERE STR_TO_DATE(DATA_CHIUSURA_PREV, '%d/%m/%Y') < DATE(now()) and TAB = '")
					.append(tab).append("') as a, "); // crit BACKLOG
			querySql.append(
					" (SELECT COUNT(DISTINCT ODM) FROM ASSETINT.BACKLOG WHERE STR_TO_DATE(DATA_CHIUSURA_PREV, '%d/%m/%Y') >= DATE(now()) and TAB = '")
					.append(tab).append("') as a2, "); // high BACKLOG
			querySql.append(
					" (SELECT count(distinct SC.NAME) FROM ASSETINT.`SEGNALI CRITICI` SC left join ASSETINT.SEGNALI_WF SWF ON SC.NAME = SWF.NAME "); // crit
			// SIGNALS
			querySql.append(
					" WHERE BlockInput = 'TRUE' and `BlockInput Status` = '1' and (SWF.CHIUSO = 1 or SWF.CHIUSO is NULL) and (DATA_INIZIO is null and DATA_FINE is null || DATA_INIZIO <= "
							+ NOW_APP_TIMEZONE + " and DATA_FINE >= " + NOW_APP_TIMEZONE + ")) as b, ");
			querySql.append(
					" (SELECT count(distinct SC2.NAME) FROM ASSETINT.`SEGNALI CRITICI` SC2 left join ASSETINT.SEGNALI_WF SWF2 ON SC2.NAME = SWF2.NAME "); // high
			// SIGNALS
			querySql.append(
					" WHERE BlockInput = 'TRUE' and `BlockInput Status` = '0' and (SWF2.CHIUSO = 1 or SWF2.CHIUSO is NULL) and (DATA_INIZIO is null and DATA_FINE is null || DATA_INIZIO <= "
							+ NOW_APP_TIMEZONE + " and DATA_FINE >= " + NOW_APP_TIMEZONE + ")) as b2, ");

			querySql.append(" (SELECT null) as c, "); // crit CORROSION
			querySql.append(" (SELECT null) as c2, "); // high CORROSION
			querySql.append(" (SELECT null) as d, "); // crit INSPECT
			querySql.append(" (SELECT null) as d2 "); // high INSPECT

			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					res.setBacklogCrit((Long) object[0]);
					res.setBacklogHigh((Long) object[1]);
					res.setCriticalCrit((Long) object[2]);
					res.setCriticalHigh((Long) object[3]);
					res.setCorrosionCrit((Long) object[4]);
					res.setCorrosionHigh((Long) object[5]);
					res.setInspectionCrit((Long) object[6]);
					res.setInspectionHigh((Long) object[7]);
				}
			}
			return res;

		} catch (Exception e) {
			logger.error("error during showDetailsPlant", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}

	}

	@Override
	public List<Backlog> backloglist(String tab, String area, String parentArea) {


		logger.debug("Called backloglist");
		List<Backlog> results = new ArrayList<>();
		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append(" SELECT N_ODM, ODM, TIPO_MANUTENZIONE, COMPONENTE, ");
			querySql.append(" COMPONENTE_INFO, TIPO_ELEMENTO, ");
			querySql.append(" DATA_CHIUSURA_PREV, UNITA_FUNZIONALE, bk.TAB, bk.AREA, ");
			querySql.append(" DURATA, UNITA, STATO_UTENTE_ODM, DATA_INIZIO_CARDINE ");
			querySql.append(" FROM ASSETINT.BACKLOG bk");
			if (area != null) {
				querySql.append(" JOIN ASSETINT.Transcodifica_Aree ta on bk.area = ta.decoded_Value ");
			}
			querySql.append(" WHERE bk.TAB = '").append(tab).append("' ");
			if (area != null) {
				querySql.append(" AND ta.area in ").append(areasToListStringified(area, parentArea)).append(" ");
			}
			querySql.append(" ORDER BY ODM,COMPONENTE ");

			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					Backlog o = new Backlog();
					o.setnOdm((String) object[0]);
					o.setOdm((String) object[1]);
					o.setTipoManutenzione((String) object[2]);
					o.setComponente((String) object[3]);
					o.setComponenteInfo((String) object[4]);
					o.setTipoElemento((String) object[5]);
					o.setDataChiusuraPrev((String) object[6]);
					o.setUnitaFunzionale((String) object[7]);
					o.setTab((String) object[8]);
					o.setArea((String) object[9]);
					o.setDurata((String) object[10]);
					o.setUnita((String) object[11]);
					o.setStatoUtenteOdm((String) object[12]);
					o.setDataInizioCardine((String) object[13]);
					results.add(o);
				}
			}
			return results;
		} catch (Exception e) {
			logger.error("error during backloglist ", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public List<CriticalSignal> signcriticilist(String area, String parentArea) {
		logger.debug("Called signcriticilist");
		List<CriticalSignal> results = new ArrayList<>();
		try {

			StringBuilder querySql = new StringBuilder();
			querySql.append(" SELECT Area, LastStatusChangeDate, Value, ");
			querySql.append(" Category, Name, ");
			querySql.append(" Description, BlockInput, `BlockInput Status` ");
			querySql.append(" FROM ASSETINT.`SEGNALI CRITICI` SC ");
			if (area != null) {
				querySql.append(" JOIN ASSETINT.Transcodifica_Aree ta on SC.area = ta.decoded_Value ");
			}
			querySql.append(
					" WHERE BlockInput = 'TRUE' AND `BlockInput Status` in ('0','1') and `BlockInput Status` != '' ");
			if (area != null) {
				querySql.append(" AND ta.AREA in ").append(areasToListStringified(area, parentArea)).append(" ");
			}
			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					CriticalSignal o = new CriticalSignal();
					o.setArea((String) object[0]);
					o.setLastStatusChangeDate((String) object[1]);
					o.setValue((String) object[2]);
					o.setCategory((String) object[3]);
					o.setName((String) object[4]);
					o.setDescription((String) object[5]);
					o.setBlockInput((String) object[6]);
					o.setBlockInputStatus((String) object[7]);
					results.add(o);
				}
			}
			return results;
		} catch (Exception e) {
			logger.error("error during signcriticilist ", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public List<AreaDto> getAreas(String asset, Long id) {
		List<AreaDto> res = new ArrayList();

		logger.debug("Called getAreas");
		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append(" SELECT DISTINCT ID, MAP_COORDS, COLOR, COLOR2, ");
			querySql.append(" b.Descrizione as DESCRIPTION, NAME, SHAPE, ASSET ");
			querySql.append(" FROM ASSETINT.AREAS a left join ASSETINT.Transcodifica_Aree b on a.NAME = b.Area ");
			querySql.append(" WHERE ASSET = '").append(asset).append("' AND PARENT_MAP = ").append(id)
					.append(" AND a.ACTIVE = 'Y'");

			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					AreaDto o = new AreaDto();
					// o.setId((Integer)object[0]);
					o.setMapCoords((String) object[1]);
					o.setColor((String) object[2]);
					o.setColor2((String) object[3]);
					// set description
					o.setDescription((String) object[4]);
					o.setName((String) object[5]);
					o.setShape((String) object[6]);
					// o.setAsset((String)object[7]);
					res.add(o);
				}
			}
			return res;
		} catch (Exception e) {
			logger.error("error during backloglist ", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public List<CriticalSignal> getSegnaliCriticiPerWorkflow(String asset, Integer active, boolean fullSearch) {
		logger.debug("Called getSegnaliCriticiPerWorkflow");
		List<CriticalSignal> results = new ArrayList<>();

		try {

			StringBuilder querySql = new StringBuilder();
			querySql.append(" SELECT SC.NAME, Area, LastStatusChangeDate, Value, Category,");
			querySql.append(" Description, BlockInput, `BlockInput Status`, Severity, Asset ");
			querySql.append(" FROM ASSETINT.`SEGNALI CRITICI` SC ");
			if(fullSearch){
				querySql.append(" WHERE Asset = '").append(asset).append("'");
			} else {
				if (active == null) {
					// default behaviour
					querySql.append(" WHERE Asset = '").append(asset).append("'");
					querySql.append("and SC.NAME NOT IN ( SELECT NAME FROM ASSETINT.SEGNALI_WF ");
					querySql.append(" WHERE (CHIUSO = 0) ) ");
				} else {
					querySql.append(" WHERE BlockInput = '").append(active == 1 ? "True" : "False").append("'");
				}
			}

			Query sql = entityManager.createNativeQuery(querySql.toString());

			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					CriticalSignal o = new CriticalSignal();
					o.setName((String) object[0]);
					o.setArea((String) object[1]);
					o.setLastStatusChangeDate((String) object[2]);
					o.setValue((String) object[3]);
					o.setCategory((String) object[4]);
					o.setDescription((String) object[5]);
					o.setBlockInput((String) object[6]);
					o.setBlockInputStatus((String) object[7]);
					o.setSeverity((String) object[8]);
					o.setAsset((String) object[9]);
					results.add(o);
				}
			}
			return results;
		} catch (Exception e) {
			logger.error("error during getSegnaliCriticiPerWorkflow ", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public List<AreaDto> getEWPAreasWithCriticalCount(String asset, String parentMap, String parentArea) {
		Instant start = Instant.now();

		List<AreaDto> res = new ArrayList<>();

		logger.debug("Called getAreas");
		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append(" SELECT DISTINCT MAP_COORDS, COLOR, COLOR2, ");

			querySql.append(" b.Descrizione as DESCRIPTION, NAME, SHAPE, PARENT_MAP, PARENT_AREA, ");
			querySql.append(" IFNULL(SUM(crit_backlog.count),0.0), IFNULL(SUM(high_backlog.count),0.0), ");
			querySql.append(" IFNULL(SUM(crit_signals.count),0.0), IFNULL(SUM(high_signals.count),0.0), ");
			querySql.append(" IFNULL(SUM(CND_CRITICAL.count),0.0), ");
			querySql.append(" IFNULL(SUM(BACTERIA_CRITICAL.count),0.0), ");
			querySql.append(" IFNULL(SUM(COUPON_CRITICAL.count),0.0), ");
			querySql.append(" IFNULL(SUM(COUPON_HIGH.count),0.0), ");
			querySql.append(" IFNULL(SUM(PIGGING_CRITICAL.count),0.0), ");
			querySql.append(" IFNULL(SUM(PROTECTION_CRITICAL.count),0.0), ");
			querySql.append(" IFNULL(SUM(EVPMS_CRIT.count), 0.0), IFNULL(SUM(EVPMS_HIGH.count),0.0),  ");
			querySql.append(" IFNULL(SUM(PIPES_CRIT.count), 0.0), IFNULL(SUM(PIPES_HIGH.count),0.0),  ");
			querySql.append(" ta.DECODED_VALUE as CODE ");
			querySql.append(
					" FROM ASSETINT.EWP_AREAS a LEFT JOIN ASSETINT.Transcodifica_Aree b on b.Area IN (a.NAME, a.PARENT_AREA) ");
			querySql.append(
					" LEFT JOIN ASSETINT.Transcodifica_Aree ta on ta.Area = a.NAME ");

			// Joins with different critical count
			querySql.append(
					" LEFT JOIN (SELECT COUNT(DISTINCT ODM) count, AREA FROM ASSETINT.BACKLOG WHERE STR_TO_DATE(DATA_CHIUSURA_PREV, '%d/%m/%Y') < DATE(now()) and TAB = '")
					.append(asset).append("' GROUP BY area)  as crit_backlog on crit_backlog.area = b.decoded_value "); // crit
			// BACKLOG
			querySql.append(
					" LEFT JOIN (SELECT COUNT(DISTINCT ODM) count, AREA FROM ASSETINT.BACKLOG WHERE STR_TO_DATE(DATA_CHIUSURA_PREV, '%d/%m/%Y') >= DATE(now()) and TAB = '")
					.append(asset).append("' GROUP BY area)  as high_backlog on high_backlog.area = b.decoded_value "); // high
			// BACKLOG
			querySql.append(
					" LEFT JOIN (SELECT count(distinct SC.NAME) count, AREA FROM ASSETINT.`SEGNALI CRITICI` SC LEFT JOIN ASSETINT.SEGNALI_WF SWF ON SC.NAME = SWF.NAME WHERE BlockInput = 'TRUE' and `BlockInput Status` = '1' and (SWF.CHIUSO = 1 or SWF.CHIUSO is NULL) and (DATA_INIZIO is null and DATA_FINE is null || DATA_INIZIO <= "
							+ NOW_APP_TIMEZONE + " and DATA_FINE >= " + NOW_APP_TIMEZONE
							+ ") GROUP BY area) as crit_signals on crit_signals.area = b.decoded_value "); // crit
			// SIGNALS
			querySql.append(
					" LEFT JOIN (SELECT count(distinct SC2.NAME) count, AREA FROM ASSETINT.`SEGNALI CRITICI` SC2 LEFT JOIN ASSETINT.SEGNALI_WF SWF2 ON SC2.NAME = SWF2.NAME WHERE BlockInput = 'TRUE' and `BlockInput Status` = '0' and (SWF2.CHIUSO = 1 or SWF2.CHIUSO is NULL) and (DATA_INIZIO is null and DATA_FINE is null || DATA_INIZIO <= "
							+ NOW_APP_TIMEZONE + " and DATA_FINE >= " + NOW_APP_TIMEZONE
							+ ") GROUP BY area)  AS high_signals on high_signals.area = b.decoded_value "); // high

			// CORROSION CND
			querySql.append(
					" LEFT JOIN ( SELECT  count(*) count, AREA FROM ASSETINT.CORROSION_CND_VIEW where ASSET = '")
					.append(asset)
					.append("' AND NEXT_DATE IS NOT NULL AND NEXT_DATE < DATE(NOW()) GROUP BY AREA ) as CND_CRITICAL on CND_CRITICAL.area = b.decoded_value ");
			querySql.append(
					" LEFT JOIN ( SELECT  count(DISTINCT(ANALYSIS_POINT)) count, AREA from ASSETINT.CORROSION_BACTERIA where ASSET = '")
					.append(asset)
					.append("' AND (SRB_VALUE > 100 OR APB_VALUE > 100) GROUP BY AREA ) as BACTERIA_CRITICAL on BACTERIA_CRITICAL.area = b.decoded_value ");

			// CORROSION COUPON CRITICAL
			querySql.append(" LEFT JOIN ( SELECT  count(*) count, AREA FROM ASSETINT.CORROSION_COUPON CC ");
			querySql.append(" JOIN (select CODE, max(last_date) max ");
			querySql.append(" from ASSETINT.CORROSION_COUPON ");
			querySql.append(" group by CODE) as max_date on max_date.code =  CC.code and CC.last_date = max_date.max ");
			querySql.append(" where ASSET = '").append(asset).append(
					"' AND MPY >= 1  GROUP BY AREA ) as COUPON_CRITICAL on COUPON_CRITICAL.area = b.decoded_value ");
			// CORROSION COUPON HIGH
			querySql.append(" LEFT JOIN ( SELECT  count(*) count, CC.AREA FROM ASSETINT.CORROSION_COUPON CC ");
			querySql.append(" JOIN (select CODE, AREA, max(last_date) max ");
			querySql.append(" from ASSETINT.CORROSION_COUPON ");
			querySql.append(
					" group by CODE, AREA) as max_date on max_date.code =  CC.code and CC.last_date = max_date.max AND CC.AREA = max_date.AREA ");
			querySql.append(" where ASSET = '").append(asset).append(
					"' AND MPY < 1 AND NEXT_DATE IS NOT NULL AND NEXT_DATE < DATE(NOW()) GROUP BY AREA ) as COUPON_HIGH on COUPON_HIGH.area = b.decoded_value ");
			// CORROSION PIGGING
			querySql.append(" LEFT JOIN ( SELECT  count(*) count, CP.AREA FROM ASSETINT.CORROSION_PIGGING CP ");
			querySql.append(" JOIN (select AREA, SECTION, FEATURE, max(last_date) max ");
			querySql.append(" from ASSETINT.CORROSION_PIGGING ");
			querySql.append(" where erf is not null ");
			querySql.append(
					" group by SECTION, FEATURE, AREA) as max_date on max_date.section =  CP.section AND CP.feature = max_date.feature AND CP.AREA = max_date.AREA  ");
			querySql.append(" where ASSET = '").append(asset).append(
					"' AND ERF >= 1 GROUP BY AREA) as PIGGING_CRITICAL on PIGGING_CRITICAL.area = b.decoded_value ");
			// CORROSION PROTECTION
			querySql.append(" LEFT JOIN ( SELECT  count(*) count, CP.AREA FROM ASSETINT.CORROSION_PROTECTION CP ");
			querySql.append(" JOIN (select AREA, SECTION, TAG, max(last_date) max ");
			querySql.append(" from ASSETINT.CORROSION_PROTECTION ");
			querySql.append(
					" group by SECTION, TAG, AREA) as max_date on max_date.section =  CP.section AND CP.TAG = max_date.tag AND CP.last_date = max_date.max AND CP.AREA = max_date.AREA");
			querySql.append(" where ASSET = '").append(asset).append("' AND (PROTECTED_SIDE_OFF >= -0.85 ");
			querySql.append(
					" OR NOT_PROTECTED_SIDE_OFF >= -0.85 OR EXTERNAL_CONDUIT_OFF >= -0.85 OR TUBE_OFF >= -0.85  ) GROUP BY AREA ");
			querySql.append(" ) as PROTECTION_CRITICAL on PROTECTION_CRITICAL.area = b.decoded_value ");

			// EVPMS
			querySql.append(" LEFT JOIN (select count(*) as count, ST_HIGH.AREA from ASSETINT.EVPMS_STATIONS ST_HIGH ");
			querySql.append(" where ASSET = '").append(asset).append(
					"' AND (AC_PRESENCE AND ACQUISITION AND DEJITTER AND GPS AND GSM AND INTRUSION AND USB_CONNECTION) = 0 GROUP BY ST_HIGH.AREA) as EVPMS_HIGH on EVPMS_HIGH.area = b.decoded_value ");
			querySql.append(" LEFT JOIN (select count(*) as count, ST_CRIT.AREA from ASSETINT.EVPMS_STATIONS ST_CRIT ");
			querySql.append(" JOIN ASSETINT.EVPMS_ALERTS_WEEK_VIEW ALERT on ST_CRIT.station_id = ALERT.station_id ");
			querySql.append(" where ST_CRIT.ASSET = '").append(asset).append(
					"' AND (AC_PRESENCE AND ACQUISITION AND DEJITTER AND GPS AND GSM AND INTRUSION AND USB_CONNECTION) IS NOT NULL GROUP BY ST_CRIT.AREA) as EVPMS_CRIT on EVPMS_CRIT.area = b.decoded_value ");

			// JACKETED
			querySql.append(" LEFT JOIN (select count(*) as count, JP_HIGH.AREA from ASSETINT.JACKETED_PIPES JP_HIGH ");
			querySql.append(" where ASSET = '").append(asset).append(
					"' AND (BAD_ACTIVE = 1 OR ALARM  = 0) GROUP BY JP_HIGH.AREA) as PIPES_HIGH on PIPES_HIGH.area = b.decoded_value ");
			querySql.append(" LEFT JOIN (select count(*) as count, JP_CRIT.AREA from ASSETINT.JACKETED_PIPES JP_CRIT ");
			querySql.append(" where JP_CRIT.ASSET = '").append(asset).append(
					"' AND ALARM = 1 GROUP BY JP_CRIT.AREA) as PIPES_CRIT on PIPES_CRIT.area = b.decoded_value ");

			querySql.append(" WHERE ASSET = '").append(asset).append("' AND PARENT_MAP = ").append(parentMap);

			if(!parentArea.equals("0"))
				querySql.append(" AND PARENT_AREA = '").append(parentArea).append("'");

			querySql.append(" AND a.ACTIVE = 'Y'")
					.append(" GROUP BY NAME, MAP_COORDS, COLOR, COLOR2,   b.Descrizione,  SHAPE, PARENT_MAP, PARENT_AREA, CODE");


			logger.info(querySql.toString());
			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					AreaDto o = new AreaDto();
					o.setMapCoords((String) object[0]);
					o.setColor((String) object[1]);
					o.setColor2((String) object[2]);
					o.setDescription((String) object[3]);
					o.setName((String) object[4]);
					o.setShape((String) object[5]);
					o.setParentArea((String) "" + object[6]);
					o.setParentMap((String) object[7]);
					o.setBacklogCrit(((BigDecimal) object[8]).longValue());
					o.setBacklogHigh(((BigDecimal) object[9]).longValue());
					o.setCriticalCrit(((BigDecimal) object[10]).longValue());
					o.setCriticalHigh(((BigDecimal) object[11]).longValue());
					long corrosionCrit = 0;
					long corrosionHigh = 0;
					BigDecimal criticalCND = (BigDecimal) object[12];
					corrosionCrit += criticalCND.longValue();
					BigDecimal criticalBacteria = (BigDecimal) object[13];
					corrosionCrit += criticalBacteria.longValue();
					BigDecimal criticalCoupon = (BigDecimal) object[14];
					corrosionCrit += criticalCoupon.longValue();
					BigDecimal highCoupon = (BigDecimal) object[15];
					corrosionHigh += highCoupon.longValue();
					BigDecimal criticalPigging = (BigDecimal) object[16];
					corrosionCrit += criticalPigging.longValue();
					BigDecimal criticalProtection = (BigDecimal) object[17];
					corrosionCrit += criticalProtection.longValue();
					o.setCorrosionCrit(corrosionCrit);
					o.setCorrosionHigh(corrosionHigh);

					long integrityCrit = 0;
					long integrityHigh = 0;
					BigDecimal criticalEVPMS = (BigDecimal) object[18];
					integrityCrit += criticalEVPMS.longValue();
					BigDecimal highEVPMS = (BigDecimal) object[19];
					integrityHigh += highEVPMS.longValue();
					BigDecimal criticalPIPES = (BigDecimal) object[20];
					integrityCrit += criticalPIPES.longValue();
					BigDecimal highPIPES = (BigDecimal) object[21];
					integrityHigh += highPIPES.longValue();
					o.setIntegrityCrit(integrityCrit);
					o.setIntegrityHigh(integrityHigh);
					o.setCode((String) object[22]);

					res.add(o);
				}
			}
			Instant finish = Instant.now();
			long timeElapsed = Duration.between(start, finish).toMillis();
			logger.debug("getEWPAreasWithCriticalCount function took: " + timeElapsed);

			return res;
		} catch (Exception e) {
			logger.error("error during backloglist ", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public List<AreaDto> getAreasWithCriticalCount(String asset, Long id, String tab) {
		List<AreaDto> res = new ArrayList<>();

		logger.debug("Called getAreas");
		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append(" SELECT DISTINCT MAP_COORDS, COLOR, COLOR2, ");

			querySql.append(" b.Descrizione as DESCRIPTION, NAME, SHAPE, ");
			querySql.append(" IFNULL(SUM(crit_backlog.count),0.0), IFNULL(SUM(high_backlog.count),0.0), ");
			querySql.append(" IFNULL(SUM(crit_signals.count),0.0), IFNULL(SUM(high_signals.count),0.0), ");
			querySql.append(" IFNULL(SUM(CND_CRITICAL.count),0.0), ");
			querySql.append(" IFNULL(SUM(BACTERIA_CRITICAL.count),0.0), ");
			querySql.append(" IFNULL(SUM(COUPON_CRITICAL.count),0.0), ");
			querySql.append(" IFNULL(SUM(COUPON_HIGH.count),0.0), ");
			querySql.append(" IFNULL(SUM(PIGGING_CRITICAL.count),0.0), ");
			querySql.append(" IFNULL(SUM(PROTECTION_CRITICAL.count),0.0), ");
			querySql.append(" IFNULL(SUM(EVPMS_CRIT.count), 0.0), IFNULL(SUM(EVPMS_HIGH.count),0.0),  ");
			querySql.append(" IFNULL(SUM(PIPES_CRIT.count), 0.0), IFNULL(SUM(PIPES_HIGH.count),0.0)  ");
			querySql.append(" FROM ASSETINT.AREAS a LEFT JOIN ASSETINT.Transcodifica_Aree b on a.NAME = b.Area ");
			// Joins with different critical count
			querySql.append(
					" LEFT JOIN (SELECT COUNT(DISTINCT ODM) count, AREA FROM ASSETINT.BACKLOG WHERE STR_TO_DATE(DATA_CHIUSURA_PREV, '%d/%m/%Y') < DATE(now()) and TAB = '")
					.append(tab).append("' GROUP BY area)  as crit_backlog on crit_backlog.area = b.decoded_value "); // crit
			// BACKLOG
			querySql.append(
					" LEFT JOIN (SELECT COUNT(DISTINCT ODM) count, AREA FROM ASSETINT.BACKLOG WHERE STR_TO_DATE(DATA_CHIUSURA_PREV, '%d/%m/%Y') >= DATE(now()) and TAB = '")
					.append(tab).append("' GROUP BY area)  as high_backlog on high_backlog.area = b.decoded_value "); // high
			// BACKLOG
			querySql.append(
					" LEFT JOIN (SELECT count(distinct SC.NAME) count, AREA FROM ASSETINT.`SEGNALI CRITICI` SC LEFT JOIN ASSETINT.SEGNALI_WF SWF ON SC.NAME = SWF.NAME WHERE BlockInput = 'TRUE' and `BlockInput Status` = '1' and (SWF.CHIUSO = 1 or SWF.CHIUSO is NULL) and (DATA_INIZIO is null and DATA_FINE is null || DATA_INIZIO <= "
							+ NOW_APP_TIMEZONE + " and DATA_FINE >= " + NOW_APP_TIMEZONE
							+ ") GROUP BY area) as crit_signals on crit_signals.area = b.decoded_value "); // crit
			// SIGNALS
			querySql.append(
					" LEFT JOIN (SELECT count(distinct SC2.NAME) count, AREA FROM ASSETINT.`SEGNALI CRITICI` SC2 LEFT JOIN ASSETINT.SEGNALI_WF SWF2 ON SC2.NAME = SWF2.NAME WHERE BlockInput = 'TRUE' and `BlockInput Status` = '0' and (SWF2.CHIUSO = 1 or SWF2.CHIUSO is NULL) and (DATA_INIZIO is null and DATA_FINE is null || DATA_INIZIO <= "
							+ NOW_APP_TIMEZONE + " and DATA_FINE >= " + NOW_APP_TIMEZONE
							+ ") GROUP BY area)  AS high_signals on high_signals.area = b.decoded_value "); // high

			// CORROSION CND
			querySql.append(
					" LEFT JOIN ( SELECT  count(*) count, AREA FROM ASSETINT.CORROSION_CND_VIEW where ASSET = '")
					.append(asset)
					.append("' AND NEXT_DATE IS NOT NULL AND NEXT_DATE < DATE(NOW()) GROUP BY AREA ) as CND_CRITICAL on CND_CRITICAL.area = b.decoded_value ");
			querySql.append(
					" LEFT JOIN ( SELECT  count(DISTINCT(ANALYSIS_POINT)) count, AREA from ASSETINT.CORROSION_BACTERIA where ASSET = '")
					.append(asset)
					.append("' AND (SRB_VALUE > 100 OR APB_VALUE > 100) GROUP BY AREA ) as BACTERIA_CRITICAL on BACTERIA_CRITICAL.area = b.decoded_value ");

			// CORROSION COUPON CRITICAL
			querySql.append(" LEFT JOIN ( SELECT  count(*) count, AREA FROM ASSETINT.CORROSION_COUPON CC ");
			querySql.append(" JOIN (select CODE, max(last_date) max ");
			querySql.append(" from ASSETINT.CORROSION_COUPON ");
			querySql.append(" group by CODE) as max_date on max_date.code =  CC.code and CC.last_date = max_date.max ");
			querySql.append(" where ASSET = '").append(asset).append(
					"' AND MPY >= 1  GROUP BY AREA ) as COUPON_CRITICAL on COUPON_CRITICAL.area = b.decoded_value ");
			// CORROSION COUPON HIGH
			querySql.append(" LEFT JOIN ( SELECT  count(*) count, CC.AREA FROM ASSETINT.CORROSION_COUPON CC ");
			querySql.append(" JOIN (select CODE, AREA, max(last_date) max ");
			querySql.append(" from ASSETINT.CORROSION_COUPON ");
			querySql.append(
					" group by CODE, AREA) as max_date on max_date.code =  CC.code and CC.last_date = max_date.max AND CC.AREA = max_date.AREA ");
			querySql.append(" where ASSET = '").append(asset).append(
					"' AND MPY < 1 AND NEXT_DATE IS NOT NULL AND NEXT_DATE < DATE(NOW()) GROUP BY AREA ) as COUPON_HIGH on COUPON_HIGH.area = b.decoded_value ");
			// CORROSION PIGGING
			querySql.append(" LEFT JOIN ( SELECT  count(*) count, CP.AREA FROM ASSETINT.CORROSION_PIGGING CP ");
			querySql.append(" JOIN (select AREA, SECTION, FEATURE, max(last_date) max ");
			querySql.append(" from ASSETINT.CORROSION_PIGGING ");
			querySql.append(" where erf is not null ");
			querySql.append(
					" group by SECTION, FEATURE, AREA) as max_date on max_date.section =  CP.section AND CP.feature = max_date.feature AND CP.AREA = max_date.AREA  ");
			querySql.append(" where ASSET = '").append(asset).append(
					"' AND ERF >= 1 GROUP BY AREA) as PIGGING_CRITICAL on PIGGING_CRITICAL.area = b.decoded_value ");
			// CORROSION PROTECTION
			querySql.append(" LEFT JOIN ( SELECT  count(*) count, CP.AREA FROM ASSETINT.CORROSION_PROTECTION CP ");
			querySql.append(" JOIN (select AREA, SECTION, TAG, max(last_date) max ");
			querySql.append(" from ASSETINT.CORROSION_PROTECTION ");
			querySql.append(
					" group by SECTION, TAG, AREA) as max_date on max_date.section =  CP.section AND CP.TAG = max_date.tag AND CP.last_date = max_date.max AND CP.AREA = max_date.AREA");
			querySql.append(" where ASSET = '").append(asset).append("' AND (PROTECTED_SIDE_OFF >= -0.85 ");
			querySql.append(
					" OR NOT_PROTECTED_SIDE_OFF >= -0.85 OR EXTERNAL_CONDUIT_OFF >= -0.85 OR TUBE_OFF >= -0.85  ) GROUP BY AREA ");
			querySql.append(" ) as PROTECTION_CRITICAL on PROTECTION_CRITICAL.area = b.decoded_value ");

			// EVPMS
			querySql.append(" LEFT JOIN (select count(*) as count, ST_HIGH.AREA from ASSETINT.EVPMS_STATIONS ST_HIGH ");
			querySql.append(" where ASSET = '").append(asset).append(
					"' AND (AC_PRESENCE AND ACQUISITION AND DEJITTER AND GPS AND GSM AND INTRUSION AND USB_CONNECTION) = 0 GROUP BY ST_HIGH.AREA) as EVPMS_HIGH on EVPMS_HIGH.area = b.decoded_value ");
			querySql.append(" LEFT JOIN (select count(*) as count, ST_CRIT.AREA from ASSETINT.EVPMS_STATIONS ST_CRIT ");
			querySql.append(" JOIN ASSETINT.EVPMS_ALERTS_WEEK_VIEW ALERT on ST_CRIT.station_id = ALERT.station_id ");
			querySql.append(" where ST_CRIT.ASSET = '").append(asset).append(
					"' AND (AC_PRESENCE AND ACQUISITION AND DEJITTER AND GPS AND GSM AND INTRUSION AND USB_CONNECTION) IS NOT NULL GROUP BY ST_CRIT.AREA) as EVPMS_CRIT on EVPMS_CRIT.area = b.decoded_value ");

			// JACKETED
			querySql.append(" LEFT JOIN (select count(*) as count, JP_HIGH.AREA from ASSETINT.JACKETED_PIPES JP_HIGH ");
			querySql.append(" where ASSET = '").append(asset).append(
					"' AND (BAD_ACTIVE = 1 OR ALARM  = 0) GROUP BY JP_HIGH.AREA) as PIPES_HIGH on PIPES_HIGH.area = b.decoded_value ");
			querySql.append(" LEFT JOIN (select count(*) as count, JP_CRIT.AREA from ASSETINT.JACKETED_PIPES JP_CRIT ");
			querySql.append(" where JP_CRIT.ASSET = '").append(asset).append(
					"' AND ALARM = 1 GROUP BY JP_CRIT.AREA) as PIPES_CRIT on PIPES_CRIT.area = b.decoded_value ");

			querySql.append(" WHERE ASSET = '").append(asset).append("' AND PARENT_MAP = ").append(id)
					.append(" AND a.ACTIVE = 'Y'")
					.append(" GROUP BY NAME, MAP_COORDS, COLOR, COLOR2,   DESCRIZIONE,  SHAPE");

			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					AreaDto o = new AreaDto();
					o.setMapCoords((String) object[0]);
					o.setColor((String) object[1]);
					o.setColor2((String) object[2]);
					o.setDescription((String) object[3]);
					o.setName((String) object[4]);
					o.setShape((String) object[5]);
					o.setBacklogCrit(((BigDecimal) object[6]).longValue());
					o.setBacklogHigh(((BigDecimal) object[7]).longValue());
					o.setCriticalCrit(((BigDecimal) object[8]).longValue());
					o.setCriticalHigh(((BigDecimal) object[9]).longValue());
					long corrosionCrit = 0;
					long corrosionHigh = 0;
					BigDecimal criticalCND = (BigDecimal) object[10];
					corrosionCrit += criticalCND.longValue();
					BigDecimal criticalBacteria = (BigDecimal) object[11];
					corrosionCrit += criticalBacteria.longValue();
					BigDecimal criticalCoupon = (BigDecimal) object[12];
					corrosionCrit += criticalCoupon.longValue();
					BigDecimal highCoupon = (BigDecimal) object[13];
					corrosionHigh += highCoupon.longValue();
					BigDecimal criticalPigging = (BigDecimal) object[14];
					corrosionCrit += criticalPigging.longValue();
					BigDecimal criticalProtection = (BigDecimal) object[15];
					corrosionCrit += criticalProtection.longValue();
					o.setCorrosionCrit(corrosionCrit);
					o.setCorrosionHigh(corrosionHigh);

					long integrityCrit = 0;
					long integrityHigh = 0;
					BigDecimal criticalEVPMS = (BigDecimal) object[16];
					integrityCrit += criticalEVPMS.longValue();
					BigDecimal highEVPMS = (BigDecimal) object[17];
					integrityHigh += highEVPMS.longValue();
					BigDecimal criticalPIPES = (BigDecimal) object[18];
					integrityCrit += criticalPIPES.longValue();
					BigDecimal highPIPES = (BigDecimal) object[19];
					integrityHigh += highPIPES.longValue();
					o.setIntegrityCrit(integrityCrit);
					o.setIntegrityHigh(integrityHigh);

					res.add(o);
				}
			}
			return res;
		} catch (Exception e) {
			logger.error("error during backloglist ", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WellAlarm> getWellAlarms(String asset) {
		List<WellAlarm> result = new ArrayList<>();

		StringBuilder querySql = new StringBuilder();
		querySql.append(
				" SELECT COMPANY, FIELD, WELL_CODE , WELL_NAME, GENERAL_ALARM, GENERAL_ALARM_DESCRIPTION, SAFETY_VALVE,");
		querySql.append("  SAFETY_VALVE_DESCRIPTION,  WELLHEAD_TEST, WELLHEAD_TEST_DESCRIPTION, ANNULUS_PRESSURE,  ");
		querySql.append(" ANNULUS_PRESSURE_DESCRIPTION, ASSET, REF_DATE, INSERTION_DATE ");
		querySql.append(" FROM ASSETINT.WELL_ALARM_COVA_VIEW ");
		querySql.append(" WHERE ASSET = '").append(asset).append("'");

		Query sql = entityManager.createNativeQuery(querySql.toString());
		List<Object[]> sqlResult = sql.getResultList();
		if (sqlResult != null && !sqlResult.isEmpty()) {
			for (Object[] object : sqlResult) {
				WellAlarm wellAlarm = new WellAlarm();
				wellAlarm.setCompany((String) object[0]);
				wellAlarm.setField((String) object[1]);
				wellAlarm.setWellCode((String) object[2]);
				wellAlarm.setWellName((String) object[3]);
				wellAlarm.setGeneralAlarm((String) object[4]);
				wellAlarm.setGeneralAlarmDescription((String) object[5]);
				wellAlarm.setSafetyValve((String) object[6]);
				wellAlarm.setSafetyValveDescription((String) object[7]);
				wellAlarm.setWellheadTest((String) object[8]);
				wellAlarm.setWellheadTestDescription((String) object[9]);
				wellAlarm.setAnnulusPressure((String) object[10]);
				wellAlarm.setAnnulusPressureDescription((String) object[11]);
				wellAlarm.setAsset((String) object[12]);
				wellAlarm.setRefDate(object[13] != null ? object[13].toString() : null);
				wellAlarm.setInsertionDate(object[14] != null ? object[14].toString() : null);
				result.add(wellAlarm);
			}
		}
		return result;
	}

	@Override
	public void insertSegnaliCriticiWf(String hash, List<com.eni.ioc.assetintegrity.enitities.CriticalSignal> signals,
			Date start, Date end) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		logger.debug("insertSegnaliCriticiWf");

		try {
			StringBuilder sb = new StringBuilder();

			sb.append("INSERT INTO ASSETINT.SEGNALI_WF ( HASH_KEY, NAME, DATA_INIZIO, DATA_FINE) VALUES ");
			int i = 0;
			for (com.eni.ioc.assetintegrity.enitities.CriticalSignal elt : signals) {
				i++;
				sb.append(" (");
				sb.append("'").append(hash).append("',");
				sb.append("'").append(elt.getName()).append("', ");
				sb.append("'").append(sdf.format(start)).append("',");
				sb.append("'").append(sdf.format(end)).append("' ");
				sb.append(")");
				if (i < signals.size()) {
					sb.append(",");
				}
			}
			sb.append(";");

			logger.info("query insertSegnaliCriticiWf: " + sb.toString());
			Query sql = entityManager.createNativeQuery(sb.toString());
			sql.executeUpdate();

		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Override
	public List<CriticalSignalDto> signcriticilistDto(String area, String parentArea) {
		logger.debug("Called signcriticilistDto with area: " + area);
		List<CriticalSignalDto> results = new ArrayList<>();
		try {

			StringBuilder querySql = new StringBuilder();
			querySql.append(" SELECT DISTINCT (SC.NAME) as NAME, ta.Area as AREA, LastStatusChangeDate, Value, ");
			querySql.append(
					" Category, Description, BlockInput, `BlockInput Status`, APPROVATO, CHIUSO, Severity, SWF.WF_ID, ");
			querySql.append(" SWFMOC.MoC, SWF.DATA_FINE, MOC.NUMERO, MOC.DATACHIUSURA  ");
			querySql.append(" FROM ASSETINT.`SEGNALI CRITICI` SC ");

			if (area != null) {
				querySql.append(" JOIN ASSETINT.Transcodifica_Aree ta on SC.area = ta.decoded_Value ");
			}
			querySql.append(" LEFT JOIN ASSETINT.SEGNALI_WF SWF ON SC.NAME = SWF.NAME ");
			querySql.append(
					" AND (SWF.WF_ID = (SELECT MAX(WF_ID) FROM ASSETINT.SEGNALI_WF AS SWF2 WHERE SWF2.NAME = SC.NAME) OR SWF.WF_ID IS NULL) ");

			querySql.append(" LEFT JOIN ASSETINT.SEGNALI_WF_MOC SWFMOC ON SC.NAME = SWFMOC.NAME AND SWFMOC.MOC = 1");

			querySql.append(" LEFT JOIN ASSETINT.REGISTRO_MOC MOC ON ( MOC.SEGNALI LIKE CONCAT('%',SWFMOC.NAME,'%') and MOC.WF_ID = SWFMOC.WF_ID) WHERE ");
			querySql.append(" ((CHIUSO IS NOT NULL AND CHIUSO = 0 AND DATA_ATTIVAZIONE IS NULL) OR ");
			querySql.append(
					" (SWFMOC.MoC = 1 OR (BlockInput = 'TRUE' AND `BlockInput Status` IN ('0','1') AND `BlockInput Status` != '' AND SC.NAME NOT IN ");
			querySql.append(
					" (SELECT DISTINCT (SC.NAME) FROM ASSETINT.`SEGNALI CRITICI` SC LEFT JOIN ASSETINT.SEGNALI_WF SWF ON SC.NAME = SWF.NAME ");
			querySql.append(" WHERE CHIUSO IS NOT NULL AND CHIUSO = 0 AND DATA_ATTIVAZIONE IS NULL)))) ");
			querySql.append(" AND (MOC.DATAASSEGNAZIONE IS NULL OR MOC.DATAASSEGNAZIONE <= NOW()) AND (MOC.DATACHIUSURA IS NULL OR MOC.DATACHIUSURA >= NOW()) ");
			if (area != null) {
				String areas = areasToListStringified(area, parentArea);
				querySql.append(" AND ta.decoded_Value in ").append(areas).append(" ");
			}

			logger.debug(querySql.toString());
			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sdf.setTimeZone(TimeZone.getTimeZone("CET"));

				for (Object[] object : lista) {
					CriticalSignalDto o = new CriticalSignalDto();
					o.setName((String) object[0]);
					o.setArea((String) object[1]);
					o.setLastStatusChangeDate((String) object[2]);
					o.setValue((String) object[3]);
					o.setCategory((String) object[4]);
					o.setDescription((String) object[5]);
					o.setBlockInput((String) object[6]);
					o.setBlockInputStatus((String) object[7]);
					if ((Integer) object[8] != null) {
						o.setApproved((Integer) object[8] == 1);
					}
					if ((Integer) object[9] != null) {
						o.setChiuso((Integer) object[9] == 1);
					}
					o.setSeverity((String) object[10]);
					if ((String) object[11] != null) {
						o.setWfId((String) object[11]);
					}

					if ((Integer) object[12] != null) {
						o.setMoc((Integer) object[12] == 1);
					}

					if ((Date) object[13] != null) {
						o.setEnd(sdf.parse(((Date) object[13]).toString()));
					}

					o.setNumeroMoc((String) object[14]);
					if ((Date) object[15] != null) {
						o.setChiusuraMoc(sdf.parse(((Date) object[15]).toString()));
					}
					results.add(o);
				}
			}
			return results;
		} catch (Exception e) {
			logger.error("error during signcriticilistDto ", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public void storeWFRequest(String list, String asset, String duration, String emergency, String notes,
			String requestType, String sap, Date startDate) {
		logger.debug("Called storeWFRequest");
		try {
			WfRequest wfr = new WfRequest();
			wfr.setAsset(asset);
			wfr.setDuration(duration);
			wfr.setEmergency(emergency);
			wfr.setNotes(notes);
			wfr.setRequestType(requestType);
			wfr.setSap(sap);
			wfr.setSignals(list);
			wfr.setStartDate(startDate);

			entityManager.persist(wfr);
			entityManager.flush();

		} catch (Exception e) {
			logger.error("error during storeWFRequest ", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long getBacklogODMCount(String asset) {
		String querySql = " SELECT CAST(SUM(`N.Tot.OdM TECO`) AS SIGNED) FROM BACKLOG_ODM_COUNT;";
		Query sql = entityManager.createNativeQuery(querySql);
		List<Long> count = sql.getResultList();
		if (count.isEmpty()) {
			return null;
		} else {
			return count.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Long> getCriticalSignalsSeverityCount(String asset) {
		String querySql = " SELECT COUNT(*), SEVERITY FROM `SEGNALI CRITICI`  WHERE ASSET = '" + asset
				+ "' AND BlockInput = 'True' GROUP BY SEVERITY;";
		Query sql = entityManager.createNativeQuery(querySql);
		Map<String, Long> res = new HashMap<>();
		List<Object[]> lista = sql.getResultList();
		if (lista != null && !lista.isEmpty()) {
			lista.forEach((object) -> {
				res.put((String) object[1], (Long) object[0]);
			});
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long getCriticalSignalsTotalCount(String asset) {
		String querySql = " SELECT COUNT(*) FROM `SEGNALI CRITICI`  WHERE ASSET = '" + asset + "';";
		Query sql = entityManager.createNativeQuery(querySql);
		List<Long> lista = sql.getResultList();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		} else {
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Long> getMocTotalCount(String asset) {
		String querySql = " SELECT COUNT(*), IF(ISNULL(DATAAPPROVAZIONE), 'NULL', 'NOT NULL') as APPROVATO FROM ASSETINT.REGISTRO_MOC where DATAREGISTRAZIONE is not null GROUP BY APPROVATO; ";
		Query sql = entityManager.createNativeQuery(querySql);
		Map<String, Long> res = new HashMap<>();
		List<Object[]> lista = sql.getResultList();
		if (lista != null && !lista.isEmpty()) {
			lista.forEach((object) -> {
				res.put((String) object[1], (Long) object[0]);
			});
		}
		return res;
	}

	@Override
	public Map<String, Long> getBacklogTypeCount(String asset) {
		String querySql = " SELECT COUNT(DISTINCT ODM), TAM FROM BACKLOG WHERE TAB = '" + asset + "' GROUP BY TAM ";
		Query sql = entityManager.createNativeQuery(querySql);
		Map<String, Long> res = new HashMap<>();
		List<Object[]> lista = sql.getResultList();
		if (lista != null && !lista.isEmpty()) {
			lista.forEach((object) -> {
				res.put((String) object[1], (Long) object[0]);
			});
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getLastInsertionDate(String asset) {
		String querySql = " SELECT MAX(INSERTION_DATE) FROM ASSETINT.BACKLOG WHERE TAB = '" + asset + "';";
		Query sql = entityManager.createNativeQuery(querySql);
		List<Object> count = sql.getResultList();
		if (count.isEmpty() || count.get(0) == null) {
			return null;
		} else {
			return count.get(0).toString();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OperatingWindowKpi> getOperatingWindowKpi(String asset) {
		String query = "select  o.NAME, o.STATE, ROUND(o.OUT_HOUR,2) OUT_HOUR, o.LINK_VISION,"
				+ " o.INSERTION_DATE, o.ASSET " + "from ASSETINT.KPI_OPERATIONAL_OPERATING_WINDOW o  "
				+ "join (select name, max(insertion_date) max "
				+ "from ASSETINT.KPI_OPERATIONAL_OPERATING_WINDOW  WHERE ASSET = '" + asset + "'"
				+ "group by name) max_date on max_date.name = o.name AND max_date.max = o.insertion_date AND ASSET = '"
				+ asset + "'";
		Query sql = entityManager.createNativeQuery(query, OperatingWindowKpi.class);
		return sql.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IntegrityOperatingWindowKpi> getIntegrityOperatingWindowKpi(String asset) {
		String query = "select  o.NAME, o.n_IOW, o.N_IOW_OK, o.N_IOW_OUT, o.N_IOW_WARNING, o.LINK_VISION,"
				+ " o.INSERTION_DATE, o.ASSET " + "from ASSETINT.KPI_INTEGRITY_OPERATING_WINDOW o  "
				+ "join (select name, max(insertion_date) max "
				+ "from ASSETINT.KPI_INTEGRITY_OPERATING_WINDOW WHERE ASSET = '" + asset + "'"
				+ "group by name) max_date on max_date.name = o.name AND max_date.max = o.insertion_date AND ASSET = '"
				+ asset + "'";
		Query sql = entityManager.createNativeQuery(query, IntegrityOperatingWindowKpi.class);
		return sql.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CorrosionKpi> getCorrosionKpi(String asset) {
		String query = "select  c.* " + "from ASSETINT.KPI_OPERATIONAL_CORROSION c  "
				+ "join (select name, max(insertion_date) max "
				+ "from ASSETINT.KPI_OPERATIONAL_CORROSION WHERE ASSET = '" + asset + "'"
				+ "group by name) max_date on max_date.name = c.name AND max_date.max = c.insertion_date AND ASSET = '"
				+ asset + "'";
		Query sql = entityManager.createNativeQuery(query, CorrosionKpi.class);
		return sql.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CorrosionBacteria> getCorrosionBacteria(String asset, String area, String parentArea) {
		String areas = areasToListStringified(area, parentArea);
		String query = "select  c.* " + "from ASSETINT.CORROSION_BACTERIA c  "
				+ " JOIN ASSETINT.Transcodifica_Aree ta on c.area = ta.decoded_Value " + "where ASSET = '" + asset
				+ "' " + "AND ta.AREA in " + areas + " ";
		Query sql = entityManager.createNativeQuery(query, CorrosionBacteria.class);
		return sql.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Long[]> getCorrosionCND(String asset, String area, String year, String parentArea) {
		String areas = areasToListStringified(area, parentArea);
		String firstQuery = "SELECT   MODEL_NAME as name, count(*) as count, 'critical' as type  "
				+ " FROM ASSETINT.CORROSION_CND_VIEW cc "
				+ " JOIN ASSETINT.Transcodifica_Aree ta on cc.area = ta.decoded_Value " + " WHERE ASSET = '" + asset
				+ "' AND MODEL_NAME IS NOT NULL " + " AND NEXT_DATE < DATE(NOW()) AND ta.AREA in " + areas + " "
				+ (year != null ? (" AND YEAR(NEXT_DATE) = '" + year + "' ") : "") + " GROUP BY MODEL_NAME  "
				+ " UNION " + " SELECT   MODEL_NAME as name, count(*) as count, 'high' as type "
				+ " FROM ASSETINT.CORROSION_CND_VIEW cc "
				+ " JOIN ASSETINT.Transcodifica_Aree ta on cc.area = ta.decoded_Value " + " WHERE ASSET = '" + asset
				+ "' AND MODEL_NAME IS NOT NULL " + " AND NEXT_DATE >= DATE(NOW())  AND ta.AREA in " + areas + " "
				+ (year != null ? (" AND YEAR(NEXT_DATE) = '" + year + "' ") : "") + " GROUP BY MODEL_NAME ";

		Query sql = entityManager.createNativeQuery(firstQuery);
		List<Object[]> sqlList = sql.getResultList();

		Map<String, Long[]> results = new HashMap<>();
		for (Object[] row : sqlList) {
			String key = (String) row[0];
			Long[] count = results.getOrDefault(key, new Long[2]);
			String type = (String) row[2];
			int index = "critical".equals(type) ? 1 : 0;
			count[index] = (Long) row[1];
			results.put(key, count);
		}
		return results;
	}

	@Override
	public Long getCriticalCountCorrosionCND(String asset, String area, String parentArea) {
		String areas = areasToListStringified(area, parentArea);
		String query = "SELECT  count(*) FROM ASSETINT.CORROSION_CND_VIEW cc"
				+ " JOIN ASSETINT.Transcodifica_Aree ta on cc.area = ta.decoded_Value " + " WHERE ASSET = '" + asset
				+ "'" + " AND NEXT_DATE IS NOT NULL AND NEXT_DATE < DATE(NOW()) AND ta.AREA in " + areas + " ";
		return (Long) entityManager.createNativeQuery(query).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CorrosionCND> getCorrosionCNDElements(String asset, String element, String year, String area,
			String parentArea) {
		String areas = areasToListStringified(area, parentArea);
		String query = "SELECT cc.* FROM ASSETINT.CORROSION_CND_VIEW cc "
				+ " JOIN ASSETINT.Transcodifica_Aree ta on cc.area = ta.decoded_Value " + " WHERE ASSET = '" + asset
				+ "'" + " AND ta.AREA in " + areas + " AND MODEL_NAME= '" + element + "' "
				+ (year != null ? (" AND YEAR(NEXT_DATE) = '" + year + "' ") : "");
		return entityManager.createNativeQuery(query, CorrosionCND.class).getResultList();
	}

	@Override
	public String[] getYearsCorrosionCND(String asset, String area, String parentArea) {
		String areas = areasToListStringified(area, parentArea);
		String query = "SELECT CAST(MAX(YEAR(NEXT_DATE)) as CHAR), CAST(MIN(YEAR(NEXT_DATE)) AS CHAR) "
				+ " FROM ASSETINT.CORROSION_CND_VIEW cc"
				+ " JOIN ASSETINT.Transcodifica_Aree ta on cc.area = ta.decoded_Value " + " WHERE ASSET = '" + asset
				+ "' " + " AND ta.AREA in " + areas + " ";
		Object[] result = (Object[]) entityManager.createNativeQuery(query).getSingleResult();
		String[] years = new String[2];
		years[0] = (String) result[0];
		years[1] = (String) result[1];
		return years;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CorrosionProtection> getCorrosionProtection(String asset, String area, String parentArea) {
		String areas = areasToListStringified(area, parentArea);
		String query = "SELECT CP.* " + " FROM ASSETINT.CORROSION_PROTECTION CP"
				+ " JOIN ASSETINT.Transcodifica_Aree ta on CP.area = ta.decoded_Value "
				+ " JOIN (select SECTION, TAG, max(last_date) max " + " from ASSETINT.CORROSION_PROTECTION CP "
				+ " JOIN ASSETINT.Transcodifica_Aree ta on CP.area = ta.decoded_Value " + " WHERE ta.AREA in " + areas
				+ " group by SECTION, TAG) as max_date on max_date.section =  CP.section "
				+ " AND CP.TAG = max_date.tag AND CP.last_date = max_date.max " + " WHERE ASSET = '" + asset + "'"
				+ " AND ta.AREA =  " + areas + " ";
		return entityManager.createNativeQuery(query, CorrosionProtection.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CorrosionPigging> getCorrosionPigging(String asset, String area, String parentArea) {
		String areas = areasToListStringified(area, parentArea);
		String query = "SELECT cp.* " + " from ASSETINT.CORROSION_PIGGING cp "
				+ " JOIN ASSETINT.Transcodifica_Aree ta on cp.area = ta.decoded_Value "
				+ " JOIN (select SECTION, FEATURE, max(last_date) max " + " from ASSETINT.CORROSION_PIGGING cp "
				+ " JOIN ASSETINT.Transcodifica_Aree ta on cp.area = ta.decoded_Value " + " WHERE erf is not null "
				+ " AND ta.AREA in " + areas + " group by SECTION, FEATURE) "
				+ " as max_date on max_date.section =  cp.section AND cp.feature = max_date.feature "
				+ " AND cp.last_date = max_date.max  " + " WHERE ASSET = '" + asset + "' AND erf is not null"
				+ " AND ta.AREA in " + areas + " ";
		return entityManager.createNativeQuery(query, CorrosionPigging.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CorrosionCoupon> getCorrosionCoupon(String asset, String area, String parentArea) {
		String areas = areasToListStringified(area, parentArea);
		String query = "SELECT CC.* " + " FROM ASSETINT.CORROSION_COUPON CC "
				+ " JOIN ASSETINT.Transcodifica_Aree ta on CC.area = ta.decoded_Value "
				+ " JOIN (select CODE, max(last_date) max " + " from ASSETINT.CORROSION_COUPON CC_max"
				+ " JOIN ASSETINT.Transcodifica_Aree ta on CC_max.area = ta.decoded_Value " + " WHERE ta.AREA in "
				+ areas + " "
				+ " group by CODE) as max_date on max_date.code =  CC.code and CC.last_date = max_date.max "
				+ " WHERE ASSET = '" + asset + "' " + " AND ta.AREA in " + areas + " "
				+ " order by CAST(CC.code as UNSIGNED), CC.code; ";
		return entityManager.createNativeQuery(query, CorrosionCoupon.class).getResultList();
	}

	@Override
	public String[] getYearsCorrosionCoupon(String asset, String area, String parentArea) {
		String areas = areasToListStringified(area, parentArea);
		String query = "SELECT CAST(MAX(YEAR(LAST_DATE)) as CHAR), CAST(MIN(YEAR(LAST_DATE)) AS CHAR) "
				+ " FROM ASSETINT.CORROSION_COUPON CC "
				+ " JOIN ASSETINT.Transcodifica_Aree ta on CC.area = ta.decoded_Value " + " WHERE ASSET = '" + asset
				+ "' " + " AND ta.AREA in " + areas + " ";
		Object[] result = (Object[]) entityManager.createNativeQuery(query).getSingleResult();
		String[] years = new String[2];
		years[0] = (String) result[0];
		years[1] = (String) result[1];
		return years;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CorrosionCoupon> getCorrosionCoupoElements(String asset, String code, String year, String area,
			String parentArea) {
		String areas = areasToListStringified(area, parentArea);
		String query = "SELECT CC.* FROM ASSETINT.CORROSION_COUPON CC "
				+ " JOIN ASSETINT.Transcodifica_Aree ta on CC.area = ta.decoded_Value " + " WHERE ASSET = '" + asset
				+ "'" + " AND ta.AREA in " + areas + " AND CODE= '" + code + "' " + " AND YEAR(LAST_DATE) = '" + year
				+ "' " + " ORDER BY LAST_DATE DESC";
		return entityManager.createNativeQuery(query, CorrosionCoupon.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EVPMSData> getEVPMSData(String asset, String area, String parentArea) {
		String areas = areasToListStringified(area, parentArea);
		String query = " select dorsal, STATION_NAME as station, null as status,  "
				+ " (AC_PRESENCE AND ACQUISITION AND DEJITTER AND GPS AND GSM AND INTRUSION AND USB_CONNECTION) as diagnostic, "
				+ " null as leakDetection, null as eventDate,  "
				+ " null as chainage, null as latitude, null as longitude, "
				+ " 'STATION' as type, st.station_id, 1 as type_ordering " + " FROM EVPMS_STATIONS st "
				+ " JOIN ASSETINT.Transcodifica_Aree ta on st.AREA = ta.decoded_Value " + " WHERE st.asset = '" + asset
				+ "' AND ta.area in " + areas + " "
				+ " AND (AC_PRESENCE AND ACQUISITION AND DEJITTER AND GPS AND GSM AND INTRUSION AND USB_CONNECTION) IS NOT NULL "
				+ " UNION " + " select dorsal, STATION_NAME as station, null as status,  " + " null as diagnostic, "
				+ " 1 as leakDetection,  cast(alert.ALERT_date AS CHAR) as eventDate,  "
				+ " alert.chainage as chainage, alert.latitude as latituted, alert.longitude as longitude, "
				+ " 'ALERT' as type, st.station_id, 2 as type_ordering  " + " FROM EVPMS_STATIONS st "
				+ " JOIN EVPMS_ALERTS_WEEK_VIEW alert on st.station_id = alert.station_id "
				+ " JOIN ASSETINT.Transcodifica_Aree ta on st.AREA = ta.decoded_Value " + " WHERE st.asset = '" + asset
				+ "' AND ta.area in " + areas + " "
				+ " AND (AC_PRESENCE AND ACQUISITION AND DEJITTER AND GPS AND GSM AND INTRUSION AND USB_CONNECTION) IS NOT NULL "
				+ " order by station_id, type_ordering ";

		List<Object[]> sqlList = entityManager.createNativeQuery(query).getResultList();

		List<EVPMSData> results = new LinkedList<>();
		for (Object[] row : sqlList) {
			EVPMSData data = new EVPMSData();
			data.setDorsal((String) row[0]);
			data.setStation((String) row[1]);
			data.setStatus((Integer) row[2]);
			Long diagnostic = (Long) row[3];
			data.setDiagnostic(longToBoolean(diagnostic));
			data.setLeakDetection(longToBoolean((Long) row[4]));
			data.setEventDate((String) row[5]);
			data.setChainage((String) row[6]);
			data.setLatitude((String) row[7]);
			data.setLongitude((String) row[8]);
			data.setType((String) row[9]);
			results.add(data);
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JacketedPipes> getJacketedPipes(String asset, String area, String parentArea) {
		String areas = areasToListStringified(area, parentArea);
		String query = "SELECT JP.* FROM ASSETINT.JACKETED_PIPES JP "
				+ " JOIN ASSETINT.Transcodifica_Aree ta on JP.area = ta.decoded_Value " + " WHERE ASSET = '" + asset
				+ "'" + " AND ta.AREA in " + areas + " ";
		return entityManager.createNativeQuery(query, JacketedPipes.class).getResultList();
	}

	public Boolean longToBoolean(Long input) {
		return input != null ? input != 0 : null;
	}

	@SuppressWarnings("unchecked")
	@Cacheable(value = "subAreas", key = "#asset.concat(#subArea)")
	@Override
	public String getParentArea(String subArea, String asset) {

		String query = "SELECT ea.PARENT_AREA FROM ASSETINT.EWP_AREAS ea LEFT JOIN ASSETINT.Transcodifica_Aree ta ON (ea.NAME = ta.AREA) WHERE ASSET = '" + asset + "' AND ta.DECODED_VALUE = '" + subArea
				+ "' limit 1";
		List<Object> res = entityManager.createNativeQuery(query).getResultList();

		if (res != null && !res.isEmpty()) {
			return (String) res.get(0);
		}

		return null;
	}


	private String areasToListStringified(String area, String parentArea) {
		if (area == null)
			return null;

		StringBuilder sb = new StringBuilder();

		sb.append("('").append(area).append("'");
		if (parentArea != null) {
			sb.append(",'").append(parentArea).append("'");
		}
		sb.append(")");

		return sb.toString();
	}

	// PARAMETERS?
	@Override
	public List<RegistroMoc> getRegistroMoc(MocRegistryInput data) {
		StringBuilder query = new StringBuilder();
		Date to = null;
		Date from = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			query.append("select r from RegistroMoc r where 1=1 ");
			if (data != null) {
				if (data.getName() != null) {
					query.append(" and r.numero like :numero ");
				}
				if (data.getFrom() != null) {
					from = sdf.parse(data.getFrom());
					query.append(" and r.dataRegistrazione != null and r.dataRegistrazione >= :from ");
				}
				if (data.getTo() != null) {
					to = sdf.parse(data.getTo());
					query.append(" and r.dataRegistrazione != null and r.dataRegistrazione <= :to ");
				}
			}
			Query q = entityManager.createQuery(query.toString(), RegistroMoc.class);
			if (data != null && data.getName() != null) {
				q.setParameter("numero", "%" + data.getName() + "%");
			}
			if (from != null) {
				q.setParameter("from", from);
			}
			if (to != null) {
				q.setParameter("to", to);
			}
			return q.getResultList();
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	@Override
	public List<RegistroDe> getRegistroDE(DERegistryFilter filter, String asset) {
		StringBuilder query = new StringBuilder();
		Date to = null;
		Date from = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			query.append("select r from RegistroDe r where 1=1 ");
			if (filter != null) {
				if (filter.getTipo() != null) {
					query.append(" and r.tipo = :tipo ");
				}
				if (filter.getSeverity() != null) {
					query.append(" and r.severity = :severity ");
				}
				if (filter.getFrom() != null) {
					from = sdf.parse(filter.getFrom());
					query.append(" and r.dataApertura != null and r.dataApertura >= :from ");
				}
				if (filter.getTo() != null) {
					to = sdf.parse(filter.getTo());
					query.append(" and r.dataApertura != null and r.dataApertura <= :to ");
				}
			}
			Query q = entityManager.createQuery(query.toString(), RegistroMoc.class);
			if (filter != null && filter.getTipo() != null) {
				q.setParameter("tipo", filter.getTipo());
			}
			if (filter != null && filter.getSeverity() != null) {
				q.setParameter("severity", filter.getSeverity());
			}
			if (from != null) {
				q.setParameter("from", from);
			}
			if (to != null) {
				q.setParameter("to", to);
			}
			return q.getResultList();
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	@Override
	public List<BacklogStateHistory> getBacklogStateHistory(List<String> odmList) {
		logger.debug("Called getBacklogStateHistory");
		List<BacklogStateHistory> results = new ArrayList<>();
		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append("SELECT * from ASSETINT.BACKLOG_STATE_HISTORY ");
			querySql.append("WHERE (N_ODM, DATA_INIZIO_STATO) IN ");
			querySql.append("(SELECT N_ODM, MAX(DATA_INIZIO_STATO) AS DATA_INIZIO_STATO ");
			querySql.append("FROM ASSETINT.BACKLOG_STATE_HISTORY ");
			querySql.append("WHERE STATO_UTENTE_ODM IN ('ORIG', 'SUSP', 'ISSU') ");

			if (!odmList.isEmpty()) {
				String citiesCommaSeparated = String.join(",", odmList);
				querySql.append(" AND N_ODM in (").append(citiesCommaSeparated).append(") ");
			}
			querySql.append("GROUP BY N_ODM)");

			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();
			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					BacklogStateHistory b = new BacklogStateHistory();
					b.setId((Integer) object[0]);
					b.setnOdm((String) object[1]);
					b.setStatoUtenteOdm((String) object[2]);
					b.setDataInizioStato((Date) object[3]);
					b.setDataFineStato((Date) object[4]);
					results.add(b);
				}
			}
			return results;
		} catch (Exception e) {
			logger.error("error during getBacklogStateHistory ", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}
}
