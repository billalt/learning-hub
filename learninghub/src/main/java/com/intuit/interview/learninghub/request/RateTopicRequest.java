package com.intuit.interview.learninghub.request;

import lombok.Data;

@Data
public class RateTopicRequest {
    private String topicIdentifier;
    private int rate;
}
