package com.ConnectASU.exceptions;

public class FailedUnfollowException extends Exception{
    public FailedUnfollowException() {
        super("Failed to unfollow user");
    }
}
