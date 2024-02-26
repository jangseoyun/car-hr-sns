package com.car.sns.domain.user.service;

import com.car.sns.infrastructure.jpaRepository.UserAccountJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("사용자 등록 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class UserAccountWriteServiceTest {

    @InjectMocks
    private UserAccountWriteService sut;
    @Mock
    private UserAccountJpaRepository userAccountJpaRepository;

    @DisplayName("given_when_then")
    @Test
    void givenKakaoOAuth2Response_whenSavingUserAccount_thenReturnUserAccountDto() {
        //given

        //when

        //then
    }

    @DisplayName("given_when_then")
    @Test
    void given_when_then() {
        //given

        //when

        //then
    }
}