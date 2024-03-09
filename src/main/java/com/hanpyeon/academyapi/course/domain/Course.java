package com.hanpyeon.academyapi.course.domain;

import com.hanpyeon.academyapi.course.application.exception.IllegalCourseNameException;
import com.hanpyeon.academyapi.course.application.exception.IllegalCourseStudentStateException;
import com.hanpyeon.academyapi.course.application.exception.NotFoundTeacherException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Course {
    private String courseName;
    private final List<Student> students;
    private final Teacher teacher;

    public static Course of(final String courseName, final List<Student> students, final Teacher teacher) {
        validateCourseNameLength(courseName);
        validateStudentSize(students);
        validateTeacher(teacher);
        return new Course(courseName, Collections.unmodifiableList(students), teacher);
    }

    private static void validateCourseNameLength(final String courseName) {
        if (courseName.length() > 100) {
            throw new IllegalCourseNameException("글자수 초과", ErrorCode.ILLEGAL_COURSE_NAME);
        }
    }

    private static void validateStudentSize(final List<Student> students) {
        if (Objects.isNull(students)) {
            return;
        }
        if (students.stream().anyMatch(Objects::isNull)) {
            throw new IllegalCourseStudentStateException("등록 할 수 없는 학생이 포함", ErrorCode.ILLEGAL_COURSE_STUDENT_STATE);
        }
    }

    private static void validateTeacher(final Teacher teacher) {
        if (Objects.isNull(teacher)) {
            throw new NotFoundTeacherException("선생님 부재",ErrorCode.ILLEGAL_COURSE_STUDENT_STATE);
        }
    }
}
