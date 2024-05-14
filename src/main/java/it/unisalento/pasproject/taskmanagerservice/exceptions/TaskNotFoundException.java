package it.unisalento.pasproject.taskmanagerservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class TaskNotFoundException extends CustomErrorException {

    public TaskNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public TaskNotFoundException() {
        super("Task not found", HttpStatus.NOT_FOUND);
    }
}
