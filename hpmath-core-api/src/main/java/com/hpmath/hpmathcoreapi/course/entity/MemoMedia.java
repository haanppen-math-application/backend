package com.hpmath.hpmathcoreapi.course.entity;

import com.hpmath.hpmathcoreapi.media.entity.Media;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class MemoMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMO_MEDIA_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "MEDIA", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Media media;
    @ManyToOne
    @JoinColumn(name = "MEMO_MEMO_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Memo memo;
    private Integer sequence;
    @OneToMany(mappedBy = "memoMedia")
    private List<MemoMediaAttachment> memoMediaAttachments = new ArrayList<>();

    private MemoMedia(final Memo memo, final Media media, final Integer sequence) {
        this.memo = memo;
        this.media = media;
        this.sequence = sequence;
    }

    public void setSequence(final Integer sequence) {
        this.sequence = sequence;
    }

    public void setNull() {
        this.media = null;
        this.memo = null;
        this.memoMediaAttachments.stream()
                .forEach(memoMediaAttachment -> memoMediaAttachment.setNull());
    }

    public static MemoMedia of(final Memo memo, final Media media, final Integer sequence) {
        return new MemoMedia(memo, media, sequence);
    }
}
