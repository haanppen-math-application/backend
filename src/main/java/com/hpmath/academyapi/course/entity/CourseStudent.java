package com.hpmath.academyapi.course.entity;

import com.hpmath.academyapi.account.entity.Member;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor
@Getter
@ToString(exclude = "courseEntity")
public class CourseStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime registeredDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Course courseEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    private CourseStudent(final Member student, final Course courseEntity) {
        this.member = student;
        this.courseEntity = courseEntity;
    }

    public static CourseStudent addToCourse(final Member student, final Course courseEntity) {
        final CourseStudent courseStudent = new CourseStudent(student, courseEntity);
        courseEntity.addCourseStudent(courseStudent);
        return new CourseStudent(student, courseEntity);
    }

    public void delete() {
        this.member = null;
        this.courseEntity = null;
    }
}
