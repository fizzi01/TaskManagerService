package it.unisalento.pasproject.taskmanagerservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {
    /**
     * Logger instance for logging events.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    /**
     * Method to consume messages from the RabbitMQ queue.
     *
     * @param message The message received from the queue.
     */
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consume(String message) {
        LOGGER.info(String.format("RabbitMQ message received: %s", message));
    }
}
