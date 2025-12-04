package com.brian.userauthenticationservice.controller;


import com.brian.userauthenticationservice.dto.SignupRequestDto;
import com.brian.userauthenticationservice.dto.SignupResponseDto;
import com.brian.userauthenticationservice.exception.UserAlreadyExistsException;
import com.brian.userauthenticationservice.models.SignupStatus;
import com.brian.userauthenticationservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign_up")
    public ResponseEntity<SignupResponseDto> signUp(@RequestBody SignupRequestDto signupRequestDto) throws UserAlreadyExistsException {
        SignupResponseDto responseDto = new SignupResponseDto();

       String email = signupRequestDto.getEmail();
       String password = signupRequestDto.getPassword();
       String name = signupRequestDto.getName();

        try {
            String token = authService.signup(name,email,password);
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
            String token = authService.login(email,password);
            responseDto.setStatus(SignupStatus.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } catch (Exception e) {
            responseDto.setStatus(SignupStatus.FAILURE);
            return new ResponseEntity<>(responseDto, HttpStatus.CONFLICT);
        }



    }


}
