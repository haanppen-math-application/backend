package com.hanpyeon.academyapi.course.application.query;

import com.hanpyeon.academyapi.course.application.port.out.LoadAllCoursesByStudentIdPort;
import com.hanpyeon.academyapi.course.application.port.out.LoadStudentsPort;
import com.hanpyeon.academyapi.course.domain.Course;
import com.hanpyeon.academyapi.exception.BusinessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class QueryCourseByStudentIDHandler implements QueryCourseByMemberIdHandler {

    private final LoadAllCoursesByStudentIdPort loadAllCoursesByStudentIdPort;
    private final LoadStudentsPort loadStudentsPort;

    @Override
    public List<Course> query(Long memberId) {
        return loadAllCoursesByStudentIdPort.loadAll(memberId);
    }

    @Override
    public boolean applicable(Long memberId) {
        try {
            return !loadStudentsPort.loadStudents(List.of(memberId)).isEmpty();
        } catch (BusinessException exception) {
            return false;
        }
    }
}
