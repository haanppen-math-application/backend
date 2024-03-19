package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.CoursePreview;
import com.hanpyeon.academyapi.course.application.port.in.LoadCoursesByTeacherQuery;
import com.hanpyeon.academyapi.course.application.port.out.LoadCoursesByTeacherIdPort;
import com.hanpyeon.academyapi.course.domain.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LoadCoursesByTeacherQueryService implements LoadCoursesByTeacherQuery {

    private final LoadCoursesByTeacherIdPort loadCoursesByTeacherIdPort;

    @Override
    public List<CoursePreview> loadCoursePreviews(final Long teacherId) {
        List<Course> courses = loadCoursesByTeacherIdPort.loadByTeacherId(teacherId);
        return courses.stream()
                .map(CoursePreview::of)
                .toList();
    }
}
