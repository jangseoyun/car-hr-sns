package com.car.sns.application.usecase.like;

import com.car.sns.presentation.model.response.LikeToArticleResponse;

public interface LikeReadUseCase {
    //좋아요 카운트
    LikeToArticleResponse getLikeCount(Long articleId, String userId);
}
