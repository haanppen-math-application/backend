package com.hanpyeon.academyapi.repository;

import com.hanpyeon.academyapi.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByPhoneNumber(String phoneNumber);
}
