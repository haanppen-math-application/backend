package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.MemoRegisterCommand;
import com.hanpyeon.academyapi.course.application.exception.InvalidCourseAccessException;
import com.hanpyeon.academyapi.course.application.port.in.MemoRegisterUseCase;
import com.hanpyeon.academyapi.course.application.port.out.LoadCourseTeacherIdPort;
import com.hanpyeon.academyapi.course.application.port.out.RegisterMemoPort;
import com.hanpyeon.academyapi.course.domain.Memo;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemoRegisterService implements MemoRegisterUseCase {

    private final LoadCourseTeacherIdPort loadCourseTeacherIdPort;
    private final RegisterMemoPort registerMemoPort;

    @Override
    public Long register(final MemoRegisterCommand memoRegisterCommand) {
        final Long teacherId = loadCourseTeacherIdPort.loadTeacherId(memoRegisterCommand.targetCourseId());
        validate(teacherId, memoRegisterCommand.requestMemberId());

        final Memo memo = Memo.createNewMemo(memoRegisterCommand.registerTargetDateTime(), memoRegisterCommand.progressed(), memoRegisterCommand.homeWork());
        return registerMemoPort.register(memo, memoRegisterCommand.targetCourseId());
    }

    private void validate(final Long courseTeacherId, final Long requestMemberId) {
        if (courseTeacherId.equals(requestMemberId)) {
            return;
        }
        throw new InvalidCourseAccessException("반 담당 선생님이 아닙니다", ErrorCode.INVALID_COURSE_ACCESS);
    }
}
