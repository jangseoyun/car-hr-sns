package com.car.sns.domain.board.service.read;

import com.car.sns.application.usecase.ArticleReaderUseCase;
import com.car.sns.domain.board.model.type.SearchType;
import com.car.sns.domain.board.model.ArticleDto;
import com.car.sns.domain.hashtag.ArticleHashtagJpaRepository;
import com.car.sns.domain.hashtag.service.HashtagReadService;
import com.car.sns.infrastructure.repository.HashtagJpaRepository;
import com.car.sns.presentation.model.ArticleWithCommentDto;
import com.car.sns.domain.board.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleReadService implements ArticleReaderUseCase {

    private final ArticleRepository articleRepository;
    private final HashtagReadService hashtagReadService;

    @Override
    public Page<ArticleDto> getAllOrSearchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        return switch (searchType) {
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG -> hashtagReadService.searchContainHashtagName(searchKeyword, pageable);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
        };
    }

    @Override
    public ArticleWithCommentDto getArticleDetailWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다: - articleId: " + articleId));
    }

    @Override
    @Deprecated
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다: - articleId: " + articleId));
    }

}
