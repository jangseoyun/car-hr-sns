package com.car.sns.application.usecase.user;

import com.car.sns.domain.user.model.UserAccountDto;
import com.car.sns.presentation.model.request.RegisterAccountRequest;

public interface UserManagementUseCase {
    UserAccountDto userRegisterAccount(RegisterAccountRequest registerAccountRequest);
}
