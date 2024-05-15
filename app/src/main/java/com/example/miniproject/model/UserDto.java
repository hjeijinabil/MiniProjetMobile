package com.example.miniproject.model;

import com.google.firebase.Timestamp;

import java.util.UUID;

public class UserDto {

    public String uuid;
    public String email;

    public UserDto(String uuid, String email) {
        this.uuid = uuid;
        this.email = email;
    }

    public UserDto() {

    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
