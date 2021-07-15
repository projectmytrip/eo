package com.eni.ioc.assetintegrity.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eni.ioc.assetintegrity.api.AssetIntegrityRequest;
import com.eni.ioc.assetintegrity.api.CreaManualWorkflowInput;
import com.eni.ioc.assetintegrity.api.OpenWorkflowCriticalSignalsInput;
import com.eni.ioc.assetintegrity.api.ReasonMoCInput;
import com.eni.ioc.assetintegrity.api.RequestModificationInput;
import com.eni.ioc.assetintegrity.api.Segnale;
import com.eni.ioc.assetintegrity.dao.AssetIntegrityDaoWf;
import com.eni.ioc.assetintegrity.dao.UserWhitelistRepo;
import com.eni.ioc.assetintegrity.dto.CheckSignalsWF;
import com.eni.ioc.assetintegrity.dto.NotificationDto;
import com.eni.ioc.assetintegrity.dto.NotificationInput;
import com.eni.ioc.assetintegrity.dto.RequestModificationDto;
import com.eni.ioc.assetintegrity.dto.UserMailDto;
import com.eni.ioc.assetintegrity.enitities.CriticalSignalDto;
import com.eni.ioc.assetintegrity.entities.UserWhitelist;
import com.eni.ioc.assetintegrity.service.utils.ProfileUidUtils;
import com.eni.ioc.assetintegrity.service.utils.RolesPojo;
import com.eni.ioc.assetintegrity.utils.Esito;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class AssetIntegrityServiceWfImpl implements AssetIntegrityServiceWf {

	private static final Logger logger = LoggerFactory.getLogger(AssetIntegrityServiceWfImpl.class);
	private static final String COVA = "COVA";

	private static final String ASSET_EMAIL_01 = "ASSET_EMAIL_01";
	private static final String ASSET_EMAIL_02 = "ASSET_EMAIL_02";
	private static final String ASSET_EMAIL_03 = "ASSET_EMAIL_03";

	private static final String SIGNAL_STATE_OLD = "OLD";
	private static final String SIGNAL_STATE_NEW = "NEW";
	private static final String SIGNAL_STATE_TOTAL = "TOTAL";

	@Value("${email.senderAssetAddress}")
	private String emailSenderAssetAddress;

	@Value("${email.senderAssetName}")
	private String emailSenderAssetName;

	@Autowired
	AssetIntegrityDaoWf assetIntegrityDao;

	@Autowired
	Sender sender;

	@Autowired
	ProfileUidUtils profileUtils;

	@Autowired
	UserWhitelistRepo userWhitelistRepo;

	@Autowired
	private Environment env;

	@Override
	public List<CriticalSignalDto> pollingMOC(List<String> input, String asset) {
		if(input == null || input.isEmpty()) {
			logger.warn("input was empty or null");
			return new ArrayList<>();
		}

		return assetIntegrityDao.pollingMOC(input, asset);
	}

	@Override
	public void openWorkflowCriticalSignals(OpenWorkflowCriticalSignalsInput input, String asset, String user,
			String uid) {
		CreaManualWorkflowInput newWorkflow = null;

		try {

			Date start = input.getStartDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(start);

			// if input.getDuration() is like (4h, 8h, 12h ,etc) or (1gg, 2gg ,3gg, ...)
			String duration = input.getDuration();
			String uom = duration.substring(duration.length() - 1, duration.length());

			boolean isHour = "h".equalsIgnoreCase(uom);

			int lastVal = isHour ? duration.length() - 1 : duration.length() - 2;

			cal.add(isHour ? Calendar.HOUR_OF_DAY : Calendar.DATE,
					Integer.valueOf(input.getDuration().substring(0, lastVal)));
			Date end = cal.getTime();

			//convert from UTC to CET
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("CET"));
			try {
				start = sdf.parse(sdf.format(start));
				end = sdf.parse(sdf.format(end));
			} catch (Exception e) {
				logger.warn("couldnt change start and end ", e);
			}

			String originalString = wfCriticalSignalsToString(input);
			String hash = generateHash(originalString);
			if(hash != null) {
				newWorkflow = generaMessaggioCondizioneAnomala(hash, input, asset, user, uid);
				assetIntegrityDao.insertSegnaliCriticiWf(hash, input.getSignals(), start, end);
				sender.sendToRabbitMQActual(newWorkflow);
				sendSignalsToWs(asset, input.getSignals());
				assetIntegrityDao.storeWFRequest(signalsToString(input.getSignals()), asset, input.getDuration(),
						input.getEmergency(), input.getNotes(), input.getRequestType(), input.getSap(),
						input.getStartDate());
			} else {
				logger.error(
						"********************************* COULD NOT GENERATE HASHKEY. NO MESSAGE SENT *********************************");
			}
		} catch (JsonProcessingException e) {
			logger.error("errore rabbit", e);
		}

	}

	@Override
	public void requestModification(RequestModificationInput input, String asset, String user,
			String uid) {
		CreaManualWorkflowInput newWorkflow = null;

		String originalString = wfRequestModificationToString(input);
		String hash = generateHash(originalString);
		try {
			newWorkflow = generaMessaggioRequestModification(hash, input, asset, user, uid);
			sender.sendToRabbitMQActual(newWorkflow);
			assetIntegrityDao.storeMocRequest(input.getTitle(), input.getNumber(), input.getDate(),
					reasonsToString(input.getReasons()), input.getOtherReason(), input.getPriority(), input.getType(),
					input.getTypeDate(), input.getDescription(), input.getDocumentation(), segnaleToString(input.getSignals()),
					input.getUser(), input.getUid());
		} catch (JsonProcessingException e) {
			logger.error("errore rabbit", e);
		}

	}

	@Override
	public void openCriticalSignalsWaringWF(String asset) throws Exception {
		//Opening Warning WF for Case 1
		/*List<CriticalSignalDto> wf1Signals = assetIntegrityDao.getCriticalSignalsWarnings(asset);

		if ( wf1Signals != null && !wf1Signals.isEmpty() ) {
			logger.info("Warning WF1: {} critical signals for asset <{}> were found.", wf1Signals.size(), asset);
			sendCriticalSignalsWarnings(null, asset, ASSET_EMAIL_01, wf1Signals);
		} else {
			logger.info("Warning WF1: No critical signals for asset <{}> were found.", asset);
		}*/

		// LocalDateTime startRange = LocalDateTime.now().minusHours(Long.parseLong(env.getProperty("shift.duration")));
		// LocalDateTime endRange = LocalDateTime.now();

		//List<CriticalSignalDto> wf1Signals = assetIntegrityDao.getCriticalSignalsWarningsByDatetimeRange(asset,
		// startRange, endRange);
		List<CriticalSignalDto> wf1Signals = assetIntegrityDao.getCriticalSignalsWarnings(asset);

		if(wf1Signals != null && !wf1Signals.isEmpty()) {
			logger.info("Warning WF1: {} critical signals for asset <{}> were found.", wf1Signals.size(), asset);
			//sendCriticalSignalsWarnings(null, asset, ASSET_EMAIL_01, wf1Signals);
			sendCriticalSignalsWarningsToMessage(asset, ASSET_EMAIL_03, wf1Signals);
		} else {
			logger.info("Warning WF1: No critical signals for asset <{}> were found.", asset);
		}

		//Opening Warning WF for Case 2
		// Temporary comment Marius 05.06.2020
		/*
		Map<String, List<CriticalSignalDto>> wf2SignalsMap = assetIntegrityDao.getCriticalSignalsWithOpenWF(asset);

		if(wf2SignalsMap != null && !wf2SignalsMap.isEmpty()) {
			logger.info("Warning WF2: {} open WF(s) with critical signals for asset <{}> were found.", wf2SignalsMap.size(), asset);
			for (String notWfId : wf2SignalsMap.keySet()) {
				logger.info("Warning WF2: WF <{}> with {} critical signals were found.", notWfId, wf2SignalsMap.get(notWfId).size());
				sendCriticalSignalsWarnings(notWfId, asset, ASSET_EMAIL_02, wf2SignalsMap.get(notWfId));
			}
		} else {
			logger.info("Warning WF2: No critical signals for asset <{}> were found.", asset);
		}
		*/
	}

	/*private Shift findActualShift() {
		List<Shift> shifts = assetIntegrityDao.getShifts();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

		LocalTime now = LocalTime.now();
		logger.debug("Find actual shift for time={}", now.toString());

		for(Shift s: shifts){
			logger.debug("Analyzing shift={}", s.toString());
			
			LocalTime startShift = LocalTime.parse(s.getStart());
			LocalTime endShift = LocalTime.parse(s.getEnd());
			
			// gestito il caso in cui un turno è suddiviso su più giorni
			// ad esempio: inizio turno 21:00:00, fine turno 04:59:59 del giorno dopo
			
			if(startShift.isBefore(endShift)){ // es: start 05:00:00, end 12:59:59
				if(now.isBefore(endShift) &&
						now.isAfter(startShift)) {
					logger.debug("Found actual shift={}", s.toString());
					return s;
				}
			}else{ // es: start 21:00:00, end 04:59:59
				if(now.isBefore(startShift) &&
						now.isAfter(endShift)) {
					logger.debug("Found actual shift={}", s.toString());
					return s;
				}
			}
			
			
		}
		return null;
	}*/

	@Override
	public CheckSignalsWF checkSignalsStatus(String asset, String wfId) throws Exception {

		List<CriticalSignalDto> signals = assetIntegrityDao.getSignalStatus(asset, wfId);
		CheckSignalsWF check = new CheckSignalsWF();
		check.setAsset(asset);

		
		List<CriticalSignalDto> signalsCopy = (List) ((ArrayList) signals).clone();
		if(signals != null && !signals.isEmpty()) {
			for (CriticalSignalDto signal : signalsCopy) {
				if(signal.getMoc() == true) {
					signals.remove(signal);
				}
			}

			check.setSignals(signals);
			check.setIsOver(false);
			logger.info("Critical signals with BlockInputStatus == TRUE and Moc == FALSE for wf <{}> were found. ("+signals.size()+")", wfId);
		} else {
			logger.info("No critical signals with BlockInputStatus == TRUE for wf <{}> were found.", wfId);
			check.setIsOver(true);
		}

		return check;
	}

	
	@Override
	public void updateSignalMoc(CriticalSignalDto input, String asset, String uid) throws Exception {
		try {

			assetIntegrityDao.updateSignalMoc(input, asset, uid);

		} catch (Exception e) {
			logger.error("errore updateSignalMoc", e);
		}

	}

	private String signalsToString(List<com.eni.ioc.assetintegrity.enitities.CriticalSignal> lista) {
		StringBuilder sb = new StringBuilder();

		int i = 0;
		sb.append("{");
		for (com.eni.ioc.assetintegrity.enitities.CriticalSignal cr : lista) {
			sb.append(cr.toStringShort());
			if(i < lista.size() - 1) {
				sb.append(",");
			}
			i++;

		}
		sb.append("}");

		return sb.toString();
	}

	private String segnaleToString(List<Segnale> signals) {
		StringBuilder sb = new StringBuilder();

		int i = 0;
		sb.append("{");
		for (Segnale cr : signals) {
			sb.append(cr.toStringShort());
			if(i < signals.size() - 1) {
				sb.append(",");
			}
			i++;

		}
		sb.append("}");

		return sb.toString();
	}

	private String reasonsToString(List<ReasonMoCInput> reasons) {
		StringBuilder sb = new StringBuilder();

		int i = 0;
		sb.append("{");
		for (ReasonMoCInput cr : reasons) {
			sb.append(cr.toString());
			if(i < reasons.size() - 1) {
				sb.append(",");
			}
			i++;

		}
		sb.append("}");

		return sb.toString();
	}

	private String generateHash(String originalString) {

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
			StringBuilder hexString = new StringBuilder();
			for (int i = 0; i < encodedhash.length; i++) {
				String hex = Integer.toHexString(0xff & encodedhash[i]);
				if(hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	private String wfCriticalSignalsToString(OpenWorkflowCriticalSignalsInput input) {
		StringBuilder out = new StringBuilder();

		String duration = input.getDuration();
		String startDate = input.getStartDate().toString();
		List<com.eni.ioc.assetintegrity.enitities.CriticalSignal> segnali = input.getSignals();

		out.append(startDate).append(duration);

		for (com.eni.ioc.assetintegrity.enitities.CriticalSignal sign : segnali) {
			out.append(sign.toString());
		}

		return out.toString();
	}

	private String wfRequestModificationToString(RequestModificationInput input) {
		StringBuilder out = new StringBuilder();

		String number = input.getNumber();
		String date = input.getDate().toString();

		out.append(number).append(date);

		return out.toString();
	}

	private List<Segnale> criticalSignalToSegnale(List<com.eni.ioc.assetintegrity.enitities.CriticalSignal> in) {
		List<Segnale> out = new ArrayList<>();

		for (com.eni.ioc.assetintegrity.enitities.CriticalSignal entry : in) {
			Segnale toadd = new Segnale();
			toadd.setArea(entry.getArea());
			toadd.setDescription(entry.getDescription());
			toadd.setName(entry.getName());
			toadd.setType(entry.getSeverity());
			out.add(toadd);
		}

		return out;
	}

	private CreaManualWorkflowInput generaMessaggioCondizioneAnomala(String hash,
			OpenWorkflowCriticalSignalsInput entitaCondizioneAnomala, String asset, String user, String uid) {

		CreaManualWorkflowInput creaWorkflowInput = new CreaManualWorkflowInput();
		AssetIntegrityRequest aiRequest = new AssetIntegrityRequest();

		aiRequest.setId(hash);
		aiRequest.setDuration(entitaCondizioneAnomala.getDuration());
		aiRequest.setEmergenza(entitaCondizioneAnomala.getEmergency());
		aiRequest.setFromDate(entitaCondizioneAnomala.getStartDate());
		aiRequest.setMotivation(entitaCondizioneAnomala.getNotes());
		aiRequest.setOmdSap(entitaCondizioneAnomala.getSap());
		aiRequest.setRichiedente(user);
		aiRequest.setRichiedenteUid(uid);
		aiRequest.setSignals(criticalSignalToSegnale(entitaCondizioneAnomala.getSignals()));
		aiRequest.setT(new Date());
		aiRequest.setTipoRichiesta(entitaCondizioneAnomala.getRequestType());

		creaWorkflowInput.setAsset(asset);
		creaWorkflowInput.setCode("001");
		creaWorkflowInput.setLevel("alert");
		creaWorkflowInput.setVista("assetIntegrity");
		creaWorkflowInput.setManualRequest(aiRequest);

		return creaWorkflowInput;
	}

	private CreaManualWorkflowInput generaMessaggioRequestModification(String hash,
			RequestModificationInput input, String asset, String user, String uid) {

		CreaManualWorkflowInput creaWorkflowInput = new CreaManualWorkflowInput();

		creaWorkflowInput.setAsset(asset);
		creaWorkflowInput.setCode("001");
		creaWorkflowInput.setLevel("moc");
		creaWorkflowInput.setVista("assetIntegrity");
		input.setUser(user);
		input.setUid(uid);
		RequestModificationDto dto = new RequestModificationDto(input);
		creaWorkflowInput.setManualRequest(dto);

		return creaWorkflowInput;
	}

	public void notifyWs(String asset, Object param) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		String requestJson = "";

		requestJson = "{\"asset\":\"" + asset + "\", \"event\":\""
				+ env.getProperty("spring.event.type.criticalsignals") + "\", \"keyname\":\"" + param + "\"}";

		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

		logger.info("Notify web server socket with payload: " + requestJson);

		String wssUrls = env.getProperty("spring.ws.urls");
		logger.info(wssUrls);
		String[] wsUrlArr = wssUrls.split("\\|");

		for (String wsUrl : wsUrlArr) {
			logger.info("Notify web server socket to: " + wsUrl);
			ResponseEntity<Esito> resultEntity = restTemplate.exchange(wsUrl, HttpMethod.POST, entity, Esito.class);
		}
	}

	private void sendCriticalSignalsWarnings(String notWfId, String asset, String emailType, List<CriticalSignalDto> signals) throws Exception {
		if(signals != null && !signals.isEmpty()) {
			CreaManualWorkflowInput newWorkflow = new CreaManualWorkflowInput();
			AssetIntegrityRequest aiRequest = new AssetIntegrityRequest();
			aiRequest.setT(new Date());
			aiRequest.setEmailType(emailType);
			aiRequest.setNotificationSourceWfId(notWfId);

			for (CriticalSignalDto signal : signals) {
				Segnale segnale = new Segnale();
				segnale.setArea(signal.getArea());
				segnale.setDescription(signal.getDescription());
				segnale.setName(signal.getName());
				segnale.setType(signal.getSeverity());
				aiRequest.addSignal(segnale);
			}

			logger.info("Sending {} warnings (critical signals) to the queue.", signals.size());
			newWorkflow.setAsset(asset);
			newWorkflow.setCode("001");
			newWorkflow.setLevel("warning");
			newWorkflow.setVista("assetIntegrity");
			newWorkflow.setManualRequest(aiRequest);
			sender.sendToRabbitMQActual(newWorkflow);
		} else {
			logger.info("No critical signals for asset <{}> were found.", asset);
		}

	}

	private void sendCriticalSignalsWarningsToMessage(String asset, String emailType, List<CriticalSignalDto> signals) {
		try {
			if(signals == null || signals.isEmpty()) {
				logger.info("No critical signals for asset <{}> were found.", asset);
				throw new Exception();
			}

			NotificationDto notification = new NotificationDto();
			notification.setAsset(asset);
			notification.setChannel("mail");
			notification.setNotifyDate(new Date());
			notification.setVista("assetIntegrity");
			notification.setStep("");

			// per la data, si parte da new Date() come nel metodo sendCriticalSignalsWarnings 
			LocalDateTime dateT = new Date().toInstant()
				      .atZone(ZoneId.systemDefault())
				      .toLocalDateTime();
			ZonedDateTime dateLocalT = ZonedDateTime.ofLocal(dateT, ZoneOffset.systemDefault(), null);
			ZonedDateTime dateTCET = dateLocalT.withZoneSameInstant(ZoneId.of("CET"));
			String dateTFE = dateTCET.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " h " + dateTCET.format(DateTimeFormatter.ofPattern("HH:mm"));

			LocalDateTime startRange = LocalDateTime.now()
					.atZone(ZoneId.systemDefault())
					.withZoneSameInstant(ZoneId.of("CET"))
					.toLocalDateTime()
					.minusHours(Long.parseLong(env.getProperty("shift.duration")));

			LocalDateTime endRange = LocalDateTime.now()
					.atZone(ZoneId.systemDefault())
					.withZoneSameInstant(ZoneId.of("CET"))
					.toLocalDateTime();

			// si crea la tabella in html relativa ai segnali recuperati
			String signalsHtmlTable = createSignalsHtmlTable(signals, startRange, endRange);
			Map<String, Integer> signalOccurrences = countSignalOccurrences(signals, startRange, endRange);

			Map<String, String> valuesTpl = new HashMap<>();

			int countNewSignal = signalOccurrences.get(SIGNAL_STATE_NEW);
			int countOldSignal = signalOccurrences.get(SIGNAL_STATE_OLD);
			int countTotalSignal = signalOccurrences.get(SIGNAL_STATE_TOTAL);

			BigDecimal bigDecimal100 = BigDecimal.valueOf(100);
			BigDecimal percNewSignal = BigDecimal.valueOf(countNewSignal).multiply(bigDecimal100).divide(BigDecimal.valueOf(countTotalSignal), 1, RoundingMode.HALF_UP);
			BigDecimal percOldSignal = BigDecimal.valueOf(countOldSignal).multiply(bigDecimal100).divide(BigDecimal.valueOf(countTotalSignal), 1, RoundingMode.HALF_UP);
			String percNewSignalString = percNewSignal.toPlainString();
			String percOldSignalString = percOldSignal.toPlainString();
			if(percNewSignalString.endsWith(".0")) {
				percNewSignalString = percNewSignalString.split("\\.")[0];
			}
			if(percOldSignalString.endsWith(".0")) {
				percOldSignalString = percOldSignalString.split("\\.")[0];
			}

			//AGGIUNGERE VALORI
			valuesTpl.put("n_ai_dateT", dateTFE);
			valuesTpl.put("n_ai_all_signals_text", signalsHtmlTable);
			valuesTpl.put("n_bi_tot", Integer.toString(countTotalSignal));
			valuesTpl.put("n_bi_new", Integer.toString(countNewSignal));
			valuesTpl.put("n_bi_new_perc", percNewSignalString);
			valuesTpl.put("n_bi_old", Integer.toString(countOldSignal));
			valuesTpl.put("n_bi_old_perc", percOldSignalString);


			List<UserWhitelist> recipients = userWhitelistRepo.findToUser();
			List<UserWhitelist> recipientsCc = userWhitelistRepo.findCcUser();

			//GESTIONE UTENTI IN TO
			List<UserMailDto> mails = findUserMail(recipients, false);

			if(mails == null || mails.isEmpty()) {
				logger.info("No recipients <{}> were found.", asset);
				throw new Exception();
			}

			//GESTIONE UTENTI IN CC
			List<UserMailDto> mailsCc = findUserMail(recipientsCc, true);

			if(mailsCc != null) {
				mails.addAll(mailsCc);
			}

			NotificationInput n = new NotificationInput(notification, emailType, asset, valuesTpl, mails);
			n.setGroup(true);
			n.setSenderAddress(emailSenderAssetAddress);
			n.setSenderName(emailSenderAssetName);

			sender.sendToRabbitMQMessage(n);
		} catch (Exception e) {
			logger.error("could not send email", e);
		}

	}

	private List<UserMailDto> findUserMail(List<UserWhitelist> recipients, boolean isCarbonCopy) throws Exception {

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
	}

	private void sendSignalsToWs(String asset, List<com.eni.ioc.assetintegrity.enitities.CriticalSignal> signals) {
		Set<String> areas = new HashSet<String>();
		signals.forEach(item -> areas.add(item.getArea()));
		areas.forEach(area -> notifyWs(asset, area));
	}

	private String createSignalsHtmlTable(List<CriticalSignalDto> signals, LocalDateTime startRange, LocalDateTime endRange){
		StringBuilder sb = new StringBuilder();

		Map<String, ArrayList<CriticalSignalDto>> signalsMap = new TreeMap<String,ArrayList<CriticalSignalDto>>(); // chiave: area, valore: segnale

		if(signals != null && !signals.isEmpty()) {

			// realizziamo la mappa per rendere più semplice la creazione della tabella
			// essendo una TreeMap, è già previsto l'ordinamento sulla chiave
			for (CriticalSignalDto currentSignal : signals) {
				if(!signalsMap.containsKey(currentSignal.getArea())) {
					signalsMap.put(currentSignal.getArea(), new ArrayList<CriticalSignalDto>());
				}
				signalsMap.get(currentSignal.getArea()).add(currentSignal);
			}

			// abbiamo una sola tabella
			sb.append("<table class='block-input_table'>");
			// per ogni area abbiamo una sezione

			Iterator<String> signalsMapKeyIterator = signalsMap.keySet().iterator(); // uso l'iteratore per non stampare l'ultima riga vuota alla fine della tabella

			while (signalsMapKeyIterator.hasNext()) {

				String area = signalsMapKeyIterator.next();

				int num = 1;

				sb.append("<thead>");
				sb.append("<tr class='block-input_table_tr'>");
				sb.append("<th>AREA ").append(area).append("</th>");
				sb.append("<th>SEGNALI CRITICI</th>");
				sb.append("<th>STATO</th>");
				sb.append("<th>DATA ORA</th>");
				sb.append("</tr>");
				sb.append("</thead>");
				sb.append("<tbody>");
				for (CriticalSignalDto currentSignal : signalsMap.get(area)) {
			    	
			    	/*String signalHour = "";
			    	if(currentSignal.getLastStatusChangeDate() != null){
			    		String[] split = currentSignal.getLastStatusChangeDate().split("\\s+"); // es: 04/05/2020 23:59:37
			    		if(split != null && split.length == 2){
			    			signalHour = split[1]; // campo dell'ora
			    		}
			    	}*/

					String signalState = setSignalStatus(currentSignal, startRange, endRange);

					sb.append("<tr>");
					sb.append("<td>")
			    		.append(num)
			    		.append("</td>");
			    	sb.append("<td>").append(currentSignal.getName())
							.append("-")
							.append(currentSignal.getDescription())
							.append("-")
							.append(currentSignal.getArea())
							.append("-")
							.append(currentSignal.getSeverity())
							.append("</td>");

			    	if("1".equals(currentSignal.getBlockInputStatus())){
			    		sb.append("<td style='color:red'>")
			    		.append(signalState)
			    		.append("</td>");
					}else{
						if(SIGNAL_STATE_NEW.equals(signalState)) {
							if("0".equals(currentSignal.getBlockInputStatus())){
								sb.append("<td style='color:green'>")
					    		.append(signalState)
					    		.append("</td>");
							}
						} else {
				    		sb.append("<td>")
				    		.append(signalState)
				    		.append("</td>");
						}
					}
			    	
					sb.append("<td>")
							//.append(signalHour)
						.append(currentSignal.getLastStatusChangeDate())
			    		.append("</td>");
					sb.append("</tr>");
					num++;

				}

			    // alla fine dei segnali di un'area, aggiungo una riga vuota per separare le aree (tranne alla fine della tabella)
				if(signalsMapKeyIterator.hasNext()) {
					sb.append("<tr class='empty-line'><td style=\"border:none;\" /></tr>");
				}

				sb.append("</tbody>");
			}
			sb.append("</table>");
			sb.append("<br/>");

		}

		return sb.toString();
	}

	private String setSignalStatus(CriticalSignalDto signal, LocalDateTime startRange, LocalDateTime endRange) {
		String status = SIGNAL_STATE_OLD;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime lastStatusChangeDate = LocalDateTime.parse(signal.getLastStatusChangeDate(), formatter);
		if(lastStatusChangeDate.isAfter(startRange) && lastStatusChangeDate.isBefore(endRange)) {
			status = SIGNAL_STATE_NEW;
		}
		return status;
	}

	private Map<String,Integer> countSignalOccurrences(List<CriticalSignalDto> signals, LocalDateTime startRange, LocalDateTime endRange){
		Map<String, Integer> occurrences = new HashMap<String, Integer>();
		int countNewSignal = 0;
		int countOldSignal = 0;
		int countTotalSignal = 0;
		String state = "";

		for (CriticalSignalDto currentSignal : signals) {
			state = setSignalStatus(currentSignal, startRange, endRange);
			if(SIGNAL_STATE_NEW.equals(state)) {
				countNewSignal++;
			} else if(SIGNAL_STATE_OLD.equals(state)) {
				countOldSignal++;
			}
		}

		countTotalSignal = countNewSignal + countOldSignal;

		occurrences.put(SIGNAL_STATE_NEW, countNewSignal);
		occurrences.put(SIGNAL_STATE_OLD, countOldSignal);
		occurrences.put(SIGNAL_STATE_TOTAL, countTotalSignal);

		return occurrences;
	}

}
