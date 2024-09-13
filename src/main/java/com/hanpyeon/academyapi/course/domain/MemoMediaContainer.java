package com.hanpyeon.academyapi.course.domain;

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


    public static MemoMediaContainer of(final List<MemoMedia> memoMedias, final Long memoId) {
        return new MemoMediaContainer(memoMedias, memoId);
    }
}
