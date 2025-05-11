package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.account.entity.Member;
import com.hpmath.hpmathcoreapi.account.repository.MemberRepository;
import com.hpmath.hpmathcoreapi.course.application.port.out.RegisterCoursePort;
import com.hpmath.hpmathcoreapi.course.domain.Student;
import com.hpmath.hpmathcoreapi.course.domain.Teacher;
import com.hpmath.hpmathcoreapi.course.entity.Course;
import com.hpmath.hpmathcoreapi.course.entity.CourseStudent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class RegisterCourseAdapter implements RegisterCoursePort {
    private final CourseRepository courseRepository;
    private final CourseStudentRepository courseStudentRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long register(final com.hpmath.hpmathcoreapi.course.domain.Course course) {
        Course courseEntity = mapToEntity(course);
        final List<CourseStudent> courseStudents = createCourseStudents(
                findStudents(course.getStudents()),
                courseEntity);
        // 연관관계 주인 엔티티에 추가하도록 변경
        final Long courseId = courseRepository.save(courseEntity).getId();
        courseStudentRepository.saveAll(courseStudents);
        return courseId;
    }

    private Course mapToEntity(final com.hpmath.hpmathcoreapi.course.domain.Course course) {
        return new Course(
                course.getCourseName(),
                findTeacher(course.getTeacher())
        );
    }

    private List<CourseStudent> createCourseStudents(final List<Member> students, final Course courseEntity) {
        return students.stream()
                .map(student -> CourseStudent.addToCourse(student, courseEntity))
                .toList();
    }

    private List<Member> findStudents(final List<Student> students) {
        List<Long> studentIds = students.stream()
                .map(student -> student.id())
                .toList();
        return memberRepository.findMembersByIdIsInAndRoleAndRemovedIsFalse(studentIds, Role.STUDENT);
    }

    private Member findTeacher(final Teacher teacher) {
        return memberRepository.findMemberByIdAndRoleAndRemovedIsFalse(teacher.id(), Role.TEACHER)
                .orElseThrow();
    }
}
