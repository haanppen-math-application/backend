package com.hpmath.domain.course.application;

import com.hpmath.domain.course.application.dto.RegisterMemoMediaCommand;
import com.hpmath.domain.course.application.exception.MemoMediaException;
import com.hpmath.domain.course.application.port.in.RegisterMemoMediaUseCase;
import com.hpmath.domain.course.application.port.out.LoadMemoPort;
import com.hpmath.domain.course.application.port.out.RegisterMemoMediaPort;
import com.hpmath.domain.course.application.port.out.ValidateSuperUserPort;
import com.hpmath.domain.course.domain.Memo;
import com.hpmath.hpmathcore.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterMemoMediaService implements RegisterMemoMediaUseCase {

    private final LoadMemoPort loadMemoPort;
    private final RegisterMemoMediaPort registerMemoMediaPort;
    private final ValidateSuperUserPort validateSuperUserPort;

    @Override
    @Transactional
    public void register(RegisterMemoMediaCommand command) {
        final Memo memo = loadMemoPort.loadMemo(command.memoId());
        validateOwner(command.requestMemberId(), memo);
        registerMemoMediaPort.register(command.mediaSource(), command.memoId());
    }

    private void validateOwner(final Long requestMemberId, final Memo memo) {
        if (validateSuperUserPort.isSuperUser(requestMemberId)) {
            return;
        }
        if (memo.getCourse().getTeacher().id().equals(requestMemberId)) {
            return;
        }
        throw new MemoMediaException("접근할 수 없는 사용자.", ErrorCode.MEMO_MEDIA_UPDATE_EXCEPTION);
    }
}
