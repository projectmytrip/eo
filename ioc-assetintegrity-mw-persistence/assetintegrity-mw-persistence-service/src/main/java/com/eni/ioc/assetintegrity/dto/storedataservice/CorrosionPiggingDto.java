package com.eni.ioc.assetintegrity.dto.storedataservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CorrosionPiggingDto {

    private String model;
    private String technicalSite;
    private String description;
    private String nextDate;
    private String lastDate;
    private String section;
    private String dorsal;
    private List<Feature> features;

    public String getNextDate() {
        return nextDate;
    }

    public void setNextDate(String nextDate) {
        this.nextDate = nextDate;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDorsal() {
        return dorsal;
    }

    public void setDorsal(String dorsal) {
        this.dorsal = dorsal;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTechnicalSite() {
        return technicalSite;
    }

    public void setTechnicalSite(String technicalSite) {
        this.technicalSite = technicalSite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    

}
