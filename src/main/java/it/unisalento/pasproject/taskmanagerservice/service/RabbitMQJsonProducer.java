package it.unisalento.pasproject.taskmanagerservice.service;

import it.unisalento.pasproject.taskmanagerservice.dto.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonProducer {
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
     * Constructor for the RabbitMQJsonProducer.
     *
     * @param rabbitTemplate The RabbitTemplate instance to use for sending messages.
     */
    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Method to send a JSON message to RabbitMQ.
     *
     * @param taskDTO The TaskDTO to send as a JSON message.
     */
    public void sendJsonMessage(TaskDTO taskDTO) {
        LOGGER.info(String.format("RabbitMQ message sent: %s", taskDTO.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, taskDTO);
    }
}
