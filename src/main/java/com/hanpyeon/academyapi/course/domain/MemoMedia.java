package com.hanpyeon.academyapi.course.domain;

import lombok.Getter;

@Getter
public class MemoMedia extends Media {
    private final Integer sequence;

    private MemoMedia(String mediaName, String mediaSource, Long mediaSize, Integer sequence) {
        super(mediaName, mediaSource, mediaSize);
        this.sequence = sequence;
    }

    public static MemoMedia createByEntity(final String mediaName, final String mediaSource, final Long mediaSize, final Integer sequence) {
        return new MemoMedia(mediaName, mediaSource, mediaSize, sequence);
    }
    public static MemoMedia createByMedia(final Media media, final Integer sequence) {
        return new MemoMedia(media.getMediaName(), media.getMediaSource(), media.getMediaSize(), sequence);
    }
}
