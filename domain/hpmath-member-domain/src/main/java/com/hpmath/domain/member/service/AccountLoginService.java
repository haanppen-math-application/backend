package com.hpmath.domain.member.service;

import com.hpmath.common.jwt.AuthInfo;
import com.hpmath.common.jwt.JwtUtils;
import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.domain.member.dto.JwtDto;
import com.hpmath.domain.member.dto.Password;
import com.hpmath.domain.member.exceptions.AccountException;
import com.hpmath.domain.member.exceptions.InvalidPasswordException;
import com.hpmath.domain.member.exceptions.NoSuchMemberException;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcore.TimeProvider;
import com.hpmath.common.web.PasswordHandler;
import com.hpmath.common.web.log.WarnLoggable;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountLoginService {
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
        final AuthInfo authInfo = jwtUtils.getAuthInfo(refreshToken);
        final Long memberId = authInfo.memberId();

        final Member member = loadMemberById(memberId);
        accountLockService.unlock(member, now);

        return createJwtDto(member);
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
