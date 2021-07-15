package com.eni.ioc.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eni.ioc.properties.util.ApplicationProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Service
public class Sender {

	private static final Logger logger = LoggerFactory.getLogger(Sender.class);

	private static final String EMISSION = "Emission";
	private static final String FLARING = "Flaring";
	private static final String JOB_SERVER_MINUTE = "JobServerMinute";
	private static final String JOB_SERVER_HOUR = "JobServerHour";
	private static final String JOB_SERVER_DAY = "JobServerDay";

	private static Set<String> sistemDown = new HashSet<String>();

	private static Boolean isUp = false;	
	   
   private static Channel channel;
   private static Connection connection;
	   
	public Sender() {
		Sender.sistemDown.add(EMISSION);
		Sender.sistemDown.add(FLARING);
		//Sender.sistemDown.add(JOB_SERVER_MINUTE);
		//Sender.sistemDown.add(JOB_SERVER_HOUR);
		//Sender.sistemDown.add(JOB_SERVER_DAY);
	}

	public static void sendSystemDown(String type) throws IOException, TimeoutException {
		if(type.equalsIgnoreCase(EMISSION) || type.equalsIgnoreCase(FLARING)) {
			configureRabbitMQ();
	
			sistemDown.add(type);
			isUp = false;
	
			VariazioneStato message = new VariazioneStato("System down", LocalDateTime.now());
			
			channel.basicPublish(ApplicationProperties.getInstance().getSpringRabbitExchange(), 
					ApplicationProperties.getInstance().getSpringRabbitBinding(), null, message.toJson().getBytes());
					
		    logger.info(" [x] Sent message to " + ApplicationProperties.getInstance().getSpringRabbitExchange() + " exchange with " + ApplicationProperties.getInstance().getSpringRabbitBinding() + " routing key "+ message.toJson());
			
			channel.close();
			connection.close();
		}
	}

	public static void sendSystemUp(String type) throws IOException, TimeoutException {
		sistemDown.remove(type);
		logger.debug(sistemDown.toString());

		if (sistemDown.isEmpty()) {
			if (!isUp) {

				isUp = true;
				VariazioneStato message = new VariazioneStato("System up", LocalDateTime.now());
				configureRabbitMQ();
				
				channel.basicPublish(ApplicationProperties.getInstance().getSpringRabbitExchange(), 
						ApplicationProperties.getInstance().getSpringRabbitBinding(), null, message.toJson().getBytes());
						
			    logger.info(" [x] Sent message to " + ApplicationProperties.getInstance().getSpringRabbitExchange() + " exchange with " + ApplicationProperties.getInstance().getSpringRabbitBinding() + " routing key "+ message.toJson());
				
				channel.close();
				connection.close();
			}
		}
	}
	
	public static void configureRabbitMQ() throws IOException, TimeoutException {
		   
	    ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setUsername(ApplicationProperties.getInstance().getSpringRabbitUsername());
		connectionFactory.setPassword(ApplicationProperties.getInstance().getSpringRabbitPassword());
		connectionFactory.setHost(ApplicationProperties.getInstance().getSpringRabbitHost());
		connectionFactory.setPort(ApplicationProperties.getInstance().getSpringRabbitPort());
		connectionFactory.setVirtualHost(ApplicationProperties.getInstance().getSpringRabbitVhost());    	
		connection = connectionFactory.newConnection();
		channel = connection.createChannel();
	   
	    //creo l'exchange di tipo direct
		channel.exchangeDeclare(ApplicationProperties.getInstance().getSpringRabbitExchange(), "direct", true);
		channel.queueDeclare(ApplicationProperties.getInstance().getSpringRabbitQueue(), true, false, false, null);

		//creo un binding fra l'exchange e la coda, con routing-key offSpec
		channel.queueBind(ApplicationProperties.getInstance().getSpringRabbitQueue(), 
				ApplicationProperties.getInstance().getSpringRabbitExchange(),
				ApplicationProperties.getInstance().getSpringRabbitBinding());
		
   }

	public static Set<String> getSistemDown() {
		return sistemDown;
	}

	public static Boolean isSystemUp() {
		return isUp;
	}

}