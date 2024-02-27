package com.car.sns.domain.board.service;

import com.car.sns.domain.board.repository.ArticleJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

@DisplayName("TODO")
@ExtendWith(MockitoExtension.class)
class ArticleWriteServiceTest {

    @InjectMocks
    private ArticleWriteService sut;

    @Mock
    private ArticleJpaRepository articleJpaRepository;

    @DisplayName("given_when_then")
    @WithMockUser
    @Test
    void given_when_then() {
        //given

        //when

        //then
    }
}