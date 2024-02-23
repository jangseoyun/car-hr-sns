package com.car.sns.domain.user.service;

import com.car.sns.domain.user.entity.UserAccount;
import com.car.sns.domain.user.model.UserAccountDto;
import com.car.sns.infrastructure.repository.UserAccountJpaRepository;
import com.car.sns.security.KakaoOAuth2Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserAccountWriteService {

    private final UserAccountJpaRepository userAccountJpaRepository;

    public UserAccountDto saveUserAccount(KakaoOAuth2Response kakaoOAuth2Response, String dummyPassword) {
        UserAccount userAccount = kakaoOAuth2Response.toUserAccount(dummyPassword);
        return UserAccountDto.from(userAccountJpaRepository.save(userAccount));
    }
}

