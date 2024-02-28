package com.car.sns.presentation.model.request;

import com.car.sns.domain.comment.model.entity.ArticleComment;
import com.car.sns.domain.comment.model.ArticleCommentDto;
import com.car.sns.domain.user.model.UserAccountDto;
import com.car.sns.security.CarAppPrincipal;

/**
 * DTO for {@link ArticleComment}
 */
public record ArticleCommentRequest(
        Long articleId,
        Long parentCommentId,
        String content

) {

    public static ArticleCommentRequest of(Long articleId, Long parentCommentId, String content) {
        return new ArticleCommentRequest(articleId, parentCommentId, content);
    }

    public static ArticleCommentRequest of(Long articleId, String content) {
        return ArticleCommentRequest.of(articleId, null, content);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
        return ArticleCommentDto.of(articleId, parentCommentId, userAccountDto, content);
    }

    public ArticleCommentDto toDto(CarAppPrincipal carAppPrincipal) {
        return ArticleCommentDto.of(
                articleId,
                parentCommentId,
                carAppPrincipal.userAccountDto(),
                content);
    }
}