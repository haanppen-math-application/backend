package com.hpmath.domain.course.application;

import com.hpmath.domain.course.application.dto.ModifyMemoTextCommand;
import com.hpmath.domain.course.application.exception.CourseException;
import com.hpmath.domain.course.application.port.in.ModifyMemoTextUseCase;
import com.hpmath.domain.course.application.port.out.LoadMemoPort;
import com.hpmath.domain.course.application.port.out.UpdateMemoTextPort;
import com.hpmath.domain.course.domain.Memo;
import com.hpmath.hpmathcore.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class ModifyMemoTextService implements ModifyMemoTextUseCase {
    private final LoadMemoPort loadMemoPort;
    private final UpdateMemoTextPort updateMemoTextPort;

    @Override
    @Transactional
    public void modify(@Validated ModifyMemoTextCommand command) {
        final Memo targetMemo = loadMemoPort.loadMemo(command.memoId());
        validateOwner(command.requestMemberId(), targetMemo.getCourse().getTeacher().id());

        targetMemo.setTitle(command.title());
        targetMemo.setContent(command.content());
        updateMemoTextPort.update(targetMemo);
    }


    private void validateOwner(final Long requestMemberId, final Long courseOwnerId) {
        if (requestMemberId.equals(courseOwnerId)) {
            return;
        }
        throw new CourseException("소유자만 수정할 수 있습니다.", ErrorCode.MEMO_CANNOT_MODIFY);
    }
}
