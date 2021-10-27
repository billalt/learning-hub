package com.intuit.interview.learninghub.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NotBlank(message = "Username can not be empty")
    private String username;
    @NotBlank(message = "Password can not be empty")
    private  String password;
}
