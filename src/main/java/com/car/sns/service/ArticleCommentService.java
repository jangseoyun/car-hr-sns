package com.car.sns.service;

import com.car.sns.domain.Article;
import com.car.sns.domain.UserAccount;
import com.car.sns.dto.ArticleCommentDto;
import com.car.sns.dto.ArticleDto;
import com.car.sns.repository.ArticleCommentRepository;
import com.car.sns.repository.ArticleRepository;
import com.car.sns.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComment(long articleId) {
        return List.of();
    }

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
