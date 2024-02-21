package com.car.sns.config;

import com.car.sns.domain.user.entity.UserAccount;
import com.car.sns.domain.user.repository.UserAccountRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class SecurityConfigTest {
    @MockBean
    private UserAccountRepository userAccountRepository;

    @BeforeTestMethod
    public void SecuritySetup() {
        given(userAccountRepository.findByUserId(anyString())).willReturn(Optional.of(UserAccount.of(
                "seo",
                "password",
                "seo@email.com",
                "nickname",
                "memo"
                )
        ));
    }
}
