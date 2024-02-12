package com.car.sns.controller;

import com.car.sns.domain.type.SearchType;
import com.car.sns.dto.ArticleDto;
import com.car.sns.dto.response.ArticleResponse;
import com.car.sns.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 검색어가 존재하는 경우 검색 조회 결과 반환, 존재하지 않는 경우 전체 게시글 반환
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

        Page<ArticleResponse> articleResponses = articleService.searchArticles(searchType, searchKeyword, pageable).map(ArticleResponse::from);
        map.addAttribute("articles", articleResponses);
        log.info("article response: ", articleResponses);
        return "features-posts";
    }

    @GetMapping("/detail/{articleId}")
    public String readArticleDetail(@PathVariable Long articleId, ModelMap map) {
        ArticleDto articleDto = articleService.getArticle(articleId);
        map.addAttribute("articleDetail", ArticleResponse.from(articleDto));
        //TODO: 게시글 관련 댓글도 함께 조회가능하도록 구현
        return "features-posts";
    }
}
