package com.car.sns.presentation.model.response;

import com.car.sns.domain.comment.model.ArticleCommentDto;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public static ArticleWithCommentsResponse from(ArticleDetailWithCommentResponse dto) {
        return new ArticleWithCommentsResponse(
                dto.articleCommentDtos(),
                dto.title(),
                dto.content(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                dto.userAccountDto().nickname()
        );
    }

    public static Set<ArticleCommentDto> organizeChildComments(Set<ArticleCommentDto> dtos) {
        Map<Long, ArticleCommentDto> commentDtoMap = dtos.stream()
                .map(ArticleCommentDto::from)
                .collect(Collectors.toMap(ArticleCommentDto::articleId, Function.identity()));

        commentDtoMap.values().stream()
                .filter(ArticleCommentDto::hasParentComment)
                .forEach(comment -> {
                    ArticleCommentDto parentComments = commentDtoMap.get(comment.parentCommentId());
                    parentComments.childComments().add(comment);
                });

        return commentDtoMap.values().stream()
                .filter(comment -> !comment.hasParentComment())
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ArticleCommentDto::createdAt))));
    }
}
