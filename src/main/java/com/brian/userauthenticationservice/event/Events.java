package com.brian.userauthenticationservice.event;

public interface Events {

    String getTopicName();
    String getKey();
    String getUserId();
    int getPartition();
}
