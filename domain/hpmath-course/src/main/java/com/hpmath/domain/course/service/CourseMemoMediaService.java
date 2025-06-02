package com.hpmath.domain.course.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import com.hpmath.domain.course.dto.DeleteMemoMediaCommand;
import com.hpmath.domain.course.dto.RegisterMemoMediaCommand;
import com.hpmath.domain.course.dto.UpdateMediaMemoCommand;
import com.hpmath.domain.course.entity.Memo;
import com.hpmath.domain.course.entity.MemoMedia;
import com.hpmath.domain.course.exception.CourseException;
import com.hpmath.domain.course.exception.MemoMediaException;
import com.hpmath.domain.course.repository.MemoMediaRepository;
import com.hpmath.domain.course.repository.MemoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
@Transactional
public class CourseMemoMediaService {
    private final MemoRepository memoRepository;
    private final MemoMediaRepository memoMediaRepository;

    public void delete(@Valid final DeleteMemoMediaCommand command) {
        final MemoMedia memoMedia = loadMemoMedia(command.memoMediaId());
        validateOwner(command.requestMemberId(), memoMedia.getMemo(), command.role());

        memoMediaRepository.delete(memoMedia);
        memoMediaRepository.updateMemoMediaSequenceAfterDeleted(memoMedia.getId(), memoMedia.getSequence());
    }

    public void register(@Valid final RegisterMemoMediaCommand command) {
        final Memo memo = memoRepository.findWithCourseByMemoId(command.memoId())
                .orElseThrow(() -> new CourseException("존재하지 않는 메모 : " + command.memoId(), ErrorCode.MEMO_NOT_EXIST));
        validateOwner(command.requestMemberId(), memo, command.role());
        memo.addMedia(command.mediaSource());
    }

    public void updateMediaMemo(@Valid final UpdateMediaMemoCommand command) {
        final Memo memo = memoRepository.findWithCourseAndMediasByMemoId(command.memoId())
                .orElseThrow(() -> new MemoMediaException(ErrorCode.MEMO_NOT_EXIST));

        command.mediaSequences().forEach(sequence -> memo.changeSequence(
                sequence.memoMediaId(), sequence.sequence()));
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

    private MemoMedia loadMemoMedia(final Long memoMediaId) {
        return memoMediaRepository.findMemoMedia(memoMediaId)
                .orElseThrow(() -> new CourseException("Memo media not found", ErrorCode.MEMO_MEDIA_DELETE_EXCEPTION));
    }

    private boolean validateSuperMember(final Role role) {
        return role.equals(Role.ADMIN) || role.equals(Role.MANAGER);
    }
}
