package com.car.sns.presentation.model.response;

import com.car.sns.domain.board.model.entity.Article;
import com.car.sns.domain.comment.model.ArticleCommentDto;
import com.car.sns.domain.user.model.UserAccountDto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleDetailWithCommentResponse(
        Long id,
        UserAccountDto userAccountDto,
        Set<ArticleCommentDto> articleCommentDtos,
        String title,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static ArticleDetailWithCommentResponse of(Long id,
                                                      UserAccountDto userAccountDto,
                                                      Set<ArticleCommentDto> articleCommentDtoSet,
                                                      String title,
                                                      String content,
                                                      LocalDateTime createAt,
                                                      String createdBy,
                                                      LocalDateTime modifiedAt,
                                                      String modifiedBy) {
        return new ArticleDetailWithCommentResponse(id, userAccountDto, articleCommentDtoSet, title, content, createAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleDetailWithCommentResponse from(Article entity) {
        return new ArticleDetailWithCommentResponse(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getArticleComment().stream()
                        .map(ArticleCommentDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                entity.getTitle(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy());
    }


}
