package com.hpmath.app.board.api.config;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class QuestionFieldTest {
    @ParameterizedTest
    @CsvSource({
            "date, registeredDateTime",
            "solve, solved"
    })
    void 요청파라미터_에서_엔티티필드명_변경(String param, String entityField) {
        assertThat(QuestionField.mapToEntityFieldName(param))
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
        assertThatThrownBy(() -> QuestionField.mapToEntityFieldName(param))
                .isInstanceOf(IllegalArgumentException.class);
    }

}