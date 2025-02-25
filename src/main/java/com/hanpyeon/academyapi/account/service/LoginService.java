package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.JwtDto;
import com.hanpyeon.academyapi.account.exceptions.InvalidPasswordException;
import com.hanpyeon.academyapi.account.exceptions.ReLoginRequiredException;
import com.hanpyeon.academyapi.account.model.Account;
import com.hanpyeon.academyapi.account.model.Password;
import com.hanpyeon.academyapi.aspect.log.WarnLoggable;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.JwtUtils;
import com.hanpyeon.academyapi.security.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {
    private final AccountLoader accountLoader;
    private final JwtUtils jwtUtils;

    @WarnLoggable
    public JwtDto provideJwtByLogin(final String phoneNumber, final Password password) {
        final Account loginAccount = accountLoader.loadAccount(phoneNumber);
        if (!loginAccount.isMatchPassword(password)) {
            throw new InvalidPasswordException(ErrorCode.INVALID_PASSWORD_EXCEPTION);
        }
        return createJwtDto(loginAccount);
    }

    @WarnLoggable
    public JwtDto provideJwtByRefreshToken(final String refreshToken) {
        try {
            final Claims claims = jwtUtils.parseToken(refreshToken);
            final Long memberId = jwtUtils.getMemberId(claims)
                    .orElseThrow(() -> new JwtException("잘못된 JWT"));

            final Account account = accountLoader.loadAccount(memberId);
            return createJwtDto(account);
        } catch (JwtException exception) {
            throw new ReLoginRequiredException("재 로그인 필요", ErrorCode.RE_LOGIN_REQUIRED);
        }
    }

    private JwtDto createJwtDto(final Account member) {
        String userName = member.getAccountName().getName();
        String accessToken = jwtUtils.generateAccessToken(member.getId(), member.getAccountRole().getRole(),
                member.getAccountName().getName());
        String refreshToken = jwtUtils.generateRefreshToken(member.getId());
        Role role = member.getAccountRole().getRole();
        return new JwtDto(userName, accessToken, refreshToken, role);
    }
}
