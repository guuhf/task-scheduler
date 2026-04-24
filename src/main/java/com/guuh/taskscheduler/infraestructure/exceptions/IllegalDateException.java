package com.guuh.taskscheduler.infraestructure.exceptions;

public class IllegalDateException extends RuntimeException {
    public IllegalDateException(String message) {
        super(message);
    }
    public IllegalDateException(String message, Throwable cause){super(message,cause);}
}
