package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.course.application.exception.NoSuchCourseException;
import com.hpmath.hpmathcoreapi.course.application.exception.NoSuchMemberException;
import com.hpmath.hpmathcoreapi.course.application.port.out.UpdateCoursePort;
import com.hpmath.hpmathcoreapi.course.domain.Course;
import com.hpmath.hpmathcoreapi.course.entity.CourseStudent;
import com.hpmath.hpmathcore.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateCourseAdapter implements UpdateCoursePort {
    private final CourseRepository courseRepository;
    private final MemberRepository memberRepository;
    private final CourseStudentRepository courseStudentRepository;

    @Override
    @Transactional
    public void updateCourse(final Course updatedCourseDomain) {
        final com.hpmath.hpmathcoreapi.course.entity.Course course = courseRepository.findById(updatedCourseDomain.getCourseId())
                .orElseThrow(() -> new NoSuchCourseException("반 찾을 수 없음", ErrorCode.NO_SUCH_COURSE_MEMBER));
        course.changeCourseName(updatedCourseDomain.getCourseName());
        final Member newTeacher = memberRepository.findMemberByIdAndRoleAndRemovedIsFalse(updatedCourseDomain.getTeacher().id(), Role.TEACHER)
                .orElseThrow(() -> new NoSuchMemberException("선생 찾을 수 없음", ErrorCode.NO_SUCH_COURSE_MEMBER));
        course.changeTeacher(newTeacher);

        final List<Long> studentIds = updatedCourseDomain.getStudents().stream()
                .map(student -> student.id())
                .toList();
        final List<Member> students = memberRepository.findMembersByIdIsInAndRoleAndRemovedIsFalse(studentIds, Role.STUDENT);
        courseStudentRepository.deleteAll(course.getCourseStudents());
        courseStudentRepository.saveAll(students.stream().map(student -> CourseStudent.addToCourse(student, course)).toList());
    }
}
