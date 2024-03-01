package com.car.sns.presentation.controller;

import com.car.sns.application.usecase.user.UserManagementUseCase;
import com.car.sns.application.usecase.user.UserReadUseCase;
import com.car.sns.domain.user.model.LoginDto;
import com.car.sns.domain.user.model.UserAccountDto;
import com.car.sns.presentation.model.request.RegisterAccountRequest;
import com.car.sns.presentation.model.request.UserLoginRequest;
import com.car.sns.presentation.model.response.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserManagementUseCase userManagementUseCase;
    private final UserReadUseCase userReadUseCase;

    @PostMapping("/register")
    public ResponseEntity<Result> userRegister(@RequestBody RegisterAccountRequest registerAccountRequest) {
        UserAccountDto userAccountDto = userManagementUseCase.userRegisterAccount(registerAccountRequest);
        return ResponseEntity
                .ok()
                .body(Result.success(userAccountDto));
    }

    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestBody UserLoginRequest userLoginRequest) {
        log.info("userLoginRequest: {}", userLoginRequest);
        return ResponseEntity
                .ok()
                .body(Result.success(userReadUseCase.login(LoginDto.from(userLoginRequest))));
    }
}
