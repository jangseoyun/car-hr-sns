package com.car.sns.presentation.model.response;

public record UserLoginResponse(
        String token
) {
    public static UserLoginResponse of(String token) {
        return new UserLoginResponse(token);
    }
}
