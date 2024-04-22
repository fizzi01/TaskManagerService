package it.unisalento.pasproject.taskmanagerservice.business.io.exchanger;

public interface MessageExchangeStrategy {
    Object exchangeMessage(String message);
    Object exchangeMessage(String message, String routingKey);
}
