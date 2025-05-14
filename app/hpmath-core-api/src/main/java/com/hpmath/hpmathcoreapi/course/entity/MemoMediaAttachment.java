package com.hpmath.hpmathcoreapi.course.entity;

import jakarta.persistence.Column;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attachmentId;

    @ManyToOne
    @JoinColumn(name = "MEMO_MEDIA_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private MemoMedia memoMedia;

    @Column(name = "media_id")
    private String mediaSrc;

    private MemoMediaAttachment(MemoMedia memoMedia, String mediaSrc) {
        this.memoMedia = memoMedia;
        this.mediaSrc = mediaSrc;
    }

    public static MemoMediaAttachment of(final MemoMedia memoMedia, final String mediaSrc) {
        return new MemoMediaAttachment(memoMedia, mediaSrc);
    }

    public void setNull() {
        this.memoMedia = null;
        this.mediaSrc = null;
    }
}
