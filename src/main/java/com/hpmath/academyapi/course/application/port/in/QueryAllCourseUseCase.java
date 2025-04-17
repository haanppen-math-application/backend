package com.hpmath.academyapi.course.application.port.in;

import com.hpmath.academyapi.course.controller.Responses.CoursePreviewResponse;
import java.util.List;

public interface QueryAllCourseUseCase {
    List<CoursePreviewResponse> loadAllCoursePreviews();
}
