package com.car.sns.domain.hashtag.service;

import com.car.sns.domain.hashtag.ArticleHashtagJpaRepository;
import com.car.sns.infrastructure.repository.HashtagJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("[Service-Read] - 해시태그: HashtagReadServiceTest")
@ExtendWith(MockitoExtension.class)
class HashtagReadServiceTest {

    @InjectMocks
    private HashtagReadService sut;
    @Mock
    private ArticleHashtagJpaRepository articleHashtagJpaRepository;
    @Mock
    private HashtagJpaRepository hashtagJpaRepository;

    @DisplayName("[GET] - 본문을 파싱하면 해시태그 이름들을 중복 없이 반환한다.")
    @MethodSource
    @ParameterizedTest(name = "[{index}] \"{0}\" => {1}")
    void givenContent_whenParsing_thenReturnUniqueHashtagNames(String input, Set<String> expected) {
        //given

        //when
        Set<String> actual = sut.parseHashtagNames(input);

        //then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        then(hashtagJpaRepository).shouldHaveNoInteractions();
    }

    @DisplayName("given_when_then")
    @Test
    void givenHashtagName_whenFindingArticles_thenReturnArticles() {
        //given
        String hashtagName = "#java";
        Pageable pageable = Pageable.ofSize(20);
        given(hashtagJpaRepository.findByHashtag(hashtagName)).willReturn(Set.of());

        //when
        sut.searchContainHashtagName(hashtagName, pageable);

        //then
        then(hashtagJpaRepository).should().findByHashtag(hashtagName);
    }

    static Stream<Arguments> givenContent_whenParsing_thenReturnUniqueHashtagNames() {
        return Stream.of(
                arguments("#java", Set.of("java")),
                arguments("#java_spring", Set.of("java_spring")),
                arguments("#java#spring", Set.of("java", "spring")),
                arguments("#java         #spring", Set.of("java", "spring")),
                arguments("       #java         #spring", Set.of("java", "spring")),
                arguments("#", Set.of()),
                arguments("   #", Set.of()),
                arguments("#   ", Set.of()),
                arguments("", Set.of()),
                arguments(null, Set.of())
        );
    }


}