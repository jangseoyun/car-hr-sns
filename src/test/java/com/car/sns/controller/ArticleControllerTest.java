package com.car.sns.controller;

import com.car.sns.config.SecurityConfig;
import com.car.sns.domain.UserAccount;
import com.car.sns.dto.ArticleDto;
import com.car.sns.dto.ArticleWithCommentDto;
import com.car.sns.dto.UserAccountDto;
import com.car.sns.service.ArticleService;
import com.car.sns.service.PaginationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.nio.file.Paths.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("view controller - 게시글")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;
    @MockBean
    private PaginationService paginationService;

    public ArticleControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @DisplayName("[view] write - 게시글 작성 페이지 - 정상 호출")
    void givenNothing_whenRequestingCreateView_thenReturnArticleCreateView() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/articles/create"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andExpect(view().name("features-post-create"));
    }

    @Test
    @DisplayName("[view] read - 게시글 리스트 (게시판) 페이지 - 정상 호출")
    void givenNothing_whenRequestingArticlesView_thenReturnArticleView() throws Exception {
        given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        mockMvc.perform(MockMvcRequestBuilders.get("/articles/index"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andExpect(view().name("features-posts"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("paginationBarNumbers"));

        then(articleService).should().searchArticles(eq(null), eq(null), any(Pageable.class));
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @Test
    @DisplayName("[view] read - 게시글 상세 페이지 - 정상 호출")
    void givenNothing_whenRequestingArticlesView_thenReturnArticleDetail() throws Exception {
        Long articleId = 1L;
        given(articleService.getArticleWithComments(articleId)).willReturn(createdArticleWithCommentsDto());

        mockMvc.perform(MockMvcRequestBuilders.get("/articles/detail/" + articleId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andExpect(view().name("features-posts-detail"))
                .andExpect(model().attributeExists("articleDetail"));

        then(articleService).should().getArticleWithComments(articleId);
    }

    @Disabled("구현 중")
    @Test
    @DisplayName("[view] get - 게시글 검색 전용 페이지 - 정상 호출")
    void givenNothing_whenRequestingArticlesView_thenReturnArticleSearch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/articles/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles/search"));
    }

    @Disabled("구현 중")
    @Test
    @DisplayName("[view] get - 게시글 해시태그 검색 전용 페이지 - 정상 호출")
    void givenNothing_whenRequestingArticlesView_thenReturnSearchHashtag() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles/search-hashtag"));
    }

    private ArticleDto createdArticle() {
        return ArticleDto.of(createUserAccountDto(createdUserAccount()),
                LocalDateTime.now(),
                "Kamilah",
                "title",
                "content",
                "hashtag");
    }

    private ArticleWithCommentDto createdArticleWithCommentsDto() {
        return ArticleWithCommentDto.of(
                1L,
                createUserAccountDto(createdUserAccount()),
                Set.of(),
                "title",
                "content",
                "hashtag",
                LocalDateTime.now(),
                "seo",
                LocalDateTime.now(),
                "uno");
    }



    private UserAccount createdUserAccount() {
        return UserAccount.of("userId",
                "userPassword",
                "user@email.com",
                "nickname",
                null);
    }

    private UserAccountDto createUserAccountDto(UserAccount entity) {
        return UserAccountDto.from(entity);
    }
}