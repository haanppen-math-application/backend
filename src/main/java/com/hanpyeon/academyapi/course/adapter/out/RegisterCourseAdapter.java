package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.course.application.port.out.RegisterCoursePort;
import com.hanpyeon.academyapi.course.domain.Course;
import com.hanpyeon.academyapi.course.domain.Student;
import com.hanpyeon.academyapi.course.domain.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RegisterCourseAdapter implements RegisterCoursePort {
    private final CourseRepository courseRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long register(final Course course) {
        return courseRepository.save(mapToEntity(course)).getId();
    }


    private CourseEntity mapToEntity(final Course course) {
        return new CourseEntity(
                course.getCourseName(),
                findStudents(course.getStudents()),
                findTeacher(course.getTeacher())
        );
    }

    private List<Member> findStudents(final List<Student> students) {
        List<Long> studentIds = students.stream()
                .map(student -> student.memberId())
                .toList();
        return memberRepository.findAllById(studentIds);
    }

    private Member findTeacher(final Teacher teacher) {
        return memberRepository.findById(teacher.id())
                .orElseThrow();
    }
}
