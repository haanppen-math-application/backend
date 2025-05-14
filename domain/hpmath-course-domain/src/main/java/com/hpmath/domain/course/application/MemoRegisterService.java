package com.hpmath.domain.course.application;

import com.hpmath.domain.course.application.dto.MemoQueryByCourseIdAndDateCommand;
import com.hpmath.domain.course.application.dto.MemoRegisterCommand;
import com.hpmath.domain.course.application.dto.Responses.MemoViewResponse;
import com.hpmath.domain.course.application.exception.CourseException;
import com.hpmath.domain.course.application.exception.InvalidCourseAccessException;
import com.hpmath.domain.course.application.port.in.MemoRegisterUseCase;
import com.hpmath.domain.course.application.port.out.LoadCourseTeacherIdPort;
import com.hpmath.domain.course.application.port.out.QueryMemoByCourseIdAndDatePort;
import com.hpmath.domain.course.application.port.out.RegisterMemoPort;
import com.hpmath.domain.course.domain.Memo;
import com.hpmath.hpmathcore.ErrorCode;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemoRegisterService implements MemoRegisterUseCase {

    private final LoadCourseTeacherIdPort loadCourseTeacherIdPort;
    private final QueryMemoByCourseIdAndDatePort queryMemoByCourseIdAndDatePort;
    private final RegisterMemoPort registerMemoPort;

    @Override
    public Long register(final MemoRegisterCommand memoRegisterCommand) {
        final Long teacherId = loadCourseTeacherIdPort.loadTeacherId(memoRegisterCommand.targetCourseId());
        isOwner(teacherId, memoRegisterCommand.requestMemberId());
        isDuplicated(memoRegisterCommand.targetCourseId(), memoRegisterCommand.registerTargetDate());

        final Memo memo = Memo.createNewMemo(memoRegisterCommand.registerTargetDate(), memoRegisterCommand.title(), memoRegisterCommand.content());
        return registerMemoPort.register(memo, memoRegisterCommand.targetCourseId());
    }

    private void isDuplicated(final Long courseId, final LocalDate localDate) {
        final MemoViewResponse memoView = queryMemoByCourseIdAndDatePort.query(new MemoQueryByCourseIdAndDateCommand(courseId, localDate));
        if (memoView == null) {
            return;
        }
        throw new CourseException("같은 날짜에 두개이상의 메모 등록 불가",ErrorCode.MEMO_DUPLICATED_EXCEPTION);
    }

    private void isOwner(final Long courseTeacherId, final Long requestMemberId) {
        if (!courseTeacherId.equals(requestMemberId)) {
            throw new InvalidCourseAccessException("반 담당 선생님이 아닙니다", ErrorCode.INVALID_COURSE_ACCESS);
        }
    }
}
