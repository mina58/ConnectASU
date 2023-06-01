package com.ConnectASU.exceptions;

public class CannotGetMembersException extends Exception{
    public CannotGetMembersException() {
        super("Cannot get group members");
    }
}
