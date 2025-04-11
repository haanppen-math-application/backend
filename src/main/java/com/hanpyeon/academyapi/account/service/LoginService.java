package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.TimeProvider;
import com.hanpyeon.academyapi.account.dto.JwtDto;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.AccountException;
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
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {
    private final MemberRepository memberRepository;

    private final JwtUtils jwtUtils;
    private final TimeProvider timeProvider;
    private final AccountLockService accountLockService;
    private final PasswordHandler passwordHandler;

    @WarnLoggable
    public JwtDto provideJwtByLogin(final String phoneNumber, final Password password) {
        final Member member = loadMemberByPhoneNumber(phoneNumber);
        final LocalDateTime now = timeProvider.getCurrentTime();

        if (!accountLockService.checkAllowedToLogin(member, now)) {
            throw new AccountException("잠긴 계정입니다.", ErrorCode.ACCOUNT_POLICY);
        }
        if (!password.isMatch(member.getPassword(), passwordHandler)) {
            accountLockService.updateLoginFailedInfo(member, now);
            throw new InvalidPasswordException(ErrorCode.INVALID_PASSWORD_EXCEPTION);
        }
        accountLockService.unlock(member, now);
        return createJwtDto(member);
    }

    /**
     * @param refreshToken jwtrefreshToken
     * @return refreshToken을 통한 로그인 성공 시, 계정 잠금이 해제됩니다.
     */
    @WarnLoggable
    public JwtDto provideJwtByRefreshToken(final String refreshToken) {
        final LocalDateTime now = timeProvider.getCurrentTime();
        try {
            final Claims claims = jwtUtils.parseToken(refreshToken);
            final Long memberId = jwtUtils.getMemberId(claims)
                    .orElseThrow(() -> new JwtException("잘못된 JWT"));

            final Member member = loadMemberById(memberId);
            accountLockService.unlock(member, now);

            return createJwtDto(member);
        } catch (JwtException exception) {
            throw new ReLoginRequiredException("재 로그인 필요", ErrorCode.RE_LOGIN_REQUIRED);
        }
    }

    private JwtDto createJwtDto(final Member member) {
        final String userName = member.getName();
        final String accessToken = jwtUtils.generateAccessToken(member.getId(), member.getRole(), member.getName());
        final String refreshToken = jwtUtils.generateRefreshToken(member.getId());
        final Role role = member.getRole();

        return new JwtDto(userName, accessToken, refreshToken, role);
    }

    private Member loadMemberById(final Long memberId) {
        return memberRepository.findMemberByIdAndRemovedIsFalse(memberId)
                .orElseThrow(() -> new NoSuchMemberException("찾을 수 없습니다.", ErrorCode.NO_SUCH_MEMBER));
    }

    private Member loadMemberByPhoneNumber(final String phoneNumber) {
        return memberRepository.findMemberByPhoneNumberAndRemovedIsFalse(phoneNumber)
                .orElseThrow(() -> new NoSuchMemberException("찾을 수 없습니다.", ErrorCode.NO_SUCH_MEMBER));
    }
}
