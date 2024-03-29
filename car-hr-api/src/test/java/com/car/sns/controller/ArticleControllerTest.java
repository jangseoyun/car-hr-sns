package com.car.sns.controller;

import com.car.sns.application.usecase.PaginationUseCase;
import com.car.sns.application.usecase.board.ArticleManagementUseCase;
import com.car.sns.config.SecurityConfigTest;
import com.car.sns.domain.board.model.ArticleDto;
import com.car.sns.domain.board.model.type.SearchType;
import com.car.sns.domain.board.service.read.ArticleReadService;
import com.car.sns.domain.user.model.UserAccountDto;
import com.car.sns.domain.user.model.entity.UserAccount;
import com.car.sns.presentation.controller.ArticleController;
import com.car.sns.presentation.model.response.ArticleDetailWithCommentResponse;
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
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("view controller - 게시글")
@Import(SecurityConfigTest.class)
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private ArticleReadService articleReadService;
    @MockBean
    private ArticleManagementUseCase articleManagementUseCase;
    @MockBean
    private PaginationUseCase paginationUseCase;

    public ArticleControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @WithUserDetails(
            value = "seo",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    @DisplayName("[view] write - 게시글 작성 페이지 - 정상 호출")
    void givenNothing_whenRequestingCreateView_thenReturnArticleCreateView() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/articles/create"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andExpect(view().name("features-post-create"));
    }

    @Test
    @WithMockUser
    @DisplayName("[view] read - 게시글 리스트 (게시판) 페이지 - 정상 호출")
    void givenNothing_whenRequestingArticlesView_thenReturnArticleView() throws Exception {
        given(articleReadService.getAllOrSearchArticles(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());
        given(paginationUseCase.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        mockMvc.perform(MockMvcRequestBuilders.get("/articles/index"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andExpect(view().name("features-posts"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("paginationBarNumbers"));

        then(articleReadService).should().getAllOrSearchArticles(eq(null), eq(null), any(Pageable.class));
        then(paginationUseCase).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @Test
    @DisplayName("[view] read - 게시글 검색 기능 - 검색어와 함께 호출: 정상 호출")
    void givenSearchKeyword_whenSearchingArticles_thenReturnArticleWithComments() throws Exception {
        //기본 index controller를 이용하여 키워드 검색 전달 (검색 타입, 키워드 전달)
        SearchType searchTitle = SearchType.TITLE;
        String searchKeyword = "Donec";

        given(articleReadService.getAllOrSearchArticles(eq(searchTitle), eq(searchKeyword), any(Pageable.class))).willReturn(Page.empty());
        given(paginationUseCase.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        mockMvc.perform(MockMvcRequestBuilders.get("/articles/index")
                        .queryParam("searchType", searchTitle.name())
                        .queryParam("searchKeyword", searchKeyword)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andExpect(view().name("features-posts"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("searchTypes"));

        then(articleReadService).should().getAllOrSearchArticles(eq(searchTitle), eq(searchKeyword), any(Pageable.class));
        then(paginationUseCase).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @Test
    @WithUserDetails(
            value = "seo",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    @DisplayName("[view] read - 게시글 상세 페이지 - 정상 호출")
    void givenNothing_whenRequestingArticlesView_thenReturnArticleDetail() throws Exception {
        Long articleId = 1L;
        given(articleReadService.getArticleDetailWithComments(articleId)).willReturn(createdArticleWithCommentsDto());

        mockMvc.perform(MockMvcRequestBuilders.get("/articles/detail/" + articleId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andExpect(view().name("features-posts-detail"))
                .andExpect(model().attributeExists("articleDetail"));

        then(articleReadService).should().getArticleDetailWithComments(articleId);
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

    @Disabled("구조변경으로 hashtag test 분리")
    @Test
    @WithUserDetails(
            value = "seo",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    @DisplayName("[view] get - 게시글 해시태그 검색 전용 페이지 - 정상 호출")
    void givenNothing_whenRequestingArticlesView_thenReturnSearchHashtag() throws Exception {
        /*List<String> hashtags = List.of("#pink", "#red", "blue");
        given(articleReaderUseCase.searchArticlesViaHashtag(eq(null), any(Pageable.class))).willReturn(Page.empty());
        //given(articleService.getHashtags()).willReturn(hashtags);
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(1, 2, 3, 4, 5));

        mockMvc.perform(MockMvcRequestBuilders.get("/articles/hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("hashtags"))
                .andExpect(model().attributeExists("paginationBarNumbers"))
                .andExpect(model().attribute("searchType", SearchType.HASHTAG));

        then(articleService).should().searchArticlesViaHashtag(eq(null), any(Pageable.class));
        then(articleService).should().getHashtags();
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());*/
    }

    private ArticleDto createdArticle() {
        return ArticleDto.of(createUserAccountDto(createdUserAccount()),
                LocalDateTime.now(),
                "Kamilah",
                "title",
                "content");
    }

    private ArticleDetailWithCommentResponse createdArticleWithCommentsDto() {
        return ArticleDetailWithCommentResponse.of(
                1L,
                createUserAccountDto(createdUserAccount()),
                "title",
                "content",
                LocalDateTime.now(),
                "seo",
                LocalDateTime.now(),
                "seo");
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