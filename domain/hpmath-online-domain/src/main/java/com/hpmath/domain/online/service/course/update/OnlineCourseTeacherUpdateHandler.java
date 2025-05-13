package com.hpmath.domain.online.service.course.update;

import com.hpmath.domain.online.dao.OnlineCourse;
import com.hpmath.domain.online.dto.OnlineCourseInfoUpdateCommand;
import com.hpmath.domain.online.service.course.MemberLoader;
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
