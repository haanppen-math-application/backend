package com.hpmath.domain.course.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.domain.course.repository.CourseRepository;
import com.hpmath.domain.course.dto.CourseUpdateCommand;
import com.hpmath.domain.course.entity.Course;
import com.hpmath.domain.course.exception.NoSuchCourseException;
import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class UpdateCourseService {
    private final CourseRepository courseRepository;

    @Transactional
    public void updateCourse(@Valid final CourseUpdateCommand command) {
        final Course course = loadCourse(command);

        if (Objects.nonNull(command.courseName())) {
            course.changeCourseName(command.courseName());
        }
        if (Objects.nonNull(command.newTeacherId())) {
            course.changeTeacher(command.newTeacherId());
        }
    }

    private Course loadCourse(CourseUpdateCommand command) {
        return courseRepository.findById(command.courseId())
                .orElseThrow(() -> new NoSuchCourseException(ErrorCode.NO_SUCH_COURSE));
    }
}
