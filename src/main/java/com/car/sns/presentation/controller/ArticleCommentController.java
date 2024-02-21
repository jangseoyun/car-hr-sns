package com.car.sns.presentation.controller;

import com.car.sns.domain.user.model.UserAccountDto;
import com.car.sns.presentation.model.request.ArticleCommentRequest;
import com.car.sns.security.CarAppPrincipal;
import com.car.sns.domain.comment.service.ArticleCommentWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class ArticleCommentController {

    private final ArticleCommentWriteService articleCommentService;

    //추가
    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest,
                                        @AuthenticationPrincipal CarAppPrincipal carAppPrincipal
    ) {
        log.info("request: {}", articleCommentRequest.toString());
        UserAccountDto userAccountDto = UserAccountDto.of(null, "Uno", "Uno", "2343", "uno@email.com", "nickname", null);
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(userAccountDto));
        return "redirect:/articles/detail/" + articleCommentRequest.articleId();
    }

    //hard delete
    @DeleteMapping("/{commentId}")
    public String postDelete(@PathVariable("articleId") Long articleId,
                             @PathVariable("commentId") Long commentId) {
        articleCommentService.deleteComment(commentId);
        return "redirect:/articles/detail/" + articleId;
    }

    //soft delete
    @PutMapping("/{articleId}/{commentId}")
    public String postSoftDelete(@PathVariable("articleId") Long articleId,
                                 @PathVariable("commentId") Long commentId) {
        articleCommentService.softDeleteComment(commentId);
        return "redirect:/articles/detail/" + articleId;
    }
}
