package com.hpmath.domain.course.service;

import com.hpmath.client.member.MemberClient;
import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import com.hpmath.domain.course.exception.NoSuchMemberException;
import com.hpmath.domain.course.repository.CourseRepository;
import com.hpmath.domain.course.dto.UpdateCourseStudentsCommand;
import com.hpmath.domain.course.entity.Course;
import com.hpmath.domain.course.exception.NoSuchCourseException;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class UpdateCourseStudentsService {
    private final CourseRepository courseRepository;
    private final MemberClient memberClient;

    public void updateStudents(@Valid final UpdateCourseStudentsCommand command) {
        final Course course = courseRepository.findWithStudents(command.courseId())
                .orElseThrow(() -> new NoSuchCourseException(ErrorCode.NO_SUCH_COURSE));

        course.setStudents(command.studentIds());
    }

    private void isAllStudents(final List<Long> studentIds) {
        final boolean itsAllStudents = studentIds.stream().allMatch(memberId -> memberClient.isMatch(memberId, Role.STUDENT));
        if (itsAllStudents) {
            return;
        }
        throw new NoSuchMemberException(ErrorCode.INVALID_MEMBER_TARGET);
    }
}
