package com.hpmath.academyapi.online.service.course;

import com.hpmath.academyapi.account.entity.Member;
import com.hpmath.academyapi.online.dao.OnlineCourse;
import com.hpmath.academyapi.online.dao.OnlineStudent;
import com.hpmath.academyapi.online.domain.OnlineCourseDomain;
import com.hpmath.academyapi.online.domain.OnlineCourseName;
import com.hpmath.academyapi.online.domain.OnlineCourseStudents;
import com.hpmath.academyapi.online.domain.OnlineCourseTeacher;
import com.hpmath.academyapi.online.dto.AddOnlineCourseCommand;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OnlineCourseMapper {
    private final OnlineCourseAbstractFactory onlineCourseAbstractFactory;
    private final MemberLoader memberLoader;

    public OnlineCourseDomain toCourseDomain(final AddOnlineCourseCommand addOnlineCourseCommand) {
        final OnlineCourseName onlineCourseName = onlineCourseAbstractFactory.toCourseName(
                addOnlineCourseCommand.onlineCourseName());
        final OnlineCourseStudents onlineCourseStudents = onlineCourseAbstractFactory.toCourseStudents(
                addOnlineCourseCommand.students());
        final OnlineCourseTeacher onlineCourseTeacher = onlineCourseAbstractFactory.toOnlineCourseTeacher(
                addOnlineCourseCommand.teacherId());

        return new OnlineCourseDomain(onlineCourseName, onlineCourseStudents, onlineCourseTeacher);
    }

    public OnlineCourse toCourse(final OnlineCourseDomain onlineCourseDomain) {
        return new OnlineCourse(memberLoader.findTeacher(
                onlineCourseDomain.getOnlineCourseTeacherId()),
                onlineCourseDomain.getOnlineCourseName()
        );
    }

    public OnlineStudent toOnlineStudent(final OnlineCourse onlineCourse, final Member member) {
        return new OnlineStudent(onlineCourse, member);
    }

    public List<OnlineStudent> toOnlineStudents(final OnlineCourse onlineCourse, final List<Member> members) {
        return members.stream()
                .map(member -> new OnlineStudent(onlineCourse, member))
                .toList();
    }
}
