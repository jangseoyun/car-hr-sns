package com.car.sns.domain.comment.service.read;

import com.car.sns.domain.comment.model.ArticleCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleCommentReadService {
    public List<ArticleCommentDto> searchArticleComment(Long userAccountId) {
        //TODO: 댓글 작성자로 검색할 수 있또록 구현
        return List.of();
    }
}
