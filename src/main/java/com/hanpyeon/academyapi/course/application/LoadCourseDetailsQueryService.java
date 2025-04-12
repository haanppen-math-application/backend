package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.controller.Responses.CourseDetailResponse;
import com.hanpyeon.academyapi.course.application.port.in.LoadCourseDetailsQuery;
import com.hanpyeon.academyapi.course.application.port.out.LoadCoursePort;
import com.hanpyeon.academyapi.course.domain.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LoadCourseDetailsQueryService implements LoadCourseDetailsQuery {

    private final LoadCoursePort loadCoursePort;

    @Override
    @Transactional(readOnly = true)
    public CourseDetailResponse loadCourseDetails(final Long courseId) {
        final Course course = loadCoursePort.loadCourse(courseId);
        return CourseDetailResponse.of(course);
    }
}
