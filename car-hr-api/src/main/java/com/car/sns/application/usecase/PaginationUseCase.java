package com.car.sns.application.usecase;

import java.util.List;

public interface PaginationUseCase {
    List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages);

    int currentBarLength();
}
