package com.car.sns.domain.comment.service;

import com.car.sns.domain.board.entity.Article;
import com.car.sns.domain.user.entity.UserAccount;
import com.car.sns.domain.comment.model.ArticleCommentDto;
import com.car.sns.infrastructure.repository.ArticleCommentJpaRepository;
import com.car.sns.domain.board.repository.ArticleRepository;
import com.car.sns.infrastructure.repository.UserAccountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleCommentWriteService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentJpaRepository articleCommentRepository;
    private final UserAccountJpaRepository userAccountRepository;

    public void saveArticleComment(ArticleCommentDto articleCommentDto) {
        Article article = articleRepository.getReferenceById(articleCommentDto.articleId());
        UserAccount userAccount = userAccountRepository.getReferenceById(articleCommentDto.articleId());
        articleCommentRepository.save(
                articleCommentDto.toEntity(article, userAccount)
        );
    }

    public void deleteComment(Long commentId) {
        articleCommentRepository.deleteById(commentId);
    }

    public void softDeleteComment(Long commentId) {
        //TODO: soft delete를 위한 컬럼
    }
}
