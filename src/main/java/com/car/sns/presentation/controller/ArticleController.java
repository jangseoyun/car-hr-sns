package com.car.sns.presentation.controller;

import com.car.sns.application.usecase.board.ArticleManagementUseCase;
import com.car.sns.application.usecase.board.ArticleReaderUseCase;
import com.car.sns.application.usecase.PaginationUseCase;
import com.car.sns.domain.board.model.ArticleDto;
import com.car.sns.domain.board.model.CreateArticleInfoDto;
import com.car.sns.domain.board.model.type.SearchType;
import com.car.sns.presentation.model.request.ArticleModifyRequest;
import com.car.sns.presentation.model.request.ArticleRequest;
import com.car.sns.presentation.model.response.ArticleDetailWithCommentResponse;
import com.car.sns.presentation.model.response.ArticlePageResponse;
import com.car.sns.presentation.model.response.ArticleWithCommentsResponse;
import com.car.sns.presentation.model.response.Result;
import com.car.sns.security.CarAppPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/articles")
@RestController
public class ArticleController {

    private final ArticleReaderUseCase articleUseCase;
    private final ArticleManagementUseCase articleManagementUseCase;
    private final PaginationUseCase paginationUseCase;

    /**
     * 게시글 목록 Index
     * searchType, searchKeyword 존재 X : 게시글 전체 조회
     * searchType, searchKeyword 존재 O : 타입/키워드를 필터링으로 해당 게시글 조회
     */
    @GetMapping("/index")
    public ResponseEntity<ArticlePageResponse> getAllOrSearchArticles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchKeyword,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        Page<ArticleDto> articles = articleUseCase.getAllOrSearchArticles(searchType, searchKeyword, pageable);
        List<Integer> paginationBarNumbers = paginationUseCase.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
        log.info("article response: {}", articles.getContent());

        return ResponseEntity
                .ok()
                .body(ArticlePageResponse.of(articles, paginationBarNumbers));
    }

    /**
     * 게시글(articleId)의 상세 정보 반환
     * 게시글 댓글 리스트 반환
     */
    @GetMapping("/detail/{articleId}")
    public ResponseEntity<ArticleDetailWithCommentResponse> readArticleDetail(@PathVariable Long articleId,
                                                                              @AuthenticationPrincipal CarAppPrincipal carAppPrincipal)
    {
        ArticleDetailWithCommentResponse articleDetailWithCommentResponse = articleUseCase.getArticleDetailWithComments(articleId);

        log.info("detail response: {}", ArticleWithCommentsResponse.from(articleDetailWithCommentResponse));
        return ResponseEntity
                .ok()
                .body(articleDetailWithCommentResponse);
    }

    /**
     * 게시글 삭제
     * TODO: 소프트 삭제 구현 예정
     */
    @DeleteMapping("/{articleId}")
    public ModelAndView removeArticle(@PathVariable Long articleId,
                                      @AuthenticationPrincipal CarAppPrincipal carAppPrincipal)
    {
        articleManagementUseCase.deleteArticle(articleId);

        ModelAndView redirect = new ModelAndView("redirect:/articles");
        redirect.addObject("articleId", articleId);
        return redirect;
    }

    @PutMapping("")
    public ModelAndView modifyPostContent(@RequestBody ArticleModifyRequest articleModifyRequest,
                                          @AuthenticationPrincipal CarAppPrincipal carAppPrincipal)
    {
        articleManagementUseCase.updateArticle(articleModifyRequest, carAppPrincipal.getUsername());

        ModelAndView redirect = new ModelAndView("redirect:/articles/detail/");
        redirect.addObject(articleModifyRequest.articleId());
        return redirect;
    }

    /**
     * TODO: 게시글 생성 후 생성된 게시글 Response 보내주도록 리팩토링
     */
    @PostMapping("/create")
    public ResponseEntity<Result> createArticlePost(@RequestBody ArticleRequest articleRequest,
                                                    @AuthenticationPrincipal CarAppPrincipal carAppPrincipal)
    {
        Result<ArticleDto> savedArticle = articleManagementUseCase
                .createArticle(CreateArticleInfoDto.of(articleRequest, carAppPrincipal.getUsername()));

        return ResponseEntity.ok().body(savedArticle);
    }
}
