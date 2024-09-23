package com.hanpyeon.academyapi.course.domain;

import com.hanpyeon.academyapi.course.application.dto.MemoMediaUpdateSequenceCommand;
import com.hanpyeon.academyapi.course.application.exception.MemoMediaException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemoMediaContainer {
    private final List<MemoMedia> memoMedias;
    private final Long memoId;

    public Long getMemoId() {
        return this.memoId;
    }

    public List<MemoMedia> getMemoMedias() {
        return Collections.unmodifiableList(this.memoMedias);
    }

    public void deleteMemoMedia(final Long memoMediaId) {
        int deleteCount = 0;
        int deletedSequence = -1;
        MemoMedia deleteTargetMemoMedia = null;
        for (final MemoMedia memoMedia : memoMedias) {
            final Integer sequence = memoMedia.getSequence(memoMediaId);
            if (Objects.isNull(sequence)) {
                continue;
            }
            deleteTargetMemoMedia = memoMedia;
            deletedSequence = sequence;
            deleteCount ++;
        }
        if (deleteCount > 1) {
            throw new MemoMediaException("두개 이상 삭제되선 안됩니다", ErrorCode.MEMO_MEDIA_UPDATE_EXCEPTION);
        }
        memoMedias.remove(deleteTargetMemoMedia);
        updateMemoMediaSequences(deletedSequence);
    }

    private void updateMemoMediaSequences(final int targetSequence) {
        for (final MemoMedia memoMedia : memoMedias) {
            memoMedia.updateSequenceUpperThan(targetSequence);
        }
    }

    public void updateMemoMediaSequence(final MemoMediaUpdateSequenceCommand command) {
        int updatedCount = 0;
        for (final MemoMedia memoMedia : memoMedias) {
            if (memoMedia.updateSequence(command.memoMediaId(), command.sequence())) {
                updatedCount++;
            }
        }
        if (updatedCount == 0 || updatedCount > 1) {
            throw new MemoMediaException("변경된 횟수 : " + updatedCount, ErrorCode.MEMO_MEDIA_SEQUENCE);
        }
    }

    public static MemoMediaContainer of(final List<MemoMedia> memoMedias, final Long memoId) {
        return new MemoMediaContainer(memoMedias, memoId);
    }
}
