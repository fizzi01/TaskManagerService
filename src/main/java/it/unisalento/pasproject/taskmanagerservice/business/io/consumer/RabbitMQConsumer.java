package it.unisalento.pasproject.taskmanagerservice.business.io.consumer;

import it.unisalento.pasproject.taskmanagerservice.business.io.producer.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * RabbitMQConsumer class for consuming messages from RabbitMQ.
 */
@Service("RabbitMQConsumer")
public class RabbitMQConsumer implements MessageConsumerStrategy{
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

    @Autowired
    private MessageProducer messageProducer;

    @Value("${rabbitmq.exchange.security.name}")
    private String securityExchange;

    @Value("${rabbitmq.routing.security.key}")
    private String securityRoutingKey;

    /**
     * Logger instance for logging events.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    /**
     * RabbitTemplate instance for sending messages to RabbitMQ.
     */
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public <T> T consumeMessage(T message) {
        //TODO: Implement this method
        return null;
    }

    /**
     * Method to consume a message from RabbitMQ.
     *
     * @param message The message to consume.
     * @return The consumed message.

    //@RabbitListener(queues = "${rabbitmq.queue.security.name}")
    public String consumeMessage(String message){
        LOGGER.info(String.format("RabbitMQ message received: %s", message));
        messageProducer.sendMessage("Exchange CONFIRM!",securityRoutingKey,securityExchange);
        return message;
    }

    //@Override
    //@RabbitListener(queues = "${rabbitmq.queue.json.name}")
    public <T> T consumeMessage(T message) {
        LOGGER.info(String.format("RabbitMQ message received: %s", message.toString()));
        return message;
    }
     */

    @Override
    public String consumeMessage(String message, String queueName) {
        String ret = Objects.requireNonNull(rabbitTemplate.receive(queueName)).toString();
        return ret;
    }
}
