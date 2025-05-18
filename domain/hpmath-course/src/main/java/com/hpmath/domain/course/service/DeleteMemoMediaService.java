package com.hpmath.domain.course.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import com.hpmath.domain.course.dto.DeleteMemoMediaCommand;
import com.hpmath.domain.course.entity.Memo;
import com.hpmath.domain.course.entity.MemoMedia;
import com.hpmath.domain.course.exception.CourseException;
import com.hpmath.domain.course.repository.MemoMediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class DeleteMemoMediaService {
    private final MemoMediaRepository memoMediaRepository;

    @Transactional
    public void delete(DeleteMemoMediaCommand command) {
        final MemoMedia memoMedia = loadMemoMedia(command.memoMediaId());
        validateOwner(command.requestMemberId(), command.role(), memoMedia.getMemo());

        memoMediaRepository.delete(memoMedia);
        memoMediaRepository.updateMemoMediaSequenceAfterDeleted(memoMedia.getId(), memoMedia.getSequence());
    }

    private void validateOwner(final Long requestMemberId, final Role role, final Memo memo) {
        if (role.equals(Role.ADMIN) || role.equals(Role.MANAGER)) {
            return;
        }
        if (requestMemberId.equals(memo.getCourse().getTeacherId())) {
            return;
        }
        throw new CourseException(ErrorCode.MEMO_MEDIA_DELETE_EXCEPTION);
    }

    private MemoMedia loadMemoMedia(final Long memoMediaId) {
        return memoMediaRepository.findMemoMedia(memoMediaId)
                .orElseThrow(() -> new CourseException("Memo media not found", ErrorCode.MEMO_MEDIA_DELETE_EXCEPTION));
    }
}
