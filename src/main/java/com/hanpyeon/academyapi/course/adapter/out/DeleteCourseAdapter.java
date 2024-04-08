package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.port.out.DeleteCoursePort;
import com.hanpyeon.academyapi.course.domain.Course;
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
        courseRepository.deleteById(courseId);
    }
}
