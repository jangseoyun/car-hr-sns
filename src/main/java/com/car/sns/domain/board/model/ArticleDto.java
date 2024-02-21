package com.car.sns.domain.board.model;

import com.car.sns.domain.board.entity.Article;
import com.car.sns.domain.user.model.UserAccountDto;

import java.time.LocalDateTime;

/**
 * DTO for {@link Article}
 */
public record ArticleDto(
        UserAccountDto userAccountDto,
        LocalDateTime createdAt,
        String createdBy,
        String title,
        String content
) {

    public static ArticleDto of(UserAccountDto userAccountDto,
                                LocalDateTime createdAt,
                                String createdBy,
                                String title,
                                String content) {
        return new ArticleDto(userAccountDto, createdAt, createdBy, title, content);
    }

    public static ArticleDto from(Article entity) {
        return new ArticleDto(
                UserAccountDto.from(entity.getUserAccount()),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getTitle(),
                entity.getContent());
    }

    public Article toEntity() {
        return Article.of(userAccountDto.toEntity(), title, content);
    }

}