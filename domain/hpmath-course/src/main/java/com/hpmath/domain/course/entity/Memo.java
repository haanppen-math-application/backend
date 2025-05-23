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
@Table(name = "memo")
@NoArgsConstructor
@Getter
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMO_ID")
    private Long id;

    @CreationTimestamp
    @Column(name = "REGISTERED_DATE")
    private LocalDateTime registeredDateTime;

    @Column(name = "TARGET_DATE")
    private LocalDate targetDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseId", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Course course;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @OneToMany(mappedBy = "memo", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<MemoMedia> memoMedias = new ArrayList<>();

    public void addMedia(final String mediaSrc) {
        memoMedias.add(MemoMedia.of(this, mediaSrc, memoMedias.size()));
    }

    public void setTitle(final String title) {
        this.title = title;
    }
    public void setContent(final String content) {
        this.content = content;
    }
    public void delete() {
        this.course = null;
        memoMedias.stream().forEach(memoMedia -> memoMedia.setNull());
    }

    public void changeSequence(final Long memoMediaId, final Integer sequence) {
        memoMedias.stream().forEach(memoMedia -> {
            if (memoMedia.getId().equals(memoMediaId)) {
                memoMedia.setSequence(sequence);
            }
        });
    }

    private void validateSequences() {
        memoMedias.stream()
                .mapToInt(MemoMedia::getSequence);
    }

    public Memo(final Course course, final LocalDate targetDate, final String progressed, final String homework) {
        this.course = course;
        this.targetDate = targetDate;
        this.title = progressed;
        this.content = homework;
    }
}
