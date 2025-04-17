package com.hpmath.academyapi.course.adapter.out;

import com.hpmath.academyapi.course.application.port.out.DeleteCourseStudentPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
class DeleteCourseStudentAdapter implements DeleteCourseStudentPort {
    private final CourseStudentRepository courseStudentRepository;
    @Override
    @Transactional
    public void deleteCourseStudents(Long courseId) {
        courseStudentRepository.deleteCourseStudentByCourseEntityId(courseId);
    }
}
