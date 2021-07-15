package com.eni.ioc.dailyworkplan.service;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eni.ioc.dailyworkplan.api.PlanningFilter;
import com.eni.ioc.dailyworkplan.api.ReportFilter;
import com.eni.ioc.dailyworkplan.dao.DailyWorkPlanDao;
import com.eni.ioc.dailyworkplan.dto.ActivityDto;
import com.eni.ioc.dailyworkplan.dto.CardInfoDto;
import com.eni.ioc.dailyworkplan.dto.NotificationDto;
import com.eni.ioc.dailyworkplan.dto.NotificationInput;
import com.eni.ioc.dailyworkplan.dto.PlanningDto;
import com.eni.ioc.dailyworkplan.dto.ReportDto;
import com.eni.ioc.dailyworkplan.dto.RicercaFilterDto;
import com.eni.ioc.dailyworkplan.dto.UserMailDto;
import com.eni.ioc.dailyworkplan.entities.Activity;
import com.eni.ioc.dailyworkplan.entities.HistoryEmail;
import com.eni.ioc.dailyworkplan.entities.Planning;
import com.eni.ioc.dailyworkplan.entities.PlanningState;
import com.eni.ioc.dailyworkplan.entities.UserRelProfile;
import com.eni.ioc.dailyworkplan.entities.UserWhitelist;
import com.eni.ioc.dailyworkplan.enumeration.PlanningStateEnum;
import com.eni.ioc.dailyworkplan.excel.GenerateExcelReport;
import com.eni.ioc.dailyworkplan.excel.LoadExcelTemplate;
import com.eni.ioc.dailyworkplan.repository.ActivityRepository;
import com.eni.ioc.dailyworkplan.repository.PlanningRepository;
import com.eni.ioc.dailyworkplan.repository.PlanningStateRepository;
import com.eni.ioc.dailyworkplan.repository.UserRelProfileRepository;
import com.eni.ioc.dailyworkplan.repository.UserWhitelistRepository;
import com.eni.ioc.dailyworkplan.service.utils.ProfileUtils;
import com.eni.ioc.dailyworkplan.service.utils.UserPojo;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class DailyWorkPlanServiceImpl implements DailyWorkPlanService {

	private static final Logger logger = LoggerFactory.getLogger(DailyWorkPlanService.class);
	
	private static final String DAILY_WORK_PLAN_CONFIRM_EMAIL = "DAILY_WORK_PLAN_CONFIRM_EMAIL";
	private static final String TEMPORARY_CONFIRM = "Temporanea";
	private static final String DEFINITIVE_CONFIRM = "Definitiva";
	
	@Autowired
	DailyWorkPlanDao dailyWorkPlanDao;

	@Autowired
	ActivityRepository activityRepository;

	@Autowired
	PlanningRepository planningRepository;

	@Autowired
	PlanningStateRepository planningStateRepository;

	@Autowired
	Sender sender;

	@Autowired
	ProfileUtils profileUtils;

	@Autowired
	UserWhitelistRepository userWhitelistRepository;

	@Autowired
	UserRelProfileRepository userProfileRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private ActivityDto fromActivityToActivityDto(Activity act){
		return new ActivityDto(
				act.getId(),
				act.getAsset(),
				act.getInsertionDate(),
				act.getPlanningId(),
				act.getSociety(),
				act.getSupervisor(),
				act.getActivator(),
				act.getDiscipline(),
				act.getArea(),
				act.getNote(),
				act.getOdm(),
				act.getHours(),
				act.getDescription(),
				"1".equals(act.getNoMaintenance()) ? true : false,
				act.getTeam(),
				act.getXsn(),
				act.getSupport(),
				act.getTransport(),
				act.getPriority0614(),
				act.getPriority1422(),
				act.getItem(),
				act.getProgress()
		);
	}
	
	private PlanningDto fromPlanningToPlanningDTO(Planning planning, List<Activity> activityList){
		List<ActivityDto> activityListDto = new ArrayList<ActivityDto>();
		if(activityList != null && !activityList.isEmpty()){
			for(Activity entity : activityList){
				activityListDto.add(fromActivityToActivityDto(entity));
			}
			
			// ordiniamo le attività in ordine crescente di società e supervisore
			Collections.sort(activityListDto, new Comparator<ActivityDto>() {
	            public int compare(ActivityDto a1, ActivityDto a2) {
	            	int val = 0;
	            	
	            	// criteri di ordinamento:
	            	// - società ordine crescente, con valori null alla fine
	            	// - supvervisore ordine crescente, con valori null alla fine
	            	
	            	// si inizia con l'ordinare in base al campo società 
	            	if(a1.getSociety() == null && a2.getSociety() == null){
	            		val = 0;
	            	}else if(a1.getSociety() != null && a2.getSociety() == null){
	            		val = -1;
	            	}else if(a1.getSociety() == null && a2.getSociety() != null){
	            		val = 1;
	            	}else{
	            		val = a1.getSociety().compareTo(a2.getSociety());
	            	}
	            	
	            	// se le società sono uguali o entrambe null, si ordina sulla base del supervisore
	            	if(val == 0){
	            		if(a1.getSupervisor() == null && a2.getSupervisor() == null){
		            		val = 0;
		            	}else if(a1.getSupervisor() != null && a2.getSupervisor() == null){
		            		val = -1;
		            	}else if(a1.getSupervisor() == null && a2.getSupervisor() != null){
		            		val = 1;
		            	}else{
		            		val = a1.getSupervisor().compareTo(a2.getSupervisor());
		            	}
	            	}
	            	
	            	return val;
	            }
	        });
			
		}
		
		PlanningState planningState = planningStateRepository.findByCodeAndAsset(planning.getState().getCode(), planning.getAsset());
		String state = "";
		if(planningState != null){
			state = planningState.getState();
		}
		
		String lastHistoryEmailNote = "";
		List<String> allHistoryEmailNote = new ArrayList<String>();
		if(planning.getHistoryEmail() != null && !planning.getHistoryEmail().isEmpty()){
			HistoryEmail prevHistoryEmail = null;
			for(HistoryEmail current : planning.getHistoryEmail()){
				if(prevHistoryEmail == null || 
						current.getInsertionDate().after(prevHistoryEmail.getInsertionDate())){
					lastHistoryEmailNote = current.getNote();
					prevHistoryEmail = current;
				}
				allHistoryEmailNote.add(current.getNote());
			}
		}
		return new PlanningDto(
				planning.getId(),
				planning.getAsset(),
				planning.getInsertionDate(),
				planning.getReferenceDate(),
				planning.getInsertionBy(),
				planning.getInsertionByUsername(),
				planning.getState().getCode(),
				state,
				activityListDto,
				lastHistoryEmailNote,
				allHistoryEmailNote
		);
	}
	
	private Activity fromActivityDtoToActivity(ActivityDto dto){
		Activity entity = new Activity();
		entity.setId(dto.getId());
		entity.setAsset(dto.getAsset());
		entity.setPlanningId(dto.getPlanningId());
		entity.setSociety(dto.getSociety());
		entity.setSupervisor(dto.getSupervisor());
		entity.setActivator(dto.getActivator());
		entity.setDiscipline(dto.getDiscipline());
		entity.setArea(dto.getArea());
		entity.setNote(dto.getNote());
		entity.setOdm(dto.getOdm());
		entity.setHours(dto.getHours());
		entity.setDescription(dto.getDescription());
		entity.setNoMaintenance(dto.getNoMaintenance() ? "1" : "0");
		entity.setTeam(dto.getTeam());
		entity.setXsn(dto.getXsn());
		entity.setSupport(dto.getSupport());
		entity.setTransport(dto.getTransport());
		entity.setPriority0614(dto.getPriority0614());
		entity.setPriority1422(dto.getPriority1422());
		entity.setPriority1422(dto.getPriority1422());
		entity.setProgress(dto.getProgress());
		entity.setItem(dto.getItem());
		return entity;
	}
	
	private Planning fromPlanningDtoToPlanning(PlanningDto dto){
		Planning entity = new Planning();
		entity.setId(dto.getId());
		entity.setReferenceDate(dto.getReferenceDate());
		if(dto.getState() != null){
			String state = PlanningStateEnum.getEnum(dto.getStateCode()).getState();
			PlanningState planningState = planningStateRepository.findByCodeAndAsset(state, dto.getAsset());
			entity.setState(planningState);
		}
		entity.setInsertionBy(dto.getInsertionBy());
		entity.setInsertionByUsername(dto.getInsertionByUsername());
		entity.setAsset(dto.getAsset());
		return entity;
	}

	@Override
	public List<ActivityDto> listActivities(String asset){
		
		logger.info("Called listActivities");
		List<ActivityDto> activityDtoList = new ArrayList<>();
		
		//retrieve all activities
		List<Activity> allActivities = activityRepository.findAll();
		if(allActivities !=null && !allActivities.isEmpty()) {
			allActivities.forEach( (Activity act) -> {
				activityDtoList.add(fromActivityToActivityDto(act));
			});
		}
		return activityDtoList;
	};

	@Override
	public List<PlanningDto> listPlanning(String asset){
		
		logger.info("Called listPlanning");
		List<PlanningDto> planningDtoList = new ArrayList<>();
		
		List<Planning> allPlannings = planningRepository.findAll();
		if(allPlannings != null && !allPlannings.isEmpty()) {
			allPlannings.forEach( (Planning currentPlanning) -> {
				List<Activity> activityList = activityRepository.findActivityByPlanningIdAndAsset(currentPlanning.getId(), asset);
				planningDtoList.add(fromPlanningToPlanningDTO(currentPlanning, activityList));
			});
		}
		return planningDtoList;
	};

	@Override
	public List<PlanningDto> getPlanningByReferenceDate(String asset, String fromDate, String toDate){
		
		logger.info("Called getPlanningByReferenceDate");
		List<PlanningDto> planningDtoList = new ArrayList<>();
		
		List<Planning> allPlannings = null;
		
		logger.info("fromDate={}, toDate={}", fromDate, toDate);
		
		if(fromDate == null && toDate == null){
			allPlannings = planningRepository.findByAsset(asset);
		}else if(fromDate != null && toDate != null){
			allPlannings = dailyWorkPlanDao.findPlanningByReferenceDate(asset, fromDate, toDate);
		}		
		
		if(allPlannings != null && !allPlannings.isEmpty()) {
			allPlannings.forEach( (Planning currentPlanning) -> {
				List<Activity> activityList = activityRepository.findActivityByPlanningIdAndAsset(currentPlanning.getId(), asset);
				currentPlanning.setInsertionByUsername(currentPlanning.getInsertionBy() +" - "+ currentPlanning.getInsertionByUsername());
				planningDtoList.add(fromPlanningToPlanningDTO(currentPlanning, activityList));
			});
		}
		
		// viene fatto l'ordinamento in base della reference date in ordine decrescente
		Collections.sort(planningDtoList, new Comparator<PlanningDto>() {
			public int compare(PlanningDto o1, PlanningDto o2) {
				return o2.getReferenceDate().compareTo(o1.getReferenceDate());
			}
		});
		
		return planningDtoList;
	};

	@Override
	public List<PlanningDto> getPlanningById(String asset, Long planningId){
		
		logger.info("Called getPlanningById");
		List<PlanningDto> planningDtoList = new ArrayList<>();
		
		Planning planning = planningRepository.findByIdAndAsset(planningId, asset);
		List<Activity> activityList = activityRepository.findActivityByPlanningIdAndAsset(planning.getId(), asset);
		planningDtoList.add(fromPlanningToPlanningDTO(planning, activityList));
		
		return planningDtoList;
	};

	@Override
	public void saveListActivities(String asset, PlanningDto planningDto) {
		// le nuove attività vengono salvate sul database
		// per le attività modificate, si cancellano logicamente (flg_exist = 0) le vecchie e le si aggiunge con i nuovi dati
		// le attività rimosse vengono cancellate logicamente (flg_exist = 0)
		
		logger.info("Called saveListActivities");
		
		// se la programmazione esiste, la si recupera dal database, altrimenti la si crea e si salva sul database
		if(planningDto != null){
			Planning planning = null;
			if(planningDto.getId() == null){
				
				// se esistono già programmazioni con la stessa data di riferimento, vanno sovrascritte
				logicDeletePlanningByReferenceDate(asset, planningDto.getReferenceDate());
				
				// rimozione orario del campo referenceDate (ore, minuti, secondi, millisecondi impostati a 0)
				if(planningDto.getReferenceDate() != null){
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(planningDto.getReferenceDate());
					calendar.set(Calendar.HOUR_OF_DAY, 0);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.SECOND, 0);
					calendar.set(Calendar.MILLISECOND, 0);
					planningDto.setReferenceDate(calendar.getTime());
				}
				
				// recupero nome e cognome di chi ha caricato la programmazione per creare username
				UserPojo userInfo = getUserInfoByUid(planningDto.getInsertionBy());
				
				String username = userInfo.getData().getName() + " " + userInfo.getData().getSurname();
				planningDto.setInsertionByUsername(username);

				// salvataggio della nuova programmazione
				planning = fromPlanningDtoToPlanning(planningDto);
				dailyWorkPlanDao.savePlanning(planning);
				
			}else{
				planning = planningRepository.findByIdAndAsset(planningDto.getId(), asset);
			}
			
			if(planningDto.getActivityList() != null && !planningDto.getActivityList().isEmpty()){
				
				// le attività con flagAdd=true sono nuove e devono essere inserite nel database
				// le attività con flagEdit=true sono state modificate e vanno aggiornate
				// le attività con flagRemove=true devono essere cancellate logicamente dal database
				List<Activity> activityListToAdd = new ArrayList<Activity>();
				List<Activity> activityListToEdit = new ArrayList<Activity>();
				List<Activity> activityListToRemove = new ArrayList<Activity>();
				
				for(ActivityDto activityDto : planningDto.getActivityList()){
					if(activityDto.isFlagAdd()){
						Activity activity = fromActivityDtoToActivity(activityDto);
						activity.setAsset(asset);
						activity.setPlanningId(planning.getId());
						activityListToAdd.add(activity);
					}
					if(activityDto.isFlagEdit()){
						Activity activityFromFe = fromActivityDtoToActivity(activityDto); // attività modificata a front-end
						Activity activityFromDb = activityRepository.findActivityByIdAndAsset(activityDto.getId(), asset); // attività recuperata dal database
						copyActivity(activityFromFe, activityFromDb); // salviamo le nuove info in activityFromDb
						activityFromDb.setAsset(asset);
						activityListToEdit.add(activityFromDb);
					}
					if(activityDto.isFlagRemove()){
						Activity activity = activityRepository.findActivityByIdAndAsset(activityDto.getId(), asset);
						activityListToRemove.add(activity);
					}
				}
				
				dailyWorkPlanDao.saveListActivities(activityListToAdd);
				dailyWorkPlanDao.editListActivities(activityListToEdit);
				dailyWorkPlanDao.removeListActivities(activityListToRemove);
			}
		}
		
	}
	
	private void logicDeletePlanningByReferenceDate(String asset, String referenceDate){
		// la data deve essere in formato "yyyy-MM-dd"
		List<Planning> planningsToRemove = dailyWorkPlanDao.findPlanningByReferenceDate(asset, referenceDate, referenceDate);
		this.logicDeletePlanning(asset, planningsToRemove);
	}
	
	private void logicDeletePlanningByReferenceDate(String asset, Date referenceDate){
		String pattern = "yyyy-MM-dd";
		DateFormat df = new SimpleDateFormat(pattern);
		String referenceDateAsString = df.format(referenceDate);
		List<Planning> planningsToRemove = dailyWorkPlanDao.findPlanningByReferenceDate(asset, referenceDateAsString, referenceDateAsString);
		this.logicDeletePlanning(asset, planningsToRemove);
	}
	
	private void logicDeletePlanning(String asset, List<Planning> planningsToRemove){
		// Si effettua la cancellazione logica (flg_exist = 0) delle pianificazioni relative ad una referece date
		if(planningsToRemove != null && !planningsToRemove.isEmpty()){
			for(Planning p : planningsToRemove){
				List<Activity> activityToRemove = activityRepository.findActivityByPlanningIdAndAsset(p.getId(), asset);
				this.logicDeleteActivity(activityToRemove);
			}
			dailyWorkPlanDao.removeListPlanning(planningsToRemove);
		}	
	}
	
	private void logicDeleteActivity(List<Activity> activitiesToRemove){
		if(activitiesToRemove != null && !activitiesToRemove.isEmpty()){
			dailyWorkPlanDao.removeListActivities(activitiesToRemove);
		}
	}
	
	public void copyActivity(Activity sourceActivity, Activity destinationActivity) {
		destinationActivity.setAsset(sourceActivity.getAsset());
		destinationActivity.setActivator(sourceActivity.getActivator());
		destinationActivity.setArea(sourceActivity.getArea());
		destinationActivity.setDescription(sourceActivity.getDescription());
		destinationActivity.setDiscipline(sourceActivity.getDiscipline());
		destinationActivity.setHours(sourceActivity.getHours());
		destinationActivity.setItem(sourceActivity.getItem());
		destinationActivity.setNoMaintenance(sourceActivity.getNoMaintenance());
		destinationActivity.setNote(sourceActivity.getNote());
		destinationActivity.setOdm(sourceActivity.getOdm());
		destinationActivity.setPriority0614(sourceActivity.getPriority0614());
		destinationActivity.setPriority1422(sourceActivity.getPriority1422());
		destinationActivity.setProgress(sourceActivity.getProgress());
		destinationActivity.setSociety(sourceActivity.getSociety());
		destinationActivity.setSupervisor(sourceActivity.getSupervisor());
		destinationActivity.setTeam(sourceActivity.getTeam());
		destinationActivity.setTransport(sourceActivity.getTransport());
		destinationActivity.setXsn(sourceActivity.getXsn());
	}
	
	@Override
	public PlanningDto copyListActivities(String asset, String destinationDate, String sourceDate, String insertionBy) { 
		PlanningDto planningDto = null;

		logger.info("Called copyListActivities");
	
		// Elimino logicamente il planning sulla data di destinazione della copia
		logicDeletePlanningByReferenceDate(asset, destinationDate);
		
		// Recupero planning sorgente
		List<Planning> planning = dailyWorkPlanDao.findPlanningByReferenceDate(asset, sourceDate, sourceDate);
		if(planning != null && !planning.isEmpty()) {
			for(Planning sourcePlanning : planning){
				
				Planning destinationPlanning = new Planning();
				destinationPlanning.setAsset(sourcePlanning.getAsset());
				
				// recupero nome e cognome di chi ha caricato la programmazione per creare username
				destinationPlanning.setInsertionBy(insertionBy);
				UserPojo userInfo = getUserInfoByUid(insertionBy);				
				String username = userInfo.getData().getName() + " " + userInfo.getData().getSurname();
				destinationPlanning.setInsertionByUsername(username);
				
				// convertire in data destinationDate
				String pattern = "yyyy-MM-dd";
				DateFormat df = new SimpleDateFormat(pattern);
				Date destinationDateAsDate;
				try {
					destinationDateAsDate = df.parse(destinationDate);
					destinationPlanning.setReferenceDate(destinationDateAsDate);
				} catch (ParseException e) {
					logger.error("Error in copyListActivities", e);
				}
				
				if(sourcePlanning.getState() != null){
					String state = sourcePlanning.getState().getState();
					PlanningState planningState = planningStateRepository.findByStateAndAsset(state, sourcePlanning.getAsset());
					destinationPlanning.setState(planningState);
				}

				// si recuperano tutte le attività presenti sul database, collegate alla programmazione attuale
				// si controllano quali attività non sono più presenti e si effettua la cancellazione logica

				// attivita' attualmente presenti sul database
				List<Activity> sourceActivityList = activityRepository.findActivityByPlanningIdAndAsset(sourcePlanning.getId(), asset);
				List<Activity> destinationActivityList = new ArrayList<Activity>();
				
				for(Activity activity : sourceActivityList){
					Activity destinationActivity = new Activity();
					copyActivity(activity, destinationActivity);
					destinationActivityList.add(destinationActivity);
				}
				
				planningDto = fromPlanningToPlanningDTO(destinationPlanning, destinationActivityList);
				
				// essendo tutti delle nuove attività, va settato il flag flagAdd=true a tutte le attività
				if(planningDto.getActivityList() != null && !planningDto.getActivityList().isEmpty()){
					for(ActivityDto currentActivityDto : planningDto.getActivityList()){
						currentActivityDto.setFlagAdd(true);
					}
				}
				
			}
		}
		return planningDto;
	}
	
	
	@Override
	public ByteArrayOutputStream getExportExcel(String asset, PlanningFilter data) {
		logger.info("Called getExportExcel");
		Planning planning = null;
		List<Activity> activityList = null;
		planning = planningRepository.findByIdAndAsset(data.getPlanningId(), asset);
		activityList = activityRepository.findActivityByPlanningIdAndAsset(planning.getId(), asset);
		
		ByteArrayOutputStream baos = GenerateExcelReport.getExportExcel(planning, activityList);

		return baos;
	}
	
	@Override
	public ByteArrayOutputStream getEmptyTemplate(String asset) {
		
		ByteArrayOutputStream baos = GenerateExcelReport.getEmptyTemplate();
		return baos;
	}

	private UserPojo getUserInfoByUid(String uid) {
		logger.info("Called getUserInfoByUid");
		UserPojo userPojo = null;
		logger.info("Recupero info associate all'utente con UID={}", uid);
		try {
			userPojo = profileUtils.getUserInfoByUid(uid);
		} catch (JsonProcessingException | UnsupportedEncodingException e) {
			logger.error("getUserInfoByUid: Errore nel recuperare le info associate alla UID={}", uid, e);
		}
		return userPojo;
	}

	@Override
	public void confirmPlanning(String asset, Long planningId, String tipoConferma, String note, String confirmationBy) {
		
		logger.info("Called confirmPlanning");
		
		Planning planning = planningRepository.findByIdAndAsset(planningId, asset);
		if(planning != null) {
			
			// la programmazione passa in stato "CONFERMATO" che può essere TEMPORANEO o DEFINITIVO
			
			PlanningState planningState = null;
			if(TEMPORARY_CONFIRM.equals(tipoConferma)){
				planningState = planningStateRepository.findByCodeAndAsset(PlanningStateEnum.STATE_03.getState(), asset);
			}else if(DEFINITIVE_CONFIRM.equals(tipoConferma)){
				planningState = planningStateRepository.findByCodeAndAsset(PlanningStateEnum.STATE_04.getState(), asset);
			}
			planning.setState(planningState);
			dailyWorkPlanDao.savePlanning(planning);
			
			// viene preparata l'email da inviare
			this.confirmPlanningEmail(asset, planning, tipoConferma, note);
			
			// viene storicizzato l'invio dell'email
			String noteHistoryEmail = this.confirmPlanningNote(tipoConferma, confirmationBy);
			this.saveHistoryEmail(asset, planning, noteHistoryEmail);
		}
	}

	/*private List<UserMailDto> findUserMail(List<UserWhitelist> recipients, boolean isCarbonCopy) throws Exception {
		logger.info("Called findUserMail");

		Set<String> uids = new HashSet<>();

		recipients.forEach((permission) -> {
			uids.add(permission.getUid());
		});

		if(uids.isEmpty()) {
			return null;
		}

		List<UserMailDto> mails = null;
		RolesPojo rp = profileUtils.getUsersByUid(uids);
		if(rp != null && rp.getData() != null) {
			mails = rp.getData();
			for (UserMailDto s : rp.getData()) {
				s.setAction(0);
				s.setCarbonCopy(isCarbonCopy);
			}
		}
		return mails;
	}*/
	
	private void confirmPlanningEmail(String asset, Planning planning, String tipoConferma, String note){
		Map<String, String> valuesTpl = new HashMap<>();
		
		List<UserWhitelist> recipientsTo = userWhitelistRepository.findToUser();
		List<UserWhitelist> recipientsCc = userWhitelistRepository.findCcUser();
		
		List<UserMailDto> mails = new ArrayList<UserMailDto>();
		
		for(UserWhitelist to : recipientsTo){
			UserMailDto mailTo = new UserMailDto();
			mailTo.setCarbonCopy(false);
			mailTo.setEmail(to.getEmail());
			mailTo.setUid(to.getUid());
			mailTo.setAction(0);
			mails.add(mailTo);
		}
		
		for(UserWhitelist cc : recipientsCc){
			UserMailDto mailCC = new UserMailDto();
			mailCC.setCarbonCopy(false);
			mailCC.setEmail(cc.getEmail());
			mailCC.setUid(cc.getUid());
			mailCC.setAction(0);
			mails.add(mailCC);
		}
		
		
		/*
		List<UserMailDto> mails = null;
		
		try {
			//GESTIONE UTENTI IN TO
			mails = findUserMail(recipientsTo, false);
			
			if(mails == null || mails.isEmpty()) {
				logger.info("No recipientsTo <{}> were found.", asset);
			}

			//GESTIONE UTENTI IN CC
			List<UserMailDto> mailsCc = findUserMail(recipientsCc, true);
			
			if(mails == null || mails.isEmpty()) {
				logger.info("No recipients <{}> were found.", asset);
			}

			if(mailsCc != null) {
				mails.addAll(mailsCc);
			}
			
		} catch (Exception e) {
			logger.error("confirmPlanning: errore nel recuperare le email", e);
		}
		*/
		
		//AGGIUNGERE VALORI
		String pattern = "yyyy-MM-dd";
		DateFormat df = new SimpleDateFormat(pattern);
		valuesTpl.put("n_refDate", df.format(planning.getReferenceDate()));
		valuesTpl.put("n_tipoConf", tipoConferma);
		if(note != null && !note.equals("")) {
			note = "<b>Note:</b> " + note;
			valuesTpl.put("n_note", note);
		} else {
			valuesTpl.put("n_note", "");
		}
		
		
		try {
			NotificationDto notification = new NotificationDto();
			notification.setAsset(asset);
			notification.setChannel("mail");
			notification.setNotifyDate(new Date());
			notification.setVista("dailyWorkPlan");
			notification.setStep("");
			
			// creazione allegato
			PlanningFilter filter = new PlanningFilter();
			filter.setPlanningId(planning.getId());
			ByteArrayOutputStream baos = getExportExcel(planning.getAsset(), filter);
			String attachmentName = "Programmazione";
			
			// invio email
			NotificationInput n = new NotificationInput(notification, DAILY_WORK_PLAN_CONFIRM_EMAIL, asset, valuesTpl, mails);
			n.setGroup(true);
			n.setAttachment(baos != null ? baos.toByteArray() : null);
			n.setAttachmentName(attachmentName);
			
			sender.sendToRabbitMQ(n);
			
		} catch (JsonProcessingException e) {
			logger.error("confirmPlanning: errore nell'invio dell'email sulla coda", e);
		} catch (Exception e) {
			logger.error("confirmPlanning: errore nel recuperare le email", e);
		}
	}
	
	private String confirmPlanningNote(String tipoConferma, String confirmationBy){
		
		LocalDateTime sendDateTime = LocalDateTime.now()
				.atZone(ZoneId.systemDefault())
				.withZoneSameInstant(ZoneId.of("CET"))
				.toLocalDateTime();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String formattedSendDateTime = sendDateTime.format(formatter);
		
		UserPojo userInfo = getUserInfoByUid(confirmationBy);
		String username = userInfo.getData().getName() + " " + userInfo.getData().getSurname();
		
		StringBuilder sb = new StringBuilder();
		sb.append("Programmazione ")
			.append(tipoConferma.toLowerCase())
			.append(" inviata da ")
			.append(username)
			.append(" in data ")
			.append(formattedSendDateTime);
		
		return sb.toString();
	}
	
	private void saveHistoryEmail(String asset, Planning planning, String note){
		HistoryEmail historyEmail = new HistoryEmail();
		historyEmail.setAsset(asset);
		historyEmail.setPlanning(planning);
		historyEmail.setNote(note);
		dailyWorkPlanDao.saveHistoryEmail(historyEmail);
	}

	@Override
	public boolean existingPlanningByReferenceDate(String asset, String fromDate, String toDate) {
		// il metodo restituisce false se non esistono programmazioni nelle date indicate, true se ne esiste almeno una
		logger.info("Called existingPlanningByReferenceDate");
		boolean toReturn = false;
		List<Planning> allPlannings = dailyWorkPlanDao.findPlanningByReferenceDate(asset, fromDate, toDate);
		if(allPlannings != null && !allPlannings.isEmpty()){
			toReturn = true;
		}
		return toReturn;
	}

	@Override
	public PlanningDto uploadTemplateFile(String asset, MultipartFile file, String referenceDate, String uid) {
		
		logger.info("Called uploadTemplateFile");

		String stateCode = PlanningStateEnum.STATE_01.getState();
		String state = planningStateRepository.findByCodeAndAsset(stateCode, asset).getState();
		
		PlanningDto planningDto = LoadExcelTemplate.savePlanningFromTemplate(asset, file, referenceDate, uid, stateCode, state);
		
		return planningDto;
	}

	@Override
	public ReportDto getReportData(String asset, ReportFilter reportFilter) {
		Map<Integer, Map<String,String>> results = dailyWorkPlanDao.getReportMap(reportFilter, asset); 
		
		ReportDto response = new ReportDto();
		response.setChartData(results);
		return response;
	}
	

	
	public RicercaFilterDto getFilterLists(String asset){
		Map<String, List<String>> filterMap = dailyWorkPlanDao.getFilterLists(asset);
		RicercaFilterDto ricercaFilterDto = new RicercaFilterDto();
		if(filterMap != null && !filterMap.isEmpty()){
			for (String key : filterMap.keySet()) {
			    switch(key){
			    	case "SOCIETY":
			    		ricercaFilterDto.setSocietyList(filterMap.get("SOCIETY"));
			    		break;
			    	default:
			    		break;
			    }
			}
		}
		return ricercaFilterDto;
	}

	public List<String> getRolesByUid(String asset, String uid){
		logger.info("Called getRolesByUid");
		List<UserRelProfile> userProfile = userProfileRepository.findByUidAndAsset(uid, asset);
		List<String> roles = new ArrayList<String>();
		if(userProfile != null && !userProfile.isEmpty()){
			for(UserRelProfile current : userProfile){
				roles.add(current.getRoleCode());
			}
		}
		return roles;
	}
	
	@Override
	public CardInfoDto getCardInfo(String asset){
		logger.info("Called getCardInfo");
		CardInfoDto cardInfoDto = new CardInfoDto();
		
		// recupero il primo e l'ultimo giorno dell'anno corrente
		LocalDate localDate = LocalDate.now();
		int currentYear = localDate.getYear();
		LocalDate start = LocalDate.of(currentYear, Month.JANUARY, 1);
		LocalDate end = LocalDate.of(currentYear, Month.DECEMBER, 31);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String startReferenceDate = start.format(formatter);
		String endReferenceDate = end.format(formatter);
		
		Map<String, Long> cardInfoMap = dailyWorkPlanDao.getCardInfo(asset, startReferenceDate, endReferenceDate);
		
		if(cardInfoMap != null && !cardInfoMap.isEmpty()){
			for (String key : cardInfoMap.keySet()) {
			    switch(key){
			    	case "HOUR_06_14":
			    		cardInfoDto.setHour0614(cardInfoMap.get("HOUR_06_14"));
			    		break;
			    	case "HOUR_14_22":
			    		cardInfoDto.setHour1422(cardInfoMap.get("HOUR_14_22"));
			    		break;
			    	case "PRIORITY_06_14":
			    		cardInfoDto.setPriority0614(cardInfoMap.get("PRIORITY_06_14"));
			    		break;
			    	case "PRIORITY_14_22":
			    		cardInfoDto.setPriority1422(cardInfoMap.get("PRIORITY_14_22"));
			    		break;
			    	default:
			    		break;
			    }
			}
		}
		return cardInfoDto;
	}
	
}
