package com.intuit.interview.learninghub.services;

import com.intuit.interview.learninghub.exceptions.TopicPathNotFoundException;
import com.intuit.interview.learninghub.model.TopicPath;
import com.intuit.interview.learninghub.repository.TopicPathRepository;
import com.intuit.interview.learninghub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicPathService {

    @Autowired
    private TopicPathRepository pathRepository;

    @Autowired
    private UserRepository userRepository;

    public TopicPath saveOrUpdateTopicPath(TopicPath path, String userName){
        if(path.getId() != null){
            TopicPath existTopicPath = pathRepository.findByTopicPathIdentifier(userName);
            if(existTopicPath != null && (!existTopicPath.getTopicLeader().equals(userName))){
                throw new TopicPathNotFoundException("Topic Path not found in your account");
            }
        }

//        try{
//            Users user = userRepository.findByUsername(userName);
//            path.setUser(user);
//            path.set
//        }
        TopicPath path1 = pathRepository.save(path);
        return path1;
    }
}

