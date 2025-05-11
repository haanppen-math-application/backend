package com.hpmath.hpmathcoreapi.course.application.query;

import com.hpmath.hpmathcore.BusinessException;
import com.hpmath.hpmathcoreapi.course.application.port.out.LoadCoursesByTeacherIdPort;
import com.hpmath.hpmathcoreapi.course.application.port.out.LoadTeacherPort;
import com.hpmath.hpmathcoreapi.course.domain.Course;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class QueryCourseByTeacherIdHandler implements QueryCourseByMemberIdHandler {

    private final LoadTeacherPort loadTeacherPort;
    private final LoadCoursesByTeacherIdPort loadCoursesByTeacherIdPort;
    @Override
    public List<Course> query(Long memberId) {
        return loadCoursesByTeacherIdPort.loadByTeacherId(memberId);
    }

    @Override
    public boolean applicable(Long memberId) {
        try {
            loadTeacherPort.loadTeacher(memberId);
        } catch (BusinessException e) {
            return false;
        }
        return true;
    }
}
