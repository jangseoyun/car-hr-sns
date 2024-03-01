package com.car.sns.domain.board.model;

import com.car.sns.presentation.model.request.ArticleRequest;

public record CreateArticleInfoDto(
        String title,
        String content,
        String username
) {

    public static CreateArticleInfoDto of(ArticleRequest articleRequest, String username) {
        return new CreateArticleInfoDto(articleRequest.title(), articleRequest.content(), username);
    }
}
