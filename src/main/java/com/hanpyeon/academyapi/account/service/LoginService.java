package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.exceptions.NoSuchMemberException;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    private final JwtUtils jwtUtils;

    @PostMapping
    public String provideJwt(final String phoneNumber) {
        return memberRepository.findMemberByPhoneNumber(phoneNumber).stream()
                .findAny()
                .map(member ->
                        jwtUtils.generateToken(member.getId(), member.getRole(), member.getName()))
                .orElseThrow(() -> new NoSuchMemberException("등록되지 않은 사용자 입니다"));
    }
}
