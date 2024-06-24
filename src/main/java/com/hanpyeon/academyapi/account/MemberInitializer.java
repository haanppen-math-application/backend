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
import java.util.ArrayList;

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

        // for Test

        ArrayList<Member> studentList = new ArrayList<>();
        ArrayList<Member> teacherList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            studentList.add(Member.builder()
                    .phoneNumber("010" + i)
                    .name("student" + i)
                    .password(passwordEncoder.getEncodedPassword(adminPassword))
                    .role(Role.STUDENT)
                    .grade(i)
                    .registeredDate(LocalDateTime.now())
                    .build());
            teacherList.add(Member.builder()
                    .phoneNumber("0101223" + i)
                    .name("teacher" + i)
                    .password(passwordEncoder.getEncodedPassword(adminPassword))
                    .role(Role.TEACHER)
                    .registeredDate(LocalDateTime.now())
                    .build());
        }
        memberRepository.saveAll(studentList);
        memberRepository.saveAll(teacherList);
    }
}