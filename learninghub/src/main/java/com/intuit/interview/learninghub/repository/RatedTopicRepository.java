package com.intuit.interview.learninghub.repository;

import com.intuit.interview.learninghub.model.RatedTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatedTopicRepository extends JpaRepository<RatedTopic , Long> {
    Optional<RatedTopic> findByTopicIdentifierAndUsername(String topicId, String username);
}
