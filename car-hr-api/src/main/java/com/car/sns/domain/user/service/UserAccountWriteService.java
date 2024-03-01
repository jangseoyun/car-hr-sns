package com.car.sns.domain.user.service;

import com.car.sns.application.usecase.user.UserManagementUseCase;
import com.car.sns.domain.user.model.UserAccountDto;
import com.car.sns.domain.user.model.entity.UserAccount;
import com.car.sns.exception.CarHrSnsAppException;
import com.car.sns.infrastructure.jpaRepository.UserAccountJpaRepository;
import com.car.sns.presentation.model.request.RegisterAccountRequest;
import com.car.sns.security.KakaoOAuth2Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.car.sns.exception.model.AppErrorCode.USER_ALREADY_EXIST_ACCOUNT;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserAccountWriteService implements UserManagementUseCase {

    private final UserAccountJpaRepository userAccountJpaRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * kakao 자동 로그인 OAuth를 호출한 뒤 사용자 정보 내부 DB 저장
     */
    public UserAccountDto saveKakaoUserAccount(KakaoOAuth2Response kakaoOAuth2Response, String dummyPassword) {
        UserAccount userAccount = kakaoOAuth2Response.toUserAccount(dummyPassword);
        return UserAccountDto.from(userAccountJpaRepository.save(userAccount));
    }

    /**
     * 서비스내에서 사용자 회원가입
     */
    @Override
    public UserAccountDto userRegisterAccount(RegisterAccountRequest registerAccountRequest) {
        //사용자 존재 여부 검증
        userAccountJpaRepository.findByUserId(registerAccountRequest.userId())
                .ifPresent(it -> {
                    throw new CarHrSnsAppException(USER_ALREADY_EXIST_ACCOUNT, USER_ALREADY_EXIST_ACCOUNT.getMessage());
                });

        UserAccount savedUserAccount = userAccountJpaRepository.save(
                UserAccount.of(registerAccountRequest.userId(),
                        passwordEncoder.encode(registerAccountRequest.password()),
                        registerAccountRequest.email(),
                        registerAccountRequest.nickname(),
                        registerAccountRequest.memo()
                )
        );
        log.info("saved user: {}", savedUserAccount);

        return UserAccountDto.from(savedUserAccount);
    }
}

