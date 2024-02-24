package com.car.sns.domain.user.service;

import com.car.sns.domain.user.model.UserAccountDto;
import com.car.sns.infrastructure.jpaRepository.UserAccountJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAccountReadService {

    private final UserAccountJpaRepository userAccountJpaRepository;

    /**
     *  Optional로 반환하는 이유는 해당 service에서 결정하는 것이 아니라
     *  user만 찾고 이후 단계는 앞단에서 맞는 exception으로 맞춰줄 것
     */
    public Optional<UserAccountDto> searchUser(String username) {
        return userAccountJpaRepository.findByUserId(username)
                .map(UserAccountDto::from);
    }

}
