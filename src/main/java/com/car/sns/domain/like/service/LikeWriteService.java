package com.car.sns.domain.like.service;

import com.car.sns.application.usecase.alarm.AlarmManagementUseCase;
import com.car.sns.application.usecase.like.LikeManagementUseCase;
import com.car.sns.domain.alarm.model.AlarmArgs;
import com.car.sns.domain.board.model.entity.Article;
import com.car.sns.domain.board.repository.ArticleJpaRepository;
import com.car.sns.domain.like.model.entity.Likes;
import com.car.sns.domain.user.model.entity.UserAccount;
import com.car.sns.exception.CarHrSnsAppException;
import com.car.sns.infrastructure.jpaRepository.LikeJpaRepository;
import com.car.sns.infrastructure.jpaRepository.UserAccountJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.car.sns.domain.alarm.model.AlarmType.NEW_LIKE_ON_POST;
import static com.car.sns.exception.model.AppErrorCode.ENTITY_NOT_FOUND;
import static com.car.sns.exception.model.AppErrorCode.USER_NOTFOUND_ACCOUNT;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LikeWriteService implements LikeManagementUseCase {

    private final LikeJpaRepository likeJpaRepository;
    private final UserAccountJpaRepository userAccountJpaRepository;
    private final ArticleJpaRepository articleJpaRepository;
    private final AlarmManagementUseCase alarmManagementUseCase;

    @Override
    public void saveLikeTarget(Long articleId, String userId) {
        //존재하는 사용자 인지 확인
        UserAccount userAccount = userAccountJpaRepository.findByUserId(userId).orElseThrow(() -> {
            throw new CarHrSnsAppException(USER_NOTFOUND_ACCOUNT, USER_NOTFOUND_ACCOUNT.getMessage());
        });

        //존재하는 포스트인지 확인
        Article article = articleJpaRepository.findById(articleId).orElseThrow(() -> {
            throw new CarHrSnsAppException(ENTITY_NOT_FOUND, ENTITY_NOT_FOUND.getMessage());
        });

        //좋아요 저장
        likeJpaRepository.save(Likes.of(article, userAccount));
        //알람 발생
        alarmManagementUseCase.alarmOccurred(NEW_LIKE_ON_POST,
                AlarmArgs.of(userAccount.getUserId(), article.getId()),
                article.getCreatedBy());
    }
}
