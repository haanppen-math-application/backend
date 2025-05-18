package com.hpmath.domain.course.service;

import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.course.repository.CourseRepository;
import com.hpmath.domain.course.dto.Responses.CoursePreviewResponse;
import com.hpmath.domain.course.dto.Responses.TeacherPreviewResponse;
import com.hpmath.domain.course.entity.Course;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryAllCourseService {
    private final CourseRepository courseRepository;
    private final MemberClient memberClient;

    public List<CoursePreviewResponse> loadAllCoursePreviews() {
        final List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(this::mapToCoursePreview)
                .toList();
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
