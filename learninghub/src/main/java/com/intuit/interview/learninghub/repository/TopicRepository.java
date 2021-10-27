package com.intuit.interview.learninghub.repository;

import com.intuit.interview.learninghub.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Optional<Topic> findByTopicIdentifier(String topicId);
}
