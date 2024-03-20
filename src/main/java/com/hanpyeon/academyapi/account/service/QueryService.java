package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.PreviewStudent;
import com.hanpyeon.academyapi.account.dto.PreviewTeacher;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryService {

    private final MemberRepository memberRepository;

    public List<PreviewTeacher> loadTeachers() {
        List<Member> teacherEntities = memberRepository.findMembersByRole(Role.TEACHER);
        return teacherEntities.stream()
                .map(PreviewTeacher::of)
                .toList();
    }

    public List<PreviewStudent> loadStudents() {
        List<Member> studentEntities = memberRepository.findMembersByRole(Role.STUDENT);
        return studentEntities.stream()
                .map(PreviewStudent::of)
                .toList();
    }

}
