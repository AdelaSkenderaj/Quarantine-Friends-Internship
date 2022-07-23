package com.quarantinefriends.exception;

public class MatchRequestNotFoundException extends Exception {

    private static final String message = "Match Request not found";
    public MatchRequestNotFoundException() {
        super(message);
    }
}
