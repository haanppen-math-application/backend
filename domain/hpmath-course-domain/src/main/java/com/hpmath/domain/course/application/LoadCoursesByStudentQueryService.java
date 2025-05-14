package com.hpmath.domain.course.application;

import com.hpmath.domain.course.application.dto.Responses.CoursePreviewResponse;
import com.hpmath.domain.course.application.port.in.LoadCoursesByStudentQuery;
import com.hpmath.domain.course.application.port.out.LoadAllCoursesByStudentIdPort;
import com.hpmath.domain.course.domain.Course;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoadCoursesByStudentQueryService implements LoadCoursesByStudentQuery {

    private final LoadAllCoursesByStudentIdPort loadAllCoursesByStudentIdPort;
    @Override
    public List<CoursePreviewResponse> loadCoursePreviews(final Long studentId) {
        final List<Course> courses = loadAllCoursesByStudentIdPort.loadAll(studentId);
        return courses.stream()
                .map(CoursePreviewResponse::of)
                .toList();
    }
}
