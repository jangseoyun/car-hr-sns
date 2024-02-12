package com.car.sns.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.car.sns.domain.Article}
 */
public record ArticleModifyDto(
        Long articleId,
        Long userAccountId,
        String title,
        String content,
        String hashtag
) {

    public static ArticleModifyDto of(Long articleId, Long userAccountId, String title, String content, String hashtag) {
        return new ArticleModifyDto(articleId, userAccountId, title, content, hashtag);

    }
}