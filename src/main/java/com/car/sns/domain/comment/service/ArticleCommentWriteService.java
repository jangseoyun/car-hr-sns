package com.car.sns.domain.comment.service;

import com.car.sns.application.usecase.alarm.AlarmManagementUseCase;
import com.car.sns.application.usecase.comment.ArticleCommentManagementUseCase;
import com.car.sns.domain.alarm.model.AlarmArgs;
import com.car.sns.domain.board.model.entity.Article;
import com.car.sns.domain.board.repository.ArticleJpaRepository;
import com.car.sns.domain.comment.model.ArticleCommentDto;
import com.car.sns.domain.comment.model.entity.ArticleComment;
import com.car.sns.infrastructure.jpaRepository.ArticleCommentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.car.sns.domain.alarm.model.AlarmType.NEW_COMMENT_ON_ARTICLE;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleCommentWriteService implements ArticleCommentManagementUseCase {

    private final ArticleJpaRepository articleRepository;
    private final ArticleCommentJpaRepository articleCommentRepository;
    private final AlarmManagementUseCase alarmManagementUseCase;

    @Override
    public void saveArticleComment(ArticleCommentDto articleCommentDto) {
        Article article = articleRepository.getReferenceById(articleCommentDto.articleId());
        ArticleComment articleComment = articleCommentDto.toEntity(article, articleCommentDto.userAccountDto().toEntity());

        if (articleCommentDto.hasParentComment()) {
            ArticleComment parentComment = articleCommentRepository.getReferenceById(articleCommentDto.parentCommentId());
            parentComment.addChildComment(articleComment);

        } else {

            articleCommentRepository.save(articleComment).getId();
            alarmManagementUseCase.alarmOccurred(NEW_COMMENT_ON_ARTICLE,
                    AlarmArgs.of(articleComment.getUserAccount().getUserId(),
                                article.getId()),
                                article.getCreatedBy());
        }
    }

    @Deprecated
    public void deleteComment(Long commentId) {
        articleCommentRepository.deleteById(commentId);
    }

    @Override
    public void softDeleteComment(Long commentId) {
        //TODO: soft delete를 위한 컬럼
    }
}
