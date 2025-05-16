package com.hpmath.domain.course.adapter.out;

import com.hpmath.domain.course.application.port.out.DeleteStudentsPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
class DeleteStudentsAdapter implements DeleteStudentsPort {
    private final CourseStudentRepository courseStudentRepository;

    @Override
    @Transactional
    public void delete(List<Long> studentIds) {
        final int count = courseStudentRepository.deleteCourseStudentsByStudentIdIn(studentIds);
        log.info("Deleted students: {}, studentIds: {}", count, studentIds);
    }
}
