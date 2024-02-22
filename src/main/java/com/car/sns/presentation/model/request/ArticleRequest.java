package com.car.sns.presentation.model.request;

/**
 * DTO for {@link com.car.sns.domain.board.entity.Article}
 */
public record ArticleRequest(
        String title,
        String content
) {
    //데이터 검증
}