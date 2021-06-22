package com.eni.enhop.be.shifthandoverlogbook.service.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.eni.enhop.be.common.core.bean.PrintableBean;
import com.eni.enhop.be.shifthandoverlogbook.model.LogbookPage;
import com.eni.enhop.be.shifthandoverlogbook.model.LogbookTranslation;
import com.eni.enhop.be.shifthandoverlogbook.model.Section;
import com.eni.enhop.be.shifthandoverlogbook.model.SectionField;
import com.eni.enhop.be.shifthandoverlogbook.model.SquadMember;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LogbookPdfRequestDTO extends PrintableBean {
	private static final long serialVersionUID = 5994350498526998078L;

	private static final String BREAK = "<br/>";

	@JsonProperty("meta_ids")
	private List<MetaDTO> metaIds;

	@JsonProperty("id_template")
	private String templateId;

	@JsonProperty("description")
	private String description;

	@JsonProperty("description_squad")
	private String descriptionSquad;

	@JsonProperty("date")
	private String date;

	@JsonProperty("site")
	private String site;

	@JsonProperty("workshift")
	private String workshift;

	@JsonProperty("name_creator")
	private String nameCreator;

	@JsonProperty("type")
	private String type;

	@JsonProperty("note")
	private String note;

	@JsonProperty("state")
	private String state;

	public LogbookPdfRequestDTO() {
		//
	}

	public LogbookPdfRequestDTO(LogbookPage logbookPage, List<LogbookTranslation> translations) {
		if (logbookPage != null) {
			this.type = translations.stream().filter(t -> logbookPage.getLogbookType().equalsIgnoreCase(t.getCode()))
					.map(t -> t.getTranslation()).findAny().orElse(logbookPage.getLogbookType());
			this.nameCreator = logbookPage.getCreatorDisplayName();
			this.date = DateTimeFormatter.ofPattern("dd/MM/uuuu").format(logbookPage.getLogbookDate());
			this.workshift = logbookPage.getWorkShift() != null ? logbookPage.getWorkShift().getName() : "";
			this.state = logbookPage.getState() != null ? logbookPage.getState().name() : "";
			this.description = getDescription(logbookPage);

			this.descriptionSquad = getPlantSection(logbookPage, translations);
			if (logbookPage.getSquad() != null && !logbookPage.getSquad().isEmpty()) {
				this.descriptionSquad += getSquad(logbookPage);
			}

			this.site = logbookPage.getLocationId() + "";

			this.note = logbookPage.getNote() != null ? logbookPage.getNote() : "";

		}
	}

	private String getPlantSection(LogbookPage logbookPage, List<LogbookTranslation> translations) {
		if (logbookPage.getPlantSection() == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		String plantSectionLabel = translations.stream().filter(t -> "PLANT_SECTION".equalsIgnoreCase(t.getCode()))
				.map(t -> t.getTranslation()).findAny().orElse("Plant Section");
		String plantSectionName = logbookPage.getPlantSection().getName();

		sb.append("<b style=\"color: rgb(84,130,53)\">").append(plantSectionLabel).append(":</b> ")
				.append(plantSectionName);
		sb.append(BREAK);

		return sb.toString();

	}

	private String getSquad(LogbookPage logbookPage) {
		StringBuilder sb2 = new StringBuilder();
		Map<String, List<SquadMember>> squadMap = generateSquadMap(logbookPage.getSquad());

		for (Map.Entry<String, List<SquadMember>> sq : squadMap.entrySet()) {
			if (sq.getValue() != null && !sq.getValue().isEmpty()) {
				String role = sq.getKey();
				String name = null;
				StringBuilder sb3 = new StringBuilder();
				List<SquadMember> smList = sq.getValue();
				int i = 0;
				for (SquadMember sm : smList) {
					if (sm.getOperator() != null) {
						sb3.append(valueIfNotNull(sm.getOperator().getName())).append(" ")
								.append(valueIfNotNull(sm.getOperator().getSurname()));
					}
					if (i < (smList.size() - 1)) {
						sb3.append(", ");
					}
					i++;
				}
				name = sb3.toString();
				sb2.append("<b style=\"color: rgb(84,130,53)\">").append(valueIfNotNull(role)).append(":</b> ")
						.append(name);
				sb2.append(BREAK);
			}

		}
		return sb2.toString();
	}

	private String getDescription(LogbookPage logbookPage) {
		StringBuilder sb = new StringBuilder();
		if (logbookPage.getSections() != null && !logbookPage.getSections().isEmpty()) {
			List<Section> orderedSet = logbookPage.getSections().stream().sorted((Section s1, Section s2) -> {
				if (s1 == null || s1.getOrder() == null) {
					return -1;
				}
				if (s2 == null || s2.getOrder() == null) {
					return 1;
				}
				return s1.getOrder().compareTo(s2.getOrder());
			}).collect(Collectors.toList());
			for (Section s : orderedSet) {
				if (s.getFields() != null && !s.getFields().isEmpty()) {
					sb.append("<div style=\"margin-bottom: 0.1cm\">");
					sb.append("<b style=\"color: red\">").append(valueIfNotNull(s.getName())).append(":</b></div>");
					List<SectionField> orderedFieldSet = s.getFields().stream()
							.sorted((SectionField s1, SectionField s2) -> {
								if (s1 == null || s1.getOrder() == null) {
									return -1;
								}
								if (s2 == null || s2.getOrder() == null) {
									return 1;
								}
								return s1.getOrder().compareTo(s2.getOrder());
							}).collect(Collectors.toList());

					sb.append("<div style=\" font-size: 45px;\">");
					for (SectionField sf : orderedFieldSet) {
						if (StringUtils.isNoneBlank(sf.getName())) {
							sb.append("<b style=\"color: blue\">").append(valueIfNotNull(sf.getName()))
									.append("</b>: ");
						}
						sb.append("<div style=\"margin-left:0.4cm\">").append(valueIfNotNull(sf.getValue()))
								.append("</div>");
					}
					sb.append("</div>");
				}
			}
			sb.append(BREAK);
		}
		String res = sb.toString();
		System.out.println(res);
		return res;
	}

	private String valueIfNotNull(String s) {
		return s != null ? s : "";
	}

	private Map<String, List<SquadMember>> generateSquadMap(Set<SquadMember> squad) {
		return squad.stream()
				.sorted((m1, m2) -> Integer.compare(m1.getRole().getPosition(), m2.getRole().getPosition()))
				.collect(Collectors.groupingBy(m -> m.getRole().getName(), LinkedHashMap::new, Collectors.toList()));
	}

	public List<MetaDTO> getMetaIds() {
		if (metaIds == null)
			metaIds = new ArrayList<>();
		return metaIds;
	}

	public void setMetaIds(List<MetaDTO> metaIds) {
		this.metaIds = metaIds;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getWorkshift() {
		return workshift;
	}

	public void setWorkshift(String workshift) {
		this.workshift = workshift;
	}

	public String getNameCreator() {
		return nameCreator;
	}

	public void setNameCreator(String nameCreator) {
		this.nameCreator = nameCreator;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDescriptionSquad() {
		return descriptionSquad;
	}

	public void setDescriptionSquad(String descriptionSquad) {
		this.descriptionSquad = descriptionSquad;
	}

}
