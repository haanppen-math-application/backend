package com.hpmath.hpmathcoreapi.course.entity;

import com.hpmath.hpmathcoreapi.media.entity.Media;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @JoinColumn(name = "MEMO_MEDIA_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private MemoMedia memoMedia;
    @ManyToOne
    @JoinColumn(name = "MEDIA_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Media media;

    private MemoMediaAttachment(MemoMedia memoMedia, Media media) {
        this.memoMedia = memoMedia;
        this.media = media;
    }

    public static MemoMediaAttachment of(final MemoMedia memoMedia, final Media media) {
        return new MemoMediaAttachment(memoMedia, media);
    }

    public void setNull() {
        this.memoMedia = null;
        this.media = null;
    }
}
