package com.hanpyeon.academyapi.account.repository;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByPhoneNumber(final String phoneNumber);

    Optional<Member> findMemberByPhoneNumber(final String phoneNumber);
    Optional<Member> findMemberByIdAndRole(final Long id, final Role role);
    List<Member> findMembersByIdIsInAndRole(final List<Long> ids, final Role role);
    List<Member> findMembersByRole(final Role role);
}
