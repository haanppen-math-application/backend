package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.controller.Responses.CoursePreviewResponse;
import java.util.List;

public interface QueryAllCourseUseCase {
    List<CoursePreviewResponse> loadAllCoursePreviews();
}
