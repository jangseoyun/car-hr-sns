package com.car.sns.dto.response;

import com.car.sns.dto.ArticleDto;

import java.time.LocalDateTime;
import java.util.Set;

public record ArticleResponse (
        String title,
        String content,
        Set<String> hashtag,
        LocalDateTime createdAt,
        String email,
        String nickname
) {
    public static ArticleResponse of(String title, String content, Set<String> hashtag, LocalDateTime createdAt, String email, String nickname) {
        return new ArticleResponse(title, content, hashtag, createdAt, email, nickname);
    }

    public static ArticleResponse from(ArticleDto dto) {
        return new ArticleResponse(
                dto.title(),
                dto.content(),
                Set.of(dto.hashtag()),
                dto.createdAt(),
                dto.userAccountDto().email(),
                dto.userAccountDto().nickname()
        );
    }
}
