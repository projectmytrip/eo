package com.eni.ioc.assetintegrity.dao;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
import com.eni.ioc.assetintegrity.utils.AssetIntegrityException;
import com.eni.ioc.assetintegrity.utils.EWPConstants;

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

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertSegnaliCritici(List<SegnaleCritico> segnaliCritici) {
		try {
			logger.debug("inserting");
			if (!segnaliCritici.isEmpty()) {
				Set<String> availableAreas = new HashSet<>(getAreas("COVA", "1"));

				StringBuilder sb = new StringBuilder();
				sb.append(
						" REPLACE INTO ASSETINT.`SEGNALI CRITICI` ( Area, LastStatusChangeDate, Value, Category, Name, Description, BlockInput, `BlockInput Status`, Severity, Asset) ");
				sb.append(" VALUES ");

				int i = 0;
				for (SegnaleCritico kpi : segnaliCritici) {
					i++;

					sb.append(" (");
					if (availableAreas.contains(kpi.getArea())) {
						sb.append("'").append(kpi.getArea()).append("',");
					} else {
						sb.append("DEFAULT,");
					}
					sb.append("'").append(kpi.getLastStatusChangeDate()).append("',");
					sb.append("'").append(kpi.getValue()).append("',");
					sb.append("'").append(kpi.getCategory()).append("',");
					sb.append("'").append(kpi.getName()).append("',");
					sb.append("'").append(kpi.getDescription()).append("',");
					sb.append("'").append(kpi.getBlockInput()).append("',");
					sb.append("'").append(kpi.getBlockInputStatus().toUpperCase()).append("',");
					sb.append("'").append(kpi.getSeverity()).append("',");
					sb.append("'").append(kpi.getAsset()).append("'");
					sb.append(")");
					if (i < segnaliCritici.size()) {
						sb.append(",");
					}
				}
				sb.append(";");

				logger.info(String.format("query insertSegnaliCritici: %s", sb.toString()));
				Query sql = entityManager.createNativeQuery(sb.toString());
				sql.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("Error during insertSegnaliCritici ", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}
	
	@Override
	public void insertMoc(RegistroMoc input){
		entityManager.merge(input);
		entityManager.flush();
	}

	private List<String> getAreas(String asset, String parentId) {
		String query = "SELECT NAME FROM ASSETINT.AREAS WHERE ASSET = '" + asset + "' AND PARENT_MAP = " + parentId
				+ ";";
		Query sql = entityManager.createNativeQuery(query);
		return sql.getResultList();
	}

	@Override
	public void insertWellAlarm(List<WellAlarm> wellAlarms) {
		try {
			logger.debug("inserting");
			StringBuilder sb = new StringBuilder();
			sb.append(" REPLACE INTO ASSETINT.WELL_ALARM ");
			sb.append(
					" ( COMPANY, FIELD, WELL_CODE , WELL_NAME, GENERAL_ALARM, GENERAL_ALARM_DESCRIPTION, SAFETY_VALVE, SAFETY_VALVE_DESCRIPTION, ");
			sb.append(
					" WELLHEAD_TEST, WELLHEAD_TEST_DESCRIPTION, ANNULUS_PRESSURE, ANNULUS_PRESSURE_DESCRIPTION, ASSET, REF_DATE, INSERTION_DATE, FIELD_CD ) ");
			sb.append(" VALUES ");

			int i = 0;
			for (WellAlarm wellAlarm : wellAlarms) {
				i++;
				sb.append(" (");
				sb.append("'").append(wellAlarm.getCompany().replace("'", "\\\'")).append("',");
				sb.append("'").append(wellAlarm.getField().replace("'", "\\\'")).append("',");
				sb.append("'").append(wellAlarm.getWellCode()).append("',");
				sb.append("'").append(wellAlarm.getWellName().replace("'", "\\\'")).append("',");
				sb.append("'").append(wellAlarm.getGeneralAlarm()).append("',");
				sb.append("'").append(wellAlarm.getGeneralAlarmDescription().replace("'", "\\\'")).append("',");
				sb.append("'").append(wellAlarm.getSafetyValve()).append("',");
				sb.append("'").append(wellAlarm.getSafetyValveDescription().replace("'", "\\\'")).append("',");
				sb.append("'").append(wellAlarm.getWellheadTest()).append("',");
				sb.append("'").append(wellAlarm.getWellheadTestDescription().replace("'", "\\\'")).append("',");
				sb.append("'").append(wellAlarm.getAnnulusPressure()).append("',");
				sb.append("'").append(wellAlarm.getAnnulusPressureDescription().replace("'", "\\\'")).append("',");
				sb.append("'").append(wellAlarm.getAsset()).append("',");
				sb.append("'").append(wellAlarm.getRefDate() != null ? wellAlarm.getRefDate() : "").append("',");
				sb.append("'").append(wellAlarm.getInsertionDate() != null ? wellAlarm.getInsertionDate() : "")
						.append("',");
				sb.append("'").append(wellAlarm.getFieldCD() != null ? wellAlarm.getFieldCD() : "").append("'");
				sb.append(")");
				if (i < wellAlarms.size()) {
					sb.append(",");
				}
			}
			sb.append(";");

			logger.info(String.format("query insertWellAlarm: %s", sb.toString()));
			Query sql = entityManager.createNativeQuery(sb.toString());
			sql.executeUpdate();
		} catch (Exception e) {
			logger.error("Error during insertWellAlarm ", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public void insertWeekWellAlarm(List<WeekWellAlarm> wellAlarms) {

		try {

			logger.debug("replacing");
			StringBuilder sb = new StringBuilder();
			sb.append(" REPLACE INTO ASSETINT.WELL_WEEK_ALARM ");
			sb.append(
					" ( COMPANY, FIELD, WELL_CODE , WELL_NAME, GENERAL_ALARM, GENERAL_ALARM_DESCRIPTION, SAFETY_VALVE, SAFETY_VALVE_DESCRIPTION, ");
			sb.append(
					" WELLHEAD_TEST, WELLHEAD_TEST_DESCRIPTION, ANNULUS_PRESSURE, ANNULUS_PRESSURE_DESCRIPTION, ASSET, REF_DATE, INSERTION_DATE, FIELD_CD, CURR_QUARTER ) ");
			sb.append(" VALUES ");

			int i = 0;
			for (WeekWellAlarm wellAlarm : wellAlarms) {
				i++;
				sb.append(" (");
				sb.append("'").append(wellAlarm.getCompany().replace("'", "\\\'")).append("',");
				sb.append("'").append(wellAlarm.getField().replace("'", "\\\'")).append("',");
				sb.append("'").append(wellAlarm.getWellCode()).append("',");
				sb.append("'").append(wellAlarm.getWellName().replace("'", "\\\'")).append("',");
				sb.append("'").append(wellAlarm.getGeneralAlarm()).append("',");
				sb.append("'").append(wellAlarm.getGeneralAlarmDescription().replace("'", "\\\'")).append("',");
				sb.append("'").append(wellAlarm.getSafetyValve()).append("',");
				sb.append("'").append(wellAlarm.getSafetyValveDescription().replace("'", "\\\'")).append("',");
				sb.append("'").append(wellAlarm.getWellheadTest()).append("',");
				sb.append("'").append(wellAlarm.getWellheadTestDescription().replace("'", "\\\'")).append("',");
				sb.append("'").append(wellAlarm.getAnnulusPressure()).append("',");
				sb.append("'").append(wellAlarm.getAnnulusPressureDescription().replace("'", "\\\'")).append("',");
				sb.append("'").append(wellAlarm.getAsset()).append("',");
				sb.append("'").append(wellAlarm.getRefDate() != null ? wellAlarm.getRefDate() : "").append("',");
				sb.append("'").append(wellAlarm.getInsertionDate() != null ? wellAlarm.getInsertionDate() : "")
						.append("',");
				sb.append("'").append(wellAlarm.getFieldCD() != null ? wellAlarm.getFieldCD() : "").append("', ");
				sb.append("'").append(wellAlarm.getCurrQuarter() != null ? wellAlarm.getCurrQuarter() : "").append("'");
				sb.append(")");
				if (i < wellAlarms.size()) {
					sb.append(",");
				}
			}
			sb.append(";");

			Query sql = entityManager.createNativeQuery(sb.toString());
			sql.executeUpdate();
		} catch (Exception e) {
			logger.error("Error during insertWellAlarm ", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public int callInsertBacklog() {
		String procedureQuery = "CALL ASSETINT.insert_backlog();";
		Query sql = entityManager.createNativeQuery(procedureQuery);
		return sql.executeUpdate();
	}

	@Override
	public void insertFile801(List<String[]> file801) {
		entityManager.createNativeQuery("DELETE FROM ASSETINT.SAP_BACKLOG_FILE801;").executeUpdate();
		entityManager.flush();

		if (file801 != null && !file801.isEmpty()) {
			insertFromCsv(file801, "ASSETINT.SAP_BACKLOG_FILE801");

		}
	}

	@Override
	public void insertFile701(List<String[]> file701) {
		entityManager.createNativeQuery("DELETE FROM ASSETINT.SAP_BACKLOG_FILE701;").executeUpdate();
		entityManager.flush();
		if (file701 != null && !file701.isEmpty()) {
			insertFromCsv(file701, "ASSETINT.SAP_BACKLOG_FILE701");

		}
	}

	@Override
	public void updateCriticalSignalsActivationDates() {
		// NOTA: sc.LastStatusChangeDate può essere == '' !!!
		// per evitare problemi qui sotto, settiamo swf.DATA_DISATTIVAZIONE =
		// '1970-01-01 00:00:01' nel caso di sc.LastStatusChangeDate != ''
		try {
			logger.debug("updateCriticalSignalsActivationDates");
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE ASSETINT.SEGNALI_WF swf, ASSETINT.`SEGNALI CRITICI` sc ");
			sb.append("SET swf.DATA_DISATTIVAZIONE = ");
			sb.append("CASE ");
			sb.append(
					" WHEN sc.BlockInput = 'True' THEN (CASE WHEN sc.LastStatusChangeDate IS NOT NULL and sc.LastStatusChangeDate != '' THEN str_to_date(sc.LastStatusChangeDate, '%d/%m/%Y %T') ");
			sb.append(" ELSE '1970-01-01 00:00:01' END)");
			sb.append(" ELSE swf.DATA_DISATTIVAZIONE ");
			sb.append("END, ");
			sb.append("swf.DATA_ATTIVAZIONE = ");
			sb.append("CASE ");
			sb.append(" WHEN sc.BlockInput = 'False' AND swf.DATA_DISATTIVAZIONE IS NOT NULL AND swf.DATA_ATTIVAZIONE IS NULL and  ");
			sb.append(" sc.LastStatusChangeDate IS NOT NULL and sc.LastStatusChangeDate != '' THEN str_to_date(sc.LastStatusChangeDate, '%d/%m/%Y %T') ");
			sb.append(" ELSE swf.DATA_ATTIVAZIONE ");
			sb.append("END ");
			sb.append("WHERE swf.NAME = sc.Name AND swf.CHIUSO = 0;");

			logger.info("query updateCriticalSignalsActivationDates: {}", sb.toString());
			Query sql = entityManager.createNativeQuery(sb.toString());
			sql.executeUpdate();
		} catch (Exception e) {
			logger.error("There was an error while trying to update the (de)activation dates on table SEGNALI_WF", e);
		}
	}
	
	@Override
	public void updateRegistroDe(){
		try {
			logger.debug("updateRegistroDe");
			StringBuilder sb = new StringBuilder();
			sb.append(" UPDATE ASSETINT.DE_REGISTRY DE, ASSETINT.SEGNALI_WF swf ");
			sb.append(" SET DE.DATA_CHIUSURA = CASE WHEN (swf.DATA_ATTIVAZIONE IS NOT NULL) THEN swf.DATA_ATTIVAZIONE ELSE DE.DATA_CHIUSURA END, ");
			sb.append(" DE.DURATA_ATTIVITA = CASE WHEN (swf.DATA_ATTIVAZIONE IS NOT NULL) THEN DATEDIFF(swf.DATA_ATTIVAZIONE, DE.DATA_APERTURA) ELSE DE.DURATA_ATTIVITA END ");
			sb.append(" WHERE DE.HASH_KEY = swf.HASH_KEY ");
			logger.info("query updateRegistroDe: {}", sb.toString());
			Query sql = entityManager.createNativeQuery(sb.toString());
			sql.executeUpdate();
		} catch (Exception e) {
			logger.error("There was an error while updateRegistroDe", e);
		}
	}

	@Override
	public boolean updateCriticalSignWF(String asset, List<String> element, String tmpKey, String wf, Boolean approved,
			Boolean closed, String start, String end) {
		try {
			logger.debug("updateCriticalSignWF");
			StringBuilder sb = new StringBuilder();

			sb.append(" UPDATE ASSETINT.SEGNALI_WF SET ");
			sb.append(" WF_ID=");
			sb.append("'").append(wf).append("',");
			sb.append(" APPROVATO=");
			sb.append(approved != null && approved ? " 1, " : " 0, ");
			sb.append(" CHIUSO=");
			sb.append(closed != null && closed ? " 1, " : " 0, ");
			sb.append(" DATA_INIZIO=");
			sb.append("'").append(start).append("',");
			sb.append(" DATA_FINE=");
			sb.append("'").append(end).append("'");
			sb.append(" WHERE HASH_KEY=");
			sb.append("'").append(tmpKey).append("'");

			logger.info("query updateCriticalSignWF: " + sb.toString());
			Query sql = entityManager.createNativeQuery(sb.toString());
			return sql.executeUpdate() > 0;
		} catch (Exception e) {
			logger.error("Error during updateCriticalSignWF ", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}

	}

	private void insertFromCsv(List<String[]> csvContent, String tableName) {

		int batchSize = 300;
		int batches = (int) (csvContent.size() / (double) batchSize);
		if (logger.isInfoEnabled()) {
			logger.info("rows num= " + csvContent.size());
		}
		for (int batch = 0; batch <= batches; batch++) {
			logger.debug("taking sublist from " + batch * batchSize +  " to " + ((batch + 1) * batchSize > csvContent.size() ? csvContent.size() : (batch + 1) * batchSize));
			List<String[]> csvContentBatch = csvContent.subList(batch * batchSize,
					(batch + 1) * batchSize > csvContent.size() ? csvContent.size() : (batch + 1) * batchSize);

			
			if(csvContentBatch != null && !csvContentBatch.isEmpty()){
			
				for(String[] s : csvContentBatch){
					logger.debug(Arrays.toString(s));
				}
				
				//if(csvContentBatch == null){
				//logger.info("csvContentBatch è nullo");
				//}else{
				//logger.info("csvContentBatch size: {}", csvContentBatch.size());
				//}
				
				StringBuilder sb = new StringBuilder();
				sb.append(" INSERT INTO ").append(tableName).append(" VALUES ");
				int i = 0;
				for (String[] line : csvContentBatch) {
					String[] questionMarks = new String[line.length];
					Arrays.fill(questionMarks, "?");
					String queryQM = String.join(", ", questionMarks);
					i++;
					sb.append(" (").append(queryQM).append(") ");
					if (i < csvContentBatch.size()) {
						sb.append(",");
					}
				}
				sb.append(";");
				Query query = entityManager.createNativeQuery(sb.toString());
	
				int j = 0;
				for (String[] line : csvContentBatch) {
					for (int l = 0; l < line.length; l++) {
						String value = line[l];
						//logger.info("value: {}", value);
						query.setParameter(j * line.length + l + 1, value.replace("'", "\\'"));
					}
					j++;
				}
	
				query.executeUpdate();
				logger.info("Done batch insert from " + (batch * batchSize) + " to "
						+ (batch * batchSize + csvContentBatch.size()));
			}
			
		}

	}

	@Override
	public void insertBacklogTotalCount(List<String[]> parseCsvFile) {
		StringBuilder sb = new StringBuilder();
		sb.append(" INSERT INTO ASSETINT.BACKLOG_ODM_COUNT VALUES ");
		int i = 0;
		for (String[] line : parseCsvFile) {
			i++;
			sb.append(" (");
			for (int k = 0; k < line.length; k++) {
				String value = line[k];
				sb.append("'").append(value.replace("'", "\\'"));
				sb.append("', ");
			}
			LocalDateTime date = LocalDateTime.now();
			sb.append("'").append(date).append("'");
			sb.append(" )");
			if (i < parseCsvFile.size()) {
				sb.append(",");
			}
		}
		sb.append(";");
		entityManager.createNativeQuery(sb.toString()).executeUpdate();
	}

	@Override
	public void insertOperatingWindow(List<OperatingWindowKpi> data) {
		data.forEach((op) -> {
			entityManager.persist(op);
		});
	}

	@Override
	public void insertIntegrityOperatingWindow(List<IntegrityOperatingWindowKpi> data) {
		data.forEach((op) -> {
			entityManager.persist(op);
		});
	}

	@Override
	public void insertCorrosionKpi(List<CorrosionKpi> data) {
		StringBuilder sb = new StringBuilder();
		sb.append(" INSERT INTO ASSETINT.KPI_OPERATIONAL_CORROSION ");
		sb.append(" ( NAME, VALUE, INSERTION_DATE, ASSET) ");
		sb.append(" VALUES ");
		int i = 0;
		for (CorrosionKpi op : data) {
			i++;
			sb.append(" (");
			sb.append("'").append(op.getName().replace("'", "\\\'")).append("',");
			sb.append("'").append(op.getValue()).append("',");
			sb.append("'").append(op.getInsertionDate()).append("',");
			sb.append("'").append(op.getAsset()).append("'");
			sb.append(")");
			if (i < data.size()) {
				sb.append(",");
			}
		}
		sb.append(";");
		Query sql = entityManager.createNativeQuery(sb.toString());
		sql.executeUpdate();
	}

	@Override
	public List<SegnaleCritico> getCriticalsByHashKey(String hashKey) {

		List<SegnaleCritico> res = new ArrayList<>();
		try {
			StringBuilder querySql = new StringBuilder();
			querySql.append(" SELECT Area, Name, Description, BlockInput, Severity, Asset ");
			querySql.append(" FROM ASSETINT.`SEGNALI CRITICI` where Name in ( ");
			querySql.append(" select Name from ASSETINT.SEGNALI_WF where HASH_KEY = '").append(hashKey).append("') ");

			logger.debug(querySql.toString());

			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();

			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					SegnaleCritico sign = new SegnaleCritico();
					sign.setArea((String) object[0]);
					sign.setName((String) object[1]);
					sign.setDescription((String) object[2]);
					sign.setBlockInput((String) object[3]);
					sign.setSeverity((String) object[4]);
					sign.setAsset((String) object[5]);

					res.add(sign);
				}
			}

			return res;
		} catch (Exception e) {
			logger.error("error during getCriticalsByHashKey", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public void insertCorrosionBacteria(List<CorrosionBacteria> data) {
		entityManager.createNativeQuery("TRUNCATE ASSETINT.CORROSION_BACTERIA;").executeUpdate();
		data.forEach((bacteria) -> {
			entityManager.persist(bacteria);
		});
	}

	@Override
	public void insertCorrosionCND(List<CorrosionCND> input) {
		StringBuilder sb = new StringBuilder();
		sb.append(" INSERT INTO ASSETINT.CORROSION_CND ");
		sb.append(
				" ( PLANT_ACRONYM,COMPONENT_NAME,NEXT_DATE,LAST_DATE,FREQUENCY,MODEL_NAME,DATE_TYPE,AREA,ASSET,INSERTION_DATE) ");
		sb.append(" VALUES ");
		String questionMark = "(?,?,?,?,?,?,?,?,?,?)";
		int i = 0;
		for (CorrosionCND cnd : input) {
			i++;
			sb.append(questionMark);
			if (i < input.size()) {
				sb.append(",");
			}
		}

		Query sql = entityManager.createNativeQuery(sb.toString());
		int rowNum = 0;
		int rowLenght = 10;
		for (CorrosionCND cnd : input) {
			sql.setParameter(rowNum * rowLenght + 1, cnd.getPlantAcronym());
			sql.setParameter(rowNum * rowLenght + 2, cnd.getComponentName());
			sql.setParameter(rowNum * rowLenght + 3, cnd.getNextDate());
			sql.setParameter(rowNum * rowLenght + 4, cnd.getLastDate());
			sql.setParameter(rowNum * rowLenght + 5, cnd.getFrequency());
			sql.setParameter(rowNum * rowLenght + 6, cnd.getModelName());
			sql.setParameter(rowNum * rowLenght + 7, cnd.getDateType());
			sql.setParameter(rowNum * rowLenght + 8, cnd.getArea());
			sql.setParameter(rowNum * rowLenght + 9, cnd.getAsset());
			sql.setParameter(rowNum * rowLenght + 10, cnd.getInsertionDate());
			rowNum++;
		}
		sql.executeUpdate();
	}

	public void truncateCND() {
		entityManager.createNativeQuery("TRUNCATE ASSETINT.CORROSION_CND;").executeUpdate();
	}

	public String escape(Object value) {
		if (value == null) {
			return "";
		} else {
			return value.toString().replace("'", "\\\'");
		}
	}

	@Override
	public void insertCorrosionCoupon(List<CorrosionCoupon> input) {
		input.forEach((coupon) -> {
			entityManager.persist(coupon);
		});
	}

	@Override
	public void insertCorrosionProtection(List<CorrosionProtection> input) {
		StringBuilder sb = new StringBuilder();
		sb.append(" INSERT INTO ASSETINT.CORROSION_PROTECTION");
		sb.append(" ( SECTION, DORSAL, EXTERNAL_CONDUIT_OFF, EXTERNAL_CONDUIT_ON, TUBE_OFF, TUBE_ON, "
				+ " NOT_PROTECTED_SIDE_OFF, NOT_PROTECTED_SIDE_ON, PROTECTED_SIDE_OFF, PROTECTED_SIDE_ON, DESCRIPTION, TAG, "
				+ " NEXT_DATE, LAST_DATE, ASSET, INSERTION_DATE, AREA ) ");
		sb.append(" VALUES ");
		String questionMark = "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int i = 0;
		for (CorrosionProtection protection : input) {
			i++;
			sb.append(questionMark);
			if (i < input.size()) {
				sb.append(",");
			}
		}

		Query sql = entityManager.createNativeQuery(sb.toString());
		int rowNum = 0;
		int rowLenght = 17;
		for (CorrosionProtection protection : input) {
			sql.setParameter(rowNum * rowLenght + 1, protection.getSection());
			sql.setParameter(rowNum * rowLenght + 2, protection.getDorsal());
			sql.setParameter(rowNum * rowLenght + 3, protection.getExternalConduitOff());
			sql.setParameter(rowNum * rowLenght + 4, protection.getExternalConduitOn());
			sql.setParameter(rowNum * rowLenght + 5, protection.getTuebOff());
			sql.setParameter(rowNum * rowLenght + 6, protection.getTuebOn());
			sql.setParameter(rowNum * rowLenght + 7, protection.getNotProtectedSideOff());
			sql.setParameter(rowNum * rowLenght + 8, protection.getNotProtectedSideOn());
			sql.setParameter(rowNum * rowLenght + 9, protection.getProtectedSideOff());
			sql.setParameter(rowNum * rowLenght + 10, protection.getProtectedSideOn());
			sql.setParameter(rowNum * rowLenght + 11, protection.getDescription());
			sql.setParameter(rowNum * rowLenght + 12, protection.getTag());
			sql.setParameter(rowNum * rowLenght + 13, protection.getNextDate());
			sql.setParameter(rowNum * rowLenght + 14, protection.getLastDate());
			sql.setParameter(rowNum * rowLenght + 15, protection.getAsset());
			sql.setParameter(rowNum * rowLenght + 16, protection.getInsertionDate());
			sql.setParameter(rowNum * rowLenght + 17, protection.getArea());
			rowNum++;
		}
		sql.executeUpdate();
	}

	@Override
	public void insertCorrosionPigging(List<CorrosionPigging> input) {
		StringBuilder sb = new StringBuilder();
		sb.append(" INSERT INTO ASSETINT.CORROSION_PIGGING");
		sb.append(
				" (SECTION, DORSAL, FEATURE, ERF, KP, MODEL, TECHNICAL_SITE, DESCRIPTION, NEXT_DATE, LAST_DATE, ASSET, INSERTION_DATE, AREA) ");
		sb.append(" VALUES ");
		String questionMark = "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int i = 0;
		for (CorrosionPigging pigging : input) {
			i++;
			sb.append(questionMark);
			if (i < input.size()) {
				sb.append(",");
			}
		}

		Query sql = entityManager.createNativeQuery(sb.toString());
		int rowNum = 0;
		int rowLenght = 13;
		for (CorrosionPigging pigging : input) {
			sql.setParameter(rowNum * rowLenght + 1, pigging.getSection());
			sql.setParameter(rowNum * rowLenght + 2, pigging.getDorsal());
			sql.setParameter(rowNum * rowLenght + 3, pigging.getFeature());
			sql.setParameter(rowNum * rowLenght + 4, pigging.getErf());
			sql.setParameter(rowNum * rowLenght + 5, pigging.getKp());
			sql.setParameter(rowNum * rowLenght + 6, pigging.getModel());
			sql.setParameter(rowNum * rowLenght + 7, pigging.getTechnicalSite());
			sql.setParameter(rowNum * rowLenght + 8, pigging.getDescription());
			sql.setParameter(rowNum * rowLenght + 9, pigging.getNextDate());
			sql.setParameter(rowNum * rowLenght + 10, pigging.getLastDate());
			sql.setParameter(rowNum * rowLenght + 11, pigging.getAsset());
			sql.setParameter(rowNum * rowLenght + 12, pigging.getInsertionDate());
			sql.setParameter(rowNum * rowLenght + 13, pigging.getArea());
			rowNum++;
		}
		sql.executeUpdate();
	}

	@Override
	public void truncateTable(String tableName) {
		entityManager.createNativeQuery("TRUNCATE ASSETINT." + tableName + ";").executeUpdate();
	}

	@Override
	public void truncateCorrosionCoupon(String tab) {
		entityManager.createNativeQuery("DELETE FROM ASSETINT.CORROSION_COUPON where TAB = '" + tab + "';")
				.executeUpdate();
	}

	@Override
	public void saveEVPMSStations(List<EVPMSStation> data) {
		StringBuilder sb = new StringBuilder();
		sb.append(" INSERT INTO ASSETINT.EVPMS_STATIONS");
		sb.append(
				" (STATION_ID,STATION_NAME,DORSAL,AC_PRESENCE,ACQUISITION,DEJITTER,GPS,GSM,INTRUSION,USB_CONNECTION, AREA, ASSET,INSERTION_DATE) ");
		sb.append(" VALUES ");
		String questionMark = "(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int i = 0;
		for (EVPMSStation station : data) {
			i++;
			sb.append(questionMark);
			if (i < data.size()) {
				sb.append(",");
			}
		}

		Query sql = entityManager.createNativeQuery(sb.toString());
		int rowNum = 0;
		int rowLenght = 13;
		for (EVPMSStation station : data) {
			sql.setParameter(rowNum * rowLenght + 1, station.getStationId());
			sql.setParameter(rowNum * rowLenght + 2, station.getStationName());
			sql.setParameter(rowNum * rowLenght + 3, station.getDorsal());
			sql.setParameter(rowNum * rowLenght + 4, station.getAcPresence());
			sql.setParameter(rowNum * rowLenght + 5, station.getAcquisition());
			sql.setParameter(rowNum * rowLenght + 6, station.getDejitter());
			sql.setParameter(rowNum * rowLenght + 7, station.getGps());
			sql.setParameter(rowNum * rowLenght + 8, station.getIntrusion());
			sql.setParameter(rowNum * rowLenght + 9, station.getUsbConnection());
			sql.setParameter(rowNum * rowLenght + 10, station.getGsm());
			sql.setParameter(rowNum * rowLenght + 11, station.getArea());
			sql.setParameter(rowNum * rowLenght + 12, station.getAsset());
			sql.setParameter(rowNum * rowLenght + 13, station.getInsertionDate());
			rowNum++;
		}
		sql.executeUpdate();
	}

	@Override
	public void saveEVPMSAlerts(List<EVPMSAlert> data) {
		StringBuilder sb = new StringBuilder();
		sb.append(" REPLACE INTO ASSETINT.EVPMS_ALERTS");
		sb.append(" (ALERT_ID,CHAINAGE,LATITUDE,LONGITUDE,ALERT_DATE,ALERT_TYPE,STATION_ID,ASSET,INSERTION_DATE) ");
		sb.append(" VALUES ");
		String questionMark = "(?,?,?,?,?,?,?,?,?)";
		int i = 0;
		for (EVPMSAlert alert : data) {
			i++;
			sb.append(questionMark);
			if (i < data.size()) {
				sb.append(",");
			}
		}

		Query sql = entityManager.createNativeQuery(sb.toString());
		int rowNum = 0;
		int rowLenght = 9;
		for (EVPMSAlert alert : data) {
			sql.setParameter(rowNum * rowLenght + 1, alert.getAlertKey().getAlertID());
			sql.setParameter(rowNum * rowLenght + 2, alert.getChainage());
			sql.setParameter(rowNum * rowLenght + 3, alert.getLatitude());
			sql.setParameter(rowNum * rowLenght + 4, alert.getLongitude());
			sql.setParameter(rowNum * rowLenght + 5, alert.getAlertDate());
			sql.setParameter(rowNum * rowLenght + 6, alert.getAlertType());
			sql.setParameter(rowNum * rowLenght + 7, alert.getIDStation());
			sql.setParameter(rowNum * rowLenght + 8, alert.getAsset());
			sql.setParameter(rowNum * rowLenght + 9, alert.getInsertionDate());
			rowNum++;
		}
		sql.executeUpdate();
	}

	public void insertJackedPipes(List<JacketedPipes> data) {
		StringBuilder sb = new StringBuilder();
		sb.append(" REPLACE INTO ASSETINT.JACKETED_PIPES");
		sb.append(" (TAG, ALARM,BAD_ACTIVE,DELTA_P,SUB_AREA,DESCRIPTION,AREA,ASSET,INSERTION_DATE)");
		sb.append(" VALUES ");
		String questionMark = "(?,?,?,?,?,?,?,?,?)";
		int i = 0;
		for (JacketedPipes alert : data) {
			i++;
			sb.append(questionMark);
			if (i < data.size()) {
				sb.append(",");
			}
		}

		Query sql = entityManager.createNativeQuery(sb.toString());
		int rowNum = 0;
		int rowLenght = 9;
		for (JacketedPipes pipe : data) {
			sql.setParameter(rowNum * rowLenght + 1, pipe.getTag());
			sql.setParameter(rowNum * rowLenght + 2, pipe.getAlarm());
			sql.setParameter(rowNum * rowLenght + 3, pipe.getBadActive());
			sql.setParameter(rowNum * rowLenght + 4, pipe.getDeltaP());
			sql.setParameter(rowNum * rowLenght + 5, pipe.getSubArea());
			sql.setParameter(rowNum * rowLenght + 6, pipe.getDescription());
			sql.setParameter(rowNum * rowLenght + 7, pipe.getArea());
			sql.setParameter(rowNum * rowLenght + 8, pipe.getAsset());
			sql.setParameter(rowNum * rowLenght + 9, pipe.getInsertionDate());
			rowNum++;
		}
		sql.executeUpdate();

	}

	@Override
	public void insertEWP(EWP ewp) {
		StringBuilder sb = new StringBuilder();

		try {
			String status = ewp.getStatus();

			switch (status) {
			case EWPConstants.APERTURAEWP:
				sb.append(
						"REPLACE INTO ASSETINT.EWP (ID, FASE, PREPOSTO, SORVEGLIANTE, DATA_INIZIO_AUTORIZZAZIONE, DATA_SCADENZA_AUTORIZZAZIONE, ");
				sb.append(
						" DESCRIZIONE_SCE, DESCRIZIONE, APPALTATORE, IMPRESA_ESECUTRICE, RICHIEDENTE, UNITA_RICHIEDENTE, ");
				sb.append(
						" TIPOLOGIA_ATTIVITA, SCE, DATA_APERTURA, DATA_SCADENZA, AREA, ASSET, AUTORIZZANTE, UNITA_AUTORIZZANTE, SEDE_TECNICA, SORT) ");
				sb.append(
						" VALUES (?1, ?2, ?3, ?5, ?6, ?4, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17, ?18, ?19, ?20, ?21, ?23) ");
				break;
			case EWPConstants.SCADUTOEWP:
				sb.append("UPDATE ASSETINT.EWP SET FASE=?2 WHERE ID=?1");
				break;
			case EWPConstants.RINNOVOEWP:
				sb.append(
						"UPDATE ASSETINT.EWP SET FASE=?2, PREPOSTO=?3, SORVEGLIANTE=?5, DATA_INIZIO_AUTORIZZAZIONE=?6, DATA_SCADENZA_AUTORIZZAZIONE=?4 WHERE ID=?1");
				break;
			case EWPConstants.SOSPENSIONEEWP:
				sb.append("UPDATE ASSETINT.EWP SET FASE=?2, DATA_SCADENZA_AUTORIZZAZIONE=?4 WHERE ID=?1");
				break;
			case EWPConstants.CHIUSURAEWP:
				sb.append("UPDATE ASSETINT.EWP SET FASE=?2, DATA_CHIUSURA=?22 WHERE ID=?1");
				break;
			default:
				// should never get here...
				logger.error("unknown eWP status: {}", status);
				throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError,
						"unknown eWP status: " + status);
			}

			Query sql = entityManager.createNativeQuery(sb.toString());

			boolean isApertura = EWPConstants.APERTURAEWP.equalsIgnoreCase(status);
			boolean isRinnovo = EWPConstants.RINNOVOEWP.equalsIgnoreCase(status);
			boolean isSospensione = EWPConstants.SOSPENSIONEEWP.equalsIgnoreCase(status);
			boolean isChiusura = EWPConstants.CHIUSURAEWP.equalsIgnoreCase(status);

			if (isApertura) {
				sql.setParameter(7, ewp.getDescrizioneSCE()); // "descSCE"
				sql.setParameter(8, ewp.getDescrizioneLavoro()); // "descrizione"
				sql.setParameter(9, ewp.getAppaltatore()); // "appalto"
				sql.setParameter(10, ewp.getImpresaEsecutrice()); // "impresa"
				sql.setParameter(11, ewp.getRichiedente()); // "richiedente"
				sql.setParameter(12, ewp.getUnitaRichiedente()); // "unitrichiedente"
				sql.setParameter(13, ewp.getIndicazioneTipologiaAttivita()); // "tipologia"
				sql.setParameter(14, ewp.getIndicazioneSCE()); // "sce"
				sql.setParameter(15, ewp.getDataApertura()); // "apertura"
				sql.setParameter(16, ewp.getDataScadenza()); // "dataScad"
				sql.setParameter(17, ewp.getSottoArea()); // "sottoarea"
				sql.setParameter(18, ewp.getAsset()); // "asset"
				sql.setParameter(19, ewp.getAutorizzanteAnalisiDiRischio()); // "autorizzante"
				sql.setParameter(20, ewp.getUnitaAutorizzante()); // "unitautorizzante"
				sql.setParameter(21, ewp.getSedeTecnica()); // "sedeTecnica"
				sql.setParameter(23, ewp.getTags()); // "sort"
			}

			if (isApertura || isRinnovo) {
				sql.setParameter(3, ewp.getPrepostoAiLavori()); // "preposto"
				sql.setParameter(5, ewp.getSorvegliante()); // "sorvegliante"
				sql.setParameter(6, ewp.getDataOraInizioAutorizzazione()); // "inizio"
			}

			if (isApertura || isRinnovo || isSospensione) {
				sql.setParameter(4, ewp.getDataOraScadenzaAutorizzazione()); // "scadenza"
			}

			if (isChiusura) {
				sql.setParameter(22, ewp.getDataChiusura());
			}

			sql.setParameter(1, ewp.getNumeroEWP()); // "ewp"
			sql.setParameter(2, status); // "status"
			sql.executeUpdate();

		} catch (Exception e) {
			logger.error("could not insert {}", ewp.toString());
			logger.error("ewp insertion failed", e);
		}
	}

	@Override
	public String getPreviousEWPState(String ewp) {
		String q = "select FASE from ASSETINT.EWP where ID = ?1";
		Query sql = entityManager.createNativeQuery(q);
		sql.setParameter(1, ewp);

		@SuppressWarnings("unchecked")
		List<Object> res = sql.getResultList();

		if (res != null && !res.isEmpty()) {
			// should be just 1
			if (res.get(0) != null) {
				return (String) res.get(0);
			}
			return null;
		}
		return null;
	}
	
	@Override
	public void insertDE(DERegistry input){
		Long id = hashKeyFound(input.getHashKey());
		StringBuilder sb = new StringBuilder();

		if(id == null){
			sb.append("INSERT INTO ASSETINT.DE_REGISTRY (")
			  .append("TIPO, SEVERITY, AREA, UNITA, NUMERO_DE, DESCRIZIONE, MOTIVO, DURATA, PERMESSI, DATA_APERTURA, DATA_CHIUSURA, DATA_MAX, ")
			  .append("DURATA_ATTIVITA, NOTE, HASH_KEY, WF_ID, WORKFLOW_DETAIL, ASSET ) VALUES (")
			  
			  .append(strigOrNull(input.getTipo())).append(",")
			  .append(strigOrNull(input.getSeverity())).append(",")
			  .append(strigOrNull(input.getArea())).append(",")
			  .append(strigOrNull(input.getUnita())).append(",")
			  .append(strigOrNull(input.getnDe())).append(",")
			  .append(strigOrNull(input.getDescrizione())).append(",")
			  .append(strigOrNull(input.getMotivo())).append(",")
			  .append(strigOrNull(input.getDurata())).append(",")
			  .append(strigOrNull(input.getPermessi())).append(",")
			  .append(dateOrNull(input.getApertura())).append(",")
			  .append(dateOrNull(input.getChiusura())).append(",")
			  .append(dateOrNull(input.getMax())).append(",")
			  .append(strigOrNull(input.getMaxDurata())).append(",")
			  .append(strigOrNull(input.getNote())).append(",")
			  .append(strigOrNull(input.getHashKey())).append(",")
			  .append(strigOrNull(input.getWfId())).append(",")
			  .append(strigOrNull(input.getWorkflowDetail())).append(",")
			  .append(strigOrNull(input.getAsset()))
			  
			  .append(")");
			entityManager.createNativeQuery(sb.toString()).executeUpdate();
		} else {
			sb.append("REPLACE INTO ASSETINT.DE_REGISTRY (")
			  .append("ID, TIPO, SEVERITY, AREA, UNITA, NUMERO_DE, DESCRIZIONE, MOTIVO, DURATA, PERMESSI, DATA_APERTURA, DATA_CHIUSURA, DATA_MAX, ")
			  .append("DURATA_ATTIVITA, NOTE, HASH_KEY, WF_ID, WORKFLOW_DETAIL, ASSET, WF_RIPRISTINO ) VALUES (")
			  .append(id).append(",")
			  .append(strigOrNull(input.getTipo())).append(",")
			  .append(strigOrNull(input.getSeverity())).append(",")
			  .append(strigOrNull(input.getArea())).append(",")
			  .append(strigOrNull(input.getUnita())).append(",")
			  .append(strigOrNull(input.getnDe())).append(",")
			  .append(strigOrNull(input.getDescrizione())).append(",")
			  .append(strigOrNull(input.getMotivo())).append(",")
			  .append(strigOrNull(input.getDurata())).append(",")
			  .append(strigOrNull(input.getPermessi())).append(",")
			  .append(dateOrNull(input.getApertura())).append(",")
			  .append(dateOrNull(input.getChiusura())).append(",")
			  .append(dateOrNull(input.getMax())).append(",")
			  .append(strigOrNull(input.getMaxDurata())).append(",")
			  .append(strigOrNull(input.getNote())).append(",")
			  .append(strigOrNull(input.getHashKey())).append(",")
			  .append(strigOrNull(input.getWfId())).append(",")
			  .append(strigOrNull(input.getWorkflowDetail())).append(",")
			  .append(strigOrNull(input.getAsset())).append(",")
			  .append(strigOrNull(input.getWfRipristino()))
			  .append(")");
			entityManager.createNativeQuery(sb.toString()).executeUpdate();
		}
		
		
	}
	
	private Long hashKeyFound(String hashKey){
		if(hashKey == null){
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("select ID from ASSETINT.DE_REGISTRY where HASH_KEY = '").append(hashKey).append("'");
		
		Query q = entityManager.createNativeQuery(sb.toString());
		List<Integer> res = q.getResultList();
		if(res != null && !res.isEmpty()){
			Integer i = res.get(0);
			return Long.valueOf(i);
		}
		return null;
	}
	
	@Override
	public List<SegnaleCritico> getCriticalByNameList(List<String> signals){
		List<SegnaleCritico> res = new ArrayList<>();
		try {
			StringBuilder querySql = new StringBuilder();
			StringBuilder signBuilder = new StringBuilder();
			signBuilder.append("(");
			for(int j=0;j<signals.size();j++){
				signBuilder.append("'").append(signals.get(j)).append("'");
				if(j!=(signals.size()-1)){
					signBuilder.append(", ");
				}
			}
			signBuilder.append(")");
			
			querySql.append(" SELECT Area, Name, Description, BlockInput, Severity, Asset ");
			querySql.append(" FROM ASSETINT.`SEGNALI CRITICI` where Name in ").append(signBuilder.toString());

			logger.debug(querySql.toString());

			Query sql = entityManager.createNativeQuery(querySql.toString());
			@SuppressWarnings("unchecked")
			List<Object[]> lista = sql.getResultList();

			if (lista != null && !lista.isEmpty()) {
				for (Object[] object : lista) {
					SegnaleCritico sign = new SegnaleCritico();
					sign.setArea((String) object[0]);
					sign.setName((String) object[1]);
					sign.setDescription((String) object[2]);
					sign.setBlockInput((String) object[3]);
					sign.setSeverity((String) object[4]);
					sign.setAsset((String) object[5]);

					res.add(sign);
				}
			}

			return res;
		} catch (Exception e) {
			logger.error("error during getCriticalByNameList", e);
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.dbError, e.getMessage());
		}
	}
	
	@Override
	public void saveBadJackedPipes(List<BadJacketedPipes> badPipes){
		StringBuilder sb = new StringBuilder();
		sb.append(" REPLACE INTO ASSETINT.BAD_JACKETED_PIPES");
		sb.append(" (TAG, ALARM,BAD_ACTIVE,DELTA_P,SUB_AREA,DESCRIPTION,AREA,ASSET,INSERTION_DATE)");
		sb.append(" VALUES ");
		String questionMark = "(?,?,?,?,?,?,?,?,?)";
		int i = 0;
		for (BadJacketedPipes alert : badPipes) {
			i++;
			sb.append(questionMark);
			if (i < badPipes.size()) {
				sb.append(",");
			}
		}

		Query sql = entityManager.createNativeQuery(sb.toString());
		int rowNum = 0;
		int rowLenght = 9;
		for (BadJacketedPipes pipe : badPipes) {
			sql.setParameter(rowNum * rowLenght + 1, pipe.getTag());
			sql.setParameter(rowNum * rowLenght + 2, pipe.getAlarm());
			sql.setParameter(rowNum * rowLenght + 3, pipe.getBadActive());
			sql.setParameter(rowNum * rowLenght + 4, pipe.getDeltaP());
			sql.setParameter(rowNum * rowLenght + 5, pipe.getSubArea());
			sql.setParameter(rowNum * rowLenght + 6, pipe.getDescription());
			sql.setParameter(rowNum * rowLenght + 7, pipe.getArea());
			sql.setParameter(rowNum * rowLenght + 8, pipe.getAsset());
			sql.setParameter(rowNum * rowLenght + 9, pipe.getInsertionDate());
			rowNum++;
		}
		sql.executeUpdate();

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BadJacketedPipes> getBadJackedPipes(String asset){
		Query q = entityManager.createQuery("select b from BadJacketedPipes b where b.asset = :asset", BadJacketedPipes.class);
		q.setParameter("asset", asset);

		return q.getResultList();
	}
	
	private String strigOrNull(String input){
		if (input == null) return null;
		return "'"+input+"'";
	}
	
	private String dateOrNull(String input){
		if (input == null || input == "") return null;
		Date d = null;
		SimpleDateFormat sdf = null;
		if(isNumeric(input)){
			d = new Date(Long.valueOf(input));
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} 
		if(d != null){
			return "str_to_date('"+sdf.format(d)+"','%Y-%m-%d %k:%i:%s')";
		} else {			
			return "str_to_date('"+input+"','%Y-%m-%d %k:%i:%s')";
		}
	}
	
	private boolean isNumeric(String input){
		try{
			Long.valueOf(input);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
}
