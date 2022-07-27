package com.quarantinefriends.exception;

public class UserNotFoundException extends Exception{

    private static final String message = "User does not exist";

    public UserNotFoundException() {
        super(message);
    }

    public UserNotFoundException(String newMessage) {
        super(newMessage);
    }
}
