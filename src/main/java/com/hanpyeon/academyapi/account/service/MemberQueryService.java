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
public class MemberQueryService {
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

    public Page<Responses.PreviewTeacherResponse> loadTeachers(final TeacherPageQueryCommand teacherPageQueryCommand) {
        if (teacherPageQueryCommand.name() == null || teacherPageQueryCommand.name().isBlank()) {
            return memberRepository.findMembersByRoleAndRemovedIsFalse(Role.TEACHER, teacherPageQueryCommand.pageable())
                    .map(Responses.PreviewTeacherResponse::of);
        }
        return memberRepository.findMembersByRoleAndNameContainingAndRemovedIsFalse(Role.TEACHER, teacherPageQueryCommand.name(), teacherPageQueryCommand.pageable())
                .map(Responses.PreviewTeacherResponse::of);
    }

    // 동적 쿼리 필요성 확인
    public Page<Responses.PreviewStudentResponse> loadStudents(final StudentPageQueryCommand studentPageQueryCommand) {
        Page<Responses.MemberInfoResponse> members;
        if (Objects.isNull(studentPageQueryCommand.name()) || studentPageQueryCommand.name().isBlank()) {
            members = memberRepository.findMembersByRoleAndGradeBetweenAndRemovedIsFalse(Role.STUDENT, studentPageQueryCommand.startGrade(), studentPageQueryCommand.endGrade(), studentPageQueryCommand.pageable());
        } else {
            members = memberRepository.findMembersByRoleAndNameContainingAndGradeBetweenAndRemovedIsFalse(Role.STUDENT, studentPageQueryCommand.name(), studentPageQueryCommand.startGrade(), studentPageQueryCommand.endGrade(), studentPageQueryCommand.pageable());
        }
        return members.map(Responses.PreviewStudentResponse::of);
    }

    public CursorResponse<Responses.PreviewTeacherResponse> loadTeachers(final TeacherQueryCommand teacherQueryCommand) {
        final Pageable pageable = Pageable.ofSize(teacherQueryCommand.pageSize());
        List<Responses.MemberInfoResponse> teacherEntities;
        if (Objects.isNull(teacherQueryCommand.name())) {
            teacherEntities = memberRepository.findMembersByIdGreaterThanEqualAndRoleAndRemovedIsFalse(teacherQueryCommand.cursorIndex(), Role.TEACHER, pageable);
        } else {
            teacherEntities = memberRepository.findMembersByIdGreaterThanEqualAndRoleAndNameContainingAndRemovedIsFalse(teacherQueryCommand.cursorIndex(), Role.TEACHER, pageable, teacherQueryCommand.name());
        }
        final List<Responses.PreviewTeacherResponse> previewTeachers = teacherEntities.stream().map(
                Responses.PreviewTeacherResponse::of).toList();
        return new CursorResponse<>(previewTeachers, getNextCursorIndex(teacherEntities));
    }

    public CursorResponse<Responses.PreviewStudentResponse> loadStudents(final StudentQueryCommand studentQueryCommand) {
        final Pageable pageable = getPageable(studentQueryCommand.pageSize());

        List<Responses.MemberInfoResponse> studentEntities;
        if (Objects.isNull((studentQueryCommand.name()))) {
            studentEntities = memberRepository.findMembersByIdGreaterThanEqualAndRoleAndGradeBetweenAndRemovedIsFalse(studentQueryCommand.cursorIndex(), Role.STUDENT, pageable, studentQueryCommand.startGrade(), studentQueryCommand.endGrade());
        } else {
            studentEntities = memberRepository.findMembersByIdGreaterThanEqualAndRoleAndGradeBetweenAndNameContainingAndRemovedIsFalse(studentQueryCommand.cursorIndex(), Role.STUDENT, pageable, studentQueryCommand.startGrade(), studentQueryCommand.endGrade(), studentQueryCommand.name());
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
