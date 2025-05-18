package com.hpmath.domain.course.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import com.hpmath.domain.course.dto.RegisterMemoMediaCommand;
import com.hpmath.domain.course.entity.Memo;
import com.hpmath.domain.course.exception.CourseException;
import com.hpmath.domain.course.exception.MemoMediaException;
import com.hpmath.domain.course.repository.MemoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class RegisterMemoMediaService {
    private final MemoRepository memoRepository;

    @Transactional
    public void register(@Valid final RegisterMemoMediaCommand command) {
        final Memo memo = memoRepository.findWithCourseByMemoId(command.memoId())
                .orElseThrow(() -> new CourseException("존재하지 않는 메모 : " + command.memoId(), ErrorCode.MEMO_NOT_EXIST));
        validateOwner(command.requestMemberId(), memo, command.role());
        memo.addMedia(command.mediaSource());
    }

    private void validateOwner(final Long requestMemberId, final Memo memo, final Role role) {
        if (validateSuperMember(role)) {
            return;
        }
        if (memo.getCourse().getTeacherId().equals(requestMemberId)) {
            return;
        }
        throw new MemoMediaException("접근할 수 없는 사용자.", ErrorCode.MEMO_MEDIA_UPDATE_EXCEPTION);
    }

    private boolean validateSuperMember(final Role role) {
        return role.equals(Role.ADMIN) || role.equals(Role.MANAGER);
    }
}
