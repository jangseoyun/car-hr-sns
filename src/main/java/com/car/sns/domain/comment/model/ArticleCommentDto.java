package com.car.sns.domain.comment.model;

import com.car.sns.domain.board.entity.Article;
import com.car.sns.domain.comment.entity.ArticleComment;
import com.car.sns.domain.user.entity.UserAccount;
import com.car.sns.domain.user.model.UserAccountDto;

import java.time.LocalDateTime;

/**
 * DTO for {@link ArticleComment}
 */
public record ArticleCommentDto(
        Long articleId,
        UserAccountDto userAccountDto,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        String content
) {
    public static ArticleCommentDto of(Long articleId,
                             UserAccountDto userAccountDto,
                             String content) {
        return new ArticleCommentDto(articleId, userAccountDto, null, null, null, null, content);
    }

    public static ArticleCommentDto from(ArticleComment entity) {
        return new ArticleCommentDto(
                entity.getArticle().getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
                entity.getContent()
        );
    }

    public ArticleComment toEntity(Article article, UserAccount userAccount) {
        return ArticleComment.of(userAccount, article, content);
    }
}