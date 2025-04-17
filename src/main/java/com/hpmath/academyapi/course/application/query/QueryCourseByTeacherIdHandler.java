package com.hpmath.academyapi.course.application.query;

import com.hpmath.academyapi.course.application.port.out.LoadCoursesByTeacherIdPort;
import com.hpmath.academyapi.course.application.port.out.LoadTeacherPort;
import com.hpmath.academyapi.course.domain.Course;
import com.hpmath.academyapi.exception.BusinessException;
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
