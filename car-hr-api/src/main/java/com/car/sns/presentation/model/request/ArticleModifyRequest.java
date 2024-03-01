package com.car.sns.presentation.model.request;

import com.car.sns.domain.board.model.entity.Article;

import java.util.Objects;

/**
 * DTO for {@link Article}
 */
public record ArticleModifyRequest(
        Long articleId,
        String title,
        String content
) {
    public ArticleModifyRequest {
        Objects.requireNonNull(articleId);
    }

    public static ArticleModifyRequest of(Long articleId, String title, String content, String createdBy) {
        return ArticleModifyRequest.of(articleId, title, content, createdBy);
    }
}