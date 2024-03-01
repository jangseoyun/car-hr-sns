package com.car.sns.application.usecase.user;

import com.car.sns.domain.user.model.LoginDto;
import com.car.sns.presentation.model.response.UserLoginResponse;
import com.car.sns.security.CarAppPrincipal;

import java.util.Optional;

public interface UserReadUseCase {
    Optional<CarAppPrincipal> searchUser(String username);
    UserLoginResponse login(LoginDto loginDto);
}
