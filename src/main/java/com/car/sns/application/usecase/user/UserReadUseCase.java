package com.car.sns.application.usecase.user;

import com.car.sns.domain.user.model.LoginDto;

public interface UserReadUseCase {
    String login(LoginDto loginDto);
}
