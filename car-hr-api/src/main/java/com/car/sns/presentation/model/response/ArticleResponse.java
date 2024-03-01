package com.car.sns.presentation.model.response;

import com.car.sns.domain.board.model.ArticleDto;

import java.time.LocalDateTime;

public record ArticleResponse (
        String title,
        String content,
        LocalDateTime createdAt,
        String email,
        String nickname
) {
    public static ArticleResponse of(String title, String content, LocalDateTime createdAt, String email, String nickname) {
        return new ArticleResponse(title, content, createdAt, email, nickname);
    }

    public static ArticleResponse from(ArticleDto dto) {
        return new ArticleResponse(
                dto.title(),
                dto.content(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                dto.userAccountDto().nickname()
        );
    }
}
