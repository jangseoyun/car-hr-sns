package com.car.sns.application.usecase;

import com.car.sns.domain.board.model.ArticleDto;
import com.car.sns.domain.hashtag.model.ArticleHashtagDto;
import com.car.sns.domain.hashtag.model.HashtagDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HashtagUseCase {
    Page<ArticleDto> searchContainHashtagName(String hashtagKeyword, Pageable pageable);

    List<String> getHashtags();
}
