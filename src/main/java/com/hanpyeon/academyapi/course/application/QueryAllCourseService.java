package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.controller.Responses.CoursePreviewResponse;
import com.hanpyeon.academyapi.course.application.port.in.QueryAllCourseUseCase;
import com.hanpyeon.academyapi.course.application.port.out.LoadAllCoursePort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryAllCourseService implements QueryAllCourseUseCase {
    private final LoadAllCoursePort loadAllCoursePort;

    @Override
    @Transactional(readOnly = true)
    public List<CoursePreviewResponse> loadAllCoursePreviews() {
        return loadAllCoursePort.loadAllCourses()
                .stream()
                .map(CoursePreviewResponse::of)
                .toList();
    }
}
