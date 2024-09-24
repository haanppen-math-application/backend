package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.media.entity.Media;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
class MemoMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMO_MEDIA_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "MEDIA", nullable = false)
    private Media media;
    @ManyToOne
    @JoinColumn(name = "MEMO_ID", nullable = false)
    private Memo memo;
    private Integer sequence;

    private MemoMedia(final Memo memo, final Media media, final Integer sequence) {
        this.memo = memo;
        this.media = media;
        this.sequence = sequence;
    }

    void setSequence(final Integer sequence) {
        this.sequence = sequence;
    }

    static MemoMedia of(final Memo memo, final Media media, final Integer sequence) {
        return new MemoMedia(memo, media, sequence);
    }
}
