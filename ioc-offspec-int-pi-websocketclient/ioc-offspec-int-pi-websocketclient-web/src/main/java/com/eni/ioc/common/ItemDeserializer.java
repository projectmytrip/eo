package com.eni.ioc.common;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eni.ioc.pi.rest.response.Item;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class ItemDeserializer extends StdDeserializer<Item> { 

	private static final Logger	logger	= LoggerFactory.getLogger(ItemDeserializer.class);

    /**
	 * 
	 */
	private static final long serialVersionUID = 1254624830038267597L;

	public ItemDeserializer() { 
        this(null); 
    } 
 
    public ItemDeserializer(Class<?> vc) { 
        super(vc); 
    }
 
    @Override
    public Item deserialize(JsonParser jp, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        
	    String timestamp = node.get("Timestamp").asText();
	    boolean good = node.get("Good").asBoolean();
	    double value = 0;
	    if (good) {
	    	value = node.get("Value").asDouble();
	    } else {
	    	value = node.get("Value").get("Value").asDouble();
	    	logger.debug("Il valore "+value+" in Value non è di tipo double; il flag good è a "+good);
	    }
	    String unitsAbbreviation = node.get("UnitsAbbreviation").asText();
	    boolean questionable = node.get("Questionable").asBoolean();
	    boolean substituted = node.get("Substituted").asBoolean();

        return new Item(
        		timestamp,
        		value,
        		unitsAbbreviation,
        		good,
        		questionable,
        		substituted
        		);
 
    }
}