package com.eni.ioc.be.email;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eni.ioc.be.email.service.Receiver;

@Configuration
@EnableRabbit
public class RabbitMQConfiguration {

	@Value("${spring.rabbitmq.queue}")
	private String queue;

	@Value("${spring.rabbitmq.exchange}")
	private String directExchangeName;

	@Value("${spring.rabbitmq.host}")
	private String host;

	@Value("${spring.rabbitmq.username}")
	private String username;

	@Value("${spring.rabbitmq.password}")
	private String password;

	@Value("${spring.rabbitmq.port}")
	private int port;

	@Value("${spring.rabbitmq.virtual-host}")
	private String virtualHost;

	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}

	@Bean
	ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setHost(host);
		connectionFactory.setPort(port);
		connectionFactory.setVirtualHost(virtualHost);

		return connectionFactory;

	}

	@Bean
	DirectExchange direct() {
		return new DirectExchange(directExchangeName, true, false);
	}

	@Bean
	Queue queue() {
		return new Queue(queue, true);
	}

	 @Bean 
	 SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		 SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		 container.setConnectionFactory(connectionFactory);
		 container.setQueueNames(queue);
		 container.setDeclarationRetries(50);
		 container.setFailedDeclarationRetryInterval(60000);
		 container.setMessageListener(listenerAdapter); 
		 return container; 
	 }
  
	  @Bean 
	  MessageListenerAdapter listenerAdapter(Receiver receiver) { 
		  return new MessageListenerAdapter(receiver, "receiveMessage");
	  }
	 
}