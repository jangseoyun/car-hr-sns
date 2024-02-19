package com.car.sns.controller;

import com.car.sns.domain.type.SearchType;
import com.car.sns.dto.ArticleDto;
import com.car.sns.dto.ArticleWithCommentDto;
import com.car.sns.dto.response.ArticleResponse;
import com.car.sns.dto.response.ArticleWithCommentsResponse;
import com.car.sns.service.ArticleService;
import com.car.sns.service.PaginationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/detail/{articleId}")
    public String readArticleDetail(@PathVariable Long articleId, ModelMap map) {
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


}
