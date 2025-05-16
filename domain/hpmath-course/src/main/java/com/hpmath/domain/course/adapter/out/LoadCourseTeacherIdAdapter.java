package com.hpmath.domain.course.adapter.out;

import com.hpmath.domain.course.application.exception.NoSuchCourseException;
import com.hpmath.domain.course.application.port.out.LoadCourseTeacherIdPort;
import com.hpmath.domain.course.entity.Course;
import com.hpmath.common.ErrorCode;
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
