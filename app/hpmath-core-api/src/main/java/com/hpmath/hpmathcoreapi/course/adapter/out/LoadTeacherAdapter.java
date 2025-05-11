package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.board.exception.NoSuchMemberException;
import com.hpmath.hpmathcoreapi.course.application.port.out.LoadTeacherPort;
import com.hpmath.hpmathcoreapi.course.domain.Teacher;
import com.hpmath.hpmathcore.ErrorCode;
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
