package com.car.sns.dto.response;

import com.car.sns.dto.ArticleCommentDto;
import com.car.sns.dto.ArticleWithCommentDto;

import java.time.LocalDateTime;
import java.util.Set;

public record ArticleWithCommentsResponse(
        Set<ArticleCommentDto> articleCommentDtos,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String email,
        String nickname
) {
    public static ArticleWithCommentsResponse of(Set<ArticleCommentDto> articleCommentDtos, String title, String content, String hashtag, LocalDateTime createdAt, String email, String nickname) {
        return new ArticleWithCommentsResponse(articleCommentDtos, title, content, hashtag, createdAt, email, nickname);
    }

    public static ArticleWithCommentsResponse from(ArticleWithCommentDto dto) {
        return new ArticleWithCommentsResponse(
                dto.articleCommentDtos(),
                dto.title(),
                dto.content(),
                dto.hashtag(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                dto.userAccountDto().nickname()
        );
    }
}
