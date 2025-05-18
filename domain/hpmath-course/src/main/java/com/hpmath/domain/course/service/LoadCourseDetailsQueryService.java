package com.hpmath.domain.course.service;

import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.course.repository.CourseRepository;
import com.hpmath.domain.course.dto.Responses.CourseDetailResponse;
import com.hpmath.domain.course.dto.Responses.StudentPreviewResponse;
import com.hpmath.domain.course.dto.Responses.TeacherPreviewResponse;
import com.hpmath.domain.course.entity.Course;
import com.hpmath.domain.course.entity.CourseStudent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoadCourseDetailsQueryService {
    private final CourseRepository courseRepository;
    private final MemberClient memberClient;

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
}
