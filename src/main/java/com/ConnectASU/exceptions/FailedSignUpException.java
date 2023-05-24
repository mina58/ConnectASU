package com.ConnectASU.exceptions;

public class FailedSignUpException extends Exception{
    public FailedSignUpException() {
        super("Failed to sign up");
    }
}
