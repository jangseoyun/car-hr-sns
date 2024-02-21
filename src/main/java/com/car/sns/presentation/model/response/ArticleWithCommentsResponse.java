package com.car.sns.presentation.model.response;

import com.car.sns.domain.comment.model.ArticleCommentDto;
import com.car.sns.presentation.model.ArticleWithCommentDto;

import java.time.LocalDateTime;
import java.util.Set;

public record ArticleWithCommentsResponse(
        Set<ArticleCommentDto> articleCommentDtos,
        String title,
        String content,
        LocalDateTime createdAt,
        String email,
        String nickname
) {
    public static ArticleWithCommentsResponse of(Set<ArticleCommentDto> articleCommentDtos, String title, String content, LocalDateTime createdAt, String email, String nickname) {
        return new ArticleWithCommentsResponse(articleCommentDtos, title, content, createdAt, email, nickname);
    }

    public static ArticleWithCommentsResponse from(ArticleWithCommentDto dto) {
        return new ArticleWithCommentsResponse(
                dto.articleCommentDtos(),
                dto.title(),
                dto.content(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                dto.userAccountDto().nickname()
        );
    }
}
