package com.hpmath.domain.course.adapter.out;

import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.course.application.port.out.LoadTeacherPort;
import com.hpmath.domain.course.domain.Teacher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class LoadTeacherAdapter implements LoadTeacherPort {
    private final MemberClient memberClient;

    @Override
    public Teacher loadTeacher(final Long id) {
        return Teacher.from(memberClient.getMemberDetail(id));
    }
}
