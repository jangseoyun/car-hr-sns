package com.car.sns.application.usecase;

import com.car.sns.domain.board.model.ArticleDto;
import com.car.sns.domain.board.model.type.SearchType;
import com.car.sns.presentation.model.ArticleWithCommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleReaderUseCase {
    //TODO: 조회시 페이징데이터까지 반환되도록 수정

    /**
     * 게시글 목록 Index
     * searchType, searchKeyword 존재 X : 게시글 전체 조회
     * searchType, searchKeyword 존재 O : 타입/키워드를 필터링으로 해당 게시글 조회
     */
    Page<ArticleDto> getAllOrSearchArticles(SearchType searchType, String searchKeyword, Pageable pageable);

    /**
     * 게시글 단건 조회로 게시글에 포함된 댓글 리스트 조회
     */
    ArticleWithCommentDto getArticleDetailWithComments(Long articleId);

    /**
     * 댓글을 포함하지 않는 게시글 단건조회
     * @Deprecated
     * getArticleDetailWithComments 사용 권장
     */
    @Deprecated
    ArticleDto getArticle(Long articleId);

}
