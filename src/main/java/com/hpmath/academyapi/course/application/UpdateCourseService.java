package com.hpmath.academyapi.course.application;

import com.hpmath.academyapi.course.application.dto.CourseUpdateCommand;
import com.hpmath.academyapi.course.application.port.in.UpdateCourseUseCase;
import com.hpmath.academyapi.course.application.port.out.LoadCoursePort;
import com.hpmath.academyapi.course.application.port.out.LoadTeacherPort;
import com.hpmath.academyapi.course.application.port.out.UpdateCoursePort;
import com.hpmath.academyapi.course.domain.Course;
import com.hpmath.academyapi.course.domain.Teacher;
import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCourseService implements UpdateCourseUseCase {
    private final LoadCoursePort loadCoursePort;
    private final LoadTeacherPort loadTeacherPort;
    private final UpdateCoursePort updateCoursePort;
    @Override
    @Transactional
    public void updateCourse(final @Valid CourseUpdateCommand courseUpdateDto) {
        final Course course = loadCoursePort.loadCourse(courseUpdateDto.courseId());

        if (Objects.nonNull(courseUpdateDto.courseName())){
            course.changeCourseName(courseUpdateDto.courseName());
        }
        if (Objects.nonNull(courseUpdateDto.newTeacherId())) {
            final Teacher newTeacher = loadTeacherPort.loadTeacher(courseUpdateDto.newTeacherId());
            course.changeTeacher(newTeacher);
        }

        updateCoursePort.updateCourse(course);
    }
}
