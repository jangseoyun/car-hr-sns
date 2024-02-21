package com.car.sns.presentation.controller;

import com.car.sns.domain.board.type.SearchType;
import com.car.sns.presentation.model.ArticleWithCommentDto;
import com.car.sns.presentation.model.request.ArticleModifyRequest;
import com.car.sns.presentation.model.response.ArticleResponse;
import com.car.sns.presentation.model.response.ArticleWithCommentsResponse;
import com.car.sns.security.CarAppPrincipal;
import com.car.sns.domain.board.service.ArticleService;
import com.car.sns.domain.board.service.PaginationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final PaginationService paginationService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 검색어가 존재하는 경우 검색 조회 결과 반환, 존재하지 않는 경우 전체 게시글 반환
     *
     * @param map
     * @return
     */
    @GetMapping("/index")
    public String index(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchKeyword,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {
        log.info("searchType: {}", searchType);
        log.info("searchKeyword: {}", searchKeyword);

        Page<ArticleResponse> articleResponses = articleService.searchArticles(searchType, searchKeyword, pageable)
                .map(ArticleResponse::from);
        List<Integer> paginationBarNumber = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articleResponses.getTotalPages());

        map.addAttribute("articles", articleResponses);
        map.addAttribute("paginationBarNumbers", paginationBarNumber);
        map.addAttribute("searchTypes", SearchType.values());

        log.info("article response: {}", articleResponses.getContent());
        return "features-posts";
    }

    @GetMapping("/hashtag")
    public String searchHashtag(
            @RequestParam(required = false) String hashtagKeyword,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map) {
        Page<ArticleResponse> articleResponses = articleService.searchArticlesViaHashtag(hashtagKeyword, pageable).map(ArticleResponse::from);
        List<Integer> paginationBarNumber = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articleResponses.getTotalPages());
        List<String> hashtags = articleService.getHashtags();

        map.addAttribute("articles", articleResponses);
        map.addAttribute("hashtags", hashtags);
        map.addAttribute("paginationBarNumbers", paginationBarNumber);
        map.addAttribute("searchType", SearchType.HASHTAG);

        return "features-posts";
    }

    @GetMapping("/detail/{articleId}")
    public String readArticleDetail(@PathVariable Long articleId, ModelMap map
    ) {
        ArticleWithCommentDto getArticleWithComments = articleService.getArticleWithComments(articleId);
        map.addAttribute("articleDetail", ArticleWithCommentsResponse.from(getArticleWithComments));

        log.info("detail response: {}", ArticleWithCommentsResponse.from(getArticleWithComments));
        return "features-posts-detail";
    }

    @GetMapping("/create")
    public String getCreatePostPage() {
        return "features-post-create";
    }

    @DeleteMapping("/{articleId}")
    public String removeArticle(@PathVariable Long articleId) {
        articleService.deleteArticle(articleId);
        return "redirect:/articles/index";
    }

    @PutMapping("")
    public String modifyPostContent(@AuthenticationPrincipal CarAppPrincipal carAppPrincipal,
                                    ArticleModifyRequest articleModifyRequest,
                                    ModelMap map) {

        articleService.updateArticle(articleModifyRequest, carAppPrincipal.getUsername());

        return "redirect:/articles/detail/" + articleModifyRequest.articleId();
    }
}
