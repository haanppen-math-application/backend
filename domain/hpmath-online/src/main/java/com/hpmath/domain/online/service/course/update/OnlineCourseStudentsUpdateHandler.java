package com.hpmath.domain.online.service.course.update;

import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.online.dao.OnlineCourse;
import com.hpmath.domain.online.dto.OnlineCourseStudentUpdateCommand;
import com.hpmath.domain.online.exception.OnlineCourseException;
import com.hpmath.hpmathcore.Role;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OnlineCourseStudentsUpdateHandler {
    private final MemberClient memberClient;

    @Transactional(propagation = Propagation.MANDATORY)
    public void update(final OnlineCourse onlineCourse, final OnlineCourseStudentUpdateCommand onlineCourseUpdateCommand) {
        if (Objects.isNull(onlineCourseUpdateCommand.studentIds())) {
            return;
        }
        validateOnlineStudents(onlineCourseUpdateCommand.studentIds());
        onlineCourse.changeStudentsTo(onlineCourseUpdateCommand.studentIds());
    }

    private void validateOnlineStudents(final List<Long> onlineStudents) {
        if (isRealStudents(onlineStudents)) {
            return;
        }
        throw new OnlineCourseException();
    }

    private boolean isRealStudents(List<Long> onlineStudents) {
        return onlineStudents.stream()
                .parallel()
                .allMatch(memberId -> memberClient.isMatch(memberId, Role.STUDENT));
    }
}
