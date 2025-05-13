package com.hpmath.domain.directory.service.question;

import com.hpmath.domain.directory.dao.MemberManager;
import com.hpmath.domain.directory.dao.QuestionRepository;
import com.hpmath.domain.directory.dto.QuestionRegisterCommand;
import com.hpmath.domain.directory.entity.Question;
import com.hpmath.domain.directory.service.question.validate.QuestionValidateManager;
import com.hpmath.domain.member.Member;
import com.hpmath.hpmathcore.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class QuestionRegisterService {
    private final QuestionRepository questionRepository;
    private final QuestionValidateManager questionValidateManager;
    private final MemberManager memberManager;

    @Transactional
    public Long addQuestion(final QuestionRegisterCommand questionRegisterDto) {
        final Question question = create(questionRegisterDto);
        questionValidateManager.validate(question);
        questionRepository.save(question);
        return question.getId();
    }

    private Question create(final QuestionRegisterCommand questionRegisterDto) {
        final Member requestMember = memberManager.getMemberWithRoleValidated(questionRegisterDto.requestMemberId(),
                Role.STUDENT);
        Member targetMember;
        if (questionRegisterDto.targetMemberId() == null) {
            targetMember = null;
        } else {
            targetMember = memberManager.getMemberWithRoleValidated(questionRegisterDto.targetMemberId(), Role.TEACHER,
                    Role.MANAGER);
        }
        return Question.of(questionRegisterDto.images(), questionRegisterDto.title(), questionRegisterDto.content(),
                requestMember, targetMember);
    }
}
