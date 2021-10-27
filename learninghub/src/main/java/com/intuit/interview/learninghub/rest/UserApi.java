package com.intuit.interview.learninghub.rest;

import com.intuit.interview.learninghub.exceptions.UsernameExistException;
import com.intuit.interview.learninghub.model.User;
import com.intuit.interview.learninghub.repository.UserRepository;
import com.intuit.interview.learninghub.request.LoginRequest;
import com.intuit.interview.learninghub.response.JWTLoginSuccessResponse;
import com.intuit.interview.learninghub.response.MessageResponse;
import com.intuit.interview.learninghub.security.jwt.JwtUtils;
import com.intuit.interview.learninghub.services.MapValidationErrorService;
import com.intuit.interview.learninghub.services.UserDetailsImpl;
import com.intuit.interview.learninghub.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserApi {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    MapValidationErrorService errorService;

    @Autowired
    private UserValidator userValidator;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true,
                userDetails.getId(),
                userDetails.getUsername(),
                jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user , BindingResult result) {

        userValidator.validate(user , result);

        ResponseEntity<?> errorMap = errorService.mapValidationService(result);
        if(errorMap != null) return errorMap;

        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new UsernameExistException("Error: Username is already taken!"));
        }

        user.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
