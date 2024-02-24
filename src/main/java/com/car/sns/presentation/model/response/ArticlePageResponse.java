package com.car.sns.presentation.model.response;

import com.car.sns.domain.board.model.ArticleDto;
import org.springframework.data.domain.Page;

import java.util.List;

public record ArticlePageResponse(
        Page<ArticleDto> articles,
        List<Integer> paginationBarNumbers,
        List<String> hashtags
) {
    public static ArticlePageResponse of(Page<ArticleDto> articles, List<Integer> paginationBarNumbers) {
        return ArticlePageResponse.of(articles, paginationBarNumbers, List.of());
    }

    public static ArticlePageResponse of(Page<ArticleDto> articles, List<Integer> paginationBarNumbers, List<String> hashtags) {
        return new ArticlePageResponse(articles, paginationBarNumbers, hashtags);
    }
}
