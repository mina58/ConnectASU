package com.ConnectASU.exceptions;

public class FailedFollowException extends Exception{
    public FailedFollowException() {
        super("Failed to follow user");
    }
}
