package com.car.sns.service;

import com.car.sns.domain.Article;
import com.car.sns.domain.ArticleComment;
import com.car.sns.dto.ArticleCommentDto;
import com.car.sns.repository.ArticleCommentRepository;
import com.car.sns.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.then;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks
    private ArticleCommentService sut;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private ArticleCommentRepository articleCommentRepository;

    @DisplayName("게시글 ID로 조회시 관련 댓글을 반환한다")
    @Test
    void givenArticleId_whenSearchingArticleComments_thenArticleComments() {
        //given
        Long articleId = 1L;

        given(articleRepository.findById(articleId))
                .willReturn(Optional.of(any(Article.class)));

        //when
        List<ArticleCommentDto> articleComments = sut.searchArticleComment(articleId);

        //then
        assertThat(articleComments).isNotNull();
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("댓글 정보를 입력하면 댓글을 저장")
    @Test
    void givenArticleCommentInfo_whenSavingArticleComments_thenSaveArticleComment() {
        //given
        given(articleCommentRepository.save(any(ArticleComment.class)))
                .willReturn(null);

        //when
        sut.saveArticleComment("content comment");

        //then
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }
}