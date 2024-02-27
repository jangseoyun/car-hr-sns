package com.car.sns.presentation.controller;

import com.car.sns.application.usecase.like.LikeManagementUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("TODO")
@AutoConfigureMockMvc
@SpringBootTest(classes = LikeController.class)
class LikeControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private LikeManagementUseCase likeManagementUseCase;

    public LikeControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(print())
                .apply(springSecurity())
                .build();
    }

    @DisplayName("n번 게시글에 좋아요 등록")
    @WithMockUser
    @Test
    void givenLikeToArticleComment_whenSavingLike_thenSavedLikeArticleAndUser() throws Exception {
        //given
        Long articleId = 1L;
        String userId = "seoyun33";
        willDoNothing().given(likeManagementUseCase).saveLikeTarget(eq(articleId), eq(userId));

        //when
        mockMvc.perform(get("/like/" + articleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        then(likeManagementUseCase).should().saveLikeTarget(eq(articleId), eq(userId));
    }
}