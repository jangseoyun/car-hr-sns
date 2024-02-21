package com.car.sns.service;

import com.car.sns.application.usecase.ArticleManagementUseCase;
import com.car.sns.application.usecase.ArticleReaderUseCase;
import com.car.sns.domain.board.entity.Article;
import com.car.sns.domain.user.entity.UserAccount;
import com.car.sns.domain.board.model.type.SearchType;
import com.car.sns.domain.board.model.ArticleDto;
import com.car.sns.presentation.model.request.ArticleModifyRequest;
import com.car.sns.domain.board.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @Mock
    private ArticleReaderUseCase sut;
    @Mock
    private ArticleManagementUseCase articleManagementUseCase;
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
        Page<ArticleDto> articles = sut.getAllOrSearchArticles(null, null, pageable);

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
        Page<ArticleDto> articles = sut.getAllOrSearchArticles(SearchType.TITLE, "title", pageable);

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
        sut.getArticleDetailWithComments(articleId);

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
        articleManagementUseCase.createArticle(createdArticleDto(article));

        //then
        then(articleRepository)
                .should()
                .save(any(Article.class));
    }

    @DisplayName("[게시글 단건 조회] - 게시글이 존재하지 않는 경우 예외 발생")
    @Test
    void givenNonexistentArticleId_whenSearchingArticle_thenThrowsException() {
        //given
        Long articleId = 0L;
        given(articleRepository.findById(articleId))
                .willReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> sut.getArticle(articleId));

        //then
        assertThat(throwable)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("게시글이 존재하지 않습니다: - articleId: " + articleId);

        then(articleRepository).should().findById(articleId);
    }

    @Disabled("구조 변경으로 인한 hashtag test 분리")
    @DisplayName("[게시글 검색] - 해시태그 키워드를 통해 게시글 검색 : 검색어가 없는 경우")
    @Test
    void givenHashtagNull_whenSearchingArticle_thenReturnNothing() {
       /* //given
        Pageable pageable = Pageable.ofSize(20);

        //when
        Page<ArticleDto> articles = sut.searchArticlesViaHashtag(null, pageable);

        //then
        assertThat(articles).isEqualTo(Page.empty(pageable));
        then(articleRepository).shouldHaveNoInteractions();*/
    }

    @Disabled("구조 변경으로 인한 hashtag test 분리")
    @DisplayName("[해시태그 검색] - 해시태그 키워드를 통해 게시글 검색 : 검색어가 있는 경우")
    @Test
    void givenHashtag_whenSearchingArticle_thenReturnArticles() {
        //given
        /*Pageable pageable = Pageable.ofSize(20);
        String searchKeyword = "#pink";
        //TODO: hashtag 검색 시 #을 넣어서 검색하는 경우 중복으로 인한 에러 발생
        given(articleRepository.findByHashtag(searchKeyword, pageable)).willReturn(Page.empty(pageable));

        //when
        Page<ArticleDto> articles = sut.searchArticlesViaHashtag(searchKeyword, pageable);

        //then
        assertThat(articles).isEqualTo(Page.empty(pageable));
        then(articleRepository).should().findByHashtag(searchKeyword, pageable);*/
    }

    @Disabled("구조 변경으로 인한 hashtag test 분리")
    @DisplayName("[해시태그 조회] - 해시태그 중복 제거하여 리스트로 반환")
    @Test
    void givenNothing_whenCalling_thenReturnHashtags() {
        /*//given
        List<String> expectedHashtags = List.of("#pink", "#red", "yellow");
        given(articleRepository.findAllDistinctHashtag()).willReturn(expectedHashtags);

        //when
        List<String> hashtags = sut.getHashtags();

        //then
        assertThat(hashtags).isEqualTo(expectedHashtags);
        then(articleRepository).should().findAllDistinctHashtag();*/
    }


    @Test
    @Disabled("구현 중")
    void givenArticleIdAndModifiedInfo_whenUpdatingArticle_thenUpdateArticle() {
        //given
        given(articleRepository.save(any(Article.class)))
                .willReturn(null);

        //when
        articleManagementUseCase.updateArticle(
                ArticleModifyRequest.of(1L, "title", "content", "seo"),
                "seo"
        );

        //then
        then(articleRepository)
                .should()
                .save(any(Article.class));
    }

    @DisplayName("게시글의 ID를 입력하면 게시글을 삭제한다")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeleteArticle() {
        Long articleId = 1L;

        //given
        willDoNothing()
                .given(articleRepository)
                .deleteById(articleId);

        //when
        articleManagementUseCase.deleteArticle(articleId);

        //then
        then(articleRepository)
                .should()
                .deleteById(articleId);
    }

    private Article createdArticle() {
        UserAccount userAccount = createdUserAccount();
        return Article.of(userAccount, "title", "content");
    }

    private UserAccount createdUserAccount() {
        return UserAccount.of("userId", "userPassword", "user@email.com", "nickname", null);
    }

    private ArticleDto createdArticleDto(Article article) {
        return ArticleDto.from(article);
    }

}