package com.eni.ioc.dailyworkplan.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.eni.ioc.dailyworkplan.api.PlanningFilter;
import com.eni.ioc.dailyworkplan.api.ReportFilter;
import com.eni.ioc.dailyworkplan.dto.ActivityDto;
import com.eni.ioc.dailyworkplan.dto.CardInfoDto;
import com.eni.ioc.dailyworkplan.dto.PlanningDto;
import com.eni.ioc.dailyworkplan.dto.ReportDto;
import com.eni.ioc.dailyworkplan.dto.RicercaFilterDto;

public interface DailyWorkPlanService {

	List<ActivityDto> listActivities(String asset);

	List<PlanningDto> listPlanning(String asset);

	List<PlanningDto> getPlanningByReferenceDate(String asset, String fromDate, String toDate);

	List<PlanningDto> getPlanningById(String asset, Long planningId);
	
	void saveListActivities(String asset, PlanningDto planning);
	
	PlanningDto copyListActivities(String asset, String destinationDate, String sourceDate, String insertionBy);

	ByteArrayOutputStream getExportExcel(String asset, PlanningFilter data);

	void confirmPlanning(String asset, Long planningId, String tipoConferma, String note, String confirmationBy);

	boolean existingPlanningByReferenceDate(String asset, String fromDate, String toDate);

	PlanningDto uploadTemplateFile(String asset, MultipartFile file, String referenceDate, String uid);
	
	ReportDto getReportData(String asset, ReportFilter reportFilter);

	List<String> getRolesByUid(String asset, String uid);
	
	ByteArrayOutputStream getEmptyTemplate(String asset);
	
	RicercaFilterDto getFilterLists(String asset);

	CardInfoDto getCardInfo(String asset);
	
}
