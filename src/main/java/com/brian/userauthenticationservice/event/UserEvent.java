package com.brian.userauthenticationservice.event;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
public class UserEvent implements Events {
    private String eventType; // "signup" or "login"
    private String userId;
    private String email;
    private String name;
    private Long eventTime;
    private String key;
    private String topicName;
    private int partition;

}
