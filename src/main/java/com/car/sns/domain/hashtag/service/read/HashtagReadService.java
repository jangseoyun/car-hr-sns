package com.car.sns.domain.hashtag.service.read;

import com.car.sns.application.usecase.hashtag.HashtagUseCase;
import com.car.sns.domain.board.model.ArticleDto;
import com.car.sns.infrastructure.jpaRepository.ArticleHashtagJpaRepository;
import com.car.sns.infrastructure.jpaRepository.HashtagJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashtagReadService implements HashtagUseCase {

    private final HashtagJpaRepository hashtagJpaRepository;
    private final ArticleHashtagJpaRepository articleHashtagJpaRepository;

    /**
     * 1. 해시태그 이름으로 검색
     * 2. 검색한 해시태그의 해시태그 PK로 게시글 조회
     * 3. 게시글 페이징 반환
     */
    @Override
    public Page<ArticleDto> searchContainHashtagName(String hashtagKeyword, Pageable pageable) {
        if (hashtagKeyword == null || hashtagKeyword.isBlank()) {
            return Page.empty(pageable);
        }

        Set<Long> searchHashtag = hashtagJpaRepository.findByHashtag(hashtagKeyword);
        return new PageImpl<>(articleHashtagJpaRepository.findAllById(searchHashtag).stream()
                .map(articleHashtag -> ArticleDto.from(articleHashtag.getArticle()))
                .collect(Collectors.toList()));
    }

    @Override
    public List<String> getHashtags() {
        return hashtagJpaRepository.findAllDistinctHashtag();
    }

    /**
     * 본문을 파싱해서 해시태그 이름을 중복 없이 반환
     * '#', '_' 지원
     * TODO: parser domain으로 분리 가능
     */
    public Set<String> parseHashtagNames(String content) {
        if (content == null) {
            return Set.of();
        }

        Pattern pattern = Pattern.compile("#[\\w가-힣]+");
        Matcher matcher = pattern.matcher(content.strip());
        Set<String> result = new HashSet<>();

        while (matcher.find()) {
            result.add(matcher.group().replace("#", ""));
        }

        return Set.copyOf(result);
    }

    public Set<String> findHashtagByNames(Set<String> hashtagNames) {
        return hashtagJpaRepository.findByHashtagNameIn(hashtagNames);
    }

    public void deleteHashtagWithoutArticles(Long hashtagId) {
        try {
            articleHashtagJpaRepository.findById(hashtagId);
        } catch (IllegalArgumentException e) {
            hashtagJpaRepository.deleteById(hashtagId);
        }
    }
}
