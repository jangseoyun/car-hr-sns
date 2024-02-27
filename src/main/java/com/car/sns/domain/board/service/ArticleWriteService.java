package com.car.sns.domain.board.service;

import com.car.sns.application.usecase.board.ArticleManagementUseCase;
import com.car.sns.domain.board.model.ArticleDto;
import com.car.sns.domain.board.model.CreateArticleInfoDto;
import com.car.sns.domain.board.model.entity.Article;
import com.car.sns.domain.board.repository.ArticleJpaRepository;
import com.car.sns.domain.hashtag.service.HashtagWriteService;
import com.car.sns.domain.user.model.entity.UserAccount;
import com.car.sns.exception.CarHrSnsAppException;
import com.car.sns.infrastructure.jpaRepository.UserAccountJpaRepository;
import com.car.sns.presentation.model.request.ArticleModifyRequest;
import com.car.sns.presentation.model.response.Result;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.car.sns.exception.model.AppErrorCode.ENTITY_NOT_FOUND;
import static com.car.sns.exception.model.AppErrorCode.USER_NOT_MATCH;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ArticleWriteService implements ArticleManagementUseCase {

    private final ArticleJpaRepository articleRepository;
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
    public void updateArticle(ArticleModifyRequest articleModifyRequest, String authUsername) {
            Article article = articleRepository.findById(articleModifyRequest.articleId()).orElseThrow(() -> {
                throw new CarHrSnsAppException(ENTITY_NOT_FOUND, ENTITY_NOT_FOUND.getMessage());
            });

            if (!article.getCreatedBy().equals(authUsername)) {
                throw new CarHrSnsAppException(USER_NOT_MATCH, USER_NOT_MATCH.getMessage());
            }

            if (articleModifyRequest.title() != null) {article.setTitle(articleModifyRequest.title());}
            if (articleModifyRequest.content() != null) {article.setContent(articleModifyRequest.content());}
    }

    @Override
    public Result<ArticleDto> createArticle(CreateArticleInfoDto createArticleInfoDto) {
        //article에 있는 해시태그 분리해서 저장
        UserAccount accessUser = userAccountJpaRepository.findByUserId(createArticleInfoDto.username())
                .orElseThrow( () -> new EntityNotFoundException("잘못된 사용자 접속입니다."));

        Article article = Article.of(accessUser, createArticleInfoDto.title(), createArticleInfoDto.content());
        Article response = articleRepository.save(article);

        //TODO: 게시글 생성시 해시태그를 생성하여 저장하는 방식인데 사용자가 모를 경우 null 입력 받음
        hashtagWriteService.renewHashtagsFromContent(article.getContent());
        return Result.success(ArticleDto.from(response));
    }
}
