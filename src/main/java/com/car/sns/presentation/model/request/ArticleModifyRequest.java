package com.car.sns.presentation.model.request;

import com.car.sns.domain.board.entity.Article;

/**
 * DTO for {@link Article}
 */
public record ArticleModifyRequest(
        Long articleId,
        String title,
        String content,
        String hashtag,
        String createdBy) {

    public static ArticleModifyRequest of(Long articleId, String title, String content, String hashtag, String createdBy) {
        return ArticleModifyRequest.of(articleId, title, content, hashtag, createdBy);
    }
}