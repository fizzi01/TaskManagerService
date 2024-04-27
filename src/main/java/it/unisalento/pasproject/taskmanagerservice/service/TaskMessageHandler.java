package it.unisalento.pasproject.taskmanagerservice.service;

import it.unisalento.pasproject.taskmanagerservice.business.io.producer.MessageProducer;
import it.unisalento.pasproject.taskmanagerservice.business.io.producer.MessageProducerStrategy;
import it.unisalento.pasproject.taskmanagerservice.domain.Task;
import it.unisalento.pasproject.taskmanagerservice.dto.MessageDTO;
import it.unisalento.pasproject.taskmanagerservice.dto.TaskMessageAssignDTO;
import it.unisalento.pasproject.taskmanagerservice.dto.TaskMessageDTO;
import it.unisalento.pasproject.taskmanagerservice.repositories.TaskRepository;
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

    //TODO: Ricevere messaggio di risposta (message exchange) ????
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
    public MessageDTO receiveTaskAssignmentMessage(TaskMessageAssignDTO message){
        Optional<Task> task = taskRepository.findById(message.getIdTask());

        if(task.isEmpty()) {
            return new MessageDTO("Task not found", 404);
        }

        Task retTask = task.get();

        Optional.ofNullable(message.getAssignedUsers()).ifPresent(retTask::setAssignedUsers);
        taskRepository.save(retTask);
        return new MessageDTO("Task updated", 200);
    }

    /**
     * Riceve un messaggio di tipo TaskMessageDTO e aggiorna lo stato della task.
     * */
    @RabbitListener(queues = "${rabbitmq.queue.taskexecution.name}")
    public MessageDTO receiveTaskExecutionMessage(TaskMessageDTO message){
        Optional<Task> task = taskRepository.findById(message.getId());

        if(task.isEmpty()) {
            return new MessageDTO("Task not found", 404);
        }

        Task retTask = task.get();

        Optional.ofNullable(message.getRunning()).ifPresent(retTask::setRunning);

        taskRepository.save(retTask);
        return new MessageDTO("Task updated", 200);
    }
}
