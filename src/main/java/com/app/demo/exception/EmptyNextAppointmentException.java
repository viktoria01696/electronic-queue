package com.app.demo.exception;

public class EmptyNextAppointmentException extends RuntimeException {

    public EmptyNextAppointmentException() {
        super("There are no more active appointments for the requested date!");
    }

}
