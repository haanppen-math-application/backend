package com.hpmath.academyapi.course.adapter.out;

import com.hpmath.academyapi.account.entity.Member;
import com.hpmath.academyapi.account.repository.MemberRepository;
import com.hpmath.academyapi.board.exception.NoSuchMemberException;
import com.hpmath.academyapi.course.application.port.out.LoadTeacherPort;
import com.hpmath.academyapi.course.domain.Teacher;
import com.hpmath.academyapi.exception.ErrorCode;
import com.hpmath.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class LoadTeacherAdapter implements LoadTeacherPort {
    private final MemberRepository memberRepository;
    private final CourseMapper courseMapper;

    @Override
    public Teacher loadTeacher(final Long id) {
        final Member member = loadMember(id);
        return courseMapper.mapToTeacher(member);
    }

    private Member loadMember(final Long memberId) {
        return memberRepository.findMemberByIdAndRoleAndRemovedIsFalse(memberId, Role.TEACHER)
                .orElseThrow(() -> new NoSuchMemberException(ErrorCode.NO_SUCH_MEMBER));
    }
}
