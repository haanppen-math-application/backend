package com.hanpyeon.academyapi.course.domain;

import com.hanpyeon.academyapi.course.application.dto.MemoMediaUpdateSequenceCommand;
import com.hanpyeon.academyapi.course.application.exception.MemoMediaException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

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

    public void updateMemoMediaSequence(final MemoMediaUpdateSequenceCommand command) {
        int updatedCount = 0;
        for (final MemoMedia memoMedia : memoMedias) {
            if (memoMedia.updateSequence(command.memoMediaId(), command.sequence())) {
                updatedCount ++;
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
