package com.car.sns.service;

import com.car.sns.domain.type.SearchType;
import com.car.sns.dto.ArticleDto;
import com.car.sns.dto.ArticleModifyDto;
import com.car.sns.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public List<ArticleDto> searchArticles(SearchType searchType, String searchTitleKeyword) {
        return List.of();
    }

    @Transactional(readOnly = true)
    public ArticleDto searchArticles(SearchType searchType, long ld) {
        return null;
    }

    public void searchArticles(ArticleDto articleDto) {

    }

    public void updateArticle(long articleId, ArticleModifyDto articleModifyDto) {

    }

    public void deleteArticle(long articleId) {

    }
}
