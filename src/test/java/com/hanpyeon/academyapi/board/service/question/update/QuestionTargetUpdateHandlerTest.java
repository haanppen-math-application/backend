package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.board.dto.QuestionUpdateDto;
import com.hanpyeon.academyapi.security.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class QuestionTargetUpdateHandlerTest {

    @InjectMocks
    private QuestionTargetUpdateHandler targetUpdateHandler;

    @Test
    void 업데이트_가능_테스트() {
        final QuestionUpdateDto questionUpdateDto = new QuestionUpdateDto(1l, 2l, 3l, Role.STUDENT, "test", "test");

        assertEquals(targetUpdateHandler.applicable(questionUpdateDto), true);
    }

    @Test
    void 업데이트_불가_테스트() {
        final QuestionUpdateDto questionUpdateDto = new QuestionUpdateDto(1l,  null, 3l, Role.STUDENT, "test", "test");

        assertEquals(targetUpdateHandler.applicable(questionUpdateDto), false);
    }

    @Test
    void 질문_업데이트_테스트() {
//        final QuestionUpdateDto questionUpdateDto = new QuestionUpdateDto(1l, "2", 3l, 3l, Collections.emptyList());
//        final Question question = Mockito.mock(Question.class);
//        final Member member = Mockito.mock(Member.class);
//        Mockito.when(questionRelatedMemberProvider.getUpperTeacherTargetMember(3l))
//                .thenReturn(member);
//
//        targetUpdateHandler.update(question, questionUpdateDto);
//
//        Mockito.verify(question).changeTargetMember(member);
    }
}