package com.brian.userauthenticationservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity(name = "token")
public class Token extends BaseModel{

    private String value;
    private Date expiryDate;
    @ManyToOne
    private User user;



}
