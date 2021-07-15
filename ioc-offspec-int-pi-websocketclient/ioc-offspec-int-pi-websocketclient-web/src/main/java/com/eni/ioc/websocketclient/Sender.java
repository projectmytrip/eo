package com.eni.ioc.websocketclient;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class Sender {
	
   private static final Logger logger = LoggerFactory.getLogger(Sender.class);
	  
   @Autowired
   private RabbitTemplate rabbitTemplate;

   @Autowired
   private DirectExchange direct;
   
   @Autowired
   private Binding binding;
   
   public void sendToRabbitMQ(String status) throws JsonProcessingException {
	   
	   VariazioneStato message = new VariazioneStato(status, LocalDateTime.now());
	   
	   rabbitTemplate.convertAndSend(direct.getName(), binding.getRoutingKey(), message.toJson());

	   logger.info(" [x] Sent message to " + direct.getName() + " exchange with " + binding.getRoutingKey() + " routing key "+ message.toJson());
	   
   }
}