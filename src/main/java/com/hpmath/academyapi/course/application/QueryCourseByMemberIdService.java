package com.hpmath.academyapi.course.application;

import com.hpmath.academyapi.course.controller.Responses.CoursePreviewResponse;
import com.hpmath.academyapi.course.application.port.in.QueryCourseByMemberIdUseCase;
import com.hpmath.academyapi.course.application.query.QueryCourseByMemberIdManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QueryCourseByMemberIdService implements QueryCourseByMemberIdUseCase {

    private final QueryCourseByMemberIdManager queryCourseByMemberIdManager;

    @Override
    public List<CoursePreviewResponse> loadCoursePreviews(final Long memberId) {
        return queryCourseByMemberIdManager.query(memberId);
    }
}
