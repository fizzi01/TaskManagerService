package it.unisalento.pasproject.taskmanagerservice.business.io.producer;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Setter
@Service
public class MessageProducer {

    private MessageProducerStrategy strategy;

    @Autowired
    public MessageProducer(MessageProducerStrategy strategy) {
        this.strategy = strategy;
    }

    public <T> void sendMessage(T message, String routingKey) {
        strategy.sendMessage(message, routingKey);
    }

    public <T> void sendMessage(T messageDTO) {
        strategy.sendMessage(messageDTO);
    }


}
