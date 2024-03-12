package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.account.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
class CourseStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime registeredDateTime;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course courseEntity;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Member member;

    private CourseStudent(final Member member, final Course courseEntity) {
        this.member = member;
        this.courseEntity = courseEntity;
    }

    static CourseStudent of(final Member member, final Course courseEntity) {
        return new CourseStudent(member, courseEntity);
    }
}
