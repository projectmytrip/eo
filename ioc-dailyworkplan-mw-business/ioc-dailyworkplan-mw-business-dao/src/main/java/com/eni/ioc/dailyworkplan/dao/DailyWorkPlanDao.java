package com.eni.ioc.dailyworkplan.dao;

import java.util.List;
import java.util.Map;

import com.eni.ioc.dailyworkplan.api.ReportFilter;
import com.eni.ioc.dailyworkplan.entities.Activity;
import com.eni.ioc.dailyworkplan.entities.HistoryEmail;
import com.eni.ioc.dailyworkplan.entities.Planning;

public interface DailyWorkPlanDao {

	Activity findByParams(Long id);
	
	List<Planning> findPlanningByReferenceDate(String asset, String fromDate, String toDate);

	void savePlanning(Planning planning);
	
	void saveListActivities(List<Activity> activityList);

	void editListActivities(List<Activity> activityListToEdit);

	void removeListActivities(List<Activity> activityListToDelete);

	void removeListPlanning(List<Planning> planningToDelete);

	void saveHistoryEmail(HistoryEmail historyEmail);
	
	Map<String, List<String>> getFilterLists(String asset);
	
	Map<Integer, Map<String,String>> getReportMap(ReportFilter reportFilter, String asset);
	
	Map<String, Long> getCardInfo(String asset, String startReferenceDate, String endReferenceDate);
}
