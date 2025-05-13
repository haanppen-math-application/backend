package com.hpmath.domain.online.service.course.update;

import com.hpmath.domain.online.dao.OnlineCourse;
import com.hpmath.domain.online.domain.OnlineCourseName;
import com.hpmath.domain.online.dto.OnlineCourseInfoUpdateCommand;
import com.hpmath.domain.online.service.course.OnlineCourseAbstractFactory;
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
