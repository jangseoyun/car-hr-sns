package com.car.sns.presentation.model.request;

public record UserLoginRequest(
        String userId,
        String password
) {
    public UserLoginRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
