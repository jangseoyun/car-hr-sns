package com.car.sns.presentation.model.request;

import com.car.sns.domain.comment.entity.ArticleComment;
import com.car.sns.domain.comment.model.ArticleCommentDto;
import com.car.sns.domain.user.model.UserAccountDto;

/**
 * DTO for {@link ArticleComment}
 */
public record ArticleCommentRequest(
        Long articleId,
        String content
) {

    public static ArticleCommentRequest of(Long articleId, String content) {
        return new ArticleCommentRequest(articleId, content);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
        return ArticleCommentDto.of(articleId, userAccountDto, content);
    }
}