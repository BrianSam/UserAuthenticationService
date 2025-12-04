package com.brian.userauthenticationservice.dto;

import com.brian.userauthenticationservice.models.SignupStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponseDto {
    private SignupStatus status;
    private String message;

}
