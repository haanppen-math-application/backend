package com.hanpyeon.academyapi.course.domain;

import com.hanpyeon.academyapi.course.application.exception.IllegalCourseNameException;
import com.hanpyeon.academyapi.course.application.exception.IllegalCourseStudentStateException;
import com.hanpyeon.academyapi.course.application.exception.IllegalCourseStudentSizeException;
import com.hanpyeon.academyapi.course.application.exception.NotFoundTeacherException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Course {
    private static final int MAX_STUDENT_SIZE = 50;
    private final Long courseId;
    private String courseName;
    private final List<Student> students;
    private final Teacher teacher;

    public void addStudents(final List<Student> students) {
        final List<Student> newStudents = students.stream()
                .filter(student -> !this.students.contains(student))
                .toList();
        validateStudents(newStudents);
        this.students.addAll(newStudents);
    }

    public static Course createNewCourse(final String courseName, final List<Student> students, final Teacher teacher) {
        final Course course = new Course(null, courseName, students, teacher);
        validate(course);
        return course;
    }
    public static Course loadByEntity(final Long courseId, final String courseName, final List<Student> students, final Teacher teacher) {
        final Course course = new Course(courseId, courseName, students, teacher);
        validate(course);
        return course;
    }

    private static void validate(Course course) {
        validateCourseNameLength(course.courseName);
        validateStudents(course.getStudents());
        validateTeacher(course.getTeacher());
    }

    private static void validateCourseNameLength(final String courseName) {
        if (courseName.length() > 100) {
            throw new IllegalCourseNameException("글자수 초과", ErrorCode.ILLEGAL_COURSE_NAME);
        }
    }

    private static void validateStudents(final List<Student> students) {
        if (Objects.isNull(students)) {
            return;
        }
        if (students.stream().anyMatch(Objects::isNull)) {
            throw new IllegalCourseStudentStateException("등록 할 수 없는 학생이 포함", ErrorCode.ILLEGAL_COURSE_STUDENT_STATE);
        }
        validateStudentSize(students.size());
    }

    private static void validateStudentSize(final Integer size) {
        if (size > MAX_STUDENT_SIZE) {
            throw new IllegalCourseStudentSizeException("최대인원 : " + MAX_STUDENT_SIZE + "현재원 : " + size, ErrorCode.ILLEGAL_COURSE_STUDENT_SIZE);
        }
    }

    private static void validateTeacher(final Teacher teacher) {
        if (Objects.isNull(teacher)) {
            throw new NotFoundTeacherException("선생님 부재", ErrorCode.ILLEGAL_COURSE_STUDENT_STATE);
        }
    }
}
