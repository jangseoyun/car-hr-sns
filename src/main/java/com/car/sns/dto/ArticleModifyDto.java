package com.car.sns.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.car.sns.domain.Article}
 */
public record ArticleModifyDto(
        String title,
        String content,
        String hashtag
) {

    public static ArticleModifyDto of(String title, String content, String hashtag) {
        return new ArticleModifyDto(title, content, hashtag);

    }
}