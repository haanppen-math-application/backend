package com.hpmath.domain.course.adapter.out;

import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.course.application.port.out.LoadStudentsPort;
import com.hpmath.domain.course.domain.Student;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LoadStudentsAdapter implements LoadStudentsPort {
    private final MemberClient memberClient;

    @Override
    public List<Student> loadStudents(final List<Long> memberIds) {
        return memberIds.stream()
                .parallel()
                .map(memberClient::getMemberDetail)
                .map(Student::from)
                .toList();
    }
}
