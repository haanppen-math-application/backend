package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.PreviewStudent;
import com.hanpyeon.academyapi.account.dto.PreviewTeacher;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryService {
    private final MemberRepository memberRepository;

    public List<PreviewTeacher> loadTeachers(final String name) {
        List<Member> teacherEntities = memberRepository.findMembersByRole(Role.TEACHER);
        if (name.equals("null")) {
            return teacherEntities.stream()
                    .map(PreviewTeacher::of)
                    .toList();
        }
        return teacherEntities.stream()
                .filter(teacher -> teacher.getName().contains(name))
                .map(PreviewTeacher::of)
                .toList();
    }

    public List<PreviewStudent> loadStudents(final String name) {
        List<Member> studentEntities = memberRepository.findMembersByRole(Role.STUDENT);
        if (name.equals("null")) {
            return studentEntities.stream()
                    .map(PreviewStudent::of)
                    .toList();
        }
        return studentEntities.stream().filter(student -> student.getName().contains(name))
                .map(PreviewStudent::of)
                .toList();
    }
}
