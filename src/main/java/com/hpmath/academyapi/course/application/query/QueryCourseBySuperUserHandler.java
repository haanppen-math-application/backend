package com.hpmath.academyapi.course.application.query;

import com.hpmath.academyapi.course.application.port.out.LoadCoursesByTeacherIdPort;
import com.hpmath.academyapi.course.application.port.out.ValidateSuperUserPort;
import com.hpmath.academyapi.course.domain.Course;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class QueryCourseBySuperUserHandler implements QueryCourseByMemberIdHandler {
    private final ValidateSuperUserPort validateSuperUserPort;
    private final LoadCoursesByTeacherIdPort loadCoursesByTeacherIdPort;

    @Override
    public List<Course> query(Long memberId) {
        return loadCoursesByTeacherIdPort.loadByTeacherId(memberId);
    }

    @Override
    public boolean applicable(Long memberId) {
        return validateSuperUserPort.isSuperUser(memberId);
    }
}
