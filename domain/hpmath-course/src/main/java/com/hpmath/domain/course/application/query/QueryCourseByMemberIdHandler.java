package com.hpmath.domain.course.application.query;

import com.hpmath.domain.course.domain.Course;
import java.util.List;

interface QueryCourseByMemberIdHandler {
    List<Course> query(final Long memberId);

    boolean applicable(final Long memberId);
}
