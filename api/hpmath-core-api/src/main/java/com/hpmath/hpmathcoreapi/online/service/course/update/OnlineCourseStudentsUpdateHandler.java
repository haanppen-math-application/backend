package com.hpmath.hpmathcoreapi.online.service.course.update;

import com.hpmath.hpmathcoreapi.account.entity.Member;
import com.hpmath.hpmathcoreapi.online.dao.OnlineCourse;
import com.hpmath.hpmathcoreapi.online.dao.OnlineStudent;
import com.hpmath.hpmathcoreapi.online.dao.OnlineStudentRepository;
import com.hpmath.hpmathcoreapi.online.domain.OnlineCourseStudents;
import com.hpmath.hpmathcoreapi.online.dto.OnlineCourseStudentUpdateCommand;
import com.hpmath.hpmathcoreapi.online.service.course.MemberLoader;
import com.hpmath.hpmathcoreapi.online.service.course.OnlineCourseAbstractFactory;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OnlineCourseStudentsUpdateHandler {
    private final MemberLoader memberLoader;
    private final OnlineCourseAbstractFactory onlineCourseAbstractFactory;
    private final OnlineStudentRepository onlineStudentRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public void update(final OnlineCourse onlineCourse, final OnlineCourseStudentUpdateCommand onlineCourseUpdateCommand) {
        if (Objects.isNull(onlineCourseUpdateCommand.studentIds())) {
            return;
        }
        onlineStudentRepository.removeAllByOnlineCourseId(onlineCourse.getId());
        final OnlineCourseStudents onlineCourseStudents = onlineCourseAbstractFactory.toCourseStudents(onlineCourseUpdateCommand.studentIds());
        final List<Member> members = memberLoader.findStudents(onlineCourseStudents.getOnlineCourseStudents());
        final List<OnlineStudent> onlineStudents = members.stream()
                .map(member -> new OnlineStudent(onlineCourse, member))
                .toList();
        onlineStudentRepository.saveAll(onlineStudents);
    }
}
