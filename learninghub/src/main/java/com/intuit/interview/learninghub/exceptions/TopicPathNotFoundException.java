package com.intuit.interview.learninghub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TopicPathNotFoundException extends RuntimeException{

    public TopicPathNotFoundException(String message) {
        super(message);
    }
}
