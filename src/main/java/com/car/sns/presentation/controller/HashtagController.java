package com.car.sns.presentation.controller;

import com.car.sns.application.usecase.HashtagUseCase;
import com.car.sns.application.usecase.PaginationUseCase;
import com.car.sns.domain.board.model.type.SearchType;
import com.car.sns.presentation.model.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

    @GetMapping("/search")
    public String searchHashtag(
            @RequestParam(required = false) String hashtagKeyword,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map) {
        //TODO: controller에서는 간단히 사용자 요청을 전달하는 목적으로 많은 로직이 들어가서는 안된다
        Page<ArticleResponse> articleResponses = hashtagUseCase.searchArticlesViaHashtag(hashtagKeyword, pageable).map(ArticleResponse::from);
        List<Integer> paginationBarNumber = paginationUseCase.getPaginationBarNumbers(pageable.getPageNumber(), articleResponses.getTotalPages());
        List<String> hashtags = hashtagUseCase.getHashtags();

        map.addAttribute("articles", articleResponses);
        map.addAttribute("hashtags", hashtags);
        map.addAttribute("paginationBarNumbers", paginationBarNumber);
        map.addAttribute("searchType", SearchType.HASHTAG);

        return "features-posts";
    }
}
