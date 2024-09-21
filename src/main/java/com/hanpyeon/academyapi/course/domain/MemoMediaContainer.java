package com.hanpyeon.academyapi.course.domain;

import com.hanpyeon.academyapi.course.application.dto.MemoMediaSequenceModifyCommand;
import com.hanpyeon.academyapi.course.application.dto.UpdateMediaMemoCommand;
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

    public void updateSequence(final MemoMediaSequenceModifyCommand sequenceModifyCommand) {
        for (final MemoMedia memoMedia : memoMedias) {
            memoMedia.setSequence(sequenceModifyCommand.memoMediaId(), sequenceModifyCommand.sequence());
        }
    }

    public static MemoMediaContainer of(final List<MemoMedia> memoMedias, final Long memoId) {
        return new MemoMediaContainer(memoMedias, memoId);
    }
}
