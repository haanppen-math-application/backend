package com.hpmath.hpmathcoreapi.course.application;

import com.hpmath.hpmathcoreapi.course.application.dto.RegisterStudentCommand;
import com.hpmath.hpmathcoreapi.course.application.port.in.AddStudentToCourseUseCase;
import com.hpmath.hpmathcoreapi.course.application.port.out.AddCourseStudentPort;
import com.hpmath.hpmathcoreapi.course.application.port.out.LoadCoursePort;
import com.hpmath.hpmathcoreapi.course.application.port.out.LoadStudentsPort;
import com.hpmath.hpmathcoreapi.course.domain.Course;
import com.hpmath.hpmathcoreapi.course.domain.Student;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class AddStudentToCourseService implements AddStudentToCourseUseCase {

    private final LoadCoursePort loadCoursePort;
    private final LoadStudentsPort loadStudentsPort;
    private final AddCourseStudentPort addCourseStudentPort;

    @Override
    public void addStudentToCourse(RegisterStudentCommand registerStudentDto) {
        final Course course = loadCoursePort.loadCourse(registerStudentDto.courseId());
        final List<Student> students = loadStudentsPort.loadStudents(registerStudentDto.targetMembersId());

        course.addStudents(students);
        addCourseStudentPort.addToCourse(course);
    }
}
