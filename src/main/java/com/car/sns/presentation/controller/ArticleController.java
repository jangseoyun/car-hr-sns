package com.car.sns.presentation.controller;

import com.car.sns.application.usecase.ArticleManagementUseCase;
import com.car.sns.application.usecase.ArticleReaderUseCase;
import com.car.sns.application.usecase.PaginationUseCase;
import com.car.sns.domain.board.model.CreateArticleInfoDto;
import com.car.sns.domain.board.model.type.SearchType;
import com.car.sns.presentation.model.ArticleWithCommentDto;
import com.car.sns.presentation.model.request.ArticleModifyRequest;
import com.car.sns.presentation.model.request.ArticleRequest;
import com.car.sns.presentation.model.response.ArticleResponse;
import com.car.sns.presentation.model.response.ArticleWithCommentsResponse;
import com.car.sns.security.CarAppPrincipal;
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

    private final ArticleReaderUseCase articleUseCase;
    private final ArticleManagementUseCase articleManagementUseCase;
    private final PaginationUseCase paginationUseCase;

    @GetMapping("/create")
    public String getCreatePostPage() {
        return "features-post-create";
    }

    /**
     * 게시글 목록 Index
     * searchType, searchKeyword 존재 X : 게시글 전체 조회
     * searchType, searchKeyword 존재 O : 타입/키워드를 필터링으로 해당 게시글 조회
     */
    @GetMapping("/index")
    public String index(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchKeyword,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {

        Page<ArticleResponse> articleResponses = articleUseCase.getAllOrSearchArticles(searchType, searchKeyword, pageable)
                .map(ArticleResponse::from);
        List<Integer> paginationBarNumber = paginationUseCase.getPaginationBarNumbers(pageable.getPageNumber(), articleResponses.getTotalPages());

        map.addAttribute("articles", articleResponses);
        map.addAttribute("paginationBarNumbers", paginationBarNumber);
        map.addAttribute("searchTypes", SearchType.values());

        log.info("article response: {}", articleResponses.getContent());
        return "features-posts";
    }

    @GetMapping("/detail/{articleId}")
    public String readArticleDetail(@PathVariable Long articleId, ModelMap map
    ) {
        ArticleWithCommentDto getArticleWithComments = articleUseCase.getArticleDetailWithComments(articleId);
        map.addAttribute("articleDetail", ArticleWithCommentsResponse.from(getArticleWithComments));

        log.info("detail response: {}", ArticleWithCommentsResponse.from(getArticleWithComments));
        return "features-posts-detail";
    }

    @DeleteMapping("/{articleId}")
    public String removeArticle(@PathVariable Long articleId) {
        articleManagementUseCase.deleteArticle(articleId);
        return "redirect:/articles/index";
    }

    @PutMapping("")
    public String modifyPostContent(@AuthenticationPrincipal CarAppPrincipal carAppPrincipal,
                                    ArticleModifyRequest articleModifyRequest) {
        articleManagementUseCase.updateArticle(articleModifyRequest, carAppPrincipal.getUsername());
        return "redirect:/articles/detail/" + articleModifyRequest.articleId();
    }

    @PostMapping("/create")
    public String createArticlePost(@AuthenticationPrincipal CarAppPrincipal carAppPrincipal,
                                    ArticleRequest articleRequest) {
        articleManagementUseCase.createArticle(CreateArticleInfoDto.of(articleRequest, carAppPrincipal.getUsername()));

        return "";
    }
}
