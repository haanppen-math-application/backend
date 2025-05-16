package com.hpmath.domain.online.service.course;

import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.online.dao.OnlineCourse;
import com.hpmath.domain.online.dao.OnlineCourseRepository;
import com.hpmath.domain.online.dao.OnlineStudentRepository;
import com.hpmath.domain.online.dto.AddOnlineCourseCommand;
import com.hpmath.domain.online.exception.OnlineCourseException;
import com.hpmath.hpmathcore.Role;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnlineCourseRegisterService {
    private final OnlineCourseRepository onlineCourseRepository;
    private final MemberClient memberClient;

    public void addOnlineCourse(final AddOnlineCourseCommand command) {
        validateTeacher(command.teacherId());
        validateOnlineStudents(command.students());

        final OnlineCourse onlineCourse = OnlineCourse.of(
                command.teacherId(),
                command.onlineCourseName(),
                command.students()
        );
        onlineCourseRepository.save(onlineCourse);
        log.info("online Course Created : " + command);
    }

    private void validateTeacher(final Long teacherId) {
        if (memberClient.isMatch(teacherId, Role.TEACHER)) {
            return;
        }
        log.warn("teacher ");
        throw new OnlineCourseException();
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
