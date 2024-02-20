package com.car.sns.controller;

import com.car.sns.config.SecurityConfigTest;
import com.car.sns.dto.ArticleCommentDto;
import com.car.sns.dto.request.ArticleCommentRequest;
import com.car.sns.service.ArticleCommentService;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("TODO")
@Import(SecurityConfigTest.class)
@WebMvcTest(ArticleCommentController.class)
class ArticleCommentControllerTest {

    private final MockMvc mockMvc;

    public ArticleCommentControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @MockBean
    private ArticleCommentService articleCommentService;

    @DisplayName("given_when_then")
    @WithUserDetails(
            value = "seo",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    @Test
    void givenCommentInfo_whenSavingComment_thenReturnArticleCommentView() throws Exception {
        //given
        Long articleId = 1L;
        willDoNothing().given(articleCommentService).saveArticleComment(any(ArticleCommentDto.class));

        //when
        mockMvc.perform(post("/comments/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/articles/detail/" + articleId));

        //then
        then(articleCommentService).should().saveArticleComment(any(ArticleCommentDto.class));
    }
}