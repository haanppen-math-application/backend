package com.hpmath.domain.directory.service.question;

import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.directory.dao.QuestionRepository;
import com.hpmath.domain.directory.dto.QuestionRegisterCommand;
import com.hpmath.domain.directory.entity.Question;
import com.hpmath.domain.directory.exception.BoardException;
import com.hpmath.domain.directory.service.question.validate.QuestionValidateManager;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathcore.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class QuestionRegisterService {
    private final MemberClient memberRoleClient;
    private final QuestionRepository questionRepository;
    private final QuestionValidateManager questionValidateManager;

    @Transactional
    public Long addQuestion(final QuestionRegisterCommand questionRegisterDto) {
        final Question question = create(questionRegisterDto);
        questionValidateManager.validate(question);
        questionRepository.save(question);
        return question.getId();
    }

    private Question create(final QuestionRegisterCommand questionRegisterDto) {
        validateRequestMembersRole(questionRegisterDto.role());
        validateTargetMembersRole(questionRegisterDto.targetMemberId());

        return Question.of(questionRegisterDto.images(), questionRegisterDto.title(), questionRegisterDto.content(),
                questionRegisterDto.requestMemberId(), questionRegisterDto.targetMemberId());
    }

    private void validateRequestMembersRole(final Role requestMemberRole) {
        if (requestMemberRole == Role.STUDENT) {
            return;
        }
        throw new BoardException(ErrorCode.BOARD_EXCEPTION);
    }

    private void validateTargetMembersRole(final Long requestMemberId) {
        if (!memberRoleClient.isMatch(requestMemberId, Role.TEACHER, Role.MANAGER)) {
            throw new BoardException(ErrorCode.BOARD_EXCEPTION);
        }
    }
}
