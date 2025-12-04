package com.brian.userauthenticationservice.controller;


import com.brian.userauthenticationservice.dto.SignupRequestDto;
import com.brian.userauthenticationservice.dto.SignupResponseDto;
import com.brian.userauthenticationservice.event.Events;
import com.brian.userauthenticationservice.event.UserEvent;
import com.brian.userauthenticationservice.exception.UserAlreadyExistsException;
import com.brian.userauthenticationservice.models.SignupStatus;
import com.brian.userauthenticationservice.service.AuthService;
import com.brian.userauthenticationservice.utils.KafkaUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    KafkaUtils kafkaUtils;

    public AuthController(AuthService authService, KafkaUtils kafkaUtils) {
        this.authService = authService;
        this.kafkaUtils = kafkaUtils;
    }

    @PostMapping("/sign_up")
    public ResponseEntity<SignupResponseDto> signUp(@RequestBody SignupRequestDto signupRequestDto) throws UserAlreadyExistsException {
        SignupResponseDto responseDto = new SignupResponseDto();

       String email = signupRequestDto.getEmail();
       String password = signupRequestDto.getPassword();
       String name = signupRequestDto.getName();

        try {
            String id = authService.signup(name,email,password);
            UserEvent event = new UserEvent();
            event.setName(name);
            event.setEmail(email);
            event.setEventType("SIGNUP");
            event.setEventTime(Instant.now().toEpochMilli());
            event.setPartition(0);
            event.setUserId(id);
            event.setTopicName("user-events");
            event.setKey(id+"_user-events_SIGNUP");
            kafkaUtils.publishEvent(event);
            responseDto.setStatus(SignupStatus.SUCCESS);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (Exception e) {
            responseDto.setStatus(SignupStatus.FAILURE);
            responseDto.setMessage(e.getMessage());
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/login")
    public ResponseEntity<SignupResponseDto> login(@RequestBody SignupRequestDto signupRequestDto) {
        SignupResponseDto responseDto = new SignupResponseDto();
        String email = signupRequestDto.getEmail();
        String password = signupRequestDto.getPassword();
        String name = signupRequestDto.getName();

        try {
            String id = authService.login(email,password);
            UserEvent event = new UserEvent();
            event.setName(name);
            event.setEmail(email);
            event.setEventType("LOGIN");
            event.setEventTime(Instant.now().toEpochMilli());
            event.setPartition(1);
            event.setTopicName("user-events");
            event.setKey(id+"_user-events_LOGIN");
            kafkaUtils.publishEvent(event);

            responseDto.setStatus(SignupStatus.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } catch (Exception e) {
            responseDto.setStatus(SignupStatus.FAILURE);
            return new ResponseEntity<>(responseDto, HttpStatus.CONFLICT);
        }



    }


}
