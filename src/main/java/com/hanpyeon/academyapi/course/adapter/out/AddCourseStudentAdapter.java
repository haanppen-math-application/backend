package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.course.application.port.out.AddCourseStudentPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class AddCourseStudentAdapter implements AddCourseStudentPort {

    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final CourseStudentRepository courseStudentRepository;

    @Override
    public void addToCourse(final Long courseId, final List<Long> studentId) {
        final List<Member> students = memberRepository.findAllById(studentId);
        final Course course = courseRepository.findById(courseId)
                .orElseThrow();
        final List<CourseStudent> newCourseStudents = students.stream().map(student -> CourseStudent.of(student, course))
                .toList();

        courseStudentRepository.saveAll(newCourseStudents);
    }
}
