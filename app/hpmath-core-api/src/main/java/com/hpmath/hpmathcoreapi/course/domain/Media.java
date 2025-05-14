package com.hpmath.hpmathcoreapi.course.domain;

import com.hpmath.client.media.MediaClient.MediaInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Media {
    private final String mediaName;
    private final String mediaSource;
    private final Long mediaSize;

    public static Media createByEntity(final String mediaName, final String mediaSource, final Long mediaSize) {
        return new Media(mediaName, mediaSource, mediaSize);
    }

    public static Media from(final MediaInfo mediaInfo) {
        return new Media(
                mediaInfo.mediaName(),
                mediaInfo.mediaSrc(),
                mediaInfo.fileSize()
        );
    }
//    public MemoMedia mapToMemoMedia(final Integer sequence) {
//        if (Objects.isNull(sequence)) {
//            throw new CourseException(ErrorCode.MEMO_NOT_EXIST);
//        }
//        return MemoMedia.createByMedia(this, sequence);
//    }
}
