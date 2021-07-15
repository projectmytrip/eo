package com.eni.ioc.common;

import com.eni.ioc.pi.rest.response.Item;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemDeserializer extends StdDeserializer<Item> { 

	private static final Logger	logger	= LoggerFactory.getLogger(ItemDeserializer.class);

    /**
	 * 
	 */
	private static final long serialVersionUID = 1254624830038267597L;
	
	private static final String VALUE = "Value";
	private static final String NAME = "Name";
	private static final String GOOD = "Good";
	private static final String TIMESTAMP = "Timestamp";
        

	public ItemDeserializer() { 
        this(null); 
    } 
 
    public ItemDeserializer(Class<?> vc) { 
        super(vc); 
    }
 
    @Override
    public Item deserialize(JsonParser jp, DeserializationContext ctxt) 
      throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        
        String name = node.get(NAME).asText();
        JsonNode valueNode = node.get(VALUE);
	    boolean good = valueNode.get(GOOD).asBoolean();
	    String value = "";
            String timestamp = null;
            timestamp = valueNode.get(TIMESTAMP).asText(); 
            if(valueNode.get(VALUE).isValueNode()){
	    	value = valueNode.get(VALUE).asText();	    	
            } else {
	    	value = valueNode.get(VALUE).get(VALUE).asText();
	    }
            
        return new Item(name,value,good, timestamp);
 
    }
}