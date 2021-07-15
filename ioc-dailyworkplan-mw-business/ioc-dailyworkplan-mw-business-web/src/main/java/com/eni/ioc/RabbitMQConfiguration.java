package com.eni.ioc;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfiguration {

	@Value("${spring.rabbitmq.queue}")
	private String queueName;

	@Value("${spring.rabbitmq.exchange}")
	private String directExchangeName;

	@Value("${spring.rabbitmq.binding}")
	private String bindingName;

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
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		return rabbitTemplate;
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
		return new Queue(queueName, true);
	}

	@Bean
	Binding bindingActual(Queue queue, DirectExchange direct) {
		return BindingBuilder.bind(queue).to(direct).with(bindingName);
	}
}