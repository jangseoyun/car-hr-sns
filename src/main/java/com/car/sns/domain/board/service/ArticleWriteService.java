package com.car.sns.domain.board.service;

import com.car.sns.application.usecase.board.ArticleManagementUseCase;
import com.car.sns.domain.board.model.ArticleDto;
import com.car.sns.domain.board.model.CreateArticleInfoDto;
import com.car.sns.domain.board.model.entity.Article;
import com.car.sns.domain.board.repository.ArticleRepository;
import com.car.sns.domain.hashtag.service.HashtagWriteService;
import com.car.sns.domain.user.model.entity.UserAccount;
import com.car.sns.infrastructure.jpaRepository.UserAccountJpaRepository;
import com.car.sns.presentation.model.request.ArticleModifyRequest;
import com.car.sns.presentation.model.response.Result;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ArticleWriteService implements ArticleManagementUseCase {

    private final ArticleRepository articleRepository;
    private final UserAccountJpaRepository userAccountJpaRepository;
    private final HashtagWriteService hashtagWriteService;

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
            }
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 찾을 수 없습니다 - dto: {}", articleModifyDto);
        }
    }

    @Override
    public Result<ArticleDto> createArticle(CreateArticleInfoDto createArticleInfoDto) {
        //article에 있는 해시태그 분리해서 저장
        UserAccount accessUser = userAccountJpaRepository.findByUserId(createArticleInfoDto.username())
                .orElseThrow( () -> new EntityNotFoundException("잘못된 사용자 접속입니다."));

        Article article = Article.of(accessUser, createArticleInfoDto.title(), createArticleInfoDto.content());
        Article response = articleRepository.save(article);

        //hashtagWriteService.renewHashtagsFromContent(article.getContent());
        return Result.success(ArticleDto.from(article));
    }
}
