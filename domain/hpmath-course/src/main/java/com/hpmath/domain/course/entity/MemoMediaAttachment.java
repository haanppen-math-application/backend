package com.hpmath.domain.course.entity;

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

    public static MemoMediaAttachment of(final MemoMedia memoMedia, final String mediaSrc) {
        final MemoMediaAttachment attachment = new MemoMediaAttachment();
        attachment.memoMedia = memoMedia;
        attachment.mediaSrc = mediaSrc;
        return attachment;
    }
}
