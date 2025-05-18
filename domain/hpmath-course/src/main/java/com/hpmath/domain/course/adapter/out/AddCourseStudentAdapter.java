package com.hpmath.domain.course.adapter.out;

import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.course.domain.Course;
import com.hpmath.domain.course.domain.Student;
import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import com.hpmath.domain.course.exception.NoSuchCourseException;
import com.hpmath.domain.course.exception.NoSuchMemberException;
import com.hpmath.domain.course.application.port.out.AddCourseStudentPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class AddCourseStudentAdapter implements AddCourseStudentPort {
    private final MemberClient memberClient;
    private final CourseRepository courseRepository;

    @Override
    public void addToCourse(final Course courseDomain) {
        final List<Long> studentIds = getStudentIds(courseDomain);
        validateStudents(studentIds);
        final com.hpmath.domain.course.entity.Course course = findCourse(courseDomain);
        course.addStudents(studentIds);
    }

    private static List<Long> getStudentIds(Course courseDomain) {
        return courseDomain.getStudents().stream()
                .mapToLong(Student::id)
                .boxed()
                .toList();
    }

    private com.hpmath.domain.course.entity.Course findCourse(Course courseDomain) {
        return courseRepository.findById(courseDomain.getCourseId())
                .orElseThrow(() -> new NoSuchCourseException(ErrorCode.NO_SUCH_COURSE));
    }

    private void validateStudents(final List<Long> studentIds) {
        if (checkRealStudents(studentIds)) {
            return;
        }
        log.warn("input ids is not real students : {}", studentIds);
        throw new NoSuchMemberException("새로 등록될 학생을 찾을 수 없습니다", ErrorCode.NO_SUCH_COURSE_MEMBER);
    }

    private boolean checkRealStudents(final List<Long> studentIds) {
        return studentIds.stream()
                .parallel()
                .allMatch(studentId -> memberClient.isMatch(studentId, Role.STUDENT));
    }
}
