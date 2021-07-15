package com.eni.ioc.assetintegrity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eni.ioc.assetintegrity.api.CreaManualWorkflowInput;
import com.eni.ioc.assetintegrity.dto.NotificationInput;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class Sender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private DirectExchange direct;

	@Autowired
	private Binding bindingActual;

	@Autowired
	private Binding bindingMessage;

	private static final Logger logger = LoggerFactory.getLogger(Sender.class);

	public void sendToRabbitMQActual(CreaManualWorkflowInput creaWorkflowInput) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)
				.registerModule(new JavaTimeModule())
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		String message = mapper.writeValueAsString(creaWorkflowInput);

		rabbitTemplate.convertAndSend(direct.getName(), bindingActual.getRoutingKey(), message);

       //System.out.println(" [x] Sent message to " + direct.getName() + " exchange with " + bindingActual
		// .getRoutingKey() + " routing key "+ message);
       logger.info(" [x] Sent message to {} exchange with {} routing key {}", direct.getName(), bindingActual.getRoutingKey(), message);
	}

	public void sendToRabbitMQMessage(NotificationInput notification) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)
				.registerModule(new JavaTimeModule())
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		String message = mapper.writeValueAsString(notification);

		rabbitTemplate.convertAndSend(direct.getName(), bindingMessage.getRoutingKey(), message);

		logger.info(" [x] Sent message to " + direct.getName() + " exchange with " + bindingMessage.getRoutingKey() +
				" routing key " + message);

	}
}