package com.car.sns.application.usecase.board;

import com.car.sns.domain.board.model.ArticleDto;
import com.car.sns.domain.board.model.CreateArticleInfoDto;
import com.car.sns.presentation.model.request.ArticleModifyRequest;
import com.car.sns.presentation.model.response.Result;

public interface ArticleManagementUseCase {
    Result<ArticleDto> createArticle(CreateArticleInfoDto createArticleInfoDto);
    void deleteArticle(long articleId);
    void updateArticle(ArticleModifyRequest articleModifyDto, String authUsername);
}
