package com.eni.ioc.be.email.service;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eni.ioc.be.email.controller.EmailController;
import com.eni.ioc.be.email.dto.input.Notification;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class Receiver {

	private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

	private CountDownLatch latch = new CountDownLatch(1);

	@Autowired
	Queue queue;

	@Autowired
	EmailController controller;

    @RabbitListener(queues = "#{queue.name}" )
    public void receiveMessage(String body) throws IOException {
    	logger.info(" [*] Waiting for messages.");
    	
    	logger.info(" [x] Received message from " + queue.getName() + " queue " + body);
    	
    	Notification message = new ObjectMapper()
    			.enable(SerializationFeature.INDENT_OUTPUT)
	    		.registerModule(new JavaTimeModule())
	    		.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    			.readerFor(Notification.class)
    			.readValue(body);
    	
    	try {
    		controller.sendEmail(message);
    	} catch (Exception e) {
    		logger.error("",e);
    	}
    	
        latch.countDown();
    }

	public CountDownLatch getLatch() {
		return latch;
	}

}
