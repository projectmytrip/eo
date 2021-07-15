package com.eni.ioc.common;

import com.eni.ioc.pi.rest.response.CorrosionCNDItem;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CorrosionCNDDeserializer extends StdDeserializer<CorrosionCNDItem> {

    private static final String SIGLA_IMPIANTO = "ASIGLA_IMPIANTO";
    private static final String AREA_IMPIANTO = "ASEZ_IMP";
    private static final String COMPONENT_NAME = "ANOME";
    private static final String LAST_DATE_KEY = "DSP_DATAULT_";
    private static final String FREQUENCY_KEY = "NSP_FREQ_";
    private static final String NEXT_DATE_KEY = "DSP_DATAPROSS_";

    private List<String[]> keyList;
    private String modelName;
    
    public CorrosionCNDDeserializer(String modelName, String[]... keyList) {
        this(null);
        this.modelName = modelName;
        this.keyList = Arrays.asList(keyList);
    }

    public CorrosionCNDDeserializer() {
        this(null);
    }

    public CorrosionCNDDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CorrosionCNDItem deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        CorrosionCNDItem item = new CorrosionCNDItem();
        item.setModelName(modelName);
        
        item.setPlantAcronym(node.get(SIGLA_IMPIANTO) != null && !node.get(SIGLA_IMPIANTO).isNull() 
                ? node.get(SIGLA_IMPIANTO).asText() : null);
        
        item.setComponentName(node.get(COMPONENT_NAME) != null  && !node.get(COMPONENT_NAME).isNull() 
                ? node.get(COMPONENT_NAME).asText() : null);
      
        item.setArea(node.get(AREA_IMPIANTO) != null  && !node.get(AREA_IMPIANTO).isNull() 
                ? node.get(AREA_IMPIANTO).asText() : null);

        for (String[] key : keyList) {
            String keyValue = key[0];
            String keyDescription = key[1];
            if (node.has(LAST_DATE_KEY + keyValue) && !node.get(LAST_DATE_KEY + keyValue).isNull() ) {
                item.setLastDate(node.get(LAST_DATE_KEY + keyValue).asText());
                item.setDateType(keyDescription);
            }
            if (node.has(NEXT_DATE_KEY + keyValue) && !node.get(NEXT_DATE_KEY + keyValue).isNull()) {
                item.setNextDate(node.get(NEXT_DATE_KEY + keyValue).asText());
                item.setDateType(keyDescription);
            }
            if (node.has(FREQUENCY_KEY + keyValue) && !node.get(FREQUENCY_KEY + keyValue).isNull()) {
                item.setFrequency(node.get(FREQUENCY_KEY + keyValue).asText());
                item.setDateType(keyDescription);
            }
            //Assume only one "date" per node
            if (item.getDateType() != null) {
                break;
            }
        }

        return item;

    }

}
