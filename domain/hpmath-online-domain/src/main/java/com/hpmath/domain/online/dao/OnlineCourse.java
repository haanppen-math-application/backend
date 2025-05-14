package com.hpmath.domain.online.dao;

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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class OnlineCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ONLINE_COURSE_ID")
    private Long id;

    @Column(name = "teacher")
    private Long teacherId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private OnlineCategory onlineCategory;

    @OneToMany(mappedBy = "onlineCourse")
    private List<OnlineVideo> videos = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<OnlineStudent> onlineStudents = new ArrayList<>();

    @Column(name = "courseName", nullable = false)
    private String courseName;

    @Column(name = "courseTitle", nullable = true)
    private String courseTitle;

    @Column(name = "courseRange", nullable = true)
    private String courseRange;

    @Column(name = "courseContent", nullable = true)
    private String courseContent;

    @Column
    @CreationTimestamp
    private LocalDateTime createdTime;

    @Column(name = "image")
    private String imageSrc;

    public OnlineCourse(final Long teacher, final String courseName) {
        this.teacherId = teacher;
        this.courseName = courseName;
    }

    public static OnlineCourse of(final Long teacherId, final String courseName, final List<Long> studentIds) {
        final OnlineCourse onlineCourse = new OnlineCourse(teacherId, courseName);
        onlineCourse.onlineStudents.addAll(studentIds.stream()
                .map(id -> new OnlineStudent(onlineCourse, id))
                .toList());
        return onlineCourse;
    }

    public void changeStudentsTo(final List<Long> studentIds) {
        this.onlineStudents.clear();
        onlineStudents.addAll(studentIds.stream()
                .map(id -> new OnlineStudent(this, id))
                .toList());
    }

    public void setOnlineCategory(final OnlineCategory onlineCategory) {
        this.onlineCategory = onlineCategory;
    }

    public void setCourseTitle(final String title) {
        this.courseTitle = title;
    }

    public void setTeacherId(final Long teacher) {
        this.teacherId = teacher;
    }

    public void setCourseRange(final String courseRange) {
        this.courseRange = courseRange;
    }

    public void setCourseContent(final String courseDescribe) {
        this.courseContent = courseDescribe;
    }

    public void setCourseName(final String courseName) {
        this.courseName = courseName;
    }

    public void setImageSrc(final String image) {
        this.imageSrc = image;
    }

    public void clearContents() {
        this.courseTitle = null;
        this.courseContent = null;
        this.courseRange = null;
    }
}
