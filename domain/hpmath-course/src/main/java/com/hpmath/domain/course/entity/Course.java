package com.hpmath.domain.course.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "COURSE")
@NoArgsConstructor
@Getter
@ToString(exclude = "students")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;

    @CreationTimestamp
    private LocalDateTime registeredDateTime;

    @Column(name = "teacher")
    private Long teacherId;

    @OneToMany(mappedBy = "courseEntity", cascade = {CascadeType.PERSIST}, orphanRemoval = true)
    @BatchSize(size = 10)
    private Set<CourseStudent> students = new HashSet<>();

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    private List<Memo> memos = new ArrayList<>();

    public Course(final String courseName, final Long teacherId) {
        this.courseName = courseName;
        this.teacherId = teacherId;
    }

    public static Course of(final String courseName, final Long teacherId, final List<Long> studentIds) {
        final Course course = new Course(courseName, teacherId);
        course.setStudents(studentIds);
        return course;
    }

    public void setStudents(final List<Long> students) {
        this.students.clear();
        addStudents(students);
    }

    public void addStudents(final List<Long> studentIds) {
        this.students.addAll(studentIds.stream()
                .map(id -> CourseStudent.of(id, this))
                .toList());
    }

    public void changeCourseName(final String newCourseName) {
        this.courseName = newCourseName;
    }

    public void changeTeacher(final Long newTeacherId) {
        this.teacherId = newTeacherId;
    }

    public List<CourseStudent> getCourseStudents() {
        return this.students.stream().toList();
    }
}
