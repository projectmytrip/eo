package com.eni.ioc.assetintegrity.service.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eni.ioc.assetintegrity.service.sender.dto.NotificationServiceDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class Sender {

	private static final Logger	logger	= LoggerFactory.getLogger(Sender.class);

   @Autowired
   private RabbitTemplate rabbitTemplate;

   @Autowired
   private DirectExchange direct;
   
   @Autowired
   private Binding binding;
    
   public void sendToRabbitMQ(NotificationServiceDto notification) throws JsonProcessingException {
	   
	   ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)
				.registerModule(new JavaTimeModule())
	    		.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	   String message = mapper.writeValueAsString(notification);
	   
	   rabbitTemplate.convertAndSend(direct.getName(), binding.getRoutingKey(), message);

       logger.debug(" [x] Sent message to " + direct.getName() + " exchange with " + binding.getRoutingKey() + " routing key "+ message);
	   
   }
   
   
}