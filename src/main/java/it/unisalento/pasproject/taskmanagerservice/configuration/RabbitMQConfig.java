package it.unisalento.pasproject.taskmanagerservice.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    // ------  SECURITY  ------

    // Needed by authentication service
    @Value("${rabbitmq.queue.security.name}")
    private String securityResponseQueue;

    @Value("${rabbitmq.exchange.security.name}")
    private String securityExchange;

    @Value("${rabbitmq.routing.security.key}")
    private String securityRequestRoutingKey;

    @Bean
    public Queue securityResponseQueue() {
        return new Queue(securityResponseQueue);
    }

    @Bean
    public TopicExchange securityExchange() {
        return new TopicExchange(securityExchange);
    }

    @Bean
    public Binding securityBinding() {
        return BindingBuilder
                .bind(securityResponseQueue())
                .to(securityExchange())
                .with(securityRequestRoutingKey);
    }

    // ------  END SECURITY  ------



    // ------  TASK MESSAGES  ------ //

    @Value("${rabbitmq.queue.newtask.name}")
    private String newTaskQueue;

    @Value("${rabbitmq.routing.newtask.key}")
    private String newTaskTopic;

    @Value("${rabbitmq.routing.taskassignment.key}")
    private String taskAssingmentTopic;

    @Value("${rabbitmq.queue.taskassignment.name}")
    private String taskAssignmentQueue;

    @Value("${rabbitmq.queue.taskexecution.name}")
    private String taskExecutionQueue;

    @Value("${rabbitmq.routing.taskexecution.key}")
    private String taskExecutionTopic;

    // Others queue and topics for task messages ...

    @Value("${rabbitmq.exchange.data.name}")
    private String dataExchange;

    @Bean
    public Queue newTaskQueue() {
        return new Queue(newTaskQueue);
    }

    @Bean
    public Queue taskAssignmentQueue() {
        return new Queue(taskAssignmentQueue);
    }

    @Bean
    public Queue taskExecutionQueue() {
        return new Queue(taskExecutionQueue);
    }

    @Bean
    public TopicExchange dataExchange() {
        return new TopicExchange(dataExchange);
    }

    @Bean
    public Binding newTaskBinding() {
        return BindingBuilder
                .bind(newTaskQueue())
                .to(dataExchange())
                .with(newTaskTopic);
    }

    @Bean
    public Binding taskAssignmentBinding() {
        return BindingBuilder
                .bind(taskAssignmentQueue())
                .to(dataExchange())
                .with(taskAssingmentTopic);
    }

    @Bean
    public Binding taskExecutionBinding() {
        return BindingBuilder
                .bind(taskExecutionQueue())
                .to(dataExchange())
                .with(taskExecutionTopic);
    }

    // ------  END TASK MESSAGES  ------ //


    /**
     * Creates a message converter for JSON messages.
     *
     * @return a new Jackson2JsonMessageConverter instance.
     */
    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Creates an AMQP template for sending messages.
     *
     * @param connectionFactory the connection factory to use.
     * @return a new RabbitTemplate instance.
     */
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
