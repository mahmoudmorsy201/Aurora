package com.iti.aurora.model;

import java.io.Serializable;

public class User implements Serializable {

    private String userName;
    private String phoneNumber;

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName, String phoneNumber) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}