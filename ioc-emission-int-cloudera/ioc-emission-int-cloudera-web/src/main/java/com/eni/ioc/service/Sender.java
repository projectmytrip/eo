package com.eni.ioc.service;

import com.eni.ioc.ApplicationMain;
import com.eni.ioc.properties.util.ApplicationProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Sender {

    private static final Logger logger = LoggerFactory.getLogger(Sender.class);

    private static boolean sistemDown;

    private static String SYSTEM = "emissionPred";
    private static String SYS_DOWN = "System down";
    private static String SYS_UP = "System up";

    public Sender() throws IOException, TimeoutException {
        sistemDown = true;
        
    }

    public static boolean isSistemDown() {
        return sistemDown;
    }

    public static void setSistemDown(boolean sistemDown) {
        Sender.sistemDown = sistemDown;
    }
    

    public static Connection createConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername(ApplicationProperties.getInstance().getSpringRabbitUsername());
        connectionFactory.setPassword(ApplicationProperties.getInstance().getSpringRabbitPassword());
        connectionFactory.setHost(ApplicationProperties.getInstance().getSpringRabbitHost());
        connectionFactory.setPort(ApplicationProperties.getInstance().getSpringRabbitPort());
        connectionFactory.setVirtualHost(ApplicationProperties.getInstance().getSpringRabbitVhost());
        Connection connection = connectionFactory.newConnection();
        

        return connection;

    }

    public static void sendSystemDown() throws IOException, TimeoutException {

        setSistemDown(true);
        ApplicationMain.setStatus("DOWN");

        VariazioneStato message = new VariazioneStato(SYS_DOWN, LocalDateTime.now(), SYSTEM);

        sendMessage(message);

    }

    public static void sendSystemUp() throws IOException, TimeoutException {

        if (isSistemDown()) {

            ApplicationMain.setStatus("UP");
            VariazioneStato message = new VariazioneStato(SYS_UP, LocalDateTime.now(), SYSTEM);

            sendMessage(message);

        }
    }


    public static void sendMessage(VariazioneStato message) throws IOException, TimeoutException {

        try (Connection connection = createConnection(); Channel channel = connection.createChannel()) {
            
            //creo l'exchange di tipo direct
            channel.exchangeDeclare(ApplicationProperties.getInstance().getSpringRabbitExchange(), "direct", true);
            channel.queueDeclare(ApplicationProperties.getInstance().getSpringRabbitQueue(), true, false, false, null);
            
            //creo un binding fra l'exchange e la coda, con routing-key offSpec
            channel.queueBind(ApplicationProperties.getInstance().getSpringRabbitQueue(),
                    ApplicationProperties.getInstance().getSpringRabbitExchange(),
                    ApplicationProperties.getInstance().getSpringRabbitBinding());
            
            //pubblico il messaggio sull'exchange con routing-key offSpec
            channel.basicPublish(ApplicationProperties.getInstance().getSpringRabbitExchange(),
                    ApplicationProperties.getInstance().getSpringRabbitBinding(), null, message.toJson().getBytes());
            
        }
        

        logger.info(" [x] Sent message to " + ApplicationProperties.getInstance().getSpringRabbitExchange() + " exchange with " + ApplicationProperties.getInstance().getSpringRabbitBinding() + " routing key " + message.toJson());
    }



}
