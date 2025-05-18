package com.hpmath.domain.course.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import com.hpmath.domain.course.dto.DeleteCourseCommand;
import com.hpmath.domain.course.entity.Course;
import com.hpmath.domain.course.exception.CourseException;
import com.hpmath.domain.course.repository.CourseRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class DeleteCourseService {
    private final CourseRepository courseRepository;

    @Transactional
    public void delete(@Valid final DeleteCourseCommand deleteCourseCommand) {
        final Course course = loadCourse(deleteCourseCommand.courseId());

        validateOwner(course, deleteCourseCommand.role(), deleteCourseCommand.requestMemberId());
        courseRepository.delete(course);
    }

    private Course loadCourse(final Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseException(ErrorCode.NO_SUCH_COURSE));
    }

    void validateOwner(final Course course, final Role role, final Long requestMemberId) {
        if (role.equals(Role.TEACHER)) {
            if (course.getTeacherId().equals(requestMemberId)) {
                return;
            }
            throw new CourseException("선생님은 본인 반만 수정 가능합니다.", ErrorCode.INVALID_COURSE_ACCESS);
        }
    }

}
