package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.hpmathcoreapi.account.repository.MemberRepository;
import com.hpmath.hpmathcoreapi.course.application.port.out.LoadStudentsPort;
import com.hpmath.hpmathcoreapi.course.domain.Student;
import com.hpmath.hpmathcoreapi.security.Role;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LoadStudentsAdapter implements LoadStudentsPort {

    private final MemberRepository memberRepository;
    private final CourseMapper courseMapper;

    @Override
    public List<Student> loadStudents(final List<Long> memberIds) {
        int requestMemberCount = memberIds.size();

        List<Student> students = loadAllStudent(memberIds);
//        if (students.size() != requestMemberCount) {
//            throw new NoSuchMemberException(ErrorCode.NO_SUCH_MEMBER);
//        }
        return students;
    }

    public List<Student> loadAllStudent(final List<Long> memberIds) {
        return memberRepository.findMembersByIdIsInAndRoleAndRemovedIsFalse(memberIds, Role.STUDENT).stream()
                .map(courseMapper::mapToStudent)
                .toList();
    }
}
