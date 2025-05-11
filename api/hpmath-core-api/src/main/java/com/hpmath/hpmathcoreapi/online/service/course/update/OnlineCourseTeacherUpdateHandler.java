package com.hpmath.hpmathcoreapi.online.service.course.update;

import com.hpmath.hpmathcoreapi.online.dao.OnlineCourse;
import com.hpmath.hpmathcoreapi.online.dto.OnlineCourseInfoUpdateCommand;
import com.hpmath.hpmathcoreapi.online.service.course.MemberLoader;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OnlineCourseTeacherUpdateHandler implements OnlineCourseUpdateHandler {
    private final MemberLoader memberLoader;

    @Override
    public void update(final OnlineCourse onlineCourse, final OnlineCourseInfoUpdateCommand onlineCourseUpdateCommand) {
        if (Objects.isNull(onlineCourseUpdateCommand.teacherId())) {
            return;
        }
        onlineCourse.setTeacher(memberLoader.findTeacher(onlineCourseUpdateCommand.teacherId()));
    }
}
