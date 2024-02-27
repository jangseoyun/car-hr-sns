package com.car.sns.application.usecase.like;

public interface LikeManagementUseCase {
    void saveLikeTarget(Long articleId, String userId);
}
