package com.car.sns.domain.user.service;

import com.car.sns.application.usecase.user.UserReadUseCase;
import com.car.sns.domain.user.model.LoginDto;
import com.car.sns.domain.user.model.UserAccountDto;
import com.car.sns.exception.CarHrSnsAppException;
import com.car.sns.infrastructure.UserCacheRepository;
import com.car.sns.infrastructure.jpaRepository.UserAccountJpaRepository;
import com.car.sns.presentation.model.response.UserLoginResponse;
import com.car.sns.security.CarAppPrincipal;
import com.car.sns.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.car.sns.exception.model.AppErrorCode.INVALID_USER_ID_PASSWORD;
import static com.car.sns.exception.model.AppErrorCode.USER_NOTFOUND_ACCOUNT;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAccountReadService implements UserReadUseCase {

    @Value("${jwt.token.secret}")
    private String secretKey;
    private long expireTimeMs = 1000 * 60 * 60; //TODO:test를 위한것으로 Redis implement
    private final PasswordEncoder passwordEncoder;
    private final UserAccountJpaRepository userAccountJpaRepository;
    private final UserCacheRepository userCacheRepository;

    /**
     *  Optional로 반환하는 이유는 해당 service에서 결정하는 것이 아니라
     *  user만 찾고 이후 단계는 앞단에서 맞는 exception으로 맞춰줄 것
     *  캐시에 올라간 상태인지 확인 후 DB 요청
     */
    public Optional<CarAppPrincipal> searchUser(String username) {
        CarAppPrincipal carAppPrincipal = userCacheRepository.getUser(username).orElseGet(() ->
                userAccountJpaRepository.findByUserId(username)
                        .map(UserAccountDto::from)
                        .map(CarAppPrincipal::from)
                        .orElseThrow(() -> {
                            throw new CarHrSnsAppException(USER_NOTFOUND_ACCOUNT, USER_NOTFOUND_ACCOUNT.getMessage());
                        })
        );
        return Optional.of(carAppPrincipal);
    }

    @Override
    public UserLoginResponse login(LoginDto loginDto) {
        userAccountJpaRepository.findByUserId(loginDto.userId())
                .filter(user -> passwordEncoder.matches(loginDto.password(), user.getUserPassword()))
                .map(UserAccountDto::from)
                .ifPresentOrElse(
                        userAccountDto -> userCacheRepository.setUser(CarAppPrincipal.from(userAccountDto)),
                        () -> {
                    throw new CarHrSnsAppException(INVALID_USER_ID_PASSWORD, INVALID_USER_ID_PASSWORD.getMessage());
                });

        log.info("login userAccountDto: {}", loginDto);
        return  UserLoginResponse.of(JwtUtil.createToken(loginDto.userId(), secretKey, expireTimeMs));
    }
}
