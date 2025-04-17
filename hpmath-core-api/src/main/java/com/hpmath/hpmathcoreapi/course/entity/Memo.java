package com.hpmath.hpmathcoreapi.course.entity;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "MEMO")
@NoArgsConstructor
@Getter
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    @OneToMany(mappedBy = "memo")
    private List<MemoMedia> memoMedias;

/*
    // 영상 로직
    @OneToMany(mappedBy = "")
    private List<Image> conceptMedia;
    @OneToMany(mappedBy = "")
    private List<Image> courseMedia;
*/
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

    public Memo(final Course course, final LocalDate targetDate, final String progressed, final String homework) {
        this.course = course;
        this.targetDate = targetDate;
        this.title = progressed;
        this.content = homework;
    }
}
