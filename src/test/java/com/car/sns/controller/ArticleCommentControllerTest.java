package com.car.sns.controller;

import com.car.sns.config.SecurityConfigTest;
import com.car.sns.domain.comment.entity.ArticleComment;
import com.car.sns.domain.comment.model.ArticleCommentDto;
import com.car.sns.presentation.controller.ArticleCommentController;
import com.car.sns.domain.comment.service.ArticleCommentWriteService;
import com.car.sns.presentation.model.request.ArticleCommentRequest;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
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
    private ArticleCommentWriteService articleCommentService;

    @DisplayName("댓글 등록")
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

    @DisplayName("대댓글 등록")
    @Test
    void given_when_then() throws Exception {
        //given
        Long articleId = 1L;
        ArticleCommentRequest commentRequest = ArticleCommentRequest.of(articleId, 1L, "대댓글 테스트");
        willDoNothing().given(articleCommentService).saveArticleComment(any(ArticleCommentDto.class));

        //when
        mockMvc.perform(post("/comments/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/detail/" + articleId))
                .andExpect(redirectedUrl("/articles" + articleId));

        //then
        then(articleCommentService).should().saveArticleComment(any(ArticleCommentDto.class));

    }
}