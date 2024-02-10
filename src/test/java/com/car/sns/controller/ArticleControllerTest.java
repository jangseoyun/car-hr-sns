package com.car.sns.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static java.nio.file.Paths.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("view controller - 게시글")
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    private final MockMvc mockMvc;

    public ArticleControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Disabled("구현 중")
    @Test
    @DisplayName("[view] get - 게시글 리스트 (게시판) 페이지 - 정상 호출")
    void givenNothing_whenRequestingArticlesView_thenReturnArticleView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles"));
    }

    @Disabled("구현 중")
    @Test
    @DisplayName("[view] get - 게시글 상세 페이지 - 정상 호출")
    void givenNothing_whenRequestingArticlesView_thenReturnArticleDetail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("article/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComment"));
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
}