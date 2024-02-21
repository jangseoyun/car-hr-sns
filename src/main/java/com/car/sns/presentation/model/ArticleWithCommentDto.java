package com.car.sns.presentation.model;

import com.car.sns.domain.board.entity.Article;
import com.car.sns.domain.comment.model.ArticleCommentDto;
import com.car.sns.domain.user.model.UserAccountDto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleWithCommentDto(
        Long id,
        UserAccountDto userAccountDto,
        Set<ArticleCommentDto> articleCommentDtos,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static ArticleWithCommentDto of(Long id,
                                           UserAccountDto userAccountDto,
                                           Set<ArticleCommentDto> articleCommentDtoSet,
                                           String title,
                                           String content,
                                           String hashtag,
                                           LocalDateTime createAt,
                                           String createdBy,
                                           LocalDateTime modifiedAt,
                                           String modifiedBy) {
        return new ArticleWithCommentDto(id, userAccountDto, articleCommentDtoSet, title, content, hashtag, createAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleWithCommentDto from(Article entity) {
        return new ArticleWithCommentDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getArticleComment().stream()
                        .map(ArticleCommentDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy());
    }


}
