package com.hanpyeon.academyapi.account.repository;

import com.hanpyeon.academyapi.account.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByPhoneNumber(final String phoneNumber);

    Optional<Member> findMemberByPhoneNumber(final String phoneNumber);
}
