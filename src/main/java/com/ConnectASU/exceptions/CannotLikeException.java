package com.ConnectASU.exceptions;

public class CannotLikeException extends Exception{
    public CannotLikeException() {
        super("Cannot like this post");
    }
}
