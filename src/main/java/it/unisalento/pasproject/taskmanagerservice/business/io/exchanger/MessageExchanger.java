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
    
    public Object exchangeMessage(String message) {
        return strategy.exchangeMessage(message);
    }

    public Object exchangeMessage(String message, String routingKey) {
        return strategy.exchangeMessage(message, routingKey);
    }
}
