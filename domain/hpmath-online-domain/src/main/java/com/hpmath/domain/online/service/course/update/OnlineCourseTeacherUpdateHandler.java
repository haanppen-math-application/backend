package com.hpmath.domain.online.service.course.update;

import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.online.dao.OnlineCourse;
import com.hpmath.domain.online.dto.OnlineCourseInfoUpdateCommand;
import com.hpmath.domain.online.exception.OnlineCourseException;
import com.hpmath.hpmathcore.Role;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OnlineCourseTeacherUpdateHandler implements OnlineCourseUpdateHandler {

    private final MemberClient memberClient;

    @Override
    public void update(final OnlineCourse onlineCourse, final OnlineCourseInfoUpdateCommand onlineCourseUpdateCommand) {
        if (Objects.isNull(onlineCourseUpdateCommand.teacherId())) {
            return;
        }
        validateTeacher(onlineCourseUpdateCommand.teacherId());
        onlineCourse.setTeacherId(onlineCourseUpdateCommand.teacherId());
    }

    private void validateTeacher(final Long teacherId) {
        if (memberClient.isMatch(teacherId, Role.TEACHER)) {
            return;
        }
        throw new OnlineCourseException();
    }
}
