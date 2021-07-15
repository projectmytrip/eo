package com.eni.ioc.pi.rest.response;

public class CorrosionCNDModel {

    private String componentType;
    private String modelType;
    private String modelId;
    private String modelName;
    String[][] keyList;

    /**
     * Parametri per effetuare la chiamata e mappare i valori ricevuti dal PI
     * riguardo lo scadenziaro.
     *
     * @param componentType - parametro uri della chiamata
     * @param modelType - parametro uri della chamata
     * @param modelId - parametro uri della chiamata
     * @param modelName - Tipologia del modello utilizzata per indicare a FE il
     * nome del modello (usata per raggruppare i dati)
     * @param keyList - Coppia chiave-descrizione che indica il numero da
     * utilizzare per mappare i campi delle date. Ciascun campo ha una struttura
     * NOME_CAMPO_{key}. La descrizione è una costante che verrà mappata ad una
     * specifica string a FE.
     */
    public CorrosionCNDModel(String componentType, String modelType, String modelId,
            String modelName, String[]... keyList) {
        this.componentType = componentType;
        this.modelType = modelType;
        this.modelId = modelId;
        this.modelName = modelName;
        this.keyList = keyList;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String[][] getKeyList() {
        return keyList;
    }

    public void setKeyList(String[][] keyList) {
        this.keyList = keyList;
    }

}
