package com.car.sns.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Data rest API test")
@Disabled("Spring Data REST 통합테스트는 불필요하므로 제외한다")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class DataRestTest {

    private final MockMvc mockMvc;

    public DataRestTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @DisplayName("게시글 리스트 조회")
    @Test
    void givenNoting_whenRequestingArticle_thenReturnsArticlesJsonResponse() throws Exception {
        mockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
                .andReturn();
    }

    @DisplayName("게시글 단건 조회")
    @Test
    void givenNoting_whenRequestingArticle_thenReturnsArticleJsonResponse() throws Exception {
        mockMvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
                .andReturn();
    }

    @DisplayName("게시글 댓글 리스트 조회")
    @Test
    void givenNoting_whenRequestingArticleComments_thenReturnsCommentsJsonResponse() throws Exception {
        mockMvc.perform(get("/api/articleComments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
                .andReturn();
    }
}
