package com.hpmath.domain.course.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import com.hpmath.domain.course.repository.CourseRepository;
import com.hpmath.domain.course.dto.CourseRegisterCommand;
import com.hpmath.domain.course.entity.Course;
import com.hpmath.domain.course.exception.CourseException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@RequiredArgsConstructor
@Validated
public class CourseRegisterService {
    private final CourseRepository courseRepository;

    public Long register(@Valid final CourseRegisterCommand command) {
        validate(command.role(), command.requestMemberId(), command.teacherId());
        Course course = Course.of(command.courseName(), command.teacherId(), command.students());

        return courseRepository.save(course).getId();
    }

    private void validate(final Role role, final Long requestMemberId, final Long teacherId) {
        if (teacherId.equals(requestMemberId)) {
            return;
        }
        if (role.equals(Role.ADMIN) || role.equals(Role.MANAGER)) {
            return;
        }
        throw new CourseException("선생님은 본인의 수업만 만들 수 있습니다", ErrorCode.INVALID_COURSE_ACCESS);
    }
}
