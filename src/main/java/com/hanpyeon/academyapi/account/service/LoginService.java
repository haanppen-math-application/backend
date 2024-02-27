package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.InvalidPasswordException;
import com.hanpyeon.academyapi.account.exceptions.NoSuchMemberException;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.JwtUtils;
import com.hanpyeon.academyapi.security.PasswordHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;
    private final JwtUtils jwtUtils;
    private final PasswordHandler passwordHandler;

    @PostMapping
    public String provideJwt(final String phoneNumber, final String password) {
        Member member = memberRepository.findMemberByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NoSuchMemberException(ErrorCode.NOT_REGISTERED_MEMBER));

        if (!passwordHandler.matches(password, member.getPassword())) {
            throw new InvalidPasswordException(ErrorCode.INVALID_PASSWORD_EXCEPTION);
        }

        return jwtUtils.generateToken(member.getId(), member.getRole(), member.getName());
    }
}
