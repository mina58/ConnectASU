package com.ConnectASU.exceptions;

public class CannotGetLikesException extends Exception {
    public CannotGetLikesException() {
        super("Cannot get post likes");
    }
}
