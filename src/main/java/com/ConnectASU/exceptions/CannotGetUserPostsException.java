package com.ConnectASU.exceptions;

public class CannotGetUserPostsException extends Exception{
    public CannotGetUserPostsException() {
        super("Cannot get user posts");
    }
}
