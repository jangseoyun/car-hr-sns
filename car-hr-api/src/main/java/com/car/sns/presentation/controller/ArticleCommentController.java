package com.car.sns.presentation.controller;

import com.car.sns.application.usecase.comment.ArticleCommentManagementUseCase;
import com.car.sns.presentation.model.request.ArticleCommentRequest;
import com.car.sns.presentation.model.response.Result;
import com.car.sns.security.CarAppPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class ArticleCommentController {

    private final ArticleCommentManagementUseCase articleCommentManagementUseCase;

    @PostMapping("/create")
    public ResponseEntity<Result> postNewArticleComment(@RequestBody ArticleCommentRequest articleCommentRequest,
                                                        @AuthenticationPrincipal CarAppPrincipal carAppPrincipal
    ) {
        log.info("request: {}", articleCommentRequest.toString());
        articleCommentManagementUseCase.saveArticleComment(articleCommentRequest.toDto(carAppPrincipal));

        return ResponseEntity.ok().body(Result.success(null));
    }

    //soft delete
    @PutMapping("/{articleId}/{commentId}")
    public String postSoftDelete(@PathVariable("articleId") Long articleId,
                                 @PathVariable("commentId") Long commentId,
                                 @AuthenticationPrincipal CarAppPrincipal carAppPrincipal) {
        articleCommentManagementUseCase.softDeleteComment(commentId);
        return "redirect:/articles/detail/" + articleId;
    }

    //hard delete
    /*@DeleteMapping("/{commentId}")
    public String postDelete(@PathVariable("articleId") Long articleId,
                             @PathVariable("commentId") Long commentId,
                             @AuthenticationPrincipal CarAppPrincipal carAppPrincipal) {
        articleCommentManagementUseCase.deleteComment(commentId);
        return "redirect:/articles/detail/" + articleId;
    }*/
}
