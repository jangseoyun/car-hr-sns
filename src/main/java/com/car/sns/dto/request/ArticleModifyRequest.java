package com.car.sns.dto.request;

/**
 * DTO for {@link com.car.sns.domain.Article}
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