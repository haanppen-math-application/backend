package com.hanpyeon.academyapi.account;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.security.PasswordHandler;
import com.hanpyeon.academyapi.security.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MemberInitializer {
    private final MemberRepository memberRepository;
    private final PasswordHandler passwordEncoder;

    @Value("${application.admin.account.id}")
    private String adminId;
    @Value("${application.admin.account.password}")
    private String adminPassword;

    @PostConstruct
    public void initAdmin() {
        memberRepository.save(Member.builder()
                .phoneNumber(adminId)
                .name("어드민계정")
                .password(passwordEncoder.getEncodedPassword(adminPassword))
                .role(Role.ADMIN)
                .registeredDate(LocalDateTime.now())
                .build());
    }
}