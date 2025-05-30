package com.hpmath.app.api.board.controller.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

class EntityFieldMappedPageRequestTest {

    @ParameterizedTest
    @CsvSource({
            "12, 12hello",
            "jdiwa, jdiwahello"
    })
    void Order_변환_테스트(String requestFieldName, String mappedFieldName) {
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Order.by(requestFieldName)));
        assertThat(EntityFieldMappedPageRequest.create(pageable, o -> o + "hello")
                .getSort()
                .getOrderFor(mappedFieldName))
                .isNotNull();

    }
}