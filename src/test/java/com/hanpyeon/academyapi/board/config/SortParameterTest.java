package com.hanpyeon.academyapi.board.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class SortParameterTest {
    @ParameterizedTest
    @CsvSource({
            "date, registeredDateTime",
            "solve, solved"
    })
    void 요청파라미터_에서_엔티티필드명_변경(String param, String entityField) {
        assertThat(SortParameter.getEntityFieldName(param))
                .isEqualTo(entityField);
    }
    @ParameterizedTest
    @CsvSource({
            "dates",
            "solved",
            " dates",
            "any"
    })
    void 실패_테스트(String param) {
        assertThatThrownBy(() -> SortParameter.getEntityFieldName(param))
                .isInstanceOf(IllegalArgumentException.class);
    }

}