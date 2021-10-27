package com.intuit.interview.learninghub.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class JWTLoginSuccessResponse {
    private boolean success;
    private Long id;
    private String username;
    private String token;
}
