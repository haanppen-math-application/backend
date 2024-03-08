package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.board.dto.QuestionUpdateDto;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.RequestDeniedException;
import com.hanpyeon.academyapi.board.service.question.validate.QuestionValidateManager;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class QuestionUpdateManager {
    private final List<QuestionUpdateHandler> questionUpdateHandlers;
    private final QuestionValidateManager questionValidateManager;

    public void update(final Question targetQuestion, final QuestionUpdateDto questionUpdateDto) {
        verifyAccess(targetQuestion.getOwnerMember().getId(), questionUpdateDto.requestMemberId());
        questionUpdateHandlers.stream()
                .forEach(questionUpdateHandler -> questionUpdateHandler.update(targetQuestion, questionUpdateDto));
        questionValidateManager.validate(targetQuestion);
    }

    private void verifyAccess(final Long ownedMemberId, final Long requestMemberId) {
        if (!requestMemberId.equals(ownedMemberId)) {
            throw new RequestDeniedException("본인 질문이 아닙니다", ErrorCode.DENIED_EXCEPTION);
        }
    }
}
