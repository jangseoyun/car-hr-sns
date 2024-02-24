package com.car.sns.repository;

import com.car.sns.domain.board.model.entity.Article;
import com.car.sns.domain.user.model.entity.UserAccount;
import com.car.sns.infrastructure.jpaRepository.ArticleCommentJpaRepository;
import com.car.sns.domain.board.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import(JpaRepositoryTest.TestJpaConfig.class)
@DataJpaTest
@DisplayName("JPA 연결 테스트.")
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentJpaRepository articleCommentRepository;

    public JpaRepositoryTest(
            @Autowired  ArticleRepository articleRepository,
            @Autowired ArticleCommentJpaRepository articleCommentRepository)
    {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @Test
    @DisplayName("select test")
    void givenTestData_whenSelecting_thenWorksFine() {

        List<Article> articles = articleRepository.findAll();

        assertThat(articles)
                .isNotNull()
                .hasSize(0);
    }

    @Test
    @DisplayName("insert test")
    void givenTestData_whenInserting_thenWorksFine() {
        UserAccount userAccount = UserAccount.of("userid", "password", "email@naver.com", "nickname", null);

        long previousCount = articleRepository.count();
        Article article = Article.of(userAccount,"new title", "new content");

        articleRepository.save(article);

        assertThat(articleRepository.count())
                .isEqualTo(previousCount + 1);
    }

    @Test
    @DisplayName("update test")
    void givenTestData_whenUpdating_thenWorksFine() {

        Article article = articleRepository.findById(1L).orElseThrow();


        Article savedArticle = articleRepository.saveAndFlush(article);
    }

    @Test
    @DisplayName("delete test")
    void givenTestData_whenDeleting_thenWorksFine() {

        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentSize = article.getArticleComment().size();

        articleRepository.delete(article);

        assertThat(articleRepository.count())
                .isEqualTo(previousArticleCount - 1);

        assertThat(articleCommentRepository.count())
                .isEqualTo(previousArticleCommentCount - deletedCommentSize);
    }

    @DisplayName("[GET] - 대댓글 조회")
    @Test
    void given_when_then() {
        //given


        //when

        //then
    }

    @EnableJpaAuditing
    @TestConfiguration
    public static class TestJpaConfig {
        @Bean
        public AuditorAware<String> auditorAware() {
            return () -> Optional.of("seo");
        }
    }

}