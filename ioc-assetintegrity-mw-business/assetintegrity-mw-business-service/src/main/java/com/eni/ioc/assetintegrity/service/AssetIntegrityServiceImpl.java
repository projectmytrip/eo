package com.eni.ioc.assetintegrity.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eni.ioc.assetintegrity.api.DERegistryFilter;
import com.eni.ioc.assetintegrity.api.MocRegistryInput;
import com.eni.ioc.assetintegrity.dao.AssetIntegrityDao;
import com.eni.ioc.assetintegrity.dao.TemplateFileRepo;
import com.eni.ioc.assetintegrity.dto.AssetTable;
import com.eni.ioc.assetintegrity.dto.BacklogOperationalKpi;
import com.eni.ioc.assetintegrity.dto.CorrosionBacteriaDto;
import com.eni.ioc.assetintegrity.dto.CorrosionCNDCount;
import com.eni.ioc.assetintegrity.dto.CorrosionCNDElement;
import com.eni.ioc.assetintegrity.dto.CorrosionCNDTable;
import com.eni.ioc.assetintegrity.dto.CorrosionCouponDto;
import com.eni.ioc.assetintegrity.dto.CorrosionCouponTable;
import com.eni.ioc.assetintegrity.dto.CorrosionPiggingDto;
import com.eni.ioc.assetintegrity.dto.CorrosionProtectionDto;
import com.eni.ioc.assetintegrity.dto.CriticalSignalsOperationalKpi;
import com.eni.ioc.assetintegrity.dto.EWPDto;
import com.eni.ioc.assetintegrity.dto.KpiDataDto;
import com.eni.ioc.assetintegrity.dto.RegistroDeDto;
import com.eni.ioc.assetintegrity.dto.RegistroMocDto;
import com.eni.ioc.assetintegrity.enitities.AreaDto;
import com.eni.ioc.assetintegrity.enitities.AreasDto;
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
import com.eni.ioc.assetintegrity.entities.CorrosionPigging;
import com.eni.ioc.assetintegrity.entities.CorrosionProtection;
import com.eni.ioc.assetintegrity.entities.CriticalSignal;
import com.eni.ioc.assetintegrity.entities.EVPMSData;
import com.eni.ioc.assetintegrity.entities.EWP;
import com.eni.ioc.assetintegrity.entities.JacketedPipes;
import com.eni.ioc.assetintegrity.entities.KpiData;
import com.eni.ioc.assetintegrity.entities.KpiNetworkData;
import com.eni.ioc.assetintegrity.entities.KpiOilData;
import com.eni.ioc.assetintegrity.entities.KpiPlantData;
import com.eni.ioc.assetintegrity.entities.KpiReservoirData;
import com.eni.ioc.assetintegrity.entities.RegistroDe;
import com.eni.ioc.assetintegrity.entities.RegistroMoc;
import com.eni.ioc.assetintegrity.entities.TemplateFile;
import com.eni.ioc.assetintegrity.entities.WellAlarm;
import com.eni.ioc.assetintegrity.utils.AssetIntegrityException;
import com.eni.ioc.assetintegrity.utils.BacklogColoreEnum;
import com.eni.ioc.assetintegrity.utils.BacklogStatoUtenteOdmEnum;
import com.eni.ioc.assetintegrity.utils.BacklogTipoManutenzioneEnum;
import com.eni.ioc.assetintegrity.utils.BacklogUnitaEnum;
import com.eni.ioc.assetintegrity.utils.Esito;

/**
 * @author a.menolascina
 */
@Service
public class AssetIntegrityServiceImpl implements AssetIntegrityService {

	private static final Logger logger = LoggerFactory.getLogger(AssetIntegrityServiceImpl.class);
	private static final String COVA = "COVA";

	private static final int CORROSION_BACTERIA_THRESHOLD = 100;
	private static final float CORROSION_COUPON_THRESHOLD = 1.0f;
	private static final float CORROSION_PIGGING_THRESHOLD = 1.0f;
	private static final float CORROSION_PROTECTION_THRESHOLD = -0.85f;
	private static final String BUCKET_AREA = "BULK";

	private static final String NA = "N/A";

	@Autowired
	AssetIntegrityDao assetIntegrityDao;

	@Autowired
	Sender sender;

	@Autowired
	private Environment env;

	@Autowired
	ExcelService excelService;

	@Autowired
	TemplateFileRepo templateFileRepo;

	@Autowired
	AssetIntegrityExcelService assetIntegrityExcelService;

	@Override
	public void updateKpiData(List<KpiDataDto> input, String table) {
		List<KpiData> dataList = new LinkedList();
		for (KpiDataDto dto : input) {
			KpiData data = null;
			AssetIntegrityTable switchCase = AssetIntegrityTable.valueOf(table.toUpperCase());
			switch (switchCase) {
			case NETWORK:
				data = new KpiNetworkData();
				break;
			case OIL:
				data = new KpiOilData();
				break;
			case PLANT:
				data = new KpiPlantData();
				break;
			case RESERVOIR:
				data = new KpiReservoirData();
				break;
			default:
				throw new IllegalArgumentException("Wrong table value inserted " + table);
			}
			data.setId(dto.getId());
			data.setValore(dto.getValore());
			data.setStato(dto.getStato());
			data.setUltimoUpdate(dto.getUltimoUpdate());
			data.setInsertionDate(new Date());
			dataList.add(data);
		}
		assetIntegrityDao.updateKpiData(dataList);
	}

	@Override
	public CriticalCount getDashboard(String asset) {
		return assetIntegrityDao.getDashboard(asset);
	}

	@Override
	public Map<String, Map<String, Integer>> getEWPCounts(String asset) {
		List<EWP> ewps = assetIntegrityDao.getEwp(asset);
		Map<String, List<String>> areasMap = assetIntegrityDao.getAreasMap(asset);
		String allsubAreas = areasMap.values().toString();

		Map<String, Map<String, Integer>> result = new HashMap<>();
		for (EWP ewp : ewps) {
			String[] tipologies = null;
			String[] areas = null;
			String tipAttivita = "";
			if (ewp.getIndicazioneSCE() == null || ewp.getIndicazioneSCE().equals("")) {
				tipAttivita = ewp.getIndicazioneTipologiaAttivita();
			} else {
				tipAttivita = ewp.getIndicazioneTipologiaAttivita() != null
						? ewp.getIndicazioneTipologiaAttivita() + "|SECE" : "SECE";
			}
			if (tipAttivita != null) {
				tipologies = tipAttivita.split("\\|");
			}
			if (ewp.getSottoArea() != null) {
				areas = ewp.getSottoArea().split("\\|");
			}

			if (areas != null && areas.length > 0 && tipologies != null) {
				for (String area : areas) {
					if (area == null || "".equals(area.trim())) {
						// addAreaToResult(result, tipologies, BUCKET_AREA);
						continue;
					}
					if (areasMap.get(area) == null) {

						if (allsubAreas.contains(area)) {
							// if area is subArea just add current area...
							addAreaToResult(result, tipologies, area);
						} else {

							addAreaToResult(result, tipologies, BUCKET_AREA);
						}

					} else {
						// if area is parentArea, loop through its subAreas...
						List<String> subAreas = areasMap.get(area);
						for (String subArea : subAreas) {
							addAreaToResult(result, tipologies, subArea);
						}
					}
				}
			} else if (areas != null && areas.length == 0 && tipologies != null) {
				addAreaToResult(result, tipologies, BUCKET_AREA);
			} else {
				logger.info("Area non gestita correttamente");
			}
		}
		return result;
	}

	private void addAreaToResult(Map<String, Map<String, Integer>> result, String[] tipologies, String subArea) {
		// Spazi confinati \ Sollevamenti critici \ Lavori a caldo \ Lavori in
		// quota \ SECE \ altri
		List<String> ewpActivities = Arrays.asList(env.getProperty("ai.ewp.lavori").split("\\|"));
		List<String> tipologiesList = Arrays.asList(tipologies);

		if (subArea.equals("ESSSC")) {
			logger.info(subArea);
		}

		Map<String, Integer> areaMap = result.get(subArea);
		if (areaMap == null) {
			areaMap = new HashMap<>();
		}

		Boolean foundActivity = false;
		for (String activity : ewpActivities) {

			if (tipologiesList.contains(activity)) {

				if (areaMap.get(activity) != null) {
					areaMap.put(activity, areaMap.get(activity) + 1);
				} else {
					areaMap.put(activity, 1);
				}
				foundActivity = true;
				break;
			}
		}

		if (!foundActivity) {
			if (areaMap.get("altri") != null) {
				areaMap.put("altri", areaMap.get("altri") + 1);
			} else {
				areaMap.put("altri", 1);
			}
		}

		result.put(subArea, areaMap);
	}

	@Override
	public Map<String, String> getEWPTags(String asset, String parentMap) {

		List<EWP> ewps = assetIntegrityDao.getEwpWithTags(asset, parentMap);

		Map<String, String> result = new HashMap<>();
		for (EWP ewp : ewps) {
			String[] tipologies = null;
			String[] tags = null;
			String tipAttivita = "";
			if (ewp.getIndicazioneSCE() == null || ewp.getIndicazioneSCE().equals("")) {
				tipAttivita = ewp.getIndicazioneTipologiaAttivita();
			} else {
				tipAttivita = ewp.getIndicazioneTipologiaAttivita() != null
						? ewp.getIndicazioneTipologiaAttivita() + "|SECE" : "SECE";
			}
			if (tipAttivita != null) {
				tipologies = tipAttivita.split("\\|");
			}
			if (ewp.getTags() != null) {
				tags = ewp.getTags().split("\\|");
			}

			if (tags != null && tipologies != null) {
				for (String tag : tags) {
					if (tag == null || "".equals(tag.trim())) {
						continue;
					} else {
						addTagToResult(result, tipologies, tag);
					}
				}
			}
		}

		return result;
	}

	private void addTagToResult(Map<String, String> result, String[] tipologies, String tag) {
		// Spazi confinati \ Sollevamenti critici \ Lavori a caldo \ Lavori in
		// quota \ SECE \ altri
		List<String> ewpActivities = Arrays.asList(env.getProperty("ai.ewp.lavori").split("\\|"));
		List<String> tipologiesList = Arrays.asList(tipologies);

		String tagAttivita = result.get(tag);

		if (tagAttivita == null) {
			tagAttivita = "";
		}

		for (String activity : tipologiesList) {
			if (!ewpActivities.contains(activity)) {
				activity = "altri";
			}

			if (tagAttivita.equals("")) {
				tagAttivita = activity;
			} else {
				if (ewpActivities.contains(activity) && ewpActivities.contains(tagAttivita)
						&& ewpActivities.indexOf(activity) < ewpActivities.indexOf(tagAttivita)) {
					tagAttivita = activity;
					break;
				} else if (ewpActivities.contains(activity) && !ewpActivities.contains(tagAttivita)) {
					tagAttivita = activity;
				} else if (!ewpActivities.contains(activity) && !ewpActivities.contains(tagAttivita)) {
					tagAttivita = activity;
				}
			}
		}

		if (!ewpActivities.contains(tagAttivita)) {
			tagAttivita = "altri";
		}

		result.put(tag, tagAttivita);
	}

	@Override
	public List<EWPDto> getEwp(String asset, String area, String parentArea) {
		List<EWP> elements = assetIntegrityDao.getEwp(asset, area, parentArea);

		Map<String, List<String>> areasMap = assetIntegrityDao.getAreasMap(asset);
		String allsubAreas = areasMap.values().toString() + areasMap.keySet().toString();

		List<EWPDto> resultList = new ArrayList<>();
		for (EWP ewpElement : elements) {
			Boolean doEwp = false;
			if (area.equals(BUCKET_AREA)) {
				List<String> ewpAreas = Arrays.asList(ewpElement.getSottoArea().split("\\|"));
				for (String ewpArea : ewpAreas) {
					if (ewpArea != null && !ewpArea.trim().equals("")
							&& ((!allsubAreas.contains(ewpArea + ",") && !allsubAreas.contains(ewpArea + "]"))
									|| ewpArea.equals(BUCKET_AREA))) {
						doEwp = true;
						break;
					}
				}
				if (ewpAreas != null && ewpAreas.size() == 0) {
					doEwp = true;

				}
			} else {
				doEwp = true;
			}

			if (doEwp) {
				EWPDto result = new EWPDto();
				result.setStatus(ewpElement.getStatus());
				result.setNumeroEWP(ewpElement.getNumeroEWP());
				result.setDescrizioneLavoro(ewpElement.getDescrizioneLavoro());
				result.setAppaltatore(ewpElement.getAppaltatore());
				result.setDataApertura(ewpElement.getDataApertura());
				result.setDataScadenza(ewpElement.getDataScadenza());
				result.setRichiedente(ewpElement.getRichiedente());
				result.setUnitRichiedente(ewpElement.getUnitaRichiedente());
				result.setAutorizzanteAnalisiDiRischio(ewpElement.getAutorizzanteAnalisiDiRischio());
				result.setUnitaAutorizzante(ewpElement.getUnitaAutorizzante());
				result.setSorvegliante(ewpElement.getSorvegliante());

				if (ewpElement.getTags() != null && !ewpElement.getTags().equals("")) {
					String tags = ewpElement.getTags().substring(1, ewpElement.getTags().length() - 1);
					logger.info(tags);
					result.setSort(Arrays.asList(tags.split("\\|")));
				} else {
					result.setSort(null);
				}

				String tipAttivita = "";
				if (ewpElement.getIndicazioneSCE() == null || ewpElement.getIndicazioneSCE().equals("")) {
					tipAttivita = ewpElement.getIndicazioneTipologiaAttivita();
				} else {
					tipAttivita = ewpElement.getIndicazioneTipologiaAttivita() != null
							? ewpElement.getIndicazioneTipologiaAttivita() + "|SECE" : "SECE";
				}
				result.setIndicazioneTipologiaAttivita(Arrays.asList(tipAttivita.split("\\|")));
				result.setPrepostoAiLavori(ewpElement.getPrepostoAiLavori());
				result.setSedeTecnica(formatPipes(ewpElement.getSedeTecnica()));
				result.setIndicazioneSCE(formatPipes(ewpElement.getIndicazioneSCE()));
				result.setDescrizioneSCE(formatPipes(ewpElement.getDescrizioneSCE()));
				result.setDataChiusura(ewpElement.getDataChiusura());
				result.setDataOraInizioAutorizzazione(ewpElement.getDataOraInizioAutorizzazione());
				result.setDataOraScadenzaAutorizzazione(ewpElement.getDataOraScadenzaAutorizzazione());
				result.setImpresaEsecutrice(ewpElement.getImpresaEsecutrice());

				resultList.add(result);
			}
		}
		return resultList;
	}

	private String formatPipes(String input) {
		if (input == null) {
			return null;
		}
		// split on pipes, discard empty or null values, join with a comma
		return Arrays.asList(input.split("\\|")).stream().filter(s -> s != null && !s.isEmpty() && !"".equals(s))
				.collect(Collectors.joining(", "));
	}

	@Override
	public CountCriticalDto countCriticalHigh(String table, String asset) {
		if (AssetIntegrityTable.valueOf(table.toUpperCase()) != null) {

			CountCriticalDto res = new CountCriticalDto();
			AssetIntegrityTable switchCase = AssetIntegrityTable.valueOf(table.toUpperCase());
			switch (switchCase) {
			case WELL:
				res = assetIntegrityDao.countCriticalHighWell(table, asset);
				break;
			default:
				res = assetIntegrityDao.countCriticalHigh(table, asset);
				break;
			}

			return res;

		} else {
			throw new AssetIntegrityException(AssetIntegrityException.CodeError.wrongParameter,
					String.format("invalid table: %s", table));
		}

	}

	@Override
	public DetailListDto showDetails(String table, String asset) {
		DetailListDto res = new DetailListDto();

		AssetIntegrityTable switchCase = AssetIntegrityTable.valueOf(table.toUpperCase());

		switch (switchCase) {
		case NETWORK:
			res = assetIntegrityDao.showDetailsNetwork(table, asset);
			break;
		case OIL:
			res = assetIntegrityDao.showDetailsOil(table, asset);
			break;
		case PLANT:
			res = assetIntegrityDao.showDetailsPlant(table, asset);
			break;
		case RESERVOIR:
			res = assetIntegrityDao.showDetailsReservoir(table, asset);
			break;
		case WELL:
			res = assetIntegrityDao.showDetailsWell(table, asset);
			break;
		default:
			logger.warn(String.format("unknown enum value: %s", table));
			break;
		}

		return res;
	}

	@Override
	public CriticalCount countOthers(String tab) {
		return assetIntegrityDao.countOthers(tab);
	}

	@Override
	public List<Backlog> backloglist(String tab, String area, String parentArea) throws ParseException {
		Instant start = Instant.now();
		List<Backlog> backlogList = assetIntegrityDao.backloglist(tab, area, parentArea);
		// inizio assegnazione colore backlog e correzione campo durata
		if (backlogList != null && !backlogList.isEmpty()) {

			// recupero i codici N_ODM per recuperare lo storico degli stati
			List<String> nOdmList = backlogList.stream().map(Backlog::getnOdm).collect(Collectors.toList());
			List<BacklogStateHistory> backlogStateHistoryList = assetIntegrityDao.getBacklogStateHistory(nOdmList);

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			for (Backlog currentBacklog : backlogList) {
				BacklogColoreEnum color = BacklogColoreEnum.RED;
				BacklogStateHistory currentHistory = backlogStateHistoryList.stream()
						.filter(elem -> currentBacklog.getnOdm().equals(elem.getnOdm())).findAny().orElse(null);

				// valorizzo il campo Data Cambio Stato da mostrare a front-end
				if (currentHistory != null && currentHistory.getDataInizioStato() != null) {
					String dataInizioStatoString = df.format(currentHistory.getDataInizioStato().getTime());
					currentBacklog.setDataCambioStato(dataInizioStatoString);
				}

				// correggo il campo durata rimuovendo la parte decimale
				// questo serve sia per mostrare il dato corretto sul front-end
				// sia nel calcolo del colore
				// se è vuoto, imposto NA
				if (currentBacklog.getDurata() == null || currentBacklog.getDurata().equals("")) {
					currentBacklog.setDurata(NA);
				} else {
					String[] splittedDurata;
					if (currentBacklog.getDurata().contains(",")) {
						splittedDurata = currentBacklog.getDurata().split(",");
						if (splittedDurata != null && splittedDurata[0] != null) {
							currentBacklog.setDurata(splittedDurata[0]);
						}
					} else if (currentBacklog.getDurata().contains(".")) {
						splittedDurata = currentBacklog.getDurata().split("\\.");
						if (splittedDurata != null && splittedDurata[0] != null) {
							currentBacklog.setDurata(splittedDurata[0]);
						}
					}
				}

				if (BacklogTipoManutenzioneEnum.PREVENTIVA.getTipoManutenzione()
						.equalsIgnoreCase(currentBacklog.getTipoManutenzione())) {
					color = backlogManutenzionePreventiva(currentBacklog, currentHistory);
				} else if (BacklogTipoManutenzioneEnum.CORRETTIVA.getTipoManutenzione()
						.equalsIgnoreCase(currentBacklog.getTipoManutenzione())) {
					color = backlogManutenzioneCorrettiva(currentBacklog, currentHistory);
				}

				currentBacklog.setColor(color.getColor());
				currentBacklog.setItalianColor(color.getItalianColor());
			}
		}
		// fine assegnazione colore backlog

		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMillis();
		logger.debug("BacklogList function took: " + timeElapsed);
		return backlogList;
	}

	private BacklogColoreEnum backlogManutenzionePreventiva(Backlog currentBacklog, BacklogStateHistory currentHistory)
			throws ParseException {
		BacklogColoreEnum color = BacklogColoreEnum.RED;

		BacklogStatoUtenteOdmEnum statoUtenteEnum = BacklogStatoUtenteOdmEnum
				.getEnum(currentBacklog.getStatoUtenteOdm());
		LocalDate today = LocalDate.now();

		if (BacklogStatoUtenteOdmEnum.ORIG.equals(statoUtenteEnum)) {

			// se stato=ORIG, coloro di verde se mi trovo nel range
			// [DataInizioCardine, DataInizioCardine + 4 settimane]
			// oppure coloro di giallo se mi trovo nel range [DataInizioCardine
			// + 4 settimane, DataInizioCardine + 4 settimane + 30% frequenza
			// OdM]
			LocalDate startRange = LocalDate.parse(currentBacklog.getDataInizioCardine(),
					DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			LocalDate endRange = startRange.plusWeeks(4);

			if ((today.isAfter(startRange) && today.isBefore(endRange)) || today.isEqual(startRange)
					|| today.isEqual(endRange)) {
				color = BacklogColoreEnum.GREEN;
			} else {

				if (currentBacklog.getDurata() != null && !NA.equalsIgnoreCase(currentBacklog.getDurata().toUpperCase())
						&& currentBacklog.getUnita() != null
						&& !NA.equalsIgnoreCase(currentBacklog.getUnita().toUpperCase())) {

					BacklogUnitaEnum unita = BacklogUnitaEnum.getEnum(currentBacklog.getUnita());
					BigDecimal durata = currentBacklog.getDurata() != null ? new BigDecimal(NumberFormat
							.getNumberInstance(Locale.getDefault()).parse(currentBacklog.getDurata()).doubleValue())
							: null;
					if (durata != null && unita != null) {
						BigDecimal durata30perc = durata.multiply(BigDecimal.valueOf(30))
								.divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN); // 30%
						switch (unita) {
						case G: // giorni
							endRange = endRange.plusDays(durata30perc.intValue());
							break;
						case H: // ore
							durata30perc = durata30perc.divide(BigDecimal.valueOf(24), RoundingMode.HALF_EVEN); // numero
																												// ore
																												// diviso
																												// 24
																												// =
																												// numero
																												// giorni
							endRange = endRange.plusDays(durata30perc.intValue());
							break;
						case MS: // mesi
							endRange = endRange.plusMonths(durata30perc.intValue());
							break;
						case ST: // settimane
							endRange = endRange.plusWeeks(durata30perc.intValue());
							break;
						case T: // giorni
							endRange = endRange.plusDays(durata30perc.intValue());
							break;
						case STD: // ore
							durata30perc = durata30perc.divide(BigDecimal.valueOf(24), RoundingMode.HALF_EVEN); // numero
																												// ore
																												// diviso
																												// 24
																												// =
																												// numero
																												// giorni
							endRange = endRange.plusDays(durata30perc.intValue());
							break;
						default:
							break;
						}
						if ((today.isAfter(startRange) && today.isBefore(endRange)) || today.isEqual(startRange)
								|| today.isEqual(endRange)) {
							color = BacklogColoreEnum.YELLOW;
						}
					}

				}

				logger.info("Caso Manutenzione Preventiva in stato ORIG: OdM {}, startRange {}, endRange {}",
						currentBacklog.getOdm(), startRange, endRange);

			}
		} else if (BacklogStatoUtenteOdmEnum.ISSU.equals(statoUtenteEnum)) {
			// se stato=ISSU, coloro di giallo se mi trovo nel range
			// [DataInizioStato, DataInizioStato + 45 giorni]
			LocalDate startRange = currentHistory.getDataInizioStato().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();
			LocalDate endRange = startRange.plusDays(45);
			if ((today.isAfter(startRange) && today.isBefore(endRange)) || today.isEqual(startRange)
					|| today.isEqual(endRange)) {
				color = BacklogColoreEnum.YELLOW;
			}
			logger.info("Caso Manutenzione Preventiva in stato ISSU: OdM {}, startRange {}, endRange {}",
					currentBacklog.getOdm(), startRange, endRange);
		} else if (BacklogStatoUtenteOdmEnum.SUSP.equals(statoUtenteEnum)) {
			// se stato=SUSP, coloro di grigio se mi trovo nel range
			// [DataInizioStato, DataInizioStato + 28 giorni]
			LocalDate startRange = currentHistory.getDataInizioStato().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();
			LocalDate endRange = startRange.plusDays(28);
			if ((today.isAfter(startRange) && today.isBefore(endRange)) || today.isEqual(startRange)
					|| today.isEqual(endRange)) {
				color = BacklogColoreEnum.GRAY;
			}
			logger.info("Caso Manutenzione Preventiva in stato SUSP: OdM {}, startRange {}, endRange {}",
					currentBacklog.getOdm(), startRange, endRange);
		}

		return color;
	}

	private BacklogColoreEnum backlogManutenzioneCorrettiva(Backlog currentBacklog,
			BacklogStateHistory currentHistory) {
		BacklogColoreEnum color = BacklogColoreEnum.RED;
		LocalDate today = LocalDate.now();

		BacklogStatoUtenteOdmEnum statoUtenteEnum = BacklogStatoUtenteOdmEnum
				.getEnum(currentBacklog.getStatoUtenteOdm());

		if (BacklogStatoUtenteOdmEnum.ORIG.equals(statoUtenteEnum)) {
			// se stato=ORIG, coloro di verde se mi trovo nel range
			// [DataInizioCardine, DataInizioCardine + 4 settimane]
			LocalDate startRange = LocalDate.parse(currentBacklog.getDataInizioCardine(),
					DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			LocalDate endRange = startRange.plusWeeks(4);
			if ((today.isAfter(startRange) && today.isBefore(endRange)) || today.isEqual(startRange)
					|| today.isEqual(endRange)) {
				color = BacklogColoreEnum.GREEN;
			}
			logger.info("Caso Manutenzione Correttiva in stato ORIG: OdM {}, startRange {}, endRange {}",
					currentBacklog.getOdm(), startRange, endRange);
		} else if (BacklogStatoUtenteOdmEnum.ISSU.equals(statoUtenteEnum)) {
			// se stato=ISSU, coloro di giallo se mi trovo nel range
			// [DataInizioStato, DataInizioStato + 45 giorni]
			LocalDate startRange = currentHistory.getDataInizioStato().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();
			LocalDate endRange = startRange.plusDays(45);
			if ((today.isAfter(startRange) && today.isBefore(endRange)) || today.isEqual(startRange)
					|| today.isEqual(endRange)) {
				color = BacklogColoreEnum.YELLOW;
			}
			logger.info("Caso Manutenzione Correttiva in stato ISSU: OdM {}, startRange {}, endRange {}",
					currentBacklog.getOdm(), startRange, endRange);
		}

		return color;
	}

	@Override
	public List<CriticalSignal> signcriticilist(String area, String parentArea) {
		return assetIntegrityDao.signcriticilist(area, parentArea);
	}

	@Override
	public List<CriticalSignalDto> signcriticilistDto(String area, String parentArea) {
		return assetIntegrityDao.signcriticilistDto(area, parentArea);
	}

	@Override
	public AreasDto getAreas(String asset, Long id, String tab) {
		List<AreaDto> list = assetIntegrityDao.getAreasWithCriticalCount(asset, id, tab);
		return new AreasDto(list);
	}

	@Override
	public AreasDto getEWPAreas(String asset, String parentMap, String parentArea) throws ParseException {
		Instant start = Instant.now();
		List<Backlog> l = this.backloglist(asset, null, null);
		List<AreaDto> list = assetIntegrityDao.getEWPAreasWithCriticalCount(asset, parentMap, parentArea);

		list.stream().forEach(area -> {
			List<Backlog> filteredBacklog = l.stream().filter(backlog -> backlog.getArea().equals(area.getCode())
					|| backlog.getArea().equals(area.getParentMap())).collect(Collectors.toList());

			area.setColor("rgba(0, 0, 0, 0)");

			for (Map.Entry<Integer, BacklogColoreEnum> entry : BacklogColoreEnum.getPriorityMap().entrySet()) {
				if (filteredBacklog.stream().anyMatch(b -> b.getColor().equals(entry.getValue().getColor()))) {
					// esiste almeno un backlog del colore X
					area.setColor(entry.getValue().getCodeColor());
					break;
				}
			}

			/*
			 * if(filteredBacklog.stream().anyMatch(b ->
			 * b.getColor().equals(BacklogColoreEnum.RED.getColor()))) {
			 * //esiste almeno un backlog rosso
			 * area.setColor(BacklogColoreEnum.RED.getCodeColor()); } else
			 * if(filteredBacklog.stream().anyMatch(b ->
			 * b.getColor().equals(BacklogColoreEnum.GREEN.getColor()) )) {
			 * //esiste almeno un backlog verde
			 * area.setColor(BacklogColoreEnum.GREEN.getCodeColor()); } else
			 * if(filteredBacklog.stream().anyMatch(b ->
			 * b.getColor().equals(BacklogColoreEnum.GRAY.getColor()))) {
			 * //esiste almeno un backlog grigio
			 * area.setColor(BacklogColoreEnum.GRAY.getCodeColor()); } else
			 * if(filteredBacklog.stream() .anyMatch(b ->
			 * b.getColor().equals(BacklogColoreEnum.YELLOW.getColor()))) {
			 * //esiste almeno un backlog giallo
			 * area.setColor(BacklogColoreEnum.YELLOW.getCodeColor()); }
			 */
		});

		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMillis();
		logger.debug("getEWPAreas function took: " + timeElapsed);
		return new AreasDto(list);
	}

	@Override
	public AreasDto getEWPSubAreas(String asset, String parentArea) {
		List<AreaDto> list = assetIntegrityDao.getEWPSubAreas(asset, parentArea);
		return new AreasDto(list);
	}

	@Override
	public List<CriticalSignal> getAllSignals(String asset, Integer active) {
		return assetIntegrityDao.getSegnaliCriticiPerWorkflow(asset, active, false);
	}

	@Override
	public List<CriticalSignal> getAllSignals(String asset, Integer active, boolean fullSearch) {
		return assetIntegrityDao.getSegnaliCriticiPerWorkflow(asset, active, fullSearch);
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

	private void sendSignalsToWs(String asset, List<com.eni.ioc.assetintegrity.enitities.CriticalSignal> signals) {
		Set<String> areas = new HashSet<String>();
		signals.forEach(item -> areas.add(item.getArea()));
		areas.forEach(area -> notifyWs(asset, area));
	}

	@Override
	public List<WellAlarm> getWellAlarms(String asset) {
		return assetIntegrityDao.getWellAlarms(asset);
	}

	@Override
	public CriticalSignalsOperationalKpi getCriticalSignalsKpi(String asset) {
		CriticalSignalsOperationalKpi kpi = new CriticalSignalsOperationalKpi();
		// Get severity count
		Map<String, Long> severityCount = assetIntegrityDao.getCriticalSignalsSeverityCount(asset);
		kpi.setSce1Count(severityCount.get("SCE1") != null ? severityCount.get("SCE1") : 0);
		kpi.setSce2Count(severityCount.get("SCE2") != null ? severityCount.get("SCE2") : 0);
		kpi.setSce3Count(severityCount.get("SCE3") != null ? severityCount.get("SCE3") : 0);
		kpi.setSceXCount(severityCount.get("SCEX") != null ? severityCount.get("SCEX") : 0);
		kpi.setBlockedCount(kpi.getSce1Count() + kpi.getSce2Count() + kpi.getSce3Count() + kpi.getSceXCount());
		kpi.setTotalCount(assetIntegrityDao.getCriticalSignalsTotalCount(asset));
		Map<String, Long> mocCount = assetIntegrityDao.getMocTotalCount(asset);
		kpi.setApprovedMocCount(mocCount.get("NOT NULL") != null ? mocCount.get("NOT NULL") : 0);
		kpi.setRequestedMocCount(mocCount.get("NULL") != null ? mocCount.get("NULL") : 0);
		kpi.setMocCount(kpi.getApprovedMocCount() + kpi.getRequestedMocCount());
		return kpi;
	}

	@Override
	public BacklogOperationalKpi getBacklogKpi(String asset) {
		BacklogOperationalKpi kpi = new BacklogOperationalKpi();
		// Get odm total count
		Long odmTotalCount = assetIntegrityDao.getBacklogODMCount(asset);
		Map<String, Long> severityCount = assetIntegrityDao.getBacklogTypeCount(asset);
		kpi.setSce1Count(severityCount.get("SCE TIPO 1") != null ? severityCount.get("SCE TIPO 1") : 0);
		kpi.setSce2Count(severityCount.get("SCE TIPO 2") != null ? severityCount.get("SCE TIPO 2") : 0);
		kpi.setSce3Count(severityCount.get("SCE TIPO 3") != null ? severityCount.get("SCE TIPO 3") : 0);
		kpi.setOtherCount(severityCount.get("OTHER") != null ? severityCount.get("OTHER") : 0);
		kpi.setOdmCount(kpi.getSce1Count() + kpi.getSce2Count() + kpi.getSce3Count() + kpi.getOtherCount());
		// Get total count
		// kpi.setOdmTotalCount(odmTotalCount + kpi.getOdmCount());
		kpi.setOdmTotalCount(odmTotalCount);

		// Get last insertionDate
		String lastInsertionDate = assetIntegrityDao.getLastInsertionDate(asset);
		kpi.setFromDate(lastInsertionDate);
		return kpi;
	}

	@Override
	public AssetTable<CorrosionBacteriaDto> getCorrosionBacteria(String asset, String area, String parentArea) {
		List<CorrosionBacteria> list = assetIntegrityDao.getCorrosionBacteria(asset, area, parentArea);
		List<CorrosionBacteriaDto> output = new LinkedList();
		AssetTable<CorrosionBacteriaDto> table = new AssetTable<>();
		table.setData(output);
		int critical = 0;
		if (list != null && !list.isEmpty()) {
			for (CorrosionBacteria b : list) {
				CorrosionBacteriaDto dto = new CorrosionBacteriaDto(b);
				int toAdd = 0;
				if (b.getSrbValue() != null) {
					if (b.getSrbValue() > CORROSION_BACTERIA_THRESHOLD) {
						toAdd = 1;
						dto.setSrbStatus(3);
					} else {
						dto.setSrbStatus(1);
					}
				}
				if (b.getApbValue() != null) {
					if (b.getApbValue() > CORROSION_BACTERIA_THRESHOLD) {
						toAdd = 1;
						dto.setApbStatus(3);
					} else {
						dto.setApbStatus(1);
					}
				}
				output.add(dto);
				critical += toAdd;
			}
		}
		table.setCritical(critical);
		return table;
	}

	@Override
	public CorrosionCNDTable getCorrosionCNDCount(String asset, String area, String year, String parentArea) {
		List<CorrosionCNDCount> data = new LinkedList<>();
		Map<String, Long[]> map = assetIntegrityDao.getCorrosionCND(asset, area, year, parentArea);

		// INSERT NULL VALUE IF REQUIRED MODEL ARE NOT PRESENT
		map.put("EQUIPMENT", map.get("EQUIPMENT"));
		map.put("VALVE", map.get("VALVE"));
		map.put("LINE", map.get("LINE"));

		Integer[] totalCount = { 0, 0 };

		for (Entry<String, Long[]> entry : map.entrySet()) {
			CorrosionCNDCount c = new CorrosionCNDCount();
			c.setModelName(entry.getKey());
			Long[] count = entry.getValue();
			if (count != null) {
				c.setExpiringCount(count[0] != null ? count[0].intValue() : 0);
				c.setExpiredCount(count[1] != null ? count[1].intValue() : 0);
			} else {
				c.setExpiringCount(0);
				c.setExpiredCount(0);
			}
			data.add(c);
			totalCount[0] += c.getExpiringCount();
			totalCount[1] += c.getExpiredCount();
		}

		// ADD total count for the year
		CorrosionCNDCount c = new CorrosionCNDCount();
		c.setModelName("TOTAL");
		c.setExpiringCount(totalCount[0]);
		c.setExpiredCount(totalCount[1]);
		data.add(0, c);

		Long count = assetIntegrityDao.getCriticalCountCorrosionCND(asset, area, parentArea);
		CorrosionCNDTable table = new CorrosionCNDTable();
		table.setCritical(count != null ? count.intValue() : 0);
		table.setData(data);
		String[] years = assetIntegrityDao.getYearsCorrosionCND(asset, area, parentArea);
		table.setMaxYear(years[0]);
		table.setMinYear(years[1]);
		return table;
	}

	@Override
	public List<CorrosionCNDElement> getCorrosionCND(String asset, String element, String year, String area,
			String parentArea) {
		List<CorrosionCND> elements = assetIntegrityDao.getCorrosionCNDElements(asset, element, year, area, parentArea);
		List<CorrosionCNDElement> resultList = new LinkedList();
		for (CorrosionCND cndElement : elements) {
			CorrosionCNDElement result = new CorrosionCNDElement();
			result.setComponentName(cndElement.getComponentName());
			result.setFrequency(cndElement.getFrequency());
			result.setLastDate(cndElement.getLastDate());
			result.setDateType(cndElement.getDateType());
			result.setNextDate(cndElement.getNextDate());
			int status = 0;
			if (cndElement.getNextDate() != null) {
				LocalDate now = LocalDate.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate nextDate = cndElement.getNextDate();
				if (nextDate.isBefore(now)) {
					status = 1;
				} else {
					status = 2;
				}
			}
			result.setStatus(status);
			resultList.add(result);
		}
		return resultList;
	}

	@Override
	public CorrosionCouponTable getCorrosionCoupon(String asset, String area, String parentArea) {
		List<CorrosionCoupon> elements = assetIntegrityDao.getCorrosionCoupon(asset, area, parentArea);
		List<CorrosionCouponDto> output = new LinkedList();
		CorrosionCouponTable table = new CorrosionCouponTable();
		int critical = 0;
		int high = 0;
		if (elements != null && !elements.isEmpty()) {
			for (CorrosionCoupon cc : elements) {
				CorrosionCouponDto dto = new CorrosionCouponDto(cc);
				if (cc.getMpy() != null && cc.getMpy() >= CORROSION_COUPON_THRESHOLD) {
					critical++;
					dto.setStatus(3);
				} else if (cc.getNextDate() != null && cc.getNextDate().isBefore(LocalDate.now())) {
					high++;
					dto.setStatus(2);
				} else {
					dto.setStatus(1);
				}
				output.add(dto);
			}
		}
		table.setData(output);
		table.setCritical(critical);
		table.setHigh(high);
		String[] years = assetIntegrityDao.getYearsCorrosionCoupon(asset, area, parentArea);
		table.setMaxYear(years[0]);
		table.setMinYear(years[1]);
		return table;
	}

	@Override
	public AssetTable<CorrosionPiggingDto> getCorrosionPigging(String asset, String area, String parentArea) {
		List<CorrosionPigging> elements = assetIntegrityDao.getCorrosionPigging(asset, area, parentArea);
		List<CorrosionPiggingDto> output = new LinkedList();
		AssetTable<CorrosionPiggingDto> table = new AssetTable<>();
		int critical = 0;
		if (elements != null && !elements.isEmpty()) {
			for (CorrosionPigging cp : elements) {
				CorrosionPiggingDto dto = new CorrosionPiggingDto(cp);
				if (cp.getErf() != null && cp.getErf() >= CORROSION_PIGGING_THRESHOLD) {
					critical++;
					dto.setStatus(3);
				} else {
					dto.setStatus(1);
				}
				output.add(dto);
			}
		}
		table.setData(output);
		table.setCritical(critical);
		return table;
	}

	@Override
	public AssetTable<CorrosionProtectionDto> getCorrosionProtection(String asset, String area, String parentArea) {
		List<CorrosionProtection> elements = assetIntegrityDao.getCorrosionProtection(asset, area, parentArea);
		List<CorrosionProtectionDto> output = new LinkedList();
		AssetTable<CorrosionProtectionDto> table = new AssetTable<>();
		int critical = 0;
		if (elements != null && !elements.isEmpty()) {
			for (CorrosionProtection cp : elements) {
				CorrosionProtectionDto dto = new CorrosionProtectionDto(cp);
				if (cp.getExternalConduitOff() != null
						&& cp.getExternalConduitOff() >= CORROSION_PROTECTION_THRESHOLD) {
					critical++;
					dto.setStatus(3);
				} else if (cp.getNotProtectedSideOff() != null
						&& cp.getNotProtectedSideOff() >= CORROSION_PROTECTION_THRESHOLD) {
					critical++;
					dto.setStatus(3);
				} else if (cp.getProtectedSideOff() != null
						&& cp.getProtectedSideOff() >= CORROSION_PROTECTION_THRESHOLD) {
					critical++;
					dto.setStatus(3);
				} else if (cp.getTuebOff() != null && cp.getTuebOff() >= CORROSION_PROTECTION_THRESHOLD) {
					critical++;
					dto.setStatus(3);
				} else {
					dto.setStatus(1);
				}
				output.add(dto);
			}
		}
		table.setData(output);
		table.setCritical(critical);
		return table;
	}

	@Override
	public List<CorrosionCouponDto> getCorrosionCouponElements(String asset, String code, String year, String area,
			String parentArea) {
		List<CorrosionCoupon> elements = assetIntegrityDao.getCorrosionCoupoElements(asset, code, year, area,
				parentArea);
		List<CorrosionCouponDto> output = new LinkedList();
		if (elements != null && !elements.isEmpty()) {
			for (CorrosionCoupon cc : elements) {
				CorrosionCouponDto dto = new CorrosionCouponDto(cc);
				if (cc.getMpy() != null && cc.getMpy() >= CORROSION_COUPON_THRESHOLD) {
					dto.setStatus(3);
				} else if (cc.getNextDate() != null && cc.getNextDate().isBefore(LocalDate.now())) {
					dto.setStatus(2);
				} else {
					dto.setStatus(1);
				}
				output.add(dto);
			}
		}
		return output;
	}

	@Override
	public AssetTable<EVPMSData> getEVPMSData(String asset, String area, String parentArea) {
		List<EVPMSData> values = assetIntegrityDao.getEVPMSData(asset, area, parentArea);
		AssetTable table = new AssetTable();
		table.setData(values);
		int critical = 0;
		int high = 0;
		for (EVPMSData data : values) {
			if ("STATION".equals(data.getType()) && !data.getDiagnostic()) {
				high++;
			} else if ("ALERT".equals(data.getType())) {
				critical++;
			}
		}
		table.setCritical(critical);
		table.setHigh(high);
		return table;
	}

	@Override
	public AssetTable<JacketedPipes> getJacketedPipes(String asset, String area, String parentArea) {
		List<JacketedPipes> values = assetIntegrityDao.getJacketedPipes(asset, area, parentArea);
		AssetTable<JacketedPipes> table = new AssetTable();
		table.setData(values);
		int critical = 0;
		int high = 0;
		for (JacketedPipes data : values) {
			if (data.getBadActive() != null && data.getBadActive() == 1) {
				high++;
				continue;
			}

			if (data.getAlarm() != null && data.getAlarm() == 0) {
				high++;
				continue;
			}

			if (data.getAlarm() != null && data.getAlarm() == 1) {
				critical++;
			}

		}
		table.setCritical(critical);
		table.setHigh(high);
		return table;

	}

	@Override
	public String parentArea(String subArea, String asset) {
		return assetIntegrityDao.getParentArea(subArea, asset);
	}

	@Override
	public List<RegistroMocDto> getRegistroMoc(MocRegistryInput data) {
		return convertToRegistroDto(assetIntegrityDao.getRegistroMoc(data));
	}

	private List<RegistroMocDto> convertToRegistroDto(List<RegistroMoc> lista) {
		List<RegistroMocDto> res = new ArrayList<>();

		if (lista != null && !lista.isEmpty()) {
			for (RegistroMoc rMoc : lista) {
				res.add(new RegistroMocDto(rMoc));
			}
		}

		return res;
	}

	@Override
	public List<RegistroDeDto> getRegistroDE(DERegistryFilter data, String asset) {
		return convertToRegistroDeDto(assetIntegrityDao.getRegistroDE(data, asset));
	}

	private List<RegistroDeDto> convertToRegistroDeDto(List<RegistroDe> lista) {
		List<RegistroDeDto> res = new ArrayList<>();

		if (lista != null && !lista.isEmpty()) {
			for (RegistroDe rMoc : lista) {
				res.add(new RegistroDeDto(rMoc));
			}
		}

		return res;
	}

	@Override
	public ByteArrayOutputStream generateMOCRegistryExcel(MocRegistryInput data, String asset) {
		List<RegistroMoc> registro = assetIntegrityDao.getRegistroMoc(data);
		return excelService.generateExcel("Registro Moc", RegistroMoc.excelOrder, asset, RegistroMoc.class, registro,
				RegistroMoc.excelHeader);
	}

	@Override
	public ByteArrayOutputStream generateDERegistryExcel(DERegistryFilter filter, String asset) {
		List<RegistroDe> registro = assetIntegrityDao.getRegistroDE(filter, asset);
		return excelService.generateExcel("Registro DE", RegistroDe.excelOrder, asset, RegistroDe.class, registro);
	}

	@Override
	public TemplateFile getTemplate(String code, String asset) throws IOException {

		List<TemplateFile> file = templateFileRepo.findByCode(code);
		if (file != null && !file.isEmpty()) {
			return assetIntegrityExcelService.populateTemplate(file.get(0), this.showDetails(code, asset).getList());
		}
		return null;
	}

	@Override
	public DetailListDto showDetailsByDate(String table, String asset, String from, String to) throws ParseException {
		DetailListDto res = new DetailListDto();
		AssetIntegrityTable switchCase = AssetIntegrityTable.valueOf(table.toUpperCase());

		switch (switchCase) {
		case NETWORK:
			res = assetIntegrityDao.showDetailsNetworkByDate(table, asset, from, to);
			break;
		case OIL:
			res = assetIntegrityDao.showDetailsOilByDate(table, asset, from, to);
			break;
		case PLANT:
			res = assetIntegrityDao.showDetailsPlantByDate(table, asset, from, to);
			break;
		case RESERVOIR:
			res = assetIntegrityDao.showDetailsReservoirByDate(table, asset, from, to);
			break;
		default:
			logger.warn(String.format("unknown enum value: %s", table));
			break;
		}

		return res;
	}
}
