package com.hanpyeon.academyapi.online;

import com.hanpyeon.academyapi.media.entity.Media;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
class OnlineVideo {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private OnlineCourse onlineCourse;

    @ManyToOne
    @JoinColumn(name = "media", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Media media;

    @Column(name = "video_name", nullable = false)
    private String videoName;

    @Column(name = "vidoe_previewable", nullable = false)
    private Boolean preview = false;

    @Column(name = "video_sequence", nullable = false)
    private Integer videoSequence;
}
