package com.hpmath.academyapi.course.adapter.out;

import com.hpmath.academyapi.account.repository.MemberRepository;
import com.hpmath.academyapi.course.application.port.out.LoadStudentsPort;
import com.hpmath.academyapi.course.domain.Student;
import com.hpmath.academyapi.security.Role;
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
