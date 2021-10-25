package com.app.demo.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Something went wrong and your profile information is not available. Reload the page!");
    }

}
