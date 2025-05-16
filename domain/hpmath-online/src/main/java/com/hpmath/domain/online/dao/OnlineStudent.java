package com.hpmath.domain.online.dao;

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
import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor
@Getter
public class OnlineStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "online_course", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private OnlineCourse course;

    @Column(name = "online_course_student")
    private Long memberId;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime registeredDateTIme;

    public OnlineStudent(final OnlineCourse course, final Long memberId) {
        course.getOnlineStudents().add(this);
        this.course = course;
        this.memberId = memberId;
    }
}
