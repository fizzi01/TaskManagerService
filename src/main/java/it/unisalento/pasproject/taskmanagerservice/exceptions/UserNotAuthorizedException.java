package it.unisalento.pasproject.taskmanagerservice.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotAuthorizedException extends CustomErrorException {

    public UserNotAuthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
