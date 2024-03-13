package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.JwtDto;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.InvalidPasswordException;
import com.hanpyeon.academyapi.account.exceptions.NoSuchMemberException;
import com.hanpyeon.academyapi.account.exceptions.ReLoginRequiredException;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.aspect.log.WarnLoggable;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.JwtUtils;
import com.hanpyeon.academyapi.security.PasswordHandler;
import com.hanpyeon.academyapi.security.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final MemberRepository memberRepository;
    private final JwtUtils jwtUtils;
    private final PasswordHandler passwordHandler;

    @WarnLoggable
    public JwtDto provideJwtByLogin(final String phoneNumber, final String password) {
        final Member member = memberRepository.findMemberByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NoSuchMemberException("jwt 발급 불가", ErrorCode.NOT_REGISTERED_MEMBER));

        if (!passwordHandler.matches(password, member.getPassword())) {
            throw new InvalidPasswordException(ErrorCode.INVALID_PASSWORD_EXCEPTION);
        }

        return createJwtDto(member);
    }

    @WarnLoggable
    public JwtDto provideJwtByRefreshToken(final String refreshToken) {
        try {

            final Claims claims = jwtUtils.parseToken(refreshToken);
            final Long memberId = jwtUtils.getMemberId(claims)
                    .orElseThrow(() -> new JwtException("잘못된 JWT"));
            final Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new NoSuchMemberException(ErrorCode.NO_SUCH_MEMBER));
            return createJwtDto(member);

        } catch (JwtException exception) {
            throw new ReLoginRequiredException("재 로그인 필요", ErrorCode.RE_LOGIN_REQUIRED);
        }
    }

    private JwtDto createJwtDto(final Member member) {
        String accessToken = jwtUtils.generateAccessToken(member.getId(), member.getRole(), member.getName());
        String refreshToken = jwtUtils.generateRefreshToken(member.getId());
        Role role = member.getRole();
        return new JwtDto(accessToken, refreshToken, role);
    }
}
