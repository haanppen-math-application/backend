package com.hanpyeon.academyapi.online;

import com.hanpyeon.academyapi.online.domain.OnlineCourseDomain;
import com.hanpyeon.academyapi.online.domain.OnlineCourseName;
import com.hanpyeon.academyapi.online.domain.OnlineCourseStudent;
import com.hanpyeon.academyapi.online.domain.OnlineCourseStudents;
import com.hanpyeon.academyapi.online.domain.OnlineCourseTeacher;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OnlineCourseMapper {

    public OnlineCourseName toCourseName(final String onlineCourseName) {
        return new OnlineCourseName(onlineCourseName);
    }

    public OnlineCourseStudents toCourseStudents(final List<Long> students) {
        return new OnlineCourseStudents(students.stream()
                .map(OnlineCourseStudent::new)
                .toList()
        );
    }

    public OnlineCourseTeacher toOnlineCourseTeacher(final Long teacherId) {
        return new OnlineCourseTeacher(teacherId);
    }

    public OnlineCourseDomain toOnlineCourse(
            final OnlineCourseName onlineCourseName,
            final OnlineCourseStudents onlineCourseStudents,
            final OnlineCourseTeacher onlineCourseTeacher
    ) {
        return new OnlineCourseDomain(
                onlineCourseName,
                onlineCourseStudents,
                onlineCourseTeacher
        );
    }
}
