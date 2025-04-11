package com.hanpyeon.academyapi.board.service.question;

import com.hanpyeon.academyapi.board.dao.QuestionRepository;
import com.hanpyeon.academyapi.board.dto.QuestionUpdateCommand;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.NoSuchQuestionException;
import com.hanpyeon.academyapi.board.exception.RequestDeniedException;
import com.hanpyeon.academyapi.board.service.question.update.QuestionUpdateHandler;
import com.hanpyeon.academyapi.board.service.question.validate.QuestionValidateManager;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.Role;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Component
@AllArgsConstructor
public class QuestionUpdateService {
    private final List<QuestionUpdateHandler> questionUpdateHandlers;
    private final QuestionValidateManager questionValidateManager;
    private final QuestionRepository questionRepository;

    @Transactional
    public Long updateQuestion(@Validated final QuestionUpdateCommand questionUpdateDto) {
        final Question targetQuestion = findQuestion(questionUpdateDto.questionId());
        verifyAccess(targetQuestion.getOwnerMember().getId(), questionUpdateDto.requestMemberId(), questionUpdateDto.memberRole());
        questionUpdateHandlers.stream()
                .forEach(questionUpdateHandler -> questionUpdateHandler.update(targetQuestion, questionUpdateDto));
        questionValidateManager.validate(targetQuestion);
        return targetQuestion.getId();
    }

    private void verifyAccess(final Long ownedMemberId, final Long requestMemberId, final Role role) {
        if (role.equals(Role.ADMIN) || role.equals(Role.TEACHER) || role.equals(Role.MANAGER)) {
            return;
        }
        if (!requestMemberId.equals(ownedMemberId)) {
            throw new RequestDeniedException("본인 질문이 아닙니다", ErrorCode.DENIED_EXCEPTION);
        }
    }

    private Question findQuestion(final Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new NoSuchQuestionException(ErrorCode.NO_SUCH_QUESTION));
    }
}
