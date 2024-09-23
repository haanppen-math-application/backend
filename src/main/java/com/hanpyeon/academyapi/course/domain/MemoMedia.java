package com.hanpyeon.academyapi.course.domain;

import lombok.Getter;

@Getter
public class MemoMedia extends Media {
    private final Long memoMediaId;
    private Integer sequence;

    private MemoMedia(Long memoMediaId, String mediaName, String mediaSource, Long mediaSize, Integer sequence) {
        super(mediaName, mediaSource, mediaSize);
        this.memoMediaId = memoMediaId;
        this.sequence = sequence;
    }

    boolean updateSequence(final Long memoMediaId, final Integer newSequence) {
        if (this.memoMediaId == memoMediaId) {
            this.sequence = newSequence;
            return true;
        }
        return false;
    }

    public static MemoMedia createByEntity(final Long memoMediaId, final String mediaName, final String mediaSource, final Long mediaSize, final Integer sequence) {
        return new MemoMedia(memoMediaId, mediaName, mediaSource, mediaSize, sequence);
    }

    public static MemoMedia createByMedia(final Media media, final Integer sequence) {
        return new MemoMedia(null, media.getMediaName(), media.getMediaSource(), media.getMediaSize(), sequence);
    }
}
