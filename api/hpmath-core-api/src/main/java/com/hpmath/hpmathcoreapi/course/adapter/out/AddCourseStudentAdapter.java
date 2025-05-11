package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.account.entity.Member;
import com.hpmath.hpmathcoreapi.account.repository.MemberRepository;
import com.hpmath.hpmathcoreapi.course.application.exception.NoSuchCourseException;
import com.hpmath.hpmathcoreapi.course.application.exception.NoSuchMemberException;
import com.hpmath.hpmathcoreapi.course.application.port.out.AddCourseStudentPort;
import com.hpmath.hpmathcoreapi.course.entity.Course;
import com.hpmath.hpmathcoreapi.course.entity.CourseStudent;
import com.hpmath.hpmathcore.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AddCourseStudentAdapter implements AddCourseStudentPort {

    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final CourseStudentRepository courseStudentRepository;

    @Override
    public void addToCourse(final com.hpmath.hpmathcoreapi.course.domain.Course courseDomain) {
        final List<Member> students = memberRepository.findMembersByIdIsInAndRoleAndRemovedIsFalse(
                courseDomain.getStudents().stream()
                        .map(student -> student.id())
                        .toList(), Role.STUDENT);
        if (students.size() != courseDomain.getStudents().size()) {
            throw new NoSuchMemberException("새로 등록될 학생을 찾을 수 없습니다", ErrorCode.NO_SUCH_COURSE_MEMBER);
        }
        final Course course = courseRepository.findById(courseDomain.getCourseId())
                .orElseThrow(() -> new NoSuchCourseException(ErrorCode.NO_SUCH_COURSE));

        courseStudentRepository.saveAll(students.stream()
                .map(student -> CourseStudent.addToCourse(student, course))
                .toList());
    }
}
