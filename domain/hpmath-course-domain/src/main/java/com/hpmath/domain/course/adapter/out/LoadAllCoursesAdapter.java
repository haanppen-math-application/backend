package com.hpmath.domain.course.adapter.out;

import com.hpmath.domain.course.application.port.out.LoadAllCoursePort;
import com.hpmath.domain.course.domain.Course;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoadAllCoursesAdapter implements LoadAllCoursePort {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public List<Course> loadAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseMapper::mapToCourseDomain)
                .toList();
    }
}
