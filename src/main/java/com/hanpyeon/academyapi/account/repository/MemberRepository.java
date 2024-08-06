package com.hanpyeon.academyapi.account.repository;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.security.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByPhoneNumber(final String phoneNumber);

//    Optional<Member> findMemberByPhoneNumberAndRemovedIsFalse(final String phoneNumber);
    Optional<Member> findMemberByPhoneNumberAndRemovedIsFalse(final String phoneNumber);
    Optional<Member> findMemberByIdAndRemovedIsFalse(final Long id);
    Optional<Member> findMemberByIdAndRoleAndRemovedIsFalse(final Long id, final Role role);
    List<Member> findMembersByIdIsInAndRoleAndRemovedIsFalse(final List<Long> ids, final Role role);
    List<Member> findMembersByRoleAndRemovedIsFalse(final Role role);
    List<Member> findMembersByIdGreaterThanEqualAndRoleAndNameContainingAndRemovedIsFalse(final Long cursorId, final Role role, final Pageable pageable, final String name);
    List<Member> findMembersByIdGreaterThanEqualAndRoleAndRemovedIsFalse(final Long cursorId, final Role role, final Pageable pageable);
    List<Member> findMembersByIdGreaterThanEqualAndRoleAndGradeBetweenAndRemovedIsFalse(final Long cursorId, final Role role, final Pageable pageable, final Integer startGrade, final Integer endGrade);
    List<Member> findMembersByIdGreaterThanEqualAndRoleAndGradeBetweenAndNameContainingAndRemovedIsFalse(final Long cursorId, final Role role, final Pageable pageable, final Integer startGrade, final Integer endGrade, final String name);
    List<Member> findMembersByIdInAndRemovedIsFalse(final List<Long> ids);
    Page<Member> findMembersByRoleAndRemovedIsFalse(final Role role, final Pageable pageable);
    Page<Member> findMembersByRoleAndGradeAndRemovedIsFalse(final Role role, final Integer grade, final Pageable pageable);
    Page<Member> findMembersByRoleAndNameContainingAndGradeAndRemovedIsFalse(final Role role, final String name, final Integer grade, final Pageable pageable);
    Page<Member> findMembersByRoleAndNameContainingAndRemovedIsFalse(final Role role, final String name, final Pageable pageable);
}
