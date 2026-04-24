package com.guuh.taskscheduler.infraestructure.handler;

import com.guuh.taskscheduler.infraestructure.exceptions.IllegalDateException;
import com.guuh.taskscheduler.infraestructure.exceptions.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(IllegalDateException.class)
    private ResponseEntity<RestErrorMessage> illegalDateHandler(IllegalDateException e){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }
    @ExceptionHandler(TaskNotFoundException.class)
    private ResponseEntity<RestErrorMessage> taskNotFoundHandler(TaskNotFoundException e){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }
}
