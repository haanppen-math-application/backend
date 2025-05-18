package com.hpmath.domain.course.service;

import com.hpmath.client.member.MemberClient;
import com.hpmath.client.member.MemberClient.MemberInfo;
import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import com.hpmath.domain.course.repository.CourseRepository;
import com.hpmath.domain.course.dto.Responses.CoursePreviewResponse;
import com.hpmath.domain.course.dto.Responses.TeacherPreviewResponse;
import com.hpmath.domain.course.entity.Course;
import com.hpmath.domain.course.exception.CourseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QueryCourseByMemberIdService {
    private final CourseRepository courseRepository;
    private final MemberClient memberClient;

    public List<CoursePreviewResponse> loadCoursePreviews(final Long memberId) {
        final MemberInfo memberInfo = memberClient.getMemberDetail(memberId);
        if (memberInfo.role() == Role.STUDENT) {
            return courseRepository.findAllByStudentId(memberId).stream().map(this::mapToCoursePreview).toList();
        }
        if (memberInfo.role() == null) {
            throw new CourseException(ErrorCode.NO_SUCH_COURSE_MEMBER);
        }
        return courseRepository.findAllByTeacherId(memberId).stream().map(this::mapToCoursePreview).toList();
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
