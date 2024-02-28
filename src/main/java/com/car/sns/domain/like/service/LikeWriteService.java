package com.car.sns.domain.like.service;

import com.car.sns.application.usecase.alarm.AlarmManagementUseCase;
import com.car.sns.application.usecase.like.LikeManagementUseCase;
import com.car.sns.domain.alarm.model.AlarmArgs;
import com.car.sns.domain.board.model.entity.Article;
import com.car.sns.domain.board.repository.ArticleJpaRepository;
import com.car.sns.domain.like.model.entity.Likes;
import com.car.sns.domain.user.model.UserAccountDto;
import com.car.sns.exception.CarHrSnsAppException;
import com.car.sns.infrastructure.jpaRepository.LikeJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.car.sns.domain.alarm.model.AlarmType.NEW_LIKE_ON_POST;
import static com.car.sns.exception.model.AppErrorCode.ENTITY_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LikeWriteService implements LikeManagementUseCase {

    private final LikeJpaRepository likeJpaRepository;
    private final ArticleJpaRepository articleJpaRepository;
    private final AlarmManagementUseCase alarmManagementUseCase;

    @Override
    public void saveLikeTarget(Long articleId, UserAccountDto authorizedUser) {
        //존재하는 포스트인지 확인
        Article article = articleJpaRepository.findById(articleId).orElseThrow(() -> {
            throw new CarHrSnsAppException(ENTITY_NOT_FOUND, ENTITY_NOT_FOUND.getMessage());
        });

        //TODO: 한 사람이 한번만 like를 할 수 있도록
        //좋아요 저장
        likeJpaRepository.save(Likes.of(article, authorizedUser.toEntity()));
        //알람 발생
        alarmManagementUseCase.alarmOccurred(NEW_LIKE_ON_POST,
                AlarmArgs.of(authorizedUser.userId(), article.getId()),
                article.getCreatedBy());
    }
}
