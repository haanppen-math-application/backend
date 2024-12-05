package com.hanpyeon.academyapi.online.dao;

import com.hanpyeon.academyapi.account.entity.Member;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor
@Getter
public class OnlineCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ONLINE_COURSE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member teacher;

    @ManyToOne
    @JoinColumn(name = "category", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private OnlineCategory onlineCategory;

    @OneToMany(mappedBy = "onlineCourse")
    private List<OnlineVideo> videos = new ArrayList<>();

    @OneToMany(mappedBy = "course")
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

    public OnlineCourse(final Member teacher, final String courseName) {
        this.teacher = teacher;
        this.courseName = courseName;
    }

    public void setCourseTitle(final String title) {
        this.courseTitle = title;
    }

    public void setTeacher(final Member teacher) {
        this.teacher = teacher;
    }

    public void setCourseName(final String courseName) {
        this.courseName = courseName;
    }

//    public void setOnlineStudents(final List<OnlineStudent> onlineStudents) {
//        onlin
//    }
}
