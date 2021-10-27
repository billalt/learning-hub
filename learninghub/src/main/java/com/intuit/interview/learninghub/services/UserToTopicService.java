package com.intuit.interview.learninghub.services;

import com.intuit.interview.learninghub.exceptions.TopicNotFoundException;
import com.intuit.interview.learninghub.exceptions.TopicPathExistException;
import com.intuit.interview.learninghub.exceptions.TopicPathNotFoundException;
import com.intuit.interview.learninghub.exceptions.TopicRatedException;
import com.intuit.interview.learninghub.model.RatedTopic;
import com.intuit.interview.learninghub.model.Topic;
import com.intuit.interview.learninghub.model.TopicPath;
import com.intuit.interview.learninghub.model.User;
import com.intuit.interview.learninghub.repository.RatedTopicRepository;
import com.intuit.interview.learninghub.repository.TopicPathRepository;
import com.intuit.interview.learninghub.repository.TopicRepository;
import com.intuit.interview.learninghub.repository.UserRepository;
import com.intuit.interview.learninghub.request.RateTopicRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserToTopicService {

    @Autowired
    private TopicPathRepository topicPathRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private RatedTopicRepository ratedTopicRepository;

    public User addTopicToUser(String topicPathIdentifier, String username){

        TopicPath existTopicPath = topicPathRepository.findByTopicPathIdentifier(topicPathIdentifier);
        User savedUser = null;
        if(existTopicPath != null){
            Set<User> users = existTopicPath.getUsers();
            for(User user : users){
                if(user.getUsername().equals(username)){
                    throw new TopicPathExistException("Topic Path '"+  existTopicPath.getPathName() + "' already exists for " + username);
                }
            }
            Optional<User> existUser = userRepository.findByUsername(username);
            User user = existUser.get();
            user.getTopicPaths().add(existTopicPath);
            savedUser = userRepository.save(user);
        }else{
            throw new TopicPathNotFoundException("Topic Path '" + existTopicPath.getPathName() + "' doesn't exist");
        }

        return savedUser;
    }

    public Topic rateTopicByUser(RateTopicRequest rateTopicRequest, String username) {

        Optional<RatedTopic> ratedTopic = ratedTopicRepository.findByTopicIdentifierAndUsername(rateTopicRequest.getTopicIdentifier() ,username);
        if(ratedTopic.isPresent()) {
            throw new TopicRatedException("Topic '" + rateTopicRequest.getTopicIdentifier() + "' already rated by " + username);
        }else{
            RatedTopic ratedTopic1 = new RatedTopic();
            ratedTopic1.setTopicIdentifier(rateTopicRequest.getTopicIdentifier());
            ratedTopic1.setUsername(username);
            ratedTopicRepository.save(ratedTopic1);
        }
        Optional<Topic> topic = topicRepository.findByTopicIdentifier(rateTopicRequest.getTopicIdentifier());
        if(!topic.isPresent()){
            throw new TopicNotFoundException("Topic '" + rateTopicRequest.getTopicIdentifier() + "' doesn't exist");
        }

        Topic existTopic = topic.get();

        Optional<User> existUser = userRepository.findByUsername(username);
        User user = existUser.get();
        final Optional<String> topicIdentifier = user.getTopicPaths().stream().
                filter(x -> x.getTopic().getTopicIdentifier().equals(rateTopicRequest.getTopicIdentifier()))
                .map(x -> x.getTopic().getTopicIdentifier()).findFirst();
        if(topic.get().getTopicIdentifier().equals(topicIdentifier.get())){
            calcTopicRate(existTopic , rateTopicRequest.getRate());
            topicRepository.save(existTopic);
        }
        return existTopic;
    }


    private void calcTopicRate(Topic topic, int rate) {
        if(topic.getNoOfRates() == null || topic.getNoOfRates() == 0){
            topic.setRate(rate * 1.0);
            topic.setNoOfRates(1);
        }else{
            double newRate = (topic.getRate() * topic.getNoOfRates() + rate) / (topic.getNoOfRates() + 1);
            newRate = Math.round(newRate * 100.0) / 100.0;
            topic.setRate(newRate);
            topic.setNoOfRates(topic.getNoOfRates() + 1);
        }
    }
}
