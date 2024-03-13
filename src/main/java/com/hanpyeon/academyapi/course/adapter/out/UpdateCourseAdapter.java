package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.course.application.exception.NoSuchCourseException;
import com.hanpyeon.academyapi.course.application.exception.NoSuchMemberException;
import com.hanpyeon.academyapi.course.application.port.out.UpdateCoursePort;
import com.hanpyeon.academyapi.course.domain.Course;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateCourseAdapter implements UpdateCoursePort {
    private final CourseRepository courseRepository;
    private final MemberRepository memberRepository;

    @Override
    public void updateCourse(final Course updatedCourseDomain) {
        final com.hanpyeon.academyapi.course.adapter.out.Course course = courseRepository.findById(updatedCourseDomain.getCourseId())
                .orElseThrow(() -> new NoSuchMemberException("학생 찾을 수 없음", ErrorCode.NO_SUCH_COURSE_MEMBER));
        course.changeCourseName(updatedCourseDomain.getCourseName());
        final Member newTeacher = memberRepository.findMemberByIdAndRole(updatedCourseDomain.getTeacher().id(), Role.TEACHER)
                .orElseThrow(() -> new NoSuchMemberException("선생 찾을 수 없음", ErrorCode.NO_SUCH_COURSE_MEMBER));

        course.changeTeacher(newTeacher);
    }
}
