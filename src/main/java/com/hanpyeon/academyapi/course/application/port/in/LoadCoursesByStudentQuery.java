package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.CoursePreview;
import java.util.List;

public interface LoadCoursesByStudentQuery {
    List<CoursePreview> loadCoursePreviews(final Long studentId);
}
