package com.brian.userauthenticationservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Session extends BaseModel{
    private Date expiryDate;
    private String token;
    @OneToOne
    private User user;
    @OneToOne
    private Role role;
    private String deviceInfo;
}
