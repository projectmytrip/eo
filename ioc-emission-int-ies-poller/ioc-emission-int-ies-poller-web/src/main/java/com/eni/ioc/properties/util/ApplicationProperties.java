package com.eni.ioc.properties.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationProperties {
	
	private static String SPRING_RABBIT_HOST;
	private static String SPRING_RABBIT_USERNAME;
	private static String SPRING_RABBIT_PASSWORD;
	private static int SPRING_RABBIT_PORT;
	private static String SPRING_RABBIT_VHOST;
	private static String SPRING_RABBIT_EXCHANGE;
	private static String SPRING_RABBIT_QUEUE;
	private static String SPRING_RABBIT_BINDING;

	private static ApplicationProperties singletonInstance;

	public static synchronized ApplicationProperties getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new ApplicationProperties();
		}
		return singletonInstance;
	}
	
	public static String getApiIesUsername() {
		return EnvironmentAwareImpl.getProperty("api.ies.username");
	}
	
	public static String getApiIesPassword() {
		return EnvironmentAwareImpl.getProperty("api.ies.password");
	}

	public static String getEmissionSite() {
		return EnvironmentAwareImpl.getProperty("ies.emissionsite");
	}
	
	public static int getEmissionHistoryParamter() {
		return Integer.parseInt(EnvironmentAwareImpl.getProperty("ies.emission.historyparameter"));
	}
	
	public static int getEmissionSO2HistoryParamter() {
		return Integer.parseInt(EnvironmentAwareImpl.getProperty("ies.emission.historyparameterSO2"));
	}
	
	public static String getJobEmissionSchedule() {
		return EnvironmentAwareImpl.getProperty("job.emission.schedule");
	}
	
	public static String getJobFlaringSchedule() {
		return EnvironmentAwareImpl.getProperty("job.flaring.schedule");
	}
	
	public static String getJobServerMinuteCrontab() {
		return EnvironmentAwareImpl.getProperty("job.server.minute.crontab");
	}
	
	public static String getJobServerHourCrontab() {
		return EnvironmentAwareImpl.getProperty("job.server.hour.crontab");
	}
	
	public static String getJobServerDayCrontab() {
		return EnvironmentAwareImpl.getProperty("job.server.day.crontab");
	}
	
	public static String getApiIesEmissionEndpoint() {
		return EnvironmentAwareImpl.getProperty("api.ies.emission.endpoint");
	}
	
	public static String getApiIesFlaringEndpoint() {
		return EnvironmentAwareImpl.getProperty("api.ies.flaring.endpoint");
	}
	
	public static String getApiIesThresholdsEndpoint() {
		return EnvironmentAwareImpl.getProperty("api.ies.thresholds.endpoint");
	}
	
	public static String getApiIesHistoryEndpoint() {
		return EnvironmentAwareImpl.getProperty("api.ies.history.endpoint");
	}
	
	public static String getApiIesServerMinuteEndpoint() {
		return EnvironmentAwareImpl.getProperty("api.ies.server.minute.endpoint");
	}
	
	public static String getApiIesServerHourEndpoint() {
		return EnvironmentAwareImpl.getProperty("api.ies.server.hour.endpoint");
	}
	
	public static String getApiIesServerDayEndpoint() {
		return EnvironmentAwareImpl.getProperty("api.ies.server.day.endpoint");
	}
	
	
	public static String getApiIesServerHistoryEndpoint() {
		return EnvironmentAwareImpl.getProperty("api.ies.server.history.endpoint");
	}
	
	public static String getEmissionPersistenceStoredataserviceEndpoint() {
		return EnvironmentAwareImpl.getProperty("emission.persistence.storedataservice.endpoint");
	}

	public static String getEmissionPersistenceStoredataserviceServerEndpoint() {
		return EnvironmentAwareImpl.getProperty("emission.persistence.storedataservice.server.endpoint");
	}
	
	public static int getEmissionPersistenceStoredataserviceTimeout() {
		return Integer.parseInt(EnvironmentAwareImpl.getProperty("emission.persistence.storedataservice.timeout"));
	}
	
	public static double getflaringClientAlertThreshold() {
		return Double.parseDouble(EnvironmentAwareImpl.getProperty("flaring.client.alert.threshold"));
	}
	
	public static double getflaringClientWarningThreshold() {
		return Double.parseDouble(EnvironmentAwareImpl.getProperty("flaring.client.warning.threshold"));
	}
	
	public String getSpringRabbitHost() {
		return SPRING_RABBIT_HOST;
	}
	
	@Value("${spring.rabbitmq.host}")
	public void setSpringRabbitHost(String sPRING_RABBIT_HOST) {
		SPRING_RABBIT_HOST = sPRING_RABBIT_HOST;
	}

	public String getSpringRabbitUsername() {
		return SPRING_RABBIT_USERNAME;
	}
	
	@Value("${spring.rabbitmq.username}")
	public void setSpringRabbitUsername(String sPRING_RABBIT_USERNAME) {
		SPRING_RABBIT_USERNAME = sPRING_RABBIT_USERNAME;
	}

	public String getSpringRabbitPassword() {
		return SPRING_RABBIT_PASSWORD;
	}

	@Value("${spring.rabbitmq.password}")
	public void setSpringRabbitPassword(String sPRING_RABBIT_PASSWORD) {
		SPRING_RABBIT_PASSWORD = sPRING_RABBIT_PASSWORD;
	}

	public int getSpringRabbitPort() {
		return SPRING_RABBIT_PORT;
	}

	@Value("${spring.rabbitmq.port}")
	public void setSpringRabbitPort(int sPRING_RABBIT_PORT) {
		SPRING_RABBIT_PORT = sPRING_RABBIT_PORT;
	}

	public String getSpringRabbitVhost() {
		return SPRING_RABBIT_VHOST;
	}

	@Value("${spring.rabbitmq.virtual-host}")
	public void setSpringRabbitVhost(String sPRING_RABBIT_VHOST) {
		SPRING_RABBIT_VHOST = sPRING_RABBIT_VHOST;
	}

	public String getSpringRabbitExchange() {
		return SPRING_RABBIT_EXCHANGE;
	}

	@Value("${spring.rabbitmq.exchange}")
	public void setSpringRabbitExchange(String sPRING_RABBIT_EXCHANGE) {
		SPRING_RABBIT_EXCHANGE = sPRING_RABBIT_EXCHANGE;
	}

	public String getSpringRabbitQueue() {
		return SPRING_RABBIT_QUEUE;
	}

	@Value("${spring.rabbitmq.queue}")
	public void setSpringRabbitQueue(String sPRING_RABBIT_QUEUE) {
		SPRING_RABBIT_QUEUE = sPRING_RABBIT_QUEUE;
	}

	public String getSpringRabbitBinding() {
		return SPRING_RABBIT_BINDING;
	}

	@Value("${spring.rabbitmq.binding}")
	public void setSpringRabbitBinding(String sPRING_RABBIT_BINDING) {
		SPRING_RABBIT_BINDING = sPRING_RABBIT_BINDING;
	}
}