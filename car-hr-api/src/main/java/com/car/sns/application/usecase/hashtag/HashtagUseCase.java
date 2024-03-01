package com.car.sns.application.usecase.hashtag;

import com.car.sns.domain.board.model.ArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HashtagUseCase {
    Page<ArticleDto> searchContainHashtagName(String hashtagKeyword, Pageable pageable);
    List<String> getHashtags();
}
