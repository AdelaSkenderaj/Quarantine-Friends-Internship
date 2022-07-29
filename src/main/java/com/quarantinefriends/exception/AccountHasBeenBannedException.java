package com.quarantinefriends.exception;

public class AccountHasBeenBannedException extends Exception {
    public AccountHasBeenBannedException(String message) {
        super(message);
    }

}
