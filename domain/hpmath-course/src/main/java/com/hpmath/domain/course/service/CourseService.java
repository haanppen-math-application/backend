package com.hpmath.domain.course.service;

import com.hpmath.client.member.MemberClient;
import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import com.hpmath.domain.course.dto.CourseRegisterCommand;
import com.hpmath.domain.course.dto.CourseUpdateCommand;
import com.hpmath.domain.course.dto.DeleteCourseCommand;
import com.hpmath.domain.course.dto.RegisterStudentCommand;
import com.hpmath.domain.course.dto.UpdateCourseStudentsCommand;
import com.hpmath.domain.course.entity.Course;
import com.hpmath.domain.course.exception.CourseException;
import com.hpmath.domain.course.exception.NoSuchCourseException;
import com.hpmath.domain.course.exception.NoSuchMemberException;
import com.hpmath.domain.course.repository.CourseRepository;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class CourseService {
    private final CourseRepository courseRepository;
    private final MemberClient memberClient;

    public Long register(@Valid final CourseRegisterCommand command) {
        validateCourseCreation(command.role(), command.requestMemberId(), command.teacherId());
        Course course = Course.of(command.courseName(), command.teacherId(), command.students());

        return courseRepository.save(course).getId();
    }

    public void delete(@Valid final DeleteCourseCommand deleteCourseCommand) {
        final Course course = loadCourse(deleteCourseCommand.courseId());

        validateOwner(course, deleteCourseCommand.role(), deleteCourseCommand.requestMemberId());
        courseRepository.delete(course);
    }

    public void updateCourse(@Valid final CourseUpdateCommand command) {
        final Course course = loadCourse(command.courseId());

        if (Objects.nonNull(command.courseName())) {
            course.changeCourseName(command.courseName());
        }
        if (Objects.nonNull(command.newTeacherId())) {
            course.changeTeacher(command.newTeacherId());
        }
    }

    public void addStudentToCourse(@Valid final RegisterStudentCommand command) {
        validateTargetsAreStudents(command.studentIds());
        final Course course = loadCourse(command.courseId());

        course.addStudents(command.studentIds());
    }

    public void updateStudents(@Valid final UpdateCourseStudentsCommand command) {
        final Course course = courseRepository.findWithStudents(command.courseId())
                .orElseThrow(() -> new NoSuchCourseException(ErrorCode.NO_SUCH_COURSE));

        course.setStudents(command.studentIds());
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

    private void validateOwner(final Course course, final Role role, final Long requestMemberId) {
        if (role.equals(Role.TEACHER)) {
            if (course.getTeacherId().equals(requestMemberId)) {
                return;
            }
            throw new CourseException("선생님은 본인 반만 수정 가능합니다.", ErrorCode.INVALID_COURSE_ACCESS);
        }
    }

    private void validateCourseCreation(final Role role, final Long requestMemberId, final Long teacherId) {
        if (teacherId.equals(requestMemberId)) {
            return;
        }
        if (role.equals(Role.ADMIN) || role.equals(Role.MANAGER)) {
            return;
        }
        throw new CourseException("선생님은 본인의 수업만 만들 수 있습니다", ErrorCode.INVALID_COURSE_ACCESS);
    }
}
