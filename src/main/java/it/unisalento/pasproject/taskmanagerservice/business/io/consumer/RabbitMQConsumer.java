package it.unisalento.pasproject.taskmanagerservice.business.io.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    @RabbitListener(queues = "${rabbitmq.queue.json.name}")
    public <T> T consumeMessage(T message) {
        LOGGER.info(String.format("RabbitMQ message received: %s", message.toString()));
        return message;
    }
}
