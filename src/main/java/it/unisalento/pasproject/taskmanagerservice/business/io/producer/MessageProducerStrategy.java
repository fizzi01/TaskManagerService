package it.unisalento.pasproject.taskmanagerservice.business.io.producer;

public interface MessageProducerStrategy {
    <T> void sendMessage(T messageDTO);
    <T> void sendMessage(T messageDTO,String routingKey);
}
