package com.hpmath.domain.course.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
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
@Table(name = "course", indexes = @Index(name = "idx_teacherId", columnList = "teacher"))
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

    @OneToMany(mappedBy = "courseEntity", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @BatchSize(size = 10)
    private final Set<CourseStudent> students = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST}, orphanRemoval = true)
    @BatchSize(size = 10)
    private final List<Memo> memos = new ArrayList<>();

    public static Course of(final String courseName, final Long teacherId, final List<Long> studentIds) {
        final Course course = new Course();
        course.courseName = courseName;
        course.teacherId = teacherId;
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

    public void addMemo(final LocalDate registeredDate, final String progressed, final String homeWork) {
        final Memo memo = Memo.of(this, registeredDate, progressed, homeWork);
        this.memos.add(memo);
    }

    public void changeCourseName(final String newCourseName) {
        this.courseName = newCourseName;
    }

    public void changeTeacher(final Long newTeacherId) {
        this.teacherId = newTeacherId;
    }
}
