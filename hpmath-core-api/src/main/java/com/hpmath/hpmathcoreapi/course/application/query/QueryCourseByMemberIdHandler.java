package com.hpmath.hpmathcoreapi.course.application.query;

import com.hpmath.hpmathcoreapi.course.domain.Course;
import java.util.List;

interface QueryCourseByMemberIdHandler {
    List<Course> query(final Long memberId);

    boolean applicable(final Long memberId);
}
