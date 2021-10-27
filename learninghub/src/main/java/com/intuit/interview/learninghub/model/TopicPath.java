package com.intuit.interview.learninghub.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="topic_path")
@Getter
@Setter
public class TopicPath implements Serializable{


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "path_name")
    private String pathName;

    @NotBlank(message = "Topic Identifier is required")
    @Size(min = 4, max = 5, message = "Please use 4 to 5 characters")
    @Column(updatable = false, unique = true)
    private String topicPathIdentifier;

    @Column(name = "description")
    private String description;

    @Column(name = "path_url")
    private String pathUrl;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date created_At;
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updated_At;

    private String topicLeader;

    @PrePersist
    protected void onCreate() {
        this.created_At = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_At = new Date();
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "topic_id", referencedColumnName = "topicIdentifier" )
    private Topic topic;

    @ManyToMany(mappedBy = "topicPaths")
    private Set<User> users = new HashSet<>();
}
