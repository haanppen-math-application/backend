package com.hpmath.domain.course.adapter.out;

import com.hpmath.domain.course.application.exception.NoSuchCourseException;
import com.hpmath.domain.course.application.port.out.LoadCoursePort;
import com.hpmath.domain.course.domain.Course;
import com.hpmath.hpmathcore.ErrorCode;
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
