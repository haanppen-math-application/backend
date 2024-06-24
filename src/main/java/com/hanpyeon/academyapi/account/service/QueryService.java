package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.*;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.course.application.dto.StudentPreview;
import com.hanpyeon.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class QueryService {
    private final MemberRepository memberRepository;

    public CursorResponse<PreviewTeacher> loadTeachers(final TeacherQueryDto teacherQueryDto) {
        final Pageable pageable = Pageable.ofSize(teacherQueryDto.pageSize());
        List<Member> teacherEntities;
        if (Objects.isNull(teacherQueryDto.name())) {
            teacherEntities = memberRepository.findMembersByIdGreaterThanEqualAndRole(teacherQueryDto.cursorIndex(), Role.TEACHER, pageable);
        }else {
            teacherEntities = memberRepository.findMembersByIdGreaterThanEqualAndRoleAndNameContaining(teacherQueryDto.cursorIndex(), Role.TEACHER, pageable, teacherQueryDto.name());
        }
        final List<PreviewTeacher> previewTeachers = teacherEntities.stream().map(PreviewTeacher::of).toList();
        return new CursorResponse<>(previewTeachers, getNextCursorIndex(teacherEntities));

    }

    public CursorResponse<PreviewStudent> loadStudents(final StudentQueryDto studentQueryDto) {
        final Pageable pageable = getPageable(studentQueryDto.pageSize());

        List<Member> studentEntities;
        if (Objects.isNull((studentQueryDto.name()))) {
            studentEntities = memberRepository.findMembersByIdGreaterThanEqualAndRoleAndGradeBetween(studentQueryDto.cursorIndex(), Role.STUDENT, pageable, studentQueryDto.startGrade(), studentQueryDto.endGrade());
        } else {
            studentEntities = memberRepository.findMembersByIdGreaterThanEqualAndRoleAndGradeBetweenAndNameContaining(studentQueryDto.cursorIndex(), Role.STUDENT, pageable, studentQueryDto.startGrade(), studentQueryDto.endGrade(), studentQueryDto.name());
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
