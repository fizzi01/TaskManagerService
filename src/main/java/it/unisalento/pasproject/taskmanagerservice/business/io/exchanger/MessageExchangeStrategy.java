package it.unisalento.pasproject.taskmanagerservice.business.io.exchanger;

public interface MessageExchangeStrategy {
    <T> T exchangeMessage(String message, Class<T> object);
    <T> T exchangeMessage(String message, String routingKey, Class<T> object);
}
