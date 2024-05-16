package it.unisalento.pasproject.taskmanagerservice.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomErrorException.class)
    protected ResponseEntity<CustomErrorResponse> handleTransactionNotFoundException(RuntimeException ex) {
        CustomErrorException exception = (CustomErrorException) ex;
        return ResponseEntity.status(exception.getErrorResponse().getStatus()).body(exception.getErrorResponse());
    }


}
