package com.car.sns.dto;

import com.car.sns.domain.Article;
import com.car.sns.domain.UserAccount;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.car.sns.domain.Article}
 */
public record ArticleDto(
        UserAccountDto userAccountDto,
        LocalDateTime createdAt,
        String createdBy,
        String title,
        String content,
        String hashtag
) {

    public static ArticleDto of(UserAccountDto userAccountDto,
                                LocalDateTime createdAt,
                                String createdBy,
                                String title,
                                String content,
                                String hashtag) {
        return new ArticleDto(userAccountDto, createdAt, createdBy, title, content, hashtag);
    }

    public static ArticleDto from(Article entity) {
        return new ArticleDto(
                UserAccountDto.from(entity.getUserAccount()),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag());
    }

    public Article toEntity() {
        return Article.of(userAccountDto.toEntity(), title, content, hashtag);
    }

}