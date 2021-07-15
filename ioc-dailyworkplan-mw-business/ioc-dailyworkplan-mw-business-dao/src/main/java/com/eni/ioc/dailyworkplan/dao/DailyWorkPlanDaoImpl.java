package com.eni.ioc.dailyworkplan.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eni.ioc.dailyworkplan.api.ReportFilter;
import com.eni.ioc.dailyworkplan.entities.Activity;
import com.eni.ioc.dailyworkplan.entities.HistoryEmail;
import com.eni.ioc.dailyworkplan.entities.Planning;
import com.eni.ioc.dailyworkplan.entities.PlanningState;
import com.eni.ioc.dailyworkplan.utils.DailyWorkPlanException;

@Repository
@Transactional
public class DailyWorkPlanDaoImpl implements DailyWorkPlanDao {

	private static final Logger logger = LoggerFactory.getLogger(DailyWorkPlanDao.class);

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public Activity findByParams(Long idActivity) {
		try {
			StringBuilder q = new StringBuilder("select a from Activity a ");
			q.append("where 1=1 ");
			q.append("and a.id=:idActivity");
			Query query = entityManager.createQuery(q.toString(), Activity.class);
			query.setParameter("idActivity", idActivity);

			List<Activity> valueList = (List<Activity>) query.getResultList();
			if (valueList != null && !valueList.isEmpty()) {
				return valueList.get(0);
			}

			return null;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DailyWorkPlanException(DailyWorkPlanException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public List<Planning> findPlanningByReferenceDate(String asset, String fromDate, String toDate) {
		try {
			StringBuilder q = new StringBuilder("select p.ID, p.ASSET, p.INSERTION_DATE, p.FLG_EXIST, p.REFERENCE_DATE, p.INSERTION_BY, p.INSERTION_BY_USERNAME, ");
			q.append("s.ID, s.ASSET, s.INSERTION_DATE, s.FLG_EXIST, s.CODE, s.STATE ");
			q.append("from DAILYWORKPLAN.PLANNING p JOIN DAILYWORKPLAN.PLANNING_STATE s on p.STATE_ID = s.ID ");
			q.append("where 1=1 ");
			q.append("and p.ASSET = '").append(asset).append("'");
			q.append("and ( DATE(p.REFERENCE_DATE) BETWEEN STR_TO_DATE('").append(fromDate).append("', '%Y-%m-%d') AND STR_TO_DATE('").append(toDate).append("', '%Y-%m-%d') ) ");
			q.append("and p.FLG_EXIST = '1' ");
			Query query = entityManager.createNativeQuery(q.toString());
			List<Object[]> lista = query.getResultList();
			List<Planning> valueList = new ArrayList<Planning>();
			if ( lista != null && !lista.isEmpty() ) {
				for ( Object[] object : lista ) {
					Planning p = new Planning();
					p.setId(object[0] instanceof Integer ? new Long((Integer)object[0]) : (Long) object[0]);
					p.setAsset((String) object[1]);
					p.setInsertionDate((Date) object[2]);
					p.setFlgExist(String.valueOf((Integer) object[3]) );
					p.setReferenceDate((Date) object[4]);
					p.setInsertionBy((String) object[5]);
					p.setInsertionByUsername((String) object[6]);
					
					PlanningState s = new PlanningState();
					s.setId(object[7] instanceof Integer ? new Long((Integer)object[7]) : (Long) object[7]);
					s.setAsset((String) object[8]);
					s.setInsertionDate((Date) object[9]);
					s.setFlgExist(String.valueOf((Integer)object[10]));
					s.setCode((String) object[11]);
					s.setState((String) object[12]);
					
					p.setState(s);
					
					valueList.add(p);
				}
			}
			return valueList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DailyWorkPlanException(DailyWorkPlanException.CodeError.dbError, e.getMessage());
		}
	}

	@Override
	public void savePlanning(Planning planning) {
		planning.setFlgExist("1");
		if(planning.getInsertionDate() == null){
			LocalDateTime nowDateTime = LocalDateTime.now()
					.atZone(ZoneId.systemDefault())
					.withZoneSameInstant(ZoneId.of("CET"))
					.toLocalDateTime();
			Date nowDate = Date
		      .from(nowDateTime.atZone(ZoneId.systemDefault())
		      .toInstant());
			planning.setInsertionDate(nowDate);
		}
		entityManager.persist(planning);
	}

	@Override
	public void saveListActivities(List<Activity> activityList) {
		if(activityList != null && !activityList.isEmpty()){
			for(Activity activity : activityList){
				activity.setFlgExist("1");
				if(activity.getInsertionDate() == null){
					LocalDateTime nowDateTime = LocalDateTime.now()
							.atZone(ZoneId.systemDefault())
							.withZoneSameInstant(ZoneId.of("CET"))
							.toLocalDateTime();
					Date nowDate = Date
				      .from(nowDateTime.atZone(ZoneId.systemDefault())
				      .toInstant());
					activity.setInsertionDate(nowDate);
				}
					
				entityManager.persist(activity);
			}
		}
	}

	@Override
	public void editListActivities(List<Activity> activityList) {
		if(activityList != null && !activityList.isEmpty()){
			for(Activity activity : activityList){
				entityManager.merge(activity);
			}
		}
	}

	@Override
	public void removeListActivities(List<Activity> activityList) {
		if(activityList != null && !activityList.isEmpty()){
			for(Activity activity : activityList){
				activity.setFlgExist("0");
				entityManager.merge(activity);
			}
		}
	}

	@Override
	public void removeListPlanning(List<Planning> planningList) {
		if(planningList != null && !planningList.isEmpty()){
			for(Planning planning : planningList){
				planning.setFlgExist("0");
				entityManager.merge(planning);
			}
		}
	}

	@Override
	public void saveHistoryEmail(HistoryEmail historyEmail) {
		historyEmail.setFlgExist("1");
		if(historyEmail.getInsertionDate() == null){
			LocalDateTime nowDateTime = LocalDateTime.now()
					.atZone(ZoneId.systemDefault())
					.withZoneSameInstant(ZoneId.of("CET"))
					.toLocalDateTime();
			Date nowDate = Date
		      .from(nowDateTime.atZone(ZoneId.systemDefault())
		      .toInstant());
			historyEmail.setInsertionDate(nowDate);
		}
		entityManager.persist(historyEmail);
	}	
	
	public Map<String, List<String>> getFilterLists(String asset){
		StringBuilder q = new StringBuilder();
		q.append(" SELECT a.chiave, a.valore ");
		q.append(" FROM ");
		q.append(" ( ");
		q.append(" (SELECT DISTINCT 'SOCIETY' as chiave, `ACTIVITY`.`SOCIETY` as valore FROM DAILYWORKPLAN.ACTIVITY  ");
		q.append(" WHERE ACTIVITY.FLG_EXIST = 1 AND ACTIVITY.ASSET = 'COVA' ");
		q.append(" AND `ACTIVITY`.`SOCIETY` IS NOT NULL ");
		q.append(" ) ");
		/*q.append(" UNION ");
		q.append(" (SELECT DISTINCT 'SUPERVISOR' as chiave, `ACTIVITY`.`SUPERVISOR` as valore FROM DAILYWORKPLAN.ACTIVITY ");
		q.append(" WHERE ACTIVITY.FLG_EXIST = 1 AND ACTIVITY.ASSET = 'COVA' ");
		q.append(" AND `ACTIVITY`.`SUPERVISOR` IS NOT NULL ");
		q.append(" ) ");*/
		q.append(" ) a ");
		q.append(" order by a.chiave ASC, a.valore ASC ");
		
		Query query = entityManager.createNativeQuery(q.toString());
        List<Object[]> lista = query.getResultList();
        Map<String, List<String>> valueMap = new HashMap<String, List<String>>();
        if ( lista != null && !lista.isEmpty() ) {
            for ( Object[] object : lista ) {
            	String key = (String) object[0];
            	String value = (String)object[1];
            	if(!valueMap.containsKey(key)){
            		valueMap.put(key, new ArrayList<String>());
            	}
                valueMap.get(key).add(value);
            }
        }
        return valueMap;
	}
	
	public Map<String, Long> getCardInfo(String asset, String startReferenceDate, String endReferenceDate){
		StringBuilder q = new StringBuilder();

		q.append(" SELECT a.chiave, a.valore ");
		q.append(" FROM ");
		q.append(" ( ");
		q.append(" SELECT 'HOUR_06_14' AS chiave, COUNT(*) AS valore ");
		q.append(" FROM DAILYWORKPLAN.ACTIVITY a1, DAILYWORKPLAN.PLANNING p1 ");
		q.append(" WHERE 1=1 ");
		q.append(" AND a1.ASSET = '" + asset + "' ");
		q.append(" AND a1.HOURS = '06:00 - 14:00' ");
		q.append(" AND a1.PLANNING_ID = p1.ID ");
		if(startReferenceDate != null && !"".equals(startReferenceDate)){
			q.append(" AND p1.REFERENCE_DATE >= STR_TO_DATE('" + startReferenceDate + "', '%Y-%m-%d') ");
		}
		if(endReferenceDate != null && !"".equals(endReferenceDate)){
			q.append(" AND p1.REFERENCE_DATE <= STR_TO_DATE('" + endReferenceDate + "', '%Y-%m-%d') ");
		}
		q.append(" UNION ALL ");
		q.append(" SELECT 'HOUR_14_22' AS chiave, COUNT(*) AS valore ");
		q.append(" FROM DAILYWORKPLAN.ACTIVITY a2, DAILYWORKPLAN.PLANNING p2 ");
		q.append(" WHERE 1=1 ");
		q.append(" AND a2.ASSET = '" + asset + "' ");
		q.append(" AND a2.HOURS = '14:00 - 22:00' ");
		q.append(" AND a2.PLANNING_ID = p2.ID ");
		if(startReferenceDate != null && !"".equals(startReferenceDate)){
			q.append(" AND p2.REFERENCE_DATE >= STR_TO_DATE('" + startReferenceDate + "', '%Y-%m-%d') ");
		}
		if(endReferenceDate != null && !"".equals(endReferenceDate)){
			q.append(" AND p2.REFERENCE_DATE <= STR_TO_DATE('" + endReferenceDate + "', '%Y-%m-%d') ");
		}
		q.append(" UNION ALL ");
		q.append(" SELECT 'PRIORITY_06_14', COUNT(*) ");
		q.append(" FROM DAILYWORKPLAN.ACTIVITY a3, DAILYWORKPLAN.PLANNING p3 ");
		q.append(" WHERE 1=1 ");
		q.append(" AND a3.ASSET = '" + asset + "' ");
		q.append(" AND a3.PRIORITY_06_14 IS NOT NULL ");
		q.append(" AND a3.PLANNING_ID = p3.ID ");
		if(startReferenceDate != null && !"".equals(startReferenceDate)){
			q.append(" AND p3.REFERENCE_DATE >= STR_TO_DATE('" + startReferenceDate + "', '%Y-%m-%d') ");
		}
		if(endReferenceDate != null && !"".equals(endReferenceDate)){
			q.append(" AND p3.REFERENCE_DATE <= STR_TO_DATE('" + endReferenceDate + "', '%Y-%m-%d') ");
		}
		q.append(" UNION ALL ");
		q.append(" SELECT 'PRIORITY_14_22', COUNT(*) ");
		q.append(" FROM DAILYWORKPLAN.ACTIVITY a4, DAILYWORKPLAN.PLANNING p4 ");
		q.append(" WHERE 1=1 ");
		q.append(" AND a4.ASSET = '" + asset + "' ");
		q.append(" AND a4.PRIORITY_14_22 IS NOT NULL ");
		q.append(" AND a4.PLANNING_ID = p4.ID ");
		if(startReferenceDate != null && !"".equals(startReferenceDate)){
			q.append(" AND p4.REFERENCE_DATE >= STR_TO_DATE('" + startReferenceDate + "', '%Y-%m-%d') ");
		}
		if(endReferenceDate != null && !"".equals(endReferenceDate)){
			q.append(" AND p4.REFERENCE_DATE <= STR_TO_DATE('" + endReferenceDate + "', '%Y-%m-%d') ");
		}
		q.append(" ) a ");
				
		Query query = entityManager.createNativeQuery(q.toString());
        List<Object[]> lista = query.getResultList();
        Map<String, Long> valueMap = new HashMap<String, Long>();
        if (lista != null && !lista.isEmpty()) {
            for ( Object[] object : lista ) {
            	String key = (String) object[0];
            	Long value = object[1] != null ? (Long)object[1] : 0;
            	valueMap.put(key, value);
            }
        }
        return valueMap;
	}

	@Override
	public Map<Integer, Map<String,String>> getReportMap(ReportFilter reportFilter, String asset){
		Map<Integer, Map<String,String>> results = new HashMap<Integer, Map<String,String>>();
		for(int j = 0; j < 3; j++) {
			Map<String, String> queryResults= new TreeMap<String, String>();
			Query query = entityManager.createNativeQuery(createQuery(reportFilter, asset, j));
			
			@SuppressWarnings("unchecked")
			List<Object[]> lista = query.getResultList();
			
			if ( lista != null && !lista.isEmpty() ) {
				for ( Object[] object : lista ) {
					switch(j) {
						case 0:
							queryResults.put((String) object[1], object[0].toString());
							break;
						case 2:
							queryResults.put((String) object[1], object[0].toString());
							break;
						case 1:
							String pattern = "dd-MM-yyyy";
							DateFormat df = new SimpleDateFormat(pattern);
							queryResults.put(df.format(object[1]),object[0].toString() );
							break;
						default: break;
					}
					
				}
				results.put(j, queryResults);
			}
			
			
		}
		return results;
	}
	
	
	
	public String createQuery (ReportFilter filtro, String asset, int chartNumber) {
		boolean flagAdd = false;
		StringBuilder query = new StringBuilder();
		String groupBy = "";
		
		// Logica con switch per il quale a seconda del numero del chart crea una select diversa per poter
		// creare grafici che usano query differenti ma con diffrenti condizioni di select e group by
		
		switch(chartNumber) {
			case 0:
				query.append("SELECT DISTINCT COUNT(`ACTIVITY`.`SOCIETY`) AS COUNT_SOCIETY, `ACTIVITY`.`SOCIETY`");
				groupBy = " AND ACTIVITY.SOCIETY IS NOT NULL GROUP BY ACTIVITY.SOCIETY ";
				break;
			case 1:
				query.append("SELECT DISTINCT COUNT(`PLANNING`.`REFERENCE_DATE`) AS COUNT_DATES, `PLANNING`.`REFERENCE_DATE` ");
				groupBy = " AND PLANNING.REFERENCE_DATE IS NOT NULL GROUP BY PLANNING.REFERENCE_DATE ORDER BY `PLANNING`.`REFERENCE_DATE` ASC"; 
				break;
			case 2:
				query.append("SELECT DISTINCT COUNT(`ACTIVITY`.`DISCIPLINE`) AS COUNT_DISCIPLINE, `ACTIVITY`.`DISCIPLINE` ");
				groupBy = " AND ACTIVITY.DISCIPLINE IS NOT NULL GROUP BY ACTIVITY.DISCIPLINE ";
				break;
		}
		
		query.append(" FROM DAILYWORKPLAN.ACTIVITY JOIN DAILYWORKPLAN.PLANNING ON PLANNING.ID = ACTIVITY.PLANNING_ID WHERE ");
		
		if(filtro.getSocieta() != null && filtro.getSocieta() != "") {
		    query.append(" ACTIVITY.SOCIETY LIKE '%").append(filtro.getSocieta()).append("%' ");
		    flagAdd = true;
		}
		if(filtro.getAttivatore() != null && filtro.getAttivatore() != "") {
		    if(flagAdd) { query.append(" AND "); }
		    query.append(" ACTIVITY.ACTIVATOR LIKE '%").append(filtro.getAttivatore()).append("%' ");
		    flagAdd = true;
		}
		if(filtro.getOdm() != null && filtro.getOdm() != "") {
		    if(flagAdd) { query.append(" AND "); }
		    query.append(" ACTIVITY.ODM LIKE '%").append(filtro.getOdm()).append("%' ");
		    flagAdd = true;
		}
		if(filtro.getMezzo() != null && filtro.getMezzo() != "") {
		    if(flagAdd) { query.append(" AND "); }
		    query.append(" ACTIVITY.TRANSPORT LIKE '%").append(filtro.getMezzo()).append("%' ");
		    flagAdd = true;
		}
		if(filtro.getItem() != null && filtro.getItem() != "") {
		    if(flagAdd) { query.append(" AND "); }
		    query.append(" ACTIVITY.ITEM LIKE '%").append(filtro.getItem()).append("%' ");
		    flagAdd = true;
		}
		if(filtro.getSupervisore() != null && filtro.getSupervisore() != "") {
		    if(flagAdd) { query.append(" AND "); }
		    query.append(" ACTIVITY.SUPERVISOR LIKE '%").append(filtro.getSupervisore()).append("%' ");
		    flagAdd = true;
		}
		if(filtro.getArea() != null && filtro.getArea() != "") {
		    if(flagAdd) { query.append(" AND "); }
		    query.append(" ACTIVITY.AREA LIKE '%").append(filtro.getArea()).append("%' ");
		    flagAdd = true;
		}
		if(filtro.getSquadra() != null && filtro.getSquadra() != "") {
		    if(flagAdd) { query.append(" AND "); }
		    query.append(" ACTIVITY.TEAM LIKE '%").append(filtro.getSquadra()).append("%' ");
		    flagAdd = true;
		}
		if(filtro.getAssistenza() != null && filtro.getAssistenza() != "") {
		    if(flagAdd) { query.append(" AND "); }
		    query.append(" ACTIVITY.SUPPORT LIKE '%").append(filtro.getAssistenza()).append("%' ");
		    flagAdd = true;
		}
		if(filtro.getAssistenza() != null && filtro.getAssistenza() != "") {
		    if(flagAdd) { query.append(" AND "); }
		    query.append(" ACTIVITY.SUPPORT LIKE '%").append(filtro.getAssistenza()).append("%' ");
		    flagAdd = true;
		}
		if(filtro.getAvanzamento() != null && filtro.getAvanzamento() != "") {
		    if(flagAdd) { query.append(" AND "); }
		    query.append(" ACTIVITY.PROGRESS ");
		    switch(filtro.getOperatoreAvanzamento()) {
		    	case "<":
		    	case ">":
		    	case "=":
		    		query.append(filtro.getOperatoreAvanzamento());
		    		break;
		    	case "≥":
		    		query.append(" >= ");
		    		break;
		    	case "≤":
		    		query.append(" <= ");
		    		break;
		    	default: break;
		    }
		    query.append(" ").append(filtro.getAvanzamento());
		    flagAdd = true;
		}
		if(filtro.getOrario() != null && filtro.getOrario() != "") {
		    if(flagAdd) { query.append(" AND "); }
		    query.append(" ACTIVITY.HOURS = '").append(filtro.getOrario()).append("' ");
		    flagAdd = true;
		}
		if(filtro.getPriorita() != null && filtro.getPriorita() != "") {
		    if(flagAdd) { query.append(" AND "); }
		    switch(filtro.getPriorita()) {
		        case "06_14":
		            query.append(" ACTIVITY.PRIORITY_06_14 IS NOT NULL ");
		            flagAdd = true;
		            break;
		        case "14_22":
		            query.append(" ACTIVITY.PRIORITY_14_22 IS NOT NULL ");
		            flagAdd = true;
		            break;
		        default:
		            break;
		    }
		}
		
		String pattern = "yyyy-MM-dd";
		DateFormat df = new SimpleDateFormat(pattern);
		String fromDateString = df.format(filtro.getFromDate());
		String toDateString = df.format(filtro.getToDate());
		
		
		query.append((flagAdd ? " AND " : " ") + " ACTIVITY.FLG_EXIST = 1 AND ");
		query.append(" PLANNING.REFERENCE_DATE >= '" + fromDateString + "'");
		query.append(" AND ");
		query.append(" PLANNING.REFERENCE_DATE <= '" + toDateString + "' AND ");
		if(asset != null) {
			query.append(" PLANNING.ASSET = '" + asset + "' AND ACTIVITY.ASSET = '" + asset + "' ");
		}
		query.append(groupBy);
		
		
		return query.toString();
	}

}
