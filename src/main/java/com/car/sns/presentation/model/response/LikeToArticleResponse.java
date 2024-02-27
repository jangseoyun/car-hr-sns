package com.car.sns.presentation.model.response;

public record LikeToArticleResponse(
        Long articleId,
        int likeCount
) {
    public static LikeToArticleResponse of(int likeCount, Long articleId) {
        return new LikeToArticleResponse(articleId, likeCount);
    }
}
