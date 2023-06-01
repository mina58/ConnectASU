package com.ConnectASU.exceptions;

public class CannotGetCommentsException extends Exception{
    public CannotGetCommentsException() {
        super("Cannot get comments");
    }
}
