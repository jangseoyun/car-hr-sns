package com.car.sns.application.usecase.like;

import com.car.sns.domain.user.model.UserAccountDto;

public interface LikeManagementUseCase {
    void saveLikeTarget(Long articleId, UserAccountDto userAccountDto);
}
