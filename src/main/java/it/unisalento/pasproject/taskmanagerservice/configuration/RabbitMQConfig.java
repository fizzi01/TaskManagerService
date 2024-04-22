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
    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${rabbitmq.queue.json.name}")
    private String jsonQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

    /**
     * Creates a queue for tasks.
     *
     * @return a new Queue instance.
     */
    @Bean
    public Queue taskQueue() {
        return new Queue(queue);
    }

    /**
     * Creates a queue for JSON tasks.
     *
     * @return a new Queue instance.
     */
    @Bean
    public Queue jsonQueue() {
        return new Queue(jsonQueue);
    }

    /**
     * Creates a topic exchange for tasks.
     *
     * @return a new TopicExchange instance.
     */
    @Bean
    public TopicExchange taskExchange() {
        return new TopicExchange(exchange);
    }

    /**
     * Binds the task queue to the task exchange with a routing key.
     *
     * @return a new Binding instance.
     */
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(taskQueue())
                .to(taskExchange())
                .with(routingKey);
    }

    /**
     * Binds the JSON task queue to the task exchange with a JSON routing key.
     *
     * @return a new Binding instance.
     */
    @Bean
    public Binding jsonBinding() {
        return BindingBuilder
                .bind(taskQueue())
                .to(taskExchange())
                .with(routingJsonKey);
    }

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
