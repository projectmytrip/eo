package com.eni.ioc.assetintegrity.dao;

 import com.eni.ioc.assetintegrity.enitities.CriticalSignalDto;
import com.eni.ioc.assetintegrity.entities.CriticalSignal;
import com.eni.ioc.assetintegrity.entities.MocRequest;
import com.eni.ioc.assetintegrity.entities.WfRequest;
import com.eni.ioc.assetintegrity.utils.AssetIntegrityException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eni.ioc.assetintegrity.entities.Shift;
/**
 * 
 * @author generated automatically by eni-msa-mw-archetype
 * 
 */
@Repository
@Transactional
public class AssetIntegrityDaoWfImpl implements AssetIntegrityDaoWf {

	private static final String DATABASE_TIMEZONE = "UTC";
	private static final String APPLICATION_TIMEZONE = "CET";
	
	private static final String NOW_APP_TIMEZONE = "CONVERT_TZ(NOW(), '" + DATABASE_TIMEZONE + "', '" + APPLICATION_TIMEZONE + "')";

	private static final Logger logger = LoggerFactory.getLogger(AssetIntegrityDaoWfImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<CriticalSignalDto> pollingMOC(List<String> input, String asset){
		logger.debug("Called pollingMOC: {}", asset);
		List<CriticalSignalDto> results = new ArrayList<>();

		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for(int i=0; i <input.size(); i++){
			sb.append("'");
			if(input.get(i) != null){				
				sb.append(input.get(i));
			}
			sb.append("'");
			if(i != (input.size()-1)) {
				sb.append(", ");
			}
		}
		sb.append(")");
		
		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append("SELECT DISTINCT NAME, Area, Description, Severity ");
			querySql.append("FROM ASSETINT.`SEGNALI CRITICI` ");
			querySql.append("WHERE BlockInput = 'TRUE' AND ");
			querySql.append(" NAME in ").append(sb.toString());
			querySql.append(" and ");
			querySql.append("Asset = '" + asset + "'");

			Query query = entityManager.createNativeQuery(querySql.toString());

			@SuppressWarnings("unchecked")
			List<Object[]> lista = query.getResultList();

			if ( lista != null && !lista.isEmpty() ) {
				for ( Object[] object : lista ) {
					CriticalSignalDto o = new CriticalSignalDto();
					o.setName((String) object[0]);
					o.setArea((String) object[1]);
					o.setDescription((String) object[2]);
					o.setSeverity((String) object[3]);
					results.add(o);
				}
			}
			return results;
		} catch (Exception e) {
			logger.error("error during pollingMOC", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public void insertSegnaliCriticiWf(String hash, List<com.eni.ioc.assetintegrity.enitities.CriticalSignal> signals, Date start, Date end) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat sdfLastChangeDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		sdfLastChangeDate.setTimeZone(TimeZone.getTimeZone("CET"));

		logger.debug("insertSegnaliCriticiWf");

		try {
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO ASSETINT.SEGNALI_WF ( HASH_KEY, NAME, DATA_INIZIO, DATA_FINE, DATA_DISATTIVAZIONE) VALUES ");
			int i = 0;
			for (com.eni.ioc.assetintegrity.enitities.CriticalSignal elt : signals) {
				logger.info(elt.toString());
				i++;
				sb.append(" (");
				sb.append("'").append(hash).append("',");
				sb.append("'").append(elt.getName()).append("', ");
				sb.append("'").append(sdf.format(start)).append("',");
				sb.append("'").append(sdf.format(end)).append("'");
				try {
					if (elt.getBlockInput().equals("True") && elt.getLastStatusChangeDate() != null
							&& !"".equals(elt.getLastStatusChangeDate())) {
						Date d = sdfLastChangeDate.parse(elt.getLastStatusChangeDate());
						sb.append(", '").append(sdf.format(d)).append("'");
					} else {
						sb.append(", NULL");
					}

				} catch (Exception e) {
					logger.info("Errore nel formatting di LastStatusChangeDate, inserisco NULL");
					sb.append(", NULL");
				}

				
				
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

	public Map<String, List<CriticalSignalDto>> getCriticalSignalsWithOpenWF(String asset) {
		logger.debug("Called getCriticalSignalsWithOpenWF: {}", asset);
		Map<String, List<CriticalSignalDto>> results = new HashMap<>();

		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append("SELECT DISTINCT (SC.NAME), Area, Description, Severity, WF_ID ");
			querySql.append("FROM ASSETINT.`SEGNALI CRITICI` SC LEFT JOIN ASSETINT.SEGNALI_WF SWF ON SC.NAME = SWF.NAME ");
			querySql.append("WHERE BlockInput = 'TRUE' AND `BlockInput Status` IN ('0','1') AND ");
			querySql.append("CHIUSO = 0 AND APPROVATO = 0 AND WF_ID IS NOT NULL AND ");
			querySql.append("Asset = '" + asset + "'");

			Query query = entityManager.createNativeQuery(querySql.toString());

			@SuppressWarnings("unchecked")
			List<Object[]> lista = query.getResultList();

			if ( lista != null && !lista.isEmpty() ) {
				for ( Object[] object : lista ) {
					List<CriticalSignalDto> dtoList = null;
					if ( results.get(String.valueOf(object[4])) != null ) {
						dtoList = results.get(String.valueOf(object[4]));
					} else {
						dtoList = new ArrayList<>();
					}

					CriticalSignalDto o = new CriticalSignalDto();
					o.setName((String) object[0]);
					o.setArea((String) object[1]);
					o.setDescription((String) object[2]);
					o.setSeverity((String) object[3]);
					dtoList.add(o);
					
					results.put(String.valueOf(object[4]), dtoList);
				}
			}

			return results;
		} catch (Exception e) {
			logger.error("error during getCriticalSignalsWithOpenWF", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	public List<CriticalSignalDto> getCriticalSignalsWarnings(String asset) {
		logger.debug("Called getCriticalSignalsWarnings: {}", asset);
		List<CriticalSignalDto> results = new ArrayList<>();

		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append("SELECT DISTINCT (SC.NAME), Area, Description, Severity, LastStatusChangeDate, `BlockInput Status` ");
			querySql.append(
					"FROM ASSETINT.`SEGNALI CRITICI` SC LEFT JOIN ASSETINT.SEGNALI_WF SWF ON SC.NAME = SWF.NAME ");
			querySql.append("WHERE BlockInput = 'TRUE' AND `BlockInput Status` in ('0','1') AND ");
			//querySql.append("(CHIUSO IS NULL OR ((CHIUSO = '1'  OR DATA_ATTIVAZIONE IS NOT NULL) AND DATA_INIZIO <
			// " + NOW_APP_TIMEZONE + " AND DATA_FINE > " + NOW_APP_TIMEZONE + ")) AND ");
			// querySql.append("(Severity = 'SCE1' OR Severity = 'SCE2') AND ");
			querySql.append("Asset = '" + asset + "'");

			Query query = entityManager.createNativeQuery(querySql.toString());

			@SuppressWarnings("unchecked")
			List<Object[]> lista = query.getResultList();

			if ( lista != null && !lista.isEmpty() ) {
				for ( Object[] object : lista ) {
					CriticalSignalDto o = new CriticalSignalDto();
					o.setName((String) object[0]);
					o.setArea((String) object[1]);
					o.setDescription((String) object[2]);
					o.setSeverity((String) object[3]);
					o.setLastStatusChangeDate((String) object[4]);
					o.setBlockInputStatus((String) object[5]);
					results.add(o);
				}
			}
			return results;
		} catch (Exception e) {
			logger.error("error during getCriticalSignalsWarnings", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public List<CriticalSignalDto> getSignalStatus(String asset, String wfId) {
		logger.debug("Called getSignalStatus: {}", wfId);
		List<CriticalSignalDto> results = new ArrayList<>();

		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append("SELECT DISTINCT(SC.NAME) as Name, Area, Description, Severity, SWF.MoC as MoC FROM `ASSETINT`.`SEGNALI CRITICI` SC ");
			querySql.append(" LEFT JOIN ASSETINT.SEGNALI_WF SWF ON (SC.NAME = SWF.NAME) ");
			querySql.append("WHERE SWF.WF_ID = '"+wfId+"' AND "
					+ "SWF.DATA_DISATTIVAZIONE IS NOT NULL AND SWF.DATA_ATTIVAZIONE IS NULL"
					+ " AND SC.`Asset` = '"+asset+"';");

			logger.debug(querySql.toString());
			Query sql = entityManager.createNativeQuery(querySql.toString(), CriticalSignal.class);
			@SuppressWarnings("unchecked")
			List<CriticalSignal> lista = sql.getResultList();

			if (lista != null && !lista.isEmpty()) {
				for (CriticalSignal object : lista) {
					CriticalSignalDto o = new CriticalSignalDto();
					o.setName(object.getName());
					o.setArea(object.getArea());
					o.setDescription(object.getDescription());
					o.setSeverity(object.getSeverity());
					o.setMoc(object.getMoc());
					results.add(o);
				}
			}
			return results;
		} catch (Exception e) {
			logger.error("error during getSignalStatus", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}
	
	
	@Override
	public void updateSignalMoc(CriticalSignalDto signal, String asset, String uid) {
		logger.debug("updateSignalMoc");

		try {
			StringBuilder sb = new StringBuilder();

			sb.append("UPDATE `ASSETINT`.`SEGNALI_WF` SET `MoC` = "+signal.getMoc()+" , `LAST_UPDATE_MOC` = NOW() , `UID_UPDATE_MOC` = '"+uid+"' ");
			sb.append(" WHERE `NAME` = '"+signal.getName()+"' AND `WF_ID` = '"+signal.getWfId()+"'");
			
			logger.info("query updateSignalMoc: " + sb.toString());
			Query sql = entityManager.createNativeQuery(sb.toString());
			sql.executeUpdate();

		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	@Override
	public void storeMocRequest(String title, String number, Date date, String reasons, String otherReason,
			String priority, String type, Date typeDate, String description, String documentation, String signals, 
			String user, String uid) {
		logger.debug("Called storeWFRequest");
		try {
			MocRequest mocr = new MocRequest();
			mocr.setTitle(title);
			mocr.setNumber(number);
			mocr.setDate(date);
			mocr.setReasons(reasons);
			mocr.setOtherReason(otherReason);
			mocr.setPriority(priority);
			mocr.setType(type);
			mocr.setTypeDate(typeDate);
			mocr.setDescription(description);
			mocr.setDocumentation(documentation);
			mocr.setSignals(signals);
			mocr.setUser(user);
			mocr.setUid(uid);
			
			entityManager.persist(mocr);
			entityManager.flush();
			
		} catch (Exception e) {
			logger.error("error during storeMocRequest ", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}
/*
	@Override
	public List<Shift> getShifts() {
		Query q = entityManager.createQuery("select t from Shift t", Shift.class);
		return q.getResultList();
	}
*/
	
	@Override
	public List<CriticalSignalDto> getCriticalSignalsWarningsByDatetimeRange(String asset, LocalDateTime startRange, LocalDateTime endRange) {
		logger.debug("Called getCriticalSignalsWarningsByDatetimeRange: {}", asset);
		List<CriticalSignalDto> results = new ArrayList<>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append("SELECT DISTINCT (SC.NAME), Area, Description, Severity, LastStatusChangeDate ");
			querySql.append(
					"FROM ASSETINT.`SEGNALI CRITICI` SC LEFT JOIN ASSETINT.SEGNALI_WF SWF ON SC.NAME = SWF.NAME ");
			querySql.append("WHERE BlockInput = 'TRUE' AND `BlockInput Status` = '0' AND ");
			querySql.append("(CHIUSO IS NULL OR ((CHIUSO = '1'  OR DATA_ATTIVAZIONE IS NOT NULL) AND DATA_INIZIO < " + NOW_APP_TIMEZONE + " AND DATA_FINE > " + NOW_APP_TIMEZONE + ")) AND ");
			// querySql.append("(Severity = 'SCE1' OR Severity = 'SCE2') AND ");
			querySql.append("Asset = '" + asset + "' AND ");
			querySql.append("STR_TO_DATE(LastStatusChangeDate, '%d/%m/%Y %H:%i:%s') >= STR_TO_DATE('" + startRange.format(formatter) + "', '%d/%m/%Y %H:%i:%s') AND ");
			querySql.append("STR_TO_DATE(LastStatusChangeDate, '%d/%m/%Y %H:%i:%s') <= STR_TO_DATE('" + endRange.format(formatter) + "', '%d/%m/%Y %H:%i:%s')");
			
			Query query = entityManager.createNativeQuery(querySql.toString());

			@SuppressWarnings("unchecked")
			List<Object[]> lista = query.getResultList();

			if ( lista != null && !lista.isEmpty() ) {
				for ( Object[] object : lista ) {
					CriticalSignalDto o = new CriticalSignalDto();
					o.setName((String) object[0]);
					o.setArea((String) object[1]);
					o.setDescription((String) object[2]);
					o.setSeverity((String) object[3]);
					o.setLastStatusChangeDate((String) object[4]);
					results.add(o);
				}
			}
			return results;
		} catch (Exception e) {
			logger.error("error during getCriticalSignalsWarningsByDatetimeRange", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}
	
}
