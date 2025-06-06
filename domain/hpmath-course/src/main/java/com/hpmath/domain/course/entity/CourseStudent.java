package com.hpmath.domain.course.entity;

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
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor
@Getter
@ToString
@Table(name = "course_student", indexes = {@Index(name = "idx_studentId_courseId", columnList = "student_id, course_id")})
@EqualsAndHashCode(of = {"studentId", "courseEntity"})
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

    public static CourseStudent of(final Long studentId, final Course course) {
        final CourseStudent courseStudent = new CourseStudent();
        courseStudent.studentId = studentId;
        courseStudent.courseEntity = course;

        return courseStudent;
    }
}
