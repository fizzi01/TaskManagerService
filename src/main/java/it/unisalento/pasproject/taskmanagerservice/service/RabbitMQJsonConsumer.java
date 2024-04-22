package it.unisalento.pasproject.taskmanagerservice.service;

import it.unisalento.pasproject.taskmanagerservice.dto.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonConsumer {
    /**
     * Logger instance for logging events.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    /**
     * Method to consume JSON messages from the RabbitMQ queue.
     *
     * @param taskDTO The TaskDTO object received from the queue.
     */
    @RabbitListener(queues = "${rabbitmq.queue.json.name}")
    public void consume(TaskDTO taskDTO) {
        LOGGER.info(String.format("RabbitMQ message received: %s", taskDTO.toString()));
    }
}
