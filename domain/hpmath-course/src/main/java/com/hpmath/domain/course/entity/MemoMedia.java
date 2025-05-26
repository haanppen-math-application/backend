package com.hpmath.domain.course.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@ToString
@Table(name = "memo_media", indexes = @Index(name = "idx_memoId", columnList = "memo_id"))
public class MemoMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "media_src")
    private String mediaSrc;

    @ManyToOne
    @JoinColumn(name = "memo_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Memo memo;

    private Integer sequence;

    @OneToMany(mappedBy = "memoMedia", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<MemoMediaAttachment> memoMediaAttachments = new ArrayList<>();

    public static MemoMedia of(final Memo memo, final String mediaSrc, final Integer sequence) {
        final MemoMedia memoMedia = new MemoMedia();
        memoMedia.memo = memo;
        memoMedia.mediaSrc = mediaSrc;
        memoMedia.setSequence(sequence);
        return memoMedia;
    }

    public void addAttachment(final String mediaSrc) {
        final MemoMediaAttachment attachment = MemoMediaAttachment.of(this, mediaSrc);
        memoMediaAttachments.add(attachment);
    }

    public void setSequence(final Integer sequence) {
        this.sequence = sequence;
    }
}
