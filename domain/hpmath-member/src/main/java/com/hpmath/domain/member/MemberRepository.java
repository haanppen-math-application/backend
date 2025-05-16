package com.hpmath.domain.member;

import com.hpmath.domain.member.dto.MemberInfoResult;
import com.hpmath.common.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends Repository<Member, Long> {

    Member save(Member member);

    @Modifying
    @Query("UPDATE Member m SET m.verifyDateTime = null, m.isVerifying = false, m.verifyMessageSendCount = 0, m.verificationCode = null")
    void resetVerificationInfos();

    boolean existsByPhoneNumberAndRemovedIsFalse(final String phoneNumber);

    Optional<Member> findMemberByPhoneNumberAndRemovedIsFalse(final String phoneNumber);

    Optional<Member> findMemberByIdAndRemovedIsFalse(final Long id);

    Optional<Member> findMemberByIdAndRoleAndRemovedIsFalse(final Long id, final Role role);

    List<Member> findMembersByIdIsInAndRoleAndRemovedIsFalse(final List<Long> ids, final Role role);

    @Query("SELECT new com.hpmath.domain.member.dto.MemberInfoResult(m.id, m.name, m.phoneNumber, m.grade, m.role, m.registeredDate) FROM Member m WHERE m.id = :id AND m.removed = FALSE")
    Optional<MemberInfoResult> findMemberInfoById(@Param("id") final Long id);

    @Query("SELECT new com.hpmath.domain.member.dto.MemberInfoResult(m.id, m.name, m.phoneNumber, m.grade, m.role, m.registeredDate) FROM Member m WHERE m.role = :targetRole AND m.removed = FALSE")
    List<MemberInfoResult> findMembersByRole(@Param("targetRole") final Role role);

    @Query("SELECT new com.hpmath.domain.member.dto.MemberInfoResult(m.id, m.name, m.phoneNumber, m.grade, m.role, m.registeredDate) FROM Member m WHERE m.name LIKE %:targetName AND m.id >= :cursorIndex AND m.role = :targetRole AND m.removed = FALSE ")
    List<MemberInfoResult> findMembersByIdGreaterThanEqualAndRoleAndNameContainingAndRemovedIsFalse(@Param("cursorIndex") final Long cursorId, @Param("targetRole") final Role role, final Pageable pageable, @Param("targetName") final String name);

    @Query("SELECT new com.hpmath.domain.member.dto.MemberInfoResult(m.id, m.name, m.phoneNumber, m.grade, m.role, m.registeredDate) FROM Member m WHERE m.id >= :cursorIndex AND m.role = :targetRole AND m.removed = FALSE")
    List<MemberInfoResult> findMembersByIdGreaterThanEqualAndRoleAndRemovedIsFalse(@Param("cursorIndex") final Long cursorId, @Param("targetRole") final Role role, final Pageable pageable);

    @Query("SELECT new com.hpmath.domain.member.dto.MemberInfoResult(m.id, m.name, m.phoneNumber, m.grade, m.role, m.registeredDate) FROM  Member m WHERE m.id >= :cursorIndex AND m.role = :targetRole AND m.grade BETWEEN :includedStartGrade AND :includedEndGrade AND m.removed = FALSE")
    List<MemberInfoResult> findMembersByIdGreaterThanEqualAndRoleAndGradeBetweenAndRemovedIsFalse(@Param("cursorIndex") final Long cursorId, @Param("targetRole") final Role role, final Pageable pageable, @Param("includedStartGrade") final Integer startGrade, @Param("includedEndGrade") final Integer endGrade);

    @Query("SELECT new com.hpmath.domain.member.dto.MemberInfoResult(m.id, m.name, m.phoneNumber, m.grade, m.role, m.registeredDate) FROM Member m WHERE m.id >= :cursorIndex AND m.role = :targetRole AND m.grade BETWEEN :includedStartGrade AND  :includedEndGrade AND m.name LIKE %:targetName% AND m.removed = FALSE")
    List<MemberInfoResult> findMembersByIdGreaterThanEqualAndRoleAndGradeBetweenAndNameContainingAndRemovedIsFalse(@Param("cursorIndex") final Long cursorId, @Param("targetRole") final Role role, final Pageable pageable, @Param("includedStartGrade") final Integer startGrade, @Param("includedEndGrade") final Integer endGrade, @Param("targetName") final String name);

    @Query("SELECT new com.hpmath.domain.member.dto.MemberInfoResult(m.id, m.name, m.phoneNumber, m.grade, m.role, m.registeredDate) FROM Member m where m.role = :targetRole AND m.removed = FALSE")
    Page<MemberInfoResult> findMembersByRoleAndRemovedIsFalse(@Param("targetRole") final Role role, final Pageable pageable);

    @Query("SELECT new com.hpmath.domain.member.dto.MemberInfoResult(m.id, m.name, m.phoneNumber, m.grade, m.role, m.registeredDate) FROM Member m where m.role = :targetRole AND m.grade BETWEEN :includedStartGrade AND :includedEndGrade AND m.removed = FALSE")
    Page<MemberInfoResult> findMembersByRoleAndGradeBetweenAndRemovedIsFalse(@Param("targetRole") final Role role, @Param("includedStartGrade") final Integer startGrade, @Param("includedEndGrade") final Integer endGrade, final Pageable pageable);

    @Query("SELECT new com.hpmath.domain.member.dto.MemberInfoResult(m.id, m.name, m.phoneNumber, m.grade, m.role, m.registeredDate) FROM Member m where m.role = :targetRole AND m.name LIKE %:targetName% AND m.grade BETWEEN :includedStartGrade AND :includedEndGrade AND m.removed = FALSE")
    Page<MemberInfoResult> findMembersByRoleAndNameContainingAndGradeBetweenAndRemovedIsFalse(@Param("targetRole") final Role role, @Param("targetName") final String name, @Param("includedStartGrade") final Integer startGrade, @Param("includedEndGrade") final Integer endGrade, final Pageable pageable);

    @Query("SELECT new com.hpmath.domain.member.dto.MemberInfoResult(m.id, m.name, m.phoneNumber, m.grade, m.role, m.registeredDate) FROM Member m where m.role = :targetRole AND m.name LIKE %:targetName% AND m.removed = FALSE")
    Page<MemberInfoResult> findMembersByRoleAndNameContainingAndRemovedIsFalse(@Param("targetRole") final Role role, @Param("targetName") final String name, final Pageable pageable);

    List<Member> findMembersByIdIsInAndRemovedIsFalse(List<Long> memberIds);
}
