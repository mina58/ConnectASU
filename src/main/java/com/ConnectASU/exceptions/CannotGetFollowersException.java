package com.ConnectASU.exceptions;

public class CannotGetFollowersException extends Exception{
    public CannotGetFollowersException() {
        super("Cannot get user followers");
    }
}
