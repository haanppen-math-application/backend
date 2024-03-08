package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.CourseRegisterDto;
import com.hanpyeon.academyapi.course.application.port.in.CourseRegisterUseCase;
import com.hanpyeon.academyapi.course.application.port.out.LoadStudentsPort;
import com.hanpyeon.academyapi.course.application.port.out.LoadTeacherPort;
import com.hanpyeon.academyapi.course.application.port.out.RegisterCoursePort;
import com.hanpyeon.academyapi.course.domain.Course;
import com.hanpyeon.academyapi.course.domain.Student;
import com.hanpyeon.academyapi.course.domain.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseRegisterService implements CourseRegisterUseCase {

    // 학생 권한 정보, 선생 정보를 현재 도메인 내에서 모르게 하기 위함
    private final LoadStudentsPort loadStudentPort;
    private final LoadTeacherPort loadTeacherPort;
    private final RegisterCoursePort registerCoursePort;

    @Override
    public Long register(final CourseRegisterDto courseRegisterDto) {
        Course course = mapToCourse(courseRegisterDto);
        return registerCoursePort.register(course);
    }

    private Course mapToCourse(final CourseRegisterDto courseRegisterDto) {
        return Course.of(
                courseRegisterDto.courseName(),
                getStudents(courseRegisterDto.students()),
                getTeacher(courseRegisterDto.teacherId())
        );
    }

    private List<Student> getStudents(final List<Long> studentIds) {
        return loadStudentPort.loadStudent(studentIds);
    }

    private Teacher getTeacher(final Long teacherId) {
        return loadTeacherPort.loadTeacher(teacherId);
    }
}
