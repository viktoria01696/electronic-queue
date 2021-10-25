package com.app.demo.exception;

public class TimetableNotFoundException extends RuntimeException {

    public TimetableNotFoundException() {
        super("The appointment schedule for this date is not available!");
    }

}
