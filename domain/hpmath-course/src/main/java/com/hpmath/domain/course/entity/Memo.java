package com.hpmath.domain.course.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "memo", indexes = {
        @Index(name = "idx_courseId_targetDate", columnList = "course_id, target_date"),
        @Index(name = "idx_targetDate", columnList = "target_date")
})
@NoArgsConstructor
@Getter
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "registered_date")
    private LocalDateTime registeredDateTime;

    @Column(name = "target_date")
    private LocalDate targetDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Course course;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "memo", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<MemoMedia> memoMedias = new ArrayList<>();

    public static Memo of(final Course course, final LocalDate targetDate, final String progressed, final String homework) {
        final Memo memo = new Memo();
        memo.course = course;
        memo.targetDate = targetDate;
        memo.title = progressed;
        memo.content = homework;

        return memo;
    }
    public void addMedia(final String mediaSrc) {
        memoMedias.add(MemoMedia.of(this, mediaSrc, memoMedias.size()));
    }
    public void setTitle(final String title) {
        this.title = title;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public void changeSequence(final Long memoMediaId, final Integer sequence) {
        memoMedias.stream().forEach(memoMedia -> {
            if (memoMedia.getId().equals(memoMediaId)) {
                memoMedia.setSequence(sequence);
            }
        });
    }
}
