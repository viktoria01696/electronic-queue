package com.app.demo.exception;

public class UnavailableDateException extends RuntimeException {

    public UnavailableDateException() {
        super("Something went wrong and the selected time is no longer available for" +
                " booking or canceling a booking!");
    }
}
