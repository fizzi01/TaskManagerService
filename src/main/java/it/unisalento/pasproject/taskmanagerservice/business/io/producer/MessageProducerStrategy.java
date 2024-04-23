package it.unisalento.pasproject.taskmanagerservice.business.io.producer;

public interface MessageProducerStrategy {
    <T> void sendMessage(T messageDTO);
    <T> void sendMessage(T messageDTO,String routingKey);
    <T> void sendMessage(T messageDTO,String routingKey, String exchange);
    <T> void sendMessage(T messageDTO,String routingKey, String exchange, String replyTo);

}
