package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.account.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "COURSE")
@NoArgsConstructor
@Getter
class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseName;

    @CreationTimestamp
    private LocalDateTime registeredDateTime;
    @OneToMany
    private List<Member> members;
    @ManyToOne
    private Member teacher;

    CourseEntity(final String courseName, final List<Member> members, final Member teacher) {
        this.courseName = courseName;
        this.members = members;
        this.teacher = teacher;
    }
}
