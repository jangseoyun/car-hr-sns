package com.car.sns.service;

import com.car.sns.domain.Article;
import com.car.sns.domain.UserAccount;
import com.car.sns.domain.type.SearchType;
import com.car.sns.dto.ArticleDto;
import com.car.sns.dto.ArticleModifyDto;
import com.car.sns.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @InjectMocks
    private ArticleService sut;
    @Mock
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {

    }

    @DisplayName("[검색] - 검색어 없이 게시글을 조회할 경우 게시글 페이지 반환")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnArticleList() {
        //given
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findAll(pageable)).willReturn(Page.empty());

        //when
        Page<ArticleDto> articles = sut.searchArticles(null, null, pageable);

        //then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findAll(pageable);
    }

    @DisplayName("[조회] - 게시글 타이틀 조회 시 관련 게시글 반환")
    @Test
    void givenArticleId_whenSearchingArticles_thenReturnArticleList() {
        //given
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByTitleContaining("title", pageable)).willReturn(Page.empty());

        //when
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "title", pageable);

        //then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findByTitleContaining("title", pageable);
    }

    @DisplayName("[조회] - 게시글 ID 입력시 해당 게시글 단건 반환")
    @Test
    void givenArticleId_whenSelectingArticle_thenReturnArticle() {
        //given
        Long articleId = 1L;
        Article article = createdArticle();
        given(articleRepository.findById(articleId))
                .willReturn(Optional.of(article));

        //when
        sut.getArticle(articleId);

        //then
        then(articleRepository)
                .should()
                .findById(articleId);
    }

    @DisplayName("[조회] - 게시글 ID 입력시 해당 게시글과 댓글 리스트 반환")
    @Test
    void givenArticleId_whenSelectingArticleAndComments_thenReturnArticleAndComments() {
        //given
        Long articleId = 1L;
        Article article = createdArticle();
        given(articleRepository.findById(articleId))
                .willReturn(Optional.of(article));

        //when
        sut.getArticleWithComments(articleId);

        //then
        then(articleRepository)
                .should()
                .findById(articleId);
    }

    @DisplayName("게시글의 정보를 입력하면, 게시글을 저장한다")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavedArticle() {
        //given
        Article article = createdArticle();
        given(articleRepository.save(any(Article.class)))
                .willReturn(null);

        //when
        sut.createArticle(createdArticleDto(article));

        //then
        then(articleRepository)
                .should()
                .save(any(Article.class));
    }

    @Test
    void givenArticleIdAndModifiedInfo_whenUpdatingArticle_thenUpdateArticle() {
        //given
        given(articleRepository.save(any(Article.class)))
                .willReturn(null);

        //when
        sut.updateArticle(ArticleModifyDto.of(1L, 1L, "title", null, null));

        //then
        then(articleRepository)
                .should()
                .save(any(Article.class));
    }

    private Article createdArticle() {
        UserAccount userAccount = createdUserAccount();
        return Article.of(userAccount, "title", "content", "hashtag");
    }

    private UserAccount createdUserAccount() {
        return UserAccount.of("userId", "userPassword", "user@email.com", "nickname", null);
    }

    private ArticleDto createdArticleDto(Article article) {
        return ArticleDto.from(article);
    }

    /* @DisplayName("게시글의 ID와 수정 정보를 입력하면, 게시글을 수정한다")


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
    }*/

}