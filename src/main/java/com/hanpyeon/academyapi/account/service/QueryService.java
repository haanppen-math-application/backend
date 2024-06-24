package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.PreviewStudent;
import com.hanpyeon.academyapi.account.dto.PreviewTeacher;
import com.hanpyeon.academyapi.account.dto.StudentQueryDto;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class QueryService {
    private final MemberRepository memberRepository;

    public List<PreviewTeacher> loadTeachers(final String name) {
        final Predicate<Member> namePredicate = getNamePredicate(name);
        List<Member> teacherEntities = memberRepository.findMembersByRole(Role.TEACHER);
        return teacherEntities.stream()
                .filter(namePredicate)
                .map(PreviewTeacher::of)
                .toList();
    }

    public List<PreviewStudent> loadStudents(final StudentQueryDto studentQueryDto) {
        List<Member> studentEntities = memberRepository.findMembersByRole(Role.STUDENT);
        final Predicate<Member> namePredicate = getNamePredicate(studentQueryDto.name());
        final Predicate<Member> gradePredicate = getGradePredicate(studentQueryDto.startGrade(), studentQueryDto.endGrade());

        return studentEntities.stream()
                .filter(namePredicate)
                .filter(gradePredicate)
                .map(PreviewStudent::of)
                .toList();
    }

    private Predicate<Member> getNamePredicate(final String queryName) {
        if (queryName != null) {
            return (name) -> name.getName().contains(queryName);
        }
        return (name) -> true;
    }

    private Predicate<Member> getGradePredicate(final Integer startGrade, final Integer endGrade) {
        return (member) -> startGrade <= member.getGrade() && member.getGrade() <= endGrade;
    }
}
