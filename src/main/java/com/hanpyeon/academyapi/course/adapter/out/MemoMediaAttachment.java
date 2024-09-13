package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.media.entity.Media;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class MemoMediaAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long attachmentId;

    @ManyToOne
    @JoinColumn(name = "MEMO_MEDIA_ID", nullable = false)
    private MemoMedia memoMedia;
    @ManyToOne
    @JoinColumn(name = "MEDIA_ID", nullable = false)
    private Media media;

    private MemoMediaAttachment(MemoMedia memoMedia, Media media) {
        this.memoMedia = memoMedia;
        this.media = media;
    }

    static MemoMediaAttachment of(final MemoMedia memoMedia, final Media media) {
        return new MemoMediaAttachment(memoMedia, media);
    }
}