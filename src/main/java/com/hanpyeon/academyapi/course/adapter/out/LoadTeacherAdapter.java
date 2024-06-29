package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.board.exception.NoSuchMemberException;
import com.hanpyeon.academyapi.course.application.port.out.LoadTeacherPort;
import com.hanpyeon.academyapi.course.domain.Teacher;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
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
