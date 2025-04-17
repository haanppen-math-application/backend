package com.hpmath.academyapi.course.adapter.out;

import com.hpmath.academyapi.course.application.exception.NoSuchCourseException;
import com.hpmath.academyapi.course.application.port.out.LoadCoursePort;
import com.hpmath.academyapi.course.domain.Course;
import com.hpmath.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LoadCourseAdapter implements LoadCoursePort {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public Course loadCourse(final Long courseId) {
        return courseMapper.mapToCourseDomain(courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchCourseException(ErrorCode.NO_SUCH_COURSE)));
    }
}
