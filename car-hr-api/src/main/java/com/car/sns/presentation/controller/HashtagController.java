package com.car.sns.presentation.controller;

import com.car.sns.application.usecase.hashtag.HashtagUseCase;
import com.car.sns.application.usecase.PaginationUseCase;
import com.car.sns.domain.board.model.ArticleDto;
import com.car.sns.presentation.model.response.ArticlePageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/hashtag")
public class HashtagController {

    private final HashtagUseCase hashtagUseCase;
    private final PaginationUseCase paginationUseCase;

    /**
     * 해시태그로 검색하여 해당하는 게시글 반환
     */
    @GetMapping("/search")
    public ResponseEntity<ArticlePageResponse> searchHashtag(
            @RequestParam(required = false) String hashtagKeyword,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable)
    {
        //TODO: hashtagUseCase 하나의 호출로 Response를 만들어 보낼 수 있는지 고려할 것
        Page<ArticleDto> articles = hashtagUseCase.searchContainHashtagName(hashtagKeyword, pageable);
        List<Integer> paginationBarNumber = paginationUseCase.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
        List<String> hashtags = hashtagUseCase.getHashtags();

        return ResponseEntity
                .ok()
                .body(ArticlePageResponse.of(articles, paginationBarNumber, hashtags));
    }
}
