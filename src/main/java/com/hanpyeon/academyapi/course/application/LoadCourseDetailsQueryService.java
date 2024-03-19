package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.CourseDetails;
import com.hanpyeon.academyapi.course.application.port.in.LoadCourseDetailsQuery;
import com.hanpyeon.academyapi.course.application.port.out.LoadCoursePort;
import com.hanpyeon.academyapi.course.domain.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoadCourseDetailsQueryService implements LoadCourseDetailsQuery {

    private final LoadCoursePort loadCoursePort;

    @Override
    public CourseDetails loadCourseDetails(final Long courseId) {
        final Course course = loadCoursePort.loadCourse(courseId);
        return CourseDetails.of(course);
    }
}
