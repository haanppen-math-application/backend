package com.hpmath.hpmathcoreapi.course.entity;

import com.hpmath.domain.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

@Entity(name = "COURSE")
@NoArgsConstructor
@Getter
@ToString
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseName;
    @CreationTimestamp
    private LocalDateTime registeredDateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member teacher;
    @OneToMany(mappedBy = "courseEntity")
    @BatchSize(size = 10)
    private List<CourseStudent> students = new ArrayList<>();

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    private List<Memo> memos = new ArrayList<>();

    public Course(final String courseName, final Member teacher) {
        this.courseName = courseName;
        this.teacher = teacher;
    }

    public void changeCourseName(final String newCourseName) {
        this.courseName = newCourseName;
    }

    public void changeTeacher(final Member newTeacher) {
        this.teacher = newTeacher;
    }

    public void addCourseStudent(final CourseStudent courseStudent) {
        this.students.add(courseStudent);
    }

    public List<CourseStudent> getCourseStudents() {
        return this.students;
    }
}
