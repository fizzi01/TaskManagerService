package it.unisalento.pasproject.taskmanagerservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {
    /**
     * The name of the RabbitMQ exchange to send messages to.
     */
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    /**
     * The routing key to use when sending messages to the RabbitMQ exchange.
     */
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    /**
     * Logger instance for logging events.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    /**
     * RabbitTemplate instance for sending messages to RabbitMQ.
     */
    private RabbitTemplate rabbitTemplate;

    /**
     * Constructor for the RabbitMQProducer.
     *
     * @param rabbitTemplate The RabbitTemplate instance to use for sending messages.
     */
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Method to send a message to RabbitMQ.
     *
     * @param message The message to send.
     */
    public void sendMessage(String message) {
        LOGGER.info(String.format("RabbitMQ message sent: %s", message));
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
