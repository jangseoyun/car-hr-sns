package com.car.sns.application.usecase;

import com.car.sns.domain.board.model.ArticleDto;
import com.car.sns.presentation.model.request.ArticleModifyRequest;
import org.springframework.stereotype.Service;

public interface ArticleManagementUseCase {
    void createArticle(ArticleDto articleDto);
    void deleteArticle(long articleId);

    void updateArticle(ArticleModifyRequest articleModifyDto, String authUsername);
}
