package com.car.sns.service;

import com.car.sns.domain.comment.service.ArticleCommentWriteService;
import com.car.sns.domain.board.model.entity.Article;
import com.car.sns.domain.comment.model.entity.ArticleComment;
import com.car.sns.domain.comment.model.ArticleCommentDto;
import com.car.sns.domain.comment.service.read.ArticleCommentReadService;
import com.car.sns.domain.user.model.UserAccountDto;
import com.car.sns.infrastructure.jpaRepository.ArticleCommentJpaRepository;
import com.car.sns.domain.board.repository.ArticleRepository;
import org.junit.jupiter.api.Disabled;
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
    private ArticleCommentReadService readSut;
    @InjectMocks
    private ArticleCommentWriteService writeSut;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private ArticleCommentJpaRepository articleCommentRepository;

    @Disabled("구현중")
    @DisplayName("게시글 ID로 조회시 관련 댓글을 반환한다")
    @Test
    void givenArticleId_whenSearchingArticleComments_thenArticleComments() {
        //given
        Long articleId = 1L;

        given(articleRepository.findById(articleId))
                .willReturn(Optional.of(any(Article.class)));

        //when
        List<ArticleCommentDto> articleComments = readSut.searchArticleComment(articleId);

        //then
        assertThat(articleComments).isNotNull();
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("댓글 정보를 입력하면 댓글을 저장")
    @Test
    void givenArticleCommentInfo_whenSavingArticleComments_thenSaveArticleComment() {
        //given
        ArticleCommentDto commentDto = createdArticleCommentDto("comment content");
        given(articleRepository.getReferenceById(anyLong())).willReturn(createArticle());
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        //when
        writeSut.saveArticleComment(commentDto);

        //then
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    private ArticleCommentDto createdArticleCommentDto(String content) {
        UserAccountDto userAccountDto = UserAccountDto.of(null, "uno", "uno", "pw", "email@email.com", "nickname", null);
        return ArticleCommentDto.of(1L, userAccountDto, content);
    }

    private Article createArticle() {
        UserAccountDto userAccountDto = UserAccountDto.of(null, null, "uno", "pw", "email@email.com", "nickname", null);
        return Article.of(userAccountDto.toEntity(), "title", "content");
    }
}