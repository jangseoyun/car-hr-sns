package com.car.sns.presentation.model.request;

import com.car.sns.domain.board.model.entity.Article;

/**
 * DTO for {@link Article}
 */
public record ArticleRequest(
        String title,
        String content
) {
    //데이터 검증
}