package com.hpmath.hpmathcoreapi.course.application.port.in;

import com.hpmath.hpmathcoreapi.course.controller.Responses.CoursePreviewResponse;
import java.util.List;

public interface LoadCoursesByStudentQuery {
    List<CoursePreviewResponse> loadCoursePreviews(final Long studentId);
}
