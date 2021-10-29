package com.intuit.interview.learninghub.services;

import com.intuit.interview.learninghub.exceptions.TopicNotFoundException;
import com.intuit.interview.learninghub.exceptions.TopicPathNotFoundException;
import com.intuit.interview.learninghub.model.Topic;
import com.intuit.interview.learninghub.model.TopicPath;
import com.intuit.interview.learninghub.repository.TopicPathRepository;
import com.intuit.interview.learninghub.repository.TopicRepository;
import com.intuit.interview.learninghub.repository.UserRepository;
import com.intuit.interview.learninghub.response.TopicPathResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopicPathService {

    @Autowired
    private TopicPathRepository pathRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    public TopicPath saveOrUpdateTopicPath(TopicPath path, String userName){
        if(path.getId() != null){
            TopicPath existTopicPath = pathRepository.findByTopicPathIdentifier(userName);
            if(existTopicPath != null && (!existTopicPath.getTopicLeader().equals(userName))){
                throw new TopicPathNotFoundException("Topic Path not found in your account");
            }
        }

        TopicPath path1 = pathRepository.save(path);
        return path1;
    }

    public List<TopicPathResponse> getPathByTopic(String topicIdentifier) {
        Optional<Topic> topic = topicRepository.findByTopicIdentifier(topicIdentifier);

        if(topic.isPresent()){
            List<TopicPathResponse> topicPathResponses = new ArrayList<>();
            Topic existTopic = topic.get();
            for(TopicPath topic1: existTopic.getTopics()){
                TopicPathResponse  response = new TopicPathResponse();
                response.setDescription(topic1.getDescription());
                response.setPathName(topic1.getPathName());
                response.setPathUrl(topic1.getPathUrl());
                response.setTopicPathIdentifier(topic1.getTopicPathIdentifier());
                topicPathResponses.add(response);
            }
            return topicPathResponses;
        }else {
            throw new TopicNotFoundException("Topic " + topicIdentifier + " Does not exist");
        }
    }

}

