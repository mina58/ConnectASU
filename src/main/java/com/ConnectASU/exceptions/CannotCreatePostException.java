package com.ConnectASU.exceptions;

public class CannotCreatePostException extends Exception{
    public CannotCreatePostException() {
        super("Cannot create post");
    }
}
