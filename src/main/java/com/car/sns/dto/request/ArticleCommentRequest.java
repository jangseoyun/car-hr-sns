package com.car.sns.dto.request;

import com.car.sns.dto.ArticleCommentDto;
import com.car.sns.dto.UserAccountDto;

/**
 * DTO for {@link com.car.sns.domain.ArticleComment}
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