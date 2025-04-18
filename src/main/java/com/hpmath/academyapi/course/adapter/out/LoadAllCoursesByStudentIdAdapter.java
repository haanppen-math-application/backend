package com.hpmath.academyapi.course.adapter.out;

import com.hpmath.academyapi.course.application.port.out.LoadAllCoursesByStudentIdPort;
import com.hpmath.academyapi.course.domain.Course;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LoadAllCoursesByStudentIdAdapter implements LoadAllCoursesByStudentIdPort {

    private final CourseStudentRepository courseStudentRepository;
    private final CourseMapper courseMapper;

    @Override
    public List<Course> loadAll(Long studentId) {
        return courseStudentRepository.findCourseStudentsByMemberId(studentId).stream()
                .map(courseStudent -> courseStudent.getCourseEntity())
                .map(courseMapper::mapToCourseDomain)
                .toList();
    }
}
