package com.hpmath.domain.course.service;

import com.hpmath.client.member.MemberClient.MemberInfo;
import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import com.hpmath.domain.course.dto.Responses.CourseDetailResponse;
import com.hpmath.domain.course.dto.Responses.CoursePreviewResponse;
import com.hpmath.domain.course.dto.Responses.StudentPreviewResponse;
import com.hpmath.domain.course.dto.Responses.TeacherPreviewResponse;
import com.hpmath.domain.course.entity.Course;
import com.hpmath.domain.course.entity.CourseStudent;
import com.hpmath.domain.course.exception.CourseException;
import com.hpmath.domain.course.exception.NoSuchMemberException;
import com.hpmath.domain.course.repository.CourseRepository;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class CourseQueryService {
    private final CourseRepository courseRepository;
    private final CourseMemberManager memberManager;

    public List<CoursePreviewResponse> loadAllCoursePreviews() {
        final List<Course> courses = courseRepository.findAllWithStudents();
        return courses.stream()
                .map(this::mapToCoursePreview)
                .toList();
    }

    public List<CoursePreviewResponse> loadCoursePreviews(@NotNull final Long memberId) {
        final MemberInfo memberInfo = memberManager.getMemberDetail(memberId)
                .orElseThrow(() -> new NoSuchMemberException(ErrorCode.NO_SUCH_MEMBER));
        if (memberInfo.role() == Role.STUDENT) {
            return courseRepository.findAllByStudentId(memberId).stream().map(this::mapToCoursePreview).toList();
        }
        return courseRepository.findAllWithStudentsByTeacherId(memberId).stream().map(this::mapToCoursePreview).toList();
    }

    public CourseDetailResponse loadCourseDetails(@NotNull final Long courseId) {
        final Course course = courseRepository.findWithStudents(courseId)
                .orElseThrow(() -> new CourseException(ErrorCode.NO_SUCH_COURSE));

        return new CourseDetailResponse(
                course.getId(),
                course.getCourseName(),
                course.getStudents().stream()
                        .mapToLong(CourseStudent::getStudentId)
                        .mapToObj(this::mapToStudent)
                        .toList(),
                mapToTeacherPreview(course.getTeacherId())
        );
    }

    private StudentPreviewResponse mapToStudent(final Long memberId) {
        return memberManager.getMemberDetail(memberId)
                .map(StudentPreviewResponse::of)
                .orElseGet(StudentPreviewResponse::none);
    }

    private CoursePreviewResponse mapToCoursePreview(Course course) {
        return CoursePreviewResponse.of(
                course.getId(),
                course.getCourseName(),
                course.getStudents().size(),
                mapToTeacherPreview(course.getTeacherId()));
    }

    private TeacherPreviewResponse mapToTeacherPreview(final Long teacherId) {
        return memberManager.getMemberDetail(teacherId)
                .map(TeacherPreviewResponse::of)
                .orElseGet(TeacherPreviewResponse::none);
    }
}
