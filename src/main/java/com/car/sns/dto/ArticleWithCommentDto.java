package com.car.sns.dto;

import com.car.sns.domain.Article;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleWithCommentDto (
        Long id,
        UserAccountDto userAccountDto,
        Set<ArticleCommentDto> articleCommentDtoSet,
        String title,
        String content,
        LocalDateTime createAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
)
{
    public static ArticleWithCommentDto of(Long id,
                                 UserAccountDto userAccountDto,
                                 Set<ArticleCommentDto> articleCommentDtoSet,
                                 String title,
                                 String content,
                                 LocalDateTime createAt,
                                 String createdBy,
                                 LocalDateTime modifiedAt,
                                 String modifiedBy) {
        return new ArticleWithCommentDto(id, userAccountDto, articleCommentDtoSet, title, content, createAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleWithCommentDto from(Article entity) {
        return new ArticleWithCommentDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getArticleComment().stream()
                        .map(ArticleCommentDto::from)
                        .collect(Collectors.toSet()),
                entity.getTitle(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy());
    }



}
