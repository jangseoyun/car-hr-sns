package com.car.sns.domain.user.model;

import com.car.sns.presentation.model.request.UserLoginRequest;

public record LoginDto(
        String userId,
        String password
) {
    public static LoginDto from(UserLoginRequest loginRequest) {
        return new LoginDto(loginRequest.userId(), loginRequest.password());
    }
}
