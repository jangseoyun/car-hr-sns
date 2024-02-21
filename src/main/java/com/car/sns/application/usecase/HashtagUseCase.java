package com.car.sns.application.usecase;

import com.car.sns.domain.board.model.ArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HashtagUseCase {
    Page<ArticleDto> searchArticlesViaHashtag(String hashtagKeyword, Pageable pageable);

    List<String> getHashtags();
}
