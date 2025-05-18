package com.hpmath.domain.course.service;

import com.hpmath.client.member.MemberClient;
import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import com.hpmath.domain.course.dto.RegisterStudentCommand;
import com.hpmath.domain.course.entity.Course;
import com.hpmath.domain.course.exception.NoSuchCourseException;
import com.hpmath.domain.course.exception.NoSuchMemberException;
import com.hpmath.domain.course.repository.CourseRepository;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
@Validated
public class AddStudentToCourseService {
    private final CourseRepository courseRepository;
    private final MemberClient memberClient;

    public void addStudentToCourse(@Valid final RegisterStudentCommand command) {
        validateTargetsAreStudents(command.studentIds());
        final Course course = loadCourse(command.courseId());

        course.addStudents(command.studentIds());
    }

    private Course loadCourse(final Long courseId) {
        return courseRepository.findWithStudents(courseId)
                .orElseThrow(() -> new NoSuchCourseException(ErrorCode.NO_SUCH_COURSE));
    }

    private void validateTargetsAreStudents(final List<Long> memberIds) {
        final boolean itsAllStudents = memberIds.stream().allMatch(memberId -> memberClient.isMatch(memberId, Role.STUDENT));
        if (itsAllStudents) {
            return;
        }
        throw new NoSuchMemberException(ErrorCode.INVALID_MEMBER_TARGET);
    }
}
