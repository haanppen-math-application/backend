package com.hpmath.domain.member.service;

import com.hpmath.domain.member.MemberRepository;
import com.hpmath.domain.member.dto.MemberInfoResult;
import com.hpmath.domain.member.dto.StudentPageQuery;
import com.hpmath.domain.member.dto.StudentQuery;
import com.hpmath.domain.member.dto.TeacherPageQuery;
import com.hpmath.domain.member.dto.TeacherQuery;
import com.hpmath.domain.member.exceptions.NoSuchMemberException;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathcore.Role;
import com.hpmath.common.page.CursorResponse;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountQueryService {
    private final MemberRepository memberRepository;

    public MemberInfoResult getMyInfo(final Long requestMemberId) {
        return memberRepository.findMemberInfoById(requestMemberId)
                .orElseThrow(() -> new NoSuchMemberException("찾을 수 없습니다.", ErrorCode.NO_SUCH_MEMBER));
    }

    public List<MemberInfoResult> loadAllStudents() {
        return memberRepository.findMembersByRole(Role.STUDENT);
    }

    public List<MemberInfoResult> loadALlTeachers() {
        return memberRepository.findMembersByRole(Role.TEACHER);
    }

    public Page<MemberInfoResult> loadTeachers(final TeacherPageQuery teacherPageQuery) {
        if (teacherPageQuery.name() == null || teacherPageQuery.name().isBlank()) {
            return memberRepository.findMembersByRoleAndRemovedIsFalse(Role.TEACHER, teacherPageQuery.pageable());
        }
        return memberRepository.findMembersByRoleAndNameContainingAndRemovedIsFalse(Role.TEACHER, teacherPageQuery.name(), teacherPageQuery.pageable());
    }

    // 동적 쿼리 필요성 확인
    public Page<MemberInfoResult> loadStudents(final StudentPageQuery studentPageQuery) {
        Page<MemberInfoResult> members;
        if (Objects.isNull(studentPageQuery.name()) || studentPageQuery.name().isBlank()) {
            members = memberRepository.findMembersByRoleAndGradeBetweenAndRemovedIsFalse(Role.STUDENT, studentPageQuery.startGrade(), studentPageQuery.endGrade(), studentPageQuery.pageable());
        } else {
            members = memberRepository.findMembersByRoleAndNameContainingAndGradeBetweenAndRemovedIsFalse(Role.STUDENT, studentPageQuery.name(), studentPageQuery.startGrade(), studentPageQuery.endGrade(), studentPageQuery.pageable());
        }
        return members;
    }

    public CursorResponse<MemberInfoResult> loadTeachers(final TeacherQuery teacherQuery) {
        final Pageable pageable = Pageable.ofSize(teacherQuery.pageSize());
        List<MemberInfoResult> teacherEntities;
        if (Objects.isNull(teacherQuery.name())) {
            teacherEntities = memberRepository.findMembersByIdGreaterThanEqualAndRoleAndRemovedIsFalse(teacherQuery.cursorIndex(), Role.TEACHER, pageable);
        } else {
            teacherEntities = memberRepository.findMembersByIdGreaterThanEqualAndRoleAndNameContainingAndRemovedIsFalse(teacherQuery.cursorIndex(), Role.TEACHER, pageable, teacherQuery.name());
        }
        return new CursorResponse<>(teacherEntities, getNextCursorIndex(teacherEntities));
    }

    public CursorResponse<MemberInfoResult> loadStudents(final StudentQuery studentQuery) {
        final Pageable pageable = getPageable(studentQuery.pageSize());

        List<MemberInfoResult> studentEntities;
        if (Objects.isNull((studentQuery.name()))) {
            studentEntities = memberRepository.findMembersByIdGreaterThanEqualAndRoleAndGradeBetweenAndRemovedIsFalse(studentQuery.cursorIndex(), Role.STUDENT, pageable, studentQuery.startGrade(), studentQuery.endGrade());
        } else {
            studentEntities = memberRepository.findMembersByIdGreaterThanEqualAndRoleAndGradeBetweenAndNameContainingAndRemovedIsFalse(studentQuery.cursorIndex(), Role.STUDENT, pageable, studentQuery.startGrade(), studentQuery.endGrade(), studentQuery.name());
        }
        return new CursorResponse<>(studentEntities, getNextCursorIndex(studentEntities));
    }

    private Pageable getPageable(final Integer size) {
        return Pageable.ofSize(size);
    }

    private Long getNextCursorIndex(final List<MemberInfoResult> result) {
        if (result.isEmpty()) {
            return null;
        }
        return result.get(result.size() - 1).id() + 1;
    }
}
