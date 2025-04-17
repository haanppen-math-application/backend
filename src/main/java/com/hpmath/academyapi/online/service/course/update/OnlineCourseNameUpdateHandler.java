package com.hpmath.academyapi.online.service.course.update;

import com.hpmath.academyapi.online.dao.OnlineCourse;
import com.hpmath.academyapi.online.domain.OnlineCourseName;
import com.hpmath.academyapi.online.dto.OnlineCourseInfoUpdateCommand;
import com.hpmath.academyapi.online.service.course.OnlineCourseAbstractFactory;
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
