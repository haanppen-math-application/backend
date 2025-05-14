package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.hpmathcoreapi.course.application.exception.NoSuchCourseException;
import com.hpmath.hpmathcoreapi.course.application.port.out.LoadCourseTeacherIdPort;
import com.hpmath.hpmathcoreapi.course.entity.Course;
import com.hpmath.hpmathcore.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LoadCourseTeacherIdAdapter implements LoadCourseTeacherIdPort {

    private final CourseRepository courseRepository;

    @Override
    public Long loadTeacherId(Long courseId) {
        final Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchCourseException("반을 찾을 수 없음", ErrorCode.NO_SUCH_COURSE));
        return course.getTeacherId();
    }
}
