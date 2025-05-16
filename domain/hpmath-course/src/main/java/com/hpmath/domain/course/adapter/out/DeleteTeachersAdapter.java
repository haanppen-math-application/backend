package com.hpmath.domain.course.adapter.out;

import com.hpmath.domain.course.application.port.out.DeleteTeachersPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteTeachersAdapter implements DeleteTeachersPort {
    private final CourseRepository courseRepository;
    @Override
    @Transactional
    public void deleteTeachers(List<Long> teacherIds) {
        courseRepository.updateTeacherToNullIn(teacherIds);
    }
}
