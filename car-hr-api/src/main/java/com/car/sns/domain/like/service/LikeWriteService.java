package com.car.sns.domain.like.service;

import com.car.sns.application.usecase.like.LikeManagementUseCase;
import com.car.sns.domain.alarm.model.AlarmArgs;
import com.car.sns.domain.board.model.entity.Article;
import com.car.sns.domain.board.repository.ArticleJpaRepository;
import com.car.sns.domain.like.model.entity.Likes;
import com.car.sns.domain.user.model.UserAccountDto;
import com.car.sns.domain.user.model.entity.UserAccount;
import com.car.sns.exception.CarHrSnsAppException;
import com.car.sns.infrastructure.jpaRepository.LikeJpaRepository;
import com.car.sns.infrastructure.jpaRepository.UserAccountJpaRepository;
import com.car.sns.kafka.model.AlarmEvent;
import com.car.sns.kafka.producer.AlarmProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.car.sns.domain.alarm.model.AlarmType.NEW_LIKE_ON_POST;
import static com.car.sns.exception.model.AppErrorCode.ENTITY_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LikeWriteService implements LikeManagementUseCase {
    private final LikeJpaRepository likeJpaRepository;
    private final ArticleJpaRepository articleJpaRepository;
    private final UserAccountJpaRepository userAccountJpaRepository;
    private final AlarmProducer alarmProducer;
    @Override
    public void saveLikeTarget(Long articleId, UserAccountDto authorizedUser) {
        //존재하는 포스트인지 확인
        Article article = articleJpaRepository.findById(articleId).orElseThrow(() -> {
            throw new CarHrSnsAppException(ENTITY_NOT_FOUND, ENTITY_NOT_FOUND.getMessage());
        });

        //TODO BUG: 넘겨주는 userDTO에는 PK값이 존재하지 않기 때문에 에러가 발생한다. 기존 user entity PK값 재설계 필요
        Optional<UserAccount> fromUser = userAccountJpaRepository.findByUserId(authorizedUser.userId());
        likeJpaRepository.save(Likes.of(article, fromUser.get()));
        //kafka & sse
        alarmProducer.send(new AlarmEvent(article.getUserAccount().getUserId(), NEW_LIKE_ON_POST, AlarmArgs.of(authorizedUser.userId(), article.getId())));
    }
}
