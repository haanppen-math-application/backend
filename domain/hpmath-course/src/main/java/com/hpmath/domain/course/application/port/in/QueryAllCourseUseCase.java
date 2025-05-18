package com.hpmath.domain.course.application.port.in;

import com.hpmath.domain.course.dto.Responses.CoursePreviewResponse;
import java.util.List;

public interface QueryAllCourseUseCase {
    List<CoursePreviewResponse> loadAllCoursePreviews();
}
