package com.intuit.interview.learninghub.rest;

import com.intuit.interview.learninghub.model.Topic;
import com.intuit.interview.learninghub.model.TopicPath;
import com.intuit.interview.learninghub.model.User;
import com.intuit.interview.learninghub.request.RateTopicRequest;
import com.intuit.interview.learninghub.response.TopicPathResponse;
import com.intuit.interview.learninghub.services.MapValidationErrorService;
import com.intuit.interview.learninghub.services.TopicPathService;
import com.intuit.interview.learninghub.services.TopicService;
import com.intuit.interview.learninghub.services.UserToTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/topic")
public class TopicApi {
    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicPathService pathService;

    @Autowired
    private UserToTopicService userToTopicService;

    @Autowired
    private MapValidationErrorService validationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewTopic(@Valid @RequestBody Topic topic, BindingResult result){

        ResponseEntity<?> errorMap = validationErrorService.mapValidationService(result);
        if(errorMap != null){
            return errorMap;
        }

        Topic topic1 = topicService.saveOrUpdateTopic(topic);
        return  new ResponseEntity<Topic>(topic1 , HttpStatus.CREATED);
    }



    @PostMapping("/path")
    public ResponseEntity<?> createNewTopicPath(@Valid @RequestBody TopicPath path , BindingResult result , Principal principal){
        ResponseEntity<?> errorMap = validationErrorService.mapValidationService(result);
        if(errorMap != null){
            return errorMap;
        }
        TopicPath path1 = pathService.saveOrUpdateTopicPath(path , principal.getName());
        return  new ResponseEntity<TopicPath>(path1 , HttpStatus.CREATED);
    }

    @PutMapping("/user/addtopic/{topicPathIdentifier}")
    public ResponseEntity<?> createNewTopicPath(@PathVariable String topicPathIdentifier , Principal principal){

        User user = userToTopicService.addTopicToUser(topicPathIdentifier , principal.getName());
        return  new ResponseEntity<User>(user , HttpStatus.CREATED);
    }

    @PutMapping("/user/ratetopic")
    public ResponseEntity<?> rateTopic(@RequestBody RateTopicRequest rateTopicRequest, BindingResult result , Principal principal){


        Topic topic = userToTopicService.rateTopicByUser(rateTopicRequest, principal.getName());
        return  new ResponseEntity<Topic>(topic , HttpStatus.OK);
    }

    @GetMapping("/topicpath/{topicIdentifier}")
    public ResponseEntity<?> getPathByTopic(@PathVariable String topicIdentifier){

        List<TopicPathResponse> topic = pathService.getPathByTopic(topicIdentifier);

        return  new ResponseEntity<List<TopicPathResponse>>(topic , HttpStatus.OK);
    }
}
