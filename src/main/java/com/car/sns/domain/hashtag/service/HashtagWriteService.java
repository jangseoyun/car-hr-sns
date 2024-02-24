package com.car.sns.domain.hashtag.service;

import com.car.sns.domain.hashtag.model.entity.Hashtag;
import com.car.sns.domain.hashtag.service.read.HashtagReadService;
import com.car.sns.infrastructure.jpaRepository.HashtagJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HashtagWriteService {

    private final HashtagReadService hashtagReadService;
    private final HashtagJpaRepository hashtagJpaRepository;

    //게시글 작성시 별도 입력을 하지 않고 본문에서 해시태그 파싱
    //DB에는 #을 제외한 문자열을 저장할 수 있도록 구현
    public void renewHashtagsFromContent(String content) {
        //게시글 작성시 별도 입력을 하지 않고 본문에서 해시태그 파싱
        Set<String> hashtagNamesInContent = hashtagReadService.parseHashtagNames(content);
        Set<String> existingHashtagNames = hashtagReadService.findHashtagByNames(hashtagNamesInContent)
                .stream().collect(Collectors.toUnmodifiableSet());

        hashtagNamesInContent.forEach(newHashtagName -> {
            if (!existingHashtagNames.contains(newHashtagName)) {
                hashtagJpaRepository.save(Hashtag.of(newHashtagName));
            }
        });
    }

}
