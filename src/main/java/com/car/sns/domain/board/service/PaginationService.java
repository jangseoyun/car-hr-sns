package com.car.sns.domain.board.service;

import com.car.sns.application.usecase.PaginationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PaginationService implements PaginationUseCase {

    //bar 길이 : 상태값으로 존재하도록 구현
    private static final int BAR_LENGTH = 5;

    @Override
    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {
        int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0);
        int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages);

        return IntStream.range(startNumber, endNumber).boxed().toList();
    }

    @Override
    public int currentBarLength() {
        return BAR_LENGTH;
    }

}
