package com.hanpyeon.academyapi.board.service.question.register;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.board.dto.QuestionRegisterDto;
import com.hanpyeon.academyapi.security.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@AllArgsConstructor
public class QuestionRelatedMemberProvider {
    private final MemberManager memberManager;

    public QuestionRelatedMember getQuestionRelatedMember(final QuestionRegisterDto questionDto) {
        Member student = findMemberWithRoleValidation(questionDto.requestMemberId(), Role.STUDENT);
        Member teacher = findMemberWithRoleValidation(questionDto.targetMemberId(), Role.MANAGER, Role.TEACHER);
        return new QuestionRelatedMember(student, teacher);
    }
    public Member getUpperTeacherTargetMember(final Long memberId) {
        return this.findMemberWithRoleValidation(memberId, Role.TEACHER, Role.MANAGER);
    }

    private Member findMemberWithRoleValidation(final Long memberId, final Role... roles) {
        return memberManager.getMemberWithValidated(
                memberId, member -> Arrays.stream(roles).anyMatch(role -> role.equals(member.getRole()))
        );
    }
}
