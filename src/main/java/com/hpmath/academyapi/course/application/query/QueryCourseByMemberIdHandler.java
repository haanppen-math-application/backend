package com.hpmath.academyapi.course.application.query;

import com.hpmath.academyapi.course.domain.Course;
import java.util.List;

interface QueryCourseByMemberIdHandler {
    List<Course> query(final Long memberId);

    boolean applicable(final Long memberId);
}
