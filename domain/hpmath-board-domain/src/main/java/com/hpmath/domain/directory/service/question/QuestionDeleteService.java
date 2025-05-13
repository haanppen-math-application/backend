package com.hpmath.domain.directory.service.question;

import com.hpmath.domain.directory.dao.MemberManager;
import com.hpmath.domain.directory.dao.QuestionRepository;
import com.hpmath.domain.directory.dto.QuestionDeleteCommand;
import com.hpmath.domain.directory.entity.Question;
import com.hpmath.domain.directory.exception.BoardException;
import com.hpmath.domain.directory.exception.NoSuchQuestionException;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathcore.Role;
import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class QuestionDeleteService {
    private final QuestionRepository questionRepository;
    private final MemberManager memberManager;

    @Transactional
    public void deleteQuestion(@Valid final QuestionDeleteCommand questionDeleteDto) {
        final Question question = findQuestion(questionDeleteDto.questionId());
        hasPermission(questionDeleteDto.requestMemberId(), question.getOwnerMember().getId());
        questionRepository.delete(question);
    }

    private void hasPermission(final Long requestMemberId, final Long questionOwnerId) {
        if (requestMemberId.equals(questionOwnerId) || Objects.nonNull(memberManager.getMemberWithRoleValidated(requestMemberId, Role.MANAGER, Role.TEACHER))) {
            return;
        }
        throw new BoardException("삭제 권한 부재", ErrorCode.DENIED_EXCEPTION);
    }

    private Question findQuestion(final Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new NoSuchQuestionException(ErrorCode.NO_SUCH_QUESTION));
    }
}
