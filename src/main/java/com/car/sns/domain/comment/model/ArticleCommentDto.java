package com.car.sns.domain.comment.model;

import com.car.sns.domain.board.model.entity.Article;
import com.car.sns.domain.comment.model.entity.ArticleComment;
import com.car.sns.domain.user.model.entity.UserAccount;
import com.car.sns.domain.user.model.UserAccountDto;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * DTO for {@link ArticleComment}
 */
public record ArticleCommentDto(
        Long articleId,
        Long parentCommentId,
        UserAccountDto userAccountDto,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        String content,
        Set<ArticleCommentDto> childComments
) {

    public static ArticleCommentDto of(Long articleId,
                                       UserAccountDto userAccountDto,
                                       String content) {
        return ArticleCommentDto.of(articleId, null, userAccountDto, content);
    }

    public static ArticleCommentDto of(Long articleId,
                                       Long parentCommentId,
                                       UserAccountDto userAccountDto,
                                       String content) {

        Comparator<ArticleCommentDto> childCommentComparator = Comparator
                .comparing(ArticleCommentDto::createdAt)
                .thenComparingLong(ArticleCommentDto::articleId);

        return new ArticleCommentDto(articleId,
                parentCommentId,
                userAccountDto,
                LocalDateTime.now(),
                null,
                null,
                null,
                content,
                new TreeSet<>(childCommentComparator));
    }

    public static ArticleCommentDto from(ArticleComment entity) {
        return new ArticleCommentDto(
                entity.getArticle().getId(),
                entity.getParentCommentId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
                entity.getContent(),
                entity.getChildComments().stream()
                        .map(ArticleCommentDto::from)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

    public static ArticleCommentDto from(ArticleCommentDto dto) {
        return new ArticleCommentDto(
                dto.articleId,
                dto.parentCommentId,
                dto.userAccountDto(),
                dto.createdAt(),
                dto.createdBy(),
                dto.modifiedAt(),
                dto.modifiedBy(),
                dto.content(),
                dto.childComments().stream()
                        .map(ArticleCommentDto::from)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

    public ArticleComment toEntity(Article article, UserAccount userAccount) {
        return ArticleComment.of(userAccount, article, content);
    }

    public boolean hasParentComment() {
        return parentCommentId != null;
    }

}