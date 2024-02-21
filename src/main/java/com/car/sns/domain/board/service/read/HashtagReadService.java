package com.car.sns.domain.board.service.read;

import com.car.sns.application.usecase.HashtagUseCase;
import com.car.sns.domain.board.model.ArticleDto;
import com.car.sns.domain.board.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashtagReadService implements HashtagUseCase {

    private final ArticleRepository articleRepository;

    @Override
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtagKeyword, Pageable pageable) {
        if (hashtagKeyword == null || hashtagKeyword.isBlank()) {
            return Page.empty(pageable);
        }

        return articleRepository.findByHashtag(hashtagKeyword, pageable)
                .map(ArticleDto::from);
    }

    @Override
    public List<String> getHashtags() {
        return articleRepository.findAllDistinctHashtag();
    }
}
