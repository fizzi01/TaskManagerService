package it.unisalento.pasproject.taskmanagerservice.service;

import it.unisalento.pasproject.taskmanagerservice.business.io.producer.MessageProducer;
import it.unisalento.pasproject.taskmanagerservice.business.io.producer.MessageProducerStrategy;
import it.unisalento.pasproject.taskmanagerservice.domain.Task;
import it.unisalento.pasproject.taskmanagerservice.dto.MessageDTO;
import it.unisalento.pasproject.taskmanagerservice.dto.TaskMessageDTO;
import it.unisalento.pasproject.taskmanagerservice.dto.TaskStatusMessageDTO;
import it.unisalento.pasproject.taskmanagerservice.repositories.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Viene gestita la consistenza dei dati inviando messaggi a RabbitMQ.
 * */
@Service
public class TaskMessageHandler {

    private final TaskRepository taskRepository;
    private final MessageProducer messageProducer;

    //LOGGERfactory
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskMessageHandler.class);

    @Autowired
    public TaskMessageHandler(TaskRepository taskRepository, MessageProducer messageProducer, @Qualifier("RabbitMQProducer") MessageProducerStrategy messageProducerStrategy) {
        this.taskRepository = taskRepository;
        this.messageProducer = messageProducer;
        messageProducer.setStrategy(messageProducerStrategy);
    }

    @Value("${rabbitmq.routing.newtask.key}")
    private String newTaskTopic;

    @Value("${rabbitmq.exchange.data.name}")
    private String dataExchange;


    public void sendNewTaskMessage(TaskMessageDTO message) {
        messageProducer.sendMessage(message, newTaskTopic, dataExchange);
    }

    public void sendUpdateTaskMessage(TaskMessageDTO message) {
        messageProducer.sendMessage(message, newTaskTopic, dataExchange);
    }

    /**
     * Riceve un messaggio di tipo TaskMessageAssignDTO e aggiorna i membri assegnati alla task.
     * */
    @RabbitListener(queues = "${rabbitmq.queue.taskassignment.name}")
    public void receiveTaskAssignmentMessage(TaskStatusMessageDTO message){

        try {
            Optional<Task> task = taskRepository.findById(message.getId());

            if (task.isEmpty()) {
                return;
            }

            Task retTask = task.get();

            Optional.ofNullable(message.getEnabled()).ifPresent(retTask::setEnabled);
            Optional.ofNullable(message.getRunning()).ifPresent(retTask::setRunning);
            Optional.ofNullable(message.getEndTime()).ifPresent(retTask::setEndTime);
            Optional.ofNullable(message.getAssignedResources()).ifPresent(retTask::setAssignedResources);
            Optional.ofNullable(message.getStartTime()).ifPresent(retTask::setStartTime);

            taskRepository.save(retTask);
        } catch (Exception e) {
            LOGGER.error("Error while updating task assignment : {} ", e.getMessage());
        }
    }

    /**
     * Riceve un messaggio di tipo TaskMessageDTO e aggiorna lo stato della task.
     * */
    @RabbitListener(queues = "${rabbitmq.queue.taskexecution.name}")
    public void receiveTaskExecutionMessage(TaskStatusMessageDTO message){

        try {
            LOGGER.info("Received task execution message: {}", message);
            Optional<Task> task = taskRepository.findById(message.getId());

            if (task.isEmpty()) {
                return;
            }

            Task retTask = task.get();

            Optional.ofNullable(message.getEnabled()).ifPresent(retTask::setEnabled);
            Optional.ofNullable(message.getRunning()).ifPresent(retTask::setRunning);
            Optional.ofNullable(message.getEndTime()).ifPresent(retTask::setEndTime);

            taskRepository.save(retTask);
        } catch (Exception e) {
            LOGGER.error("Error while updating task status : {} ", e.getMessage());
        }
    }
}
