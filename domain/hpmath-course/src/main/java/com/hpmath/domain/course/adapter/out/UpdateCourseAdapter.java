package com.hpmath.domain.course.adapter.out;

import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.course.domain.Course;
import com.hpmath.domain.course.domain.Student;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathcore.Role;
import com.hpmath.domain.course.application.exception.NoSuchCourseException;
import com.hpmath.domain.course.application.exception.NoSuchMemberException;
import com.hpmath.domain.course.application.port.out.UpdateCoursePort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateCourseAdapter implements UpdateCoursePort {
    private final CourseRepository courseRepository;
    private final MemberClient memberClient;

    @Override
    @Transactional
    public void updateCourse(final Course updatedCourseDomain) {
        final com.hpmath.domain.course.entity.Course course = findCourse(updatedCourseDomain);
        final List<Long> studentIds = updatedCourseDomain.getStudents().stream()
                .mapToLong(Student::id)
                .boxed()
                .toList();

        validateTeacher(updatedCourseDomain.getTeacher().id());
        validateStudents(studentIds);

        course.changeCourseName(updatedCourseDomain.getCourseName());
        course.changeTeacher(updatedCourseDomain.getTeacher().id());
        course.setStudents(studentIds);
    }

    private void validateStudents(final List<Long> studentIds) {
        if (studentIds.stream()
                .parallel()
                .allMatch(id -> memberClient.isMatch(id, Role.STUDENT))) {
            return;
        }
        log.warn("studentIds is contain wrong value : {}", studentIds);
        throw new NoSuchMemberException(ErrorCode.NO_SUCH_COURSE_MEMBER);
    }

    private void validateTeacher(final Long teacherId) {
        if (memberClient.isMatch(teacherId, Role.TEACHER)) {
            return;
        }
        log.warn("Invalid teacher id: {}", teacherId);
        throw new NoSuchMemberException("선생 찾을 수 없음", ErrorCode.NO_SUCH_COURSE_MEMBER);
    }

    private com.hpmath.domain.course.entity.Course findCourse(Course updatedCourseDomain) {
        return courseRepository.findById(updatedCourseDomain.getCourseId())
                .orElseThrow(() -> new NoSuchCourseException("반 찾을 수 없음", ErrorCode.NO_SUCH_COURSE_MEMBER));
    }
}
