package it.unisalento.pasproject.taskmanagerservice.business.io.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer implements MessageProducerStrategy{
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

    private final RabbitTemplate rabbitTemplate;

    /**
     * Constructor for the RabbitMQJsonProducer.
     *
     * @param rabbitTemplate The RabbitTemplate instance to use for sending messages.
     */
    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Method to send a JSON message to RabbitMQ.
     *
     * @param message The TaskDTO to send as a JSON message.
     */
    @Override
    public <T> void sendMessage(T message, String routingKey) {
        LOGGER.info(String.format("RabbitMQ message sent: %s", message.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    @Override
    public <T> void sendMessage(T messageDTO) {
        LOGGER.info(String.format("RabbitMQ message sent: %s", messageDTO.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, messageDTO);
    }


}
