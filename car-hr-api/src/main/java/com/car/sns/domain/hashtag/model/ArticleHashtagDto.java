package com.car.sns.domain.hashtag.model;

import com.car.sns.domain.hashtag.model.entity.ArticleHashtag;

import java.time.LocalDateTime;

/**
 * DTO for {@link ArticleHashtag}
 */
public record ArticleHashtagDto(
        LocalDateTime articleCreatedAt,
        String articleCreatedBy,
        Long articleId,
        String articleTitle,
        String articleContent,
        Long hashtagId,
        String hashtagHashtagName
) {
    public static ArticleHashtagDto of(LocalDateTime articleCreatedAt, String articleCreatedBy, Long articleId, String articleTitle, String articleContent, Long hashtagId, String hashtagHashtagName) {
        return new ArticleHashtagDto(articleCreatedAt, articleCreatedBy, articleId, articleTitle, articleContent, hashtagId, hashtagHashtagName);
    }
}