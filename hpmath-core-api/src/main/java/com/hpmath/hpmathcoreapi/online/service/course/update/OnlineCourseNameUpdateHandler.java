package com.hpmath.hpmathcoreapi.online.service.course.update;

import com.hpmath.hpmathcoreapi.online.dao.OnlineCourse;
import com.hpmath.hpmathcoreapi.online.domain.OnlineCourseName;
import com.hpmath.hpmathcoreapi.online.dto.OnlineCourseInfoUpdateCommand;
import com.hpmath.hpmathcoreapi.online.service.course.OnlineCourseAbstractFactory;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OnlineCourseNameUpdateHandler implements OnlineCourseUpdateHandler {
    private final OnlineCourseAbstractFactory onlineCourseAbstractFactory;

    @Override
    public void update(final OnlineCourse onlineCourse, final OnlineCourseInfoUpdateCommand onlineCourseUpdateCommand) {
        if (Objects.isNull(onlineCourseUpdateCommand.courseName())) {
            return;
        }
        final OnlineCourseName onlineCourseName = onlineCourseAbstractFactory.toCourseName(
                onlineCourseUpdateCommand.courseName());
        onlineCourse.setCourseName(onlineCourseName.name());
    }
}
