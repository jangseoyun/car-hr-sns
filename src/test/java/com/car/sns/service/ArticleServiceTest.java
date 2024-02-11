package com.car.sns.service;

import com.car.sns.domain.Article;
import com.car.sns.domain.type.SearchType;
import com.car.sns.dto.ArticleDto;
import com.car.sns.dto.ArticleModifyDto;
import com.car.sns.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @InjectMocks
    private ArticleService sut;
    @Mock
    private ArticleRepository articleRepository;

    @DisplayName("[검색] - 검색 기능 구현")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnArticleList() {
        //given

        //when
        List<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search title keyword");

        //then
        assertThat(articles).isNotNull();
    }

    @DisplayName("[조회] - 게시글 조회시 게시글 반환")
    @Test
    void givenArticleId_whenSearchingArticles_thenReturnArticleList() {
        //given

        //when
        ArticleDto articles = sut.searchArticles(SearchType.ID, 1L);

        //then
        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글 정보를 입력하면 게시글을 생성한다")
    @Test
    void givenArticleInfo_whenSavingArticle_thenReturnArticle() {
        //given
        ArticleDto articleDto = ArticleDto.of(
                LocalDateTime.now(),
                "seo-test",
                "test-title",
                "test-content",
                "hashtag"
        );

        given(articleRepository.save(any(Article.class)))
                .willReturn(null);

        //when
        sut.searchArticles(articleDto);

        //then
        // save method가 호출되었는지 확인하는 것
        then(articleRepository)
                .should()
                .save(any(Article.class));
    }

    @DisplayName("게시글의 ID와 수정 정보를 입력하면, 게시글을 수정한다")
    @Test
    void givenArticleIdAndModifiedInfo_whenUpdatingArticle_thenUpdateArticle() {
        //given
        given(articleRepository.save(any(Article.class)))
                .willReturn(null);

        //when
        sut.updateArticle(1L, ArticleModifyDto.of("title", "content", "hashtag"));

        //then
        //save method가 호출되었는지 확인하는 것
        then(articleRepository)
                .should()
                .save(any(Article.class));
    }

    @DisplayName("게시글의 ID를 입력하면 게시글을 삭제한다")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeleteArticle() {
        //given
        willDoNothing()
                .given(articleRepository)
                .delete(any(Article.class));

        //when
        sut.deleteArticle(1L);

        //then
        //save method가 호출되었는지 확인하는 것
        then(articleRepository)
                .should()
                .delete(any(Article.class));
    }

}