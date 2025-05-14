package com.hpmath.domain.course.entity;

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
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class CourseStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime registeredDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Course courseEntity;

    @Column(name = "student_id")
    private Long studentId;

    private CourseStudent(final Long studentId, final Course courseEntity) {
        this.studentId = studentId;
        this.courseEntity = courseEntity;
    }

    public static CourseStudent of(final Long studentId, final Course courseEntity) {
        return new CourseStudent(studentId, courseEntity);
    }

    public void delete() {
        this.studentId = null;
        this.courseEntity = null;
    }
}
