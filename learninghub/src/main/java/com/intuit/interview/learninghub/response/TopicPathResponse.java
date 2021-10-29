package com.intuit.interview.learninghub.response;

import lombok.Data;

@Data
public class TopicPathResponse {
    private String pathName;
    private String topicPathIdentifier;
    private String description;
    private String pathUrl;
}
