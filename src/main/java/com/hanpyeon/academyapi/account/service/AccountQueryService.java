package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.controller.Responses;
import com.hanpyeon.academyapi.account.dto.*;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.NoSuchMemberException;
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
public class AccountQueryService {
    private final MemberRepository memberRepository;

    public Responses.MyAccountInfoResponse getMyInfo(final Long requestMemberId) {
        final Member member = memberRepository.findMemberByIdAndRemovedIsFalse(requestMemberId)
                .orElseThrow(() -> new NoSuchMemberException("찾을 수 없습니다.", ErrorCode.NO_SUCH_MEMBER));
        return new Responses.MyAccountInfoResponse(
                member.getName(),
                member.getPhoneNumber(),
                member.getRole(),
                member.getGrade() == null ? null : member.getGrade()
        );
    }

    public List<Responses.PreviewStudentResponse> loadAllStudents() {
        return memberRepository.findMembersByRole(Role.STUDENT).stream()
                .map(Responses.PreviewStudentResponse::of)
                .toList();
    }

    public List<Responses.PreviewTeacherResponse> loadALlTeachers() {
        return memberRepository.findMembersByRole(Role.TEACHER).stream()
                .map(Responses.PreviewTeacherResponse::of)
                .toList();
    }

    public Page<Responses.PreviewTeacherResponse> loadTeachers(final TeacherPageQuery teacherPageQuery) {
        if (teacherPageQuery.name() == null || teacherPageQuery.name().isBlank()) {
            return memberRepository.findMembersByRoleAndRemovedIsFalse(Role.TEACHER, teacherPageQuery.pageable())
                    .map(Responses.PreviewTeacherResponse::of);
        }
        return memberRepository.findMembersByRoleAndNameContainingAndRemovedIsFalse(Role.TEACHER, teacherPageQuery.name(), teacherPageQuery.pageable())
                .map(Responses.PreviewTeacherResponse::of);
    }

    // 동적 쿼리 필요성 확인
    public Page<Responses.PreviewStudentResponse> loadStudents(final StudentPageQuery studentPageQuery) {
        Page<Responses.MemberInfoResponse> members;
        if (Objects.isNull(studentPageQuery.name()) || studentPageQuery.name().isBlank()) {
            members = memberRepository.findMembersByRoleAndGradeBetweenAndRemovedIsFalse(Role.STUDENT, studentPageQuery.startGrade(), studentPageQuery.endGrade(), studentPageQuery.pageable());
        } else {
            members = memberRepository.findMembersByRoleAndNameContainingAndGradeBetweenAndRemovedIsFalse(Role.STUDENT, studentPageQuery.name(), studentPageQuery.startGrade(), studentPageQuery.endGrade(), studentPageQuery.pageable());
        }
        return members.map(Responses.PreviewStudentResponse::of);
    }

    public CursorResponse<Responses.PreviewTeacherResponse> loadTeachers(final TeacherQuery teacherQuery) {
        final Pageable pageable = Pageable.ofSize(teacherQuery.pageSize());
        List<Responses.MemberInfoResponse> teacherEntities;
        if (Objects.isNull(teacherQuery.name())) {
            teacherEntities = memberRepository.findMembersByIdGreaterThanEqualAndRoleAndRemovedIsFalse(teacherQuery.cursorIndex(), Role.TEACHER, pageable);
        } else {
            teacherEntities = memberRepository.findMembersByIdGreaterThanEqualAndRoleAndNameContainingAndRemovedIsFalse(teacherQuery.cursorIndex(), Role.TEACHER, pageable, teacherQuery.name());
        }
        final List<Responses.PreviewTeacherResponse> previewTeachers = teacherEntities.stream().map(
                Responses.PreviewTeacherResponse::of).toList();
        return new CursorResponse<>(previewTeachers, getNextCursorIndex(teacherEntities));
    }

    public CursorResponse<Responses.PreviewStudentResponse> loadStudents(final StudentQuery studentQuery) {
        final Pageable pageable = getPageable(studentQuery.pageSize());

        List<Responses.MemberInfoResponse> studentEntities;
        if (Objects.isNull((studentQuery.name()))) {
            studentEntities = memberRepository.findMembersByIdGreaterThanEqualAndRoleAndGradeBetweenAndRemovedIsFalse(studentQuery.cursorIndex(), Role.STUDENT, pageable, studentQuery.startGrade(), studentQuery.endGrade());
        } else {
            studentEntities = memberRepository.findMembersByIdGreaterThanEqualAndRoleAndGradeBetweenAndNameContainingAndRemovedIsFalse(studentQuery.cursorIndex(), Role.STUDENT, pageable, studentQuery.startGrade(), studentQuery.endGrade(), studentQuery.name());
        }
        List<Responses.PreviewStudentResponse> previewStudents = studentEntities.stream().map(
                Responses.PreviewStudentResponse::of).toList();
        return new CursorResponse<>(previewStudents, getNextCursorIndex(studentEntities));
    }

    private Pageable getPageable(final Integer size) {
        return Pageable.ofSize(size);
    }

    private Long getNextCursorIndex(final List<Responses.MemberInfoResponse> result) {
        if (result.isEmpty()) {
            return null;
        }
        return result.get(result.size() - 1).id() + 1;
    }
}
