package com.intuit.interview.learninghub.services;

import com.intuit.interview.learninghub.model.Topic;
import com.intuit.interview.learninghub.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public Topic saveOrUpdateTopic(Topic topic){
        topicRepository.save(topic);
        return topic;
    }
}
