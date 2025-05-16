package com.hpmath.domain.course.adapter.out;

import com.hpmath.domain.course.application.exception.CourseException;
import com.hpmath.domain.course.application.port.out.DeleteCoursePort;
import com.hpmath.domain.course.entity.Course;
import com.hpmath.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
class DeleteCourseAdapter implements DeleteCoursePort {

    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public void delete(final Long courseId) {
        final Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseException("반을 찾을 수 없음", ErrorCode.NO_SUCH_COURSE));
        course.getMemos().stream()
                        .forEach(memo -> memo.delete());
        courseRepository.deleteById(courseId);
    }
}
