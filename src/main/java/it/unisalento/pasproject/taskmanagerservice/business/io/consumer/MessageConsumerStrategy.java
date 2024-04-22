package it.unisalento.pasproject.taskmanagerservice.business.io.consumer;

public interface MessageConsumerStrategy {
    <T> T consumeMessage(T message);
}
