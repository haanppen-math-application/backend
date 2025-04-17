package com.hpmath.hpmathcoreapi.course.application;

import com.hpmath.hpmathcoreapi.course.application.dto.CourseRegisterCommand;
import com.hpmath.hpmathcoreapi.course.application.exception.CourseException;
import com.hpmath.hpmathcoreapi.course.application.port.in.CourseRegisterUseCase;
import com.hpmath.hpmathcoreapi.course.application.port.out.LoadStudentsPort;
import com.hpmath.hpmathcoreapi.course.application.port.out.LoadTeacherPort;
import com.hpmath.hpmathcoreapi.course.application.port.out.RegisterCoursePort;
import com.hpmath.hpmathcoreapi.course.domain.Course;
import com.hpmath.hpmathcoreapi.course.domain.Student;
import com.hpmath.hpmathcoreapi.course.domain.Teacher;
import com.hpmath.hpmathcoreapi.exception.ErrorCode;
import com.hpmath.hpmathcoreapi.security.Role;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseRegisterService implements CourseRegisterUseCase {

    // 학생 권한 정보, 선생 정보를 현재 도메인 내에서 모르게 하기 위함
    private final LoadStudentsPort loadStudentPort;
    private final LoadTeacherPort loadTeacherPort;
    private final RegisterCoursePort registerCoursePort;

    @Override
    public Long register(@Validated final CourseRegisterCommand courseRegisterDto) {
        validate(courseRegisterDto.role(), courseRegisterDto.requestMemberId(), courseRegisterDto.teacherId());
        final Course course = mapToCourse(courseRegisterDto);
        return registerCoursePort.register(course);
    }

    private void validate(final Role role, final Long requestMemberId, final Long teacherId) {
        if (teacherId.equals(requestMemberId)) {
            return;
        }
        if (role.equals(Role.ADMIN) || role.equals(Role.MANAGER)) {
            return;
        }
        throw new CourseException("선생님은 본인의 수업만 만들 수 있습니다", ErrorCode.INVALID_COURSE_ACCESS);
    }

    private Course mapToCourse(final CourseRegisterCommand courseRegisterDto) {
        return Course.createNewCourse(
                courseRegisterDto.courseName(),
                getStudents(courseRegisterDto.students()),
                getTeacher(courseRegisterDto.teacherId())
        );
    }

    private List<Student> getStudents(final List<Long> studentIds) {
        return loadStudentPort.loadStudents(studentIds);
    }

    private Teacher getTeacher(final Long teacherId) {
        return loadTeacherPort.loadTeacher(teacherId);
    }
}
