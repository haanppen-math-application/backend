package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.*;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.AccountException;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.paging.CursorResponse;
import com.hanpyeon.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryService {
    private final MemberRepository memberRepository;

    public List<PreviewStudent> loadAllStudents() {
        return memberRepository.findMembersByRoleAndRemovedIsFalse(Role.STUDENT).stream()
                .map(PreviewStudent::of)
                .toList();
    }

    public List<PreviewTeacher> loadALlTeachers() {
        return memberRepository.findMembersByRoleAndRemovedIsFalse(Role.TEACHER).stream()
                .map(PreviewTeacher::of)
                .toList();
    }

    public Page<PreviewTeacher> loadTeachers(final Pageable pageable) {
        return memberRepository.findMembersByRoleAndRemovedIsFalse(Role.TEACHER, pageable)
                .map(PreviewTeacher::of);
    }

    public Page<PreviewStudent> loadStudents(final Pageable pageable) {
        return memberRepository.findMembersByRoleAndRemovedIsFalse(Role.STUDENT, pageable)
                .map(PreviewStudent::of);

    }

    public CursorResponse<PreviewTeacher> loadTeachers(final TeacherQueryDto teacherQueryDto) {
        final Pageable pageable = Pageable.ofSize(teacherQueryDto.pageSize());
        List<Member> teacherEntities;
        if (Objects.isNull(teacherQueryDto.name())) {
            teacherEntities = memberRepository.findMembersByIdGreaterThanEqualAndRoleAndRemovedIsFalse(teacherQueryDto.cursorIndex(), Role.TEACHER, pageable);
        }else {
            teacherEntities = memberRepository.findMembersByIdGreaterThanEqualAndRoleAndNameContainingAndRemovedIsFalse(teacherQueryDto.cursorIndex(), Role.TEACHER, pageable, teacherQueryDto.name());
        }
        final List<PreviewTeacher> previewTeachers = teacherEntities.stream().map(PreviewTeacher::of).toList();
        return new CursorResponse<>(previewTeachers, getNextCursorIndex(teacherEntities));
    }

    public CursorResponse<PreviewStudent> loadStudents(final StudentQueryDto studentQueryDto) {
        final Pageable pageable = getPageable(studentQueryDto.pageSize());

        List<Member> studentEntities;
        if (Objects.isNull((studentQueryDto.name()))) {
            studentEntities = memberRepository.findMembersByIdGreaterThanEqualAndRoleAndGradeBetweenAndRemovedIsFalse(studentQueryDto.cursorIndex(), Role.STUDENT, pageable, studentQueryDto.startGrade(), studentQueryDto.endGrade());
        } else {
            studentEntities = memberRepository.findMembersByIdGreaterThanEqualAndRoleAndGradeBetweenAndNameContainingAndRemovedIsFalse(studentQueryDto.cursorIndex(), Role.STUDENT, pageable, studentQueryDto.startGrade(), studentQueryDto.endGrade(), studentQueryDto.name());
        }
        List<PreviewStudent> previewStudents = studentEntities.stream().map(PreviewStudent::of).toList();
        return new CursorResponse<>(previewStudents, getNextCursorIndex(studentEntities));
    }

    private Pageable getPageable(final Integer size) {
        return Pageable.ofSize(size);
    }

    private Long getNextCursorIndex(final List<Member> result) {
        if (result.isEmpty()) {
            return null;
        }
        return result.get(result.size() - 1).getId() + 1;
    }
}
