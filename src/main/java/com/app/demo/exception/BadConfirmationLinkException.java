package com.app.demo.exception;

public class BadConfirmationLinkException extends RuntimeException {

    public BadConfirmationLinkException() {
        super("The time for booking confirmation has expired. " +
                "This link is no longer active!");
    }

}
