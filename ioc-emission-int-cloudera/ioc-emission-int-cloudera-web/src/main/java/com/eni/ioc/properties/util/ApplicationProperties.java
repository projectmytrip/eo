package com.eni.ioc.properties.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationProperties {

	private static String CRONTAB_CLOUDERA_RETRIEVER;
	private static String CRONTAB_CLOUDERA_RETRIEVER_AVG;
	private static String HISTORY_DEPTH_HOURS_PREDICTIVE;
	private static String HISTORY_DEPTH_HOURS_AVERAGE;
	private static String HISTORY_DEPTH_HOURS_ANOMALY;
	private static String HISTORY_DEPTH_HOURS_ROOT;
	private static String PERSISTENCE_URL_PREDICTIVE_PROBABILITIES;
	private static String PERSISTENCE_URL_PREDICTIVE_IMPACT;
	private static String PERSISTENCE_URL_PREDICTIVE_AVERAGE;
	private static String PERSISTENCE_URL_PREDICTIVE_ANOMALY;
	private static String PERSISTENCE_URL_PREDICTIVE_FLARING_EVENTS;
	private static String PERSISTENCE_URL_PREDICTIVE_FLARING_TDA;
	private static String PERSISTENCE_URL_PRED_PROB_FLAR;
	private static String CLOUDERA_JDBC_DRIVER;
	private static String CLOUDERA_JDBC_URL;
	private static String CLOUDERA_JDBC_USER;
	private static String CLOUDERA_JDBC_PWD;
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
	
	public String getCrontabClouderaRetriever() {
		return CRONTAB_CLOUDERA_RETRIEVER;
	}

	@Value("${crontab.cloudera.retriever}")
	public void setCrontabClouderaRetriever(String cRONTAB_CLOUDERA_RETRIEVER) {
		CRONTAB_CLOUDERA_RETRIEVER = cRONTAB_CLOUDERA_RETRIEVER;
	}
	
	public String getCrontabClouderaRetrieverAvg() {
		return CRONTAB_CLOUDERA_RETRIEVER_AVG;
	}

	@Value("${crontab.cloudera.retrieverAvg}")
	public void setCrontabClouderaRetrieverAvg(String cRONTAB_CLOUDERA_RETRIEVER_AVG) {
		CRONTAB_CLOUDERA_RETRIEVER_AVG = cRONTAB_CLOUDERA_RETRIEVER_AVG;
	}
	
	
	public String getPersistenceUrlPredictiveProbabilities() {
		return PERSISTENCE_URL_PREDICTIVE_PROBABILITIES;
	}

	@Value("${persistence.url.predictive.probabilities}")
	public void setPersistenceUrlPredictiveProbabilities(String pERSISTENCE_URL_PREDICTIVE_PROBABILITIES) {
		PERSISTENCE_URL_PREDICTIVE_PROBABILITIES = pERSISTENCE_URL_PREDICTIVE_PROBABILITIES;
	}
	
	
	public String getPersistenceUrlPredictiveProbabilitiesFlar() {
		return PERSISTENCE_URL_PRED_PROB_FLAR;
	}
	
	@Value("${persistence.url.predictive.probabilitiesFlar}")
	public void setPersistenceUrlPredictiveProbabilitiesFlar(String pERSISTENCE_URL_PRED_PROB_FLAR) {
		PERSISTENCE_URL_PRED_PROB_FLAR = pERSISTENCE_URL_PRED_PROB_FLAR;
	}
	
	public String getPersistenceUrlPredictiveImpact() {
		return PERSISTENCE_URL_PREDICTIVE_IMPACT;
	}

	@Value("${persistence.url.predictive.impact}")
	public void setPersistenceUrlPredictiveImpact(String pERSISTENCE_URL_PREDICTIVE_IMPACT) {
		PERSISTENCE_URL_PREDICTIVE_IMPACT = pERSISTENCE_URL_PREDICTIVE_IMPACT;
	}
	
	public String getPersistenceUrlPredictiveAverage() {
		return PERSISTENCE_URL_PREDICTIVE_AVERAGE;
	}

	@Value("${persistence.url.predictive.average}")
	public void setPersistenceUrlPredictiveAverage(String pERSISTENCE_URL_PREDICTIVE_AVERAGE) {
		PERSISTENCE_URL_PREDICTIVE_AVERAGE = pERSISTENCE_URL_PREDICTIVE_AVERAGE;
	}
	
	public String getPersistenceUrlPredictiveAnomaly() {
		return PERSISTENCE_URL_PREDICTIVE_ANOMALY;
	}

	@Value("${persistence.url.predictive.anomaly}")
	public void setPersistenceUrlPredictiveAnomaly(String pERSISTENCE_URL_PREDICTIVE_FLARING_EVENTS) {
		PERSISTENCE_URL_PREDICTIVE_ANOMALY = pERSISTENCE_URL_PREDICTIVE_FLARING_EVENTS;
	}
	
	public String getPersistenceUrlPredictiveFEvents() {
		return PERSISTENCE_URL_PREDICTIVE_FLARING_EVENTS;
	}

	@Value("${persistence.url.predictive.flaringEvents}")
	public void setPersistenceUrlPredictiveFEvents(String pERSISTENCE_URL_PREDICTIVE_FLARING_EVENTS) {
		PERSISTENCE_URL_PREDICTIVE_FLARING_EVENTS = pERSISTENCE_URL_PREDICTIVE_FLARING_EVENTS;
	}	
	
	public String getPersistenceUrlPredictiveFlaringTda() {
		return PERSISTENCE_URL_PREDICTIVE_FLARING_TDA;
	}

	@Value("${persistence.url.predictive.tda}")
	public void setPersistenceUrlPredictiveFlaringTda(String pERSISTENCE_URL_PREDICTIVE_FLARING_TDA) {
		PERSISTENCE_URL_PREDICTIVE_FLARING_TDA = pERSISTENCE_URL_PREDICTIVE_FLARING_TDA;
	}

	public String getHistoryDepthHoursPredictive() {
		return HISTORY_DEPTH_HOURS_PREDICTIVE;
	}

	@Value("${history.depth.hours.predictive}")
	public void setHistoryDepthHoursPredictive(String hISTORY_DEPTH_HOURS_PREDICTIVE) {
		HISTORY_DEPTH_HOURS_PREDICTIVE = hISTORY_DEPTH_HOURS_PREDICTIVE;
	}
	
	public String getHistoryDepthHoursAverage() {
		return HISTORY_DEPTH_HOURS_AVERAGE;
	}

	@Value("${history.depth.hours.average}")
	public void setHistoryDepthHoursAverage(String hISTORY_DEPTH_HOURS_AVERAGE) {
		HISTORY_DEPTH_HOURS_AVERAGE = hISTORY_DEPTH_HOURS_AVERAGE;
	}	
			
	public String getHistoryDepthHoursAnomaly() {
		return HISTORY_DEPTH_HOURS_ANOMALY;
	}

	@Value("${history.depth.hours.anomaly}")
	public void setHistoryDepthHoursAnomaly(String hISTORY_DEPTH_HOURS_ANOMALY) {
		HISTORY_DEPTH_HOURS_ANOMALY = hISTORY_DEPTH_HOURS_ANOMALY;
	}

	
	public String getHistoryDepthHoursRoot() {
		return HISTORY_DEPTH_HOURS_ROOT;
	}

	@Value("${history.depth.hours.root}")
	public void setHistoryDepthHoursRoot(String hISTORY_DEPTH_HOURS_ROOT) {
		HISTORY_DEPTH_HOURS_ROOT = hISTORY_DEPTH_HOURS_ROOT;
	}
	public String getClouderaJdbcDriver() {
		return CLOUDERA_JDBC_DRIVER;
	}

	@Value("${cloudera.jdbc.driver}")
	public void setClouderaJdbcDriver(String cLOUDERA_JDBC_DRIVER) {
		CLOUDERA_JDBC_DRIVER = cLOUDERA_JDBC_DRIVER;
	}

	public String getClouderaJdbcUrl() {
		return CLOUDERA_JDBC_URL;
	}

	@Value("${cloudera.jdbc.url}")
	public void setClouderaJdbcUrl(String cLOUDERA_JDBC_URL) {
		CLOUDERA_JDBC_URL = cLOUDERA_JDBC_URL;
	}

	public String getClouderaJdbcUser() {
		return CLOUDERA_JDBC_USER;
	}

	@Value("${cloudera.jdbc.user}")
	public void setClouderaJdbcUser(String cLOUDERA_JDBC_USER) {
		CLOUDERA_JDBC_USER = cLOUDERA_JDBC_USER;
	}
	
	public String getClouderaJdbcPwd() {
		return CLOUDERA_JDBC_PWD;
	}

	@Value("${cloudera.jdbc.pwd}")
	public void setClouderaJdbcPwd(String cLOUDERA_JDBC_PWD) {
		CLOUDERA_JDBC_PWD = cLOUDERA_JDBC_PWD;
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