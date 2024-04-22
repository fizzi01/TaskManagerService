package it.unisalento.pasproject.taskmanagerservice.business.io.exchanger;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("RabbitMQExchange")
public class RabbitMQExchange implements MessageExchangeStrategy{


    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQExchange(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

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

    @Override
    public Object exchangeMessage(String message) {
        // Creare un messaggio
        Message request = MessageBuilder.withBody(message.getBytes())
                .setContentType(MediaType.APPLICATION_JSON_VALUE)
                .build();

        // Inviare il messaggio e attendere una risposta
        Message response = rabbitTemplate.sendAndReceive(exchange, routingKey, request);

        // Verificare se è stata ricevuta una risposta
        if (response == null) {
            throw new UsernameNotFoundException(message);
        }

        // Restituire la risposta come stringa
        return new String(response.getBody());
    }

    @Override
    public Object exchangeMessage(String message, String routingKey) {
        // Creare un messaggio
        Message request = MessageBuilder.withBody(message.getBytes())
                .setContentType(MediaType.APPLICATION_JSON_VALUE)
                .build();

        // Inviare il messaggio e attendere una risposta
        Message response = rabbitTemplate.sendAndReceive(exchange, routingKey, request);

        // Verificare se è stata ricevuta una risposta
        if (response == null) {
            throw new UsernameNotFoundException(message);
        }

        // Restituire la risposta come stringa
        return new String(response.getBody());
    }
}
