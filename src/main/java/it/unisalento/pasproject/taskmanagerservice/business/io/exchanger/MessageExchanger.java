package it.unisalento.pasproject.taskmanagerservice.business.io.exchanger;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Setter
public class MessageExchanger {
    
    
    private MessageExchangeStrategy strategy;

    @Autowired
    public MessageExchanger(MessageExchangeStrategy messageExchangeStrategy) {
        this.strategy = messageExchangeStrategy;
    }
    
    public <T> T exchangeMessage(String message, Class<T> object) {
        return strategy.exchangeMessage(message, object);
    }

    public <T> T exchangeMessage(String message, String routingKey, Class<T> object) {
        return strategy.exchangeMessage(message, routingKey, object);
    }
}
