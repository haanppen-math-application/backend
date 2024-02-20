package com.hanpyeon.academyapi.board.config;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.function.Function;

public class QuestionPageRequest extends PageRequest {

    /**
     * Creates a new {@link PageRequest} with sort parameters applied.
     *
     * @param pageNumber zero-based page number, must not be negative.
     * @param pageSize   the size of the page to be returned, must be greater than 0.
     * @param sort       must not be {@literal null}, use {@link Sort#unsorted()} instead.
     */
    protected QuestionPageRequest(int pageNumber, int pageSize, Sort sort) {
        super(pageNumber, pageSize, sort);
    }

    static QuestionPageRequest create(Pageable pageable, Function<String, String> entityFieldMapper) {
        Sort sort = Sort.by(pageable.getSort().stream()
                .map(order -> order.withProperty(entityFieldMapper.apply(order.getProperty())))
                .toList());
        return new QuestionPageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }
}
