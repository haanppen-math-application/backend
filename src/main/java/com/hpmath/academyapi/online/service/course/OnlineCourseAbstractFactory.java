package com.hpmath.academyapi.online.service.course;

import com.hpmath.academyapi.online.domain.OnlineCourseDomain;
import com.hpmath.academyapi.online.domain.OnlineCourseName;
import com.hpmath.academyapi.online.domain.OnlineCourseRange;
import com.hpmath.academyapi.online.domain.OnlineCourseStudent;
import com.hpmath.academyapi.online.domain.OnlineCourseStudents;
import com.hpmath.academyapi.online.domain.OnlineCourseTeacher;
import com.hpmath.academyapi.online.domain.OnlineCourseTitle;
import com.hpmath.academyapi.online.domain.OnlineLessonDescription;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OnlineCourseAbstractFactory {

    public OnlineCourseName toCourseName(final String onlineCourseName) {
        return new OnlineCourseName(onlineCourseName);
    }

    public OnlineCourseRange toOnlineCourseRange(final String onlineCourseRange) {
        return new OnlineCourseRange(onlineCourseRange);
    }

    public OnlineLessonDescription toOnlineLessonDescription(final String onlineCourseDescription) {
        return new OnlineLessonDescription(onlineCourseDescription);
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

    public OnlineCourseTitle toOnlineCourseTitle(final String title) {
        return new OnlineCourseTitle(title);
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
