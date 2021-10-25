package com.app.demo.exception;

public class AppointmentChangingException extends RuntimeException {

    public AppointmentChangingException() {
        super("The appointment you are trying to change does not exist!");
    }

}
