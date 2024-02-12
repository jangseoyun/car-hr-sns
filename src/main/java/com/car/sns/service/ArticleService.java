package com.car.sns.service;

import com.car.sns.domain.Article;
import com.car.sns.domain.type.SearchType;
import com.car.sns.dto.ArticleDto;
import com.car.sns.dto.ArticleModifyDto;
import com.car.sns.dto.ArticleWithCommentDto;
import com.car.sns.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        return switch (searchType) {
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            case ID -> articleRepository.findByHashtag(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG ->
                    articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME ->
                    articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
        };
    }

    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다: - articleId: " + articleId));
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentDto getArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다: - articleId: " + articleId));
    }

    public void createArticle(ArticleDto articleDto) {
        articleRepository.save(articleDto.toEntity());
    }

    @Transactional(readOnly = true)
    public void updateArticle(ArticleModifyDto articleModifyDto) {
        try {
            Article article = articleRepository.getReferenceById(articleModifyDto.articleId());

            if (articleModifyDto.userAccountId() == article.getUserAccount().getUserAccountId()) {
                if (articleModifyDto.title() != null) {article.setTitle(articleModifyDto.title());}
                if (articleModifyDto.content() != null) {article.setTitle(articleModifyDto.content());}
                if (articleModifyDto.hashtag() != null) {article.setTitle(articleModifyDto.hashtag());}
            }
        } catch (EntityNotFoundException e) {
                System.out.println(e);
        }
    }

    public void deleteArticle(long articleId) {

    }
}
