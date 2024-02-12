package com.car.sns.dto;

import com.car.sns.domain.ArticleComment;

import java.time.LocalDateTime;

/**
 * DTO for {@link ArticleComment}
 */
public record ArticleCommentDto(
        Long ArticleId,
        UserAccountDto userAccountDto,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        String content
) {
    public ArticleCommentDto(Long ArticleId,
                             UserAccountDto userAccountDto,
                             LocalDateTime createdAt,
                             String createdBy,
                             LocalDateTime modifiedAt,
                             String modifiedBy,
                             String content) {
        this.ArticleId = ArticleId;
        this.userAccountDto = userAccountDto;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
        this.content = content;
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
}