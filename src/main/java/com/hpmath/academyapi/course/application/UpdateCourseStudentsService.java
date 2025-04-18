package com.hpmath.academyapi.course.application;

import com.hpmath.academyapi.course.application.dto.UpdateCourseStudentsCommand;
import com.hpmath.academyapi.course.application.port.in.UpdateCourseStudentsUseCase;
import com.hpmath.academyapi.course.application.port.out.LoadCoursePort;
import com.hpmath.academyapi.course.application.port.out.LoadStudentsPort;
import com.hpmath.academyapi.course.application.port.out.UpdateCoursePort;
import com.hpmath.academyapi.course.domain.Course;
import com.hpmath.academyapi.course.domain.Student;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCourseStudentsService implements UpdateCourseStudentsUseCase {
    private final LoadStudentsPort loadStudentsPort;
    private final LoadCoursePort loadCoursePort;
    private final UpdateCoursePort updateCoursePort;

    @Transactional
    public void updateStudents(UpdateCourseStudentsCommand updateCourseStudentsDto) {
        final List<Student> students = loadStudentsPort.loadStudents(updateCourseStudentsDto.studentIds());
        final Course targetCourse = loadCoursePort.loadCourse(updateCourseStudentsDto.courseId());
        targetCourse.setStudents(students);
        updateCoursePort.updateCourse(targetCourse);
    }
}
