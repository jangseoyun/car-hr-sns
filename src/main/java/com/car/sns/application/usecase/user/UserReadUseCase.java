package com.car.sns.application.usecase.user;

import com.car.sns.domain.user.model.LoginDto;
import com.car.sns.domain.user.model.UserAccountDto;
import com.car.sns.presentation.model.response.UserLoginResponse;

import java.util.Optional;

public interface UserReadUseCase {
    Optional<UserAccountDto> searchUser(String username);
    UserLoginResponse login(LoginDto loginDto);
}
