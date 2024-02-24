package com.car.sns.application.usecase.comment;

import com.car.sns.domain.comment.model.ArticleCommentDto;

public interface ArticleCommentManagementUseCase {
    Long saveArticleComment(ArticleCommentDto articleCommentDto);
    void softDeleteComment(Long commentId);
}
