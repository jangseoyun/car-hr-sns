package com.car.sns.domain.user.service;

import com.car.sns.domain.user.model.entity.UserAccount;
import com.car.sns.infrastructure.jpaRepository.UserAccountJpaRepository;
import com.car.sns.security.CarAppPrincipal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("TODO")
@ExtendWith(MockitoExtension.class)
class UserAccountReadServiceTest {

    @InjectMocks
    private UserAccountReadService sut;

    @Mock
    private UserAccountJpaRepository userAccountJpaRepository;

    @DisplayName("회원 아이디가 존재하면 회원 데이터를 Optional로 반환")
    @Test
    void givenUsername_whenSearchingUserAccount_thenReturnOptionalUserAccount() {
        //given
        String username = "seo";
        given(userAccountJpaRepository.findByUserId(username))
                .willReturn(Optional.of(createUserAccount(username)));

        //when
        Optional<CarAppPrincipal> result = sut.searchUser(username);

        //then
        assertThat(result).isPresent();
        then(userAccountJpaRepository).should().findByUserId(username);

    }

    private UserAccount createUserAccount(String username) {
        return createUserAccount(username, null);
    }

    private UserAccount createSigningUpUserAccount(String username) {
        return createUserAccount(username, username);
    }

    private UserAccount createUserAccount(String username, String createdBy) {
        return UserAccount.of(
                username,
                "password",
                "e@mail.com",
                "nickname",
                "memo",
                createdBy
        );
    }
}