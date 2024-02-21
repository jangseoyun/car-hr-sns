package com.car.sns.domain.board.service;

import com.car.sns.application.usecase.ArticleManagementUseCase;
import com.car.sns.domain.board.entity.Article;
import com.car.sns.domain.board.model.ArticleDto;
import com.car.sns.domain.board.repository.ArticleRepository;
import com.car.sns.presentation.model.request.ArticleModifyRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleWriteService implements ArticleManagementUseCase {

    private final ArticleRepository articleRepository;

    @Override
    public void deleteArticle(long articleId) {
        try {
            articleRepository.deleteById(articleId);
        } catch (EntityNotFoundException e) {
            log.warn("게시글 삭제 실패. 게시글을 찾을 수 없습니다 - articleId: {}", articleId);
        }
    }

    @Override
    public void updateArticle(ArticleModifyRequest articleModifyDto, String authUsername) {
        try {
            Article article = articleRepository.getReferenceById(articleModifyDto.articleId());

            if (articleModifyDto.createdBy() == authUsername) {
                if (articleModifyDto.title() != null) {article.setTitle(articleModifyDto.title());}
                if (articleModifyDto.content() != null) {article.setContent(articleModifyDto.content());}
                if (articleModifyDto.hashtag() != null) {article.setHashtag(articleModifyDto.hashtag());}
            }
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 찾을 수 없습니다 - dto: {}", articleModifyDto);
        }
    }

    @Override
    public void createArticle(ArticleDto articleDto) {
        articleRepository.save(articleDto.toEntity());
    }
}
