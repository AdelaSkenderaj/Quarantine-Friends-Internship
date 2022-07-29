package com.quarantinefriends.exception;

public class EmailExistException extends Exception{

    public EmailExistException(String message) {
        super(message);
    }
}
