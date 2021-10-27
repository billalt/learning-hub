package com.intuit.interview.learninghub.repository;


import com.intuit.interview.learninghub.model.TopicPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicPathRepository extends JpaRepository<TopicPath, Long> {

    TopicPath findByTopicPathIdentifier(String topicPathId);
    Iterable<TopicPath> findAllByTopicLeader(String username);

}