package com.hpmath.domain.course.service;

import com.hpmath.client.member.MemberClient;
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
import com.hpmath.domain.course.repository.CourseRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseQueryService {
    private final CourseRepository courseRepository;
    private final MemberClient memberClient;

    public List<CoursePreviewResponse> loadAllCoursePreviews() {
        final List<Course> courses = courseRepository.findAllWithStudents();
        return courses.stream()
                .map(this::mapToCoursePreview)
                .toList();
    }

    public List<CoursePreviewResponse> loadCoursePreviews(final Long memberId) {
        final MemberInfo memberInfo = memberClient.getMemberDetail(memberId);
        if (memberInfo.role() == Role.STUDENT) {
            return courseRepository.findAllByStudentId(memberId).stream().map(this::mapToCoursePreview).toList();
        }
        if (memberInfo.role() == null) {
            throw new CourseException(ErrorCode.NO_SUCH_COURSE_MEMBER);
        }
        return courseRepository.findAllWithStudentsByTeacherId(memberId).stream().map(this::mapToCoursePreview).toList();
    }

    public CourseDetailResponse loadCourseDetails(final Long courseId) {
        final Course course = courseRepository.findWithStudents(courseId).
                orElse(null);

        return new CourseDetailResponse(
                course.getId(),
                course.getCourseName(),
                course.getStudents().stream()
                        .mapToLong(CourseStudent::getStudentId)
                        .mapToObj(this::mapToStudent)
                        .toList(),
                this.mapToTeacher(course.getTeacherId())
        );
    }

    private StudentPreviewResponse mapToStudent(final Long memberId) {
        if (memberId == null) {
            return null;
        }
        return StudentPreviewResponse.of(memberClient.getMemberDetail(memberId));
    }

    private TeacherPreviewResponse mapToTeacher(final Long memberId) {
        if (memberId == null) {
            return null;
        }
        return TeacherPreviewResponse.of(memberClient.getMemberDetail(memberId));
    }

    private CoursePreviewResponse mapToCoursePreview(Course course) {
        return new CoursePreviewResponse(
                course.getCourseName(),
                course.getId(),
                course.getStudents().size(),
                mapToTeacherPreview(course.getTeacherId()));
    }

    private TeacherPreviewResponse mapToTeacherPreview(final Long teacherId) {
        if (teacherId == null) {
            return null;
        }
        return TeacherPreviewResponse.of(memberClient.getMemberDetail(teacherId));
    }
}
