package com.intuit.interview.learninghub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.*;

@Data
@Entity
@Table(	name = "users")
public class User  implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Username needs to an email")
    @NotBlank(message = "Username is required")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Please enter your full name")
    private String fullName;

    @NotBlank(message = "Password is required")
    private String password;

    @Transient
    private String confirmPassword;


    private Date created_At;
    private Date updated_At;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "topic_path_users",
            joinColumns = {@JoinColumn(name = "username")},
            inverseJoinColumns = {@JoinColumn(name = "topicPathIdentifier")})
    @JsonIgnore
    private Set<TopicPath> topicPaths = new HashSet<>();

    @PrePersist
    protected void onCreate(){
        this.created_At = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updated_At = new Date();
    }

}
