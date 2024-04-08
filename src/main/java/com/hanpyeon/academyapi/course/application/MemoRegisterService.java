package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.MemoRegisterCommand;
import com.hanpyeon.academyapi.course.application.exception.InvalidCourseAccessException;
import com.hanpyeon.academyapi.course.application.port.in.MemoRegisterUseCase;
import com.hanpyeon.academyapi.course.application.port.out.LoadCoursePort;
import com.hanpyeon.academyapi.course.domain.Course;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
class MemoRegisterService implements MemoRegisterUseCase {

    private final LoadCoursePort loadCoursePort;

    @Override
    public void register(final MemoRegisterCommand memoRegisterCommand) {
        final Course course = loadCoursePort.loadCourse(memoRegisterCommand.targetCourseId());
        validate(course, memoRegisterCommand.requestMemberId());

    }

    private void validate(final Course course, final Long requestMemberId) {
        if (course.getTeacher().id().equals(requestMemberId)) {
            return;
        }
        throw new InvalidCourseAccessException("반 담당 선생님이 아닙니다", ErrorCode.INVALID_COURSE_ACCESS);
    }
}
