package com.example.demo.model;

public class UserSession {
    private static UserSession instance;
    private UserType userType;

    public enum UserType {
        USER, GUEST, NONE
    }

    private UserSession() {
        this.userType = UserType.NONE;
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void login(UserType type) {
        this.userType = type;
    }

    public void logout() {
        this.userType = UserType.NONE;
    }

    public UserType getUserType() {
        return userType;
    }

    public boolean isLoggedIn() {
        return userType != UserType.NONE;
    }

    public boolean isUser() {
        return userType == UserType.USER;
    }

    public boolean isGuest() {
        return userType == UserType.GUEST;
    }
}
