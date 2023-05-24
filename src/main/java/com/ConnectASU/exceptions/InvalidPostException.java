package com.ConnectASU.exceptions;

public class InvalidPostException extends Exception {
    public InvalidPostException() {
        super("Invalid post content");
    }
}
