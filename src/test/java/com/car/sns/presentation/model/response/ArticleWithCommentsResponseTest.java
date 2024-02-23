package com.car.sns.presentation.model.response;

import com.car.sns.domain.comment.model.ArticleCommentDto;
import com.car.sns.domain.hashtag.model.HashtagDto;
import com.car.sns.domain.user.model.UserAccountDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DisplayName("DTO - 댓글을 포함한 게시글 응답 테스트")
class ArticleWithCommentsResponseTest {

    @DisplayName("자식 댓글이 없는 게시글 + 댓글 DTO를 api 응답으로 변환할 때, 댓글을 시간 내림차순 + ID 오름차순으로 정리한다")
    @Test
    void given_when_then() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Set<ArticleCommentDto> noParentCommentDtoSet = createNoParentCommentDtoSet();

        //when
        ArticleWithCommentsResponse actual = ArticleWithCommentsResponse.of(
                noParentCommentDtoSet,
                "title",
                "content",
                LocalDateTime.now(),
                "seo@email.com",
                "nickname"
        );

        //then
        assertThat(actual.articleCommentDtos()).isEqualTo(noParentCommentDtoSet);
    }

    @DisplayName("자식 댓글이 있는 게시글 + 댓글 DTO를 api 응답으로 변환할 때, 댓글을 시간 내림차순 + ID 오름차순으로 정리한다")
    @Test
    void given_when_thenChildComments() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Set<ArticleCommentDto> parentCommentAndChildComments = Set.of(
                createArticleCommentDto(1L, null),
                createArticleCommentDto(2L, 1L),
                createArticleCommentDto(3L, 2L),
                createArticleCommentDto(4L, 3L),
                createArticleCommentDto(5L, 4L),
                createArticleCommentDto(6L, 5L),
                createArticleCommentDto(7L, 6L),
                createArticleCommentDto(8L, 7L)
        );

        //when
        ArticleWithCommentsResponse actual = ArticleWithCommentsResponse.of(
                parentCommentAndChildComments,
                "title",
                "content",
                LocalDateTime.now(),
                "seo@email.com",
                "nickname"
        );

        //then
        assertThat(actual.articleCommentDtos()).isEqualTo(parentCommentAndChildComments);
        //TODO: 시간을 받아주는것을 넣어야 한다
        /*assertThat(actual.articleCommentDtos())
                .containsExactly(
                        createArticleCommentDto(5L, 4L),
                        createArticleCommentDto(6L, 5L),
                        createArticleCommentDto(1L, null)
                )
                .flatExtracting(ArticleCommentDto::childComments)
                .containsExactly(

                );*/
    }

    private ArticleWithCommentsResponse createArticleWithNoParentCommentsDto(Set<ArticleCommentDto> articleCommentDtos) {
        return ArticleWithCommentsResponse.of(
                articleCommentDtos,
                "title",
                "content",
                LocalDateTime.now(),
                "seo@email.com",
                "nickname"
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "uno",
                "password",
                "uno@mail.com",
                "Uno",
                "This is memo"
        );
    }

    private ArticleCommentDto createArticleCommentDto(Long id, Long parentCommentId) {
        return ArticleCommentDto.of(
                id,
                parentCommentId,
                createUserAccountDto(),
                "comment content"
        );
    }

    private Set<ArticleCommentDto> createNoParentCommentDtoSet() {
        return Set.of(
                createArticleCommentDto(1L, null),
                createArticleCommentDto(2L, null),
                createArticleCommentDto(3L, null),
                createArticleCommentDto(4L, null),
                createArticleCommentDto(5L, null),
                createArticleCommentDto(6L, null),
                createArticleCommentDto(7L, null)
        );
    }
}