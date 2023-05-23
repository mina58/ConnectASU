package com.ConnectASU.exceptions;

public class InvalidEmailException extends Exception{
    public InvalidEmailException() {
        super("Invalid email");
    }
}
