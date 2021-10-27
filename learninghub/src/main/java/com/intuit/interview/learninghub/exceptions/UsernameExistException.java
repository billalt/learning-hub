package com.intuit.interview.learninghub.exceptions;


public class UsernameExistException extends RuntimeException{

    public UsernameExistException(String message) {
        super(message);
    }
}
