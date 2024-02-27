package com.car.sns.domain.comment.service;

import com.car.sns.application.usecase.comment.ArticleCommentManagementUseCase;
import com.car.sns.domain.board.model.entity.Article;
import com.car.sns.domain.comment.model.entity.ArticleComment;
import com.car.sns.domain.user.model.entity.UserAccount;
import com.car.sns.domain.comment.model.ArticleCommentDto;
import com.car.sns.infrastructure.jpaRepository.ArticleCommentJpaRepository;
import com.car.sns.domain.board.repository.ArticleJpaRepository;
import com.car.sns.infrastructure.jpaRepository.UserAccountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleCommentWriteService implements ArticleCommentManagementUseCase {

    private final ArticleJpaRepository articleRepository;
    private final ArticleCommentJpaRepository articleCommentRepository;
    private final UserAccountJpaRepository userAccountRepository;

    @Override
    public Long saveArticleComment(ArticleCommentDto articleCommentDto) {
        Article article = articleRepository.getReferenceById(articleCommentDto.articleId());
        UserAccount userAccount = userAccountRepository.findByUserId(articleCommentDto.userAccountDto().userId()).orElseThrow();
        ArticleComment articleComment = articleCommentDto.toEntity(article, userAccount);

        if (articleCommentDto.hasParentComment()) {
            ArticleComment parentComment = articleCommentRepository.getReferenceById(articleCommentDto.parentCommentId());
            return parentComment.addChildComment(articleComment);

        } else {

            return articleCommentRepository.save(articleComment).getId();
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
