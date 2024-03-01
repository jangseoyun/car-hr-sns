package com.car.sns.domain.like.service.read;

import com.car.sns.application.usecase.like.LikeReadUseCase;
import com.car.sns.domain.board.model.entity.Article;
import com.car.sns.domain.board.repository.ArticleJpaRepository;
import com.car.sns.exception.CarHrSnsAppException;
import com.car.sns.infrastructure.jpaRepository.LikeJpaRepository;
import com.car.sns.infrastructure.jpaRepository.UserAccountJpaRepository;
import com.car.sns.presentation.model.response.LikeToArticleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.car.sns.exception.model.AppErrorCode.ENTITY_NOT_FOUND;
import static com.car.sns.exception.model.AppErrorCode.USER_NOTFOUND_ACCOUNT;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeReadService implements LikeReadUseCase {

    private final LikeJpaRepository likeJpaRepository;
    private final UserAccountJpaRepository userAccountJpaRepository;
    private final ArticleJpaRepository articleJpaRepository;

    @Override
    public LikeToArticleResponse getLikeCount(Long articleId, String userId) {
        userAccountJpaRepository.findByUserId(userId).orElseThrow(() -> {
            throw new CarHrSnsAppException(USER_NOTFOUND_ACCOUNT, USER_NOTFOUND_ACCOUNT.getMessage());
        });
        Article article = articleJpaRepository.findById(articleId).orElseThrow(() -> {
            throw new CarHrSnsAppException(ENTITY_NOT_FOUND, ENTITY_NOT_FOUND.getMessage());
        });

        return LikeToArticleResponse.of(likeJpaRepository.findByLikeCount(article), articleId);
    }
}
