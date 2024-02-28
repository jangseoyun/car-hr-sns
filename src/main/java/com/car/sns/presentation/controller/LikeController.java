package com.car.sns.presentation.controller;

import com.car.sns.application.usecase.like.LikeManagementUseCase;
import com.car.sns.application.usecase.like.LikeReadUseCase;
import com.car.sns.presentation.model.response.LikeToArticleResponse;
import com.car.sns.presentation.model.response.Result;
import com.car.sns.security.CarAppPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final LikeManagementUseCase likeManagementUseCase;
    private final LikeReadUseCase likeReadUseCase;

    @GetMapping("/{articleId}")
    public ResponseEntity<Result> saveLikeToArticle(@PathVariable("articleId") Long articleId,
                                                    @AuthenticationPrincipal CarAppPrincipal carAppPrincipal) {
        likeManagementUseCase.saveLikeTarget(articleId, carAppPrincipal.userAccountDto());
        return ResponseEntity.ok().body(Result.success(null));
    }

    @GetMapping("/{articleId}/count")
    public ResponseEntity<Result> getLikeCountNumber(@PathVariable("articleId") Long articleId,
                                                     @AuthenticationPrincipal CarAppPrincipal carAppPrincipal) {
        LikeToArticleResponse likeCount = likeReadUseCase.getLikeCount(articleId, carAppPrincipal.getName());
        return ResponseEntity.ok().body(Result.success(likeCount));
    }

}
