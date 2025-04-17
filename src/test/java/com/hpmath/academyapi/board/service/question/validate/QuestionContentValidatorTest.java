package com.hpmath.academyapi.board.service.question.validate;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * no more used
 */
@ExtendWith(MockitoExtension.class)
class QuestionContentValidatorTest {

//    QuestionValidator questionValidator = new QuestionContentValidator();
//    @Test
//    void 글자수_초과_에러처리_테스트() {
//        String exceedSizeContent = "q".repeat(501);
//        final Question question = Question.builder().content(exceedSizeContent).build();
//
//        assertThatThrownBy(() -> questionValidator.validate(question))
//                .isInstanceOf(QuestionContentOverSizeException.class);
//    }
//    @Test
//    void 글자수_경계_테스트() {
//        String exceedSizeContent = "q".repeat(500);
//
//        final Question question = Question.builder().content(exceedSizeContent).build();
//
//        assertDoesNotThrow(() -> questionValidator.validate(question));
//    }
}