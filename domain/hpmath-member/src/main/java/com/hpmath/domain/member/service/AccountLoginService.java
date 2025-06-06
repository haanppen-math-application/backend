package com.hpmath.domain.member.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import com.hpmath.common.TimeProvider;
import com.hpmath.common.jwt.AuthInfo;
import com.hpmath.common.jwt.JwtUtils;
import com.hpmath.common.web.PasswordHandler;
import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.domain.member.dto.JwtDto;
import com.hpmath.domain.member.dto.Password;
import com.hpmath.domain.member.exceptions.AccountException;
import com.hpmath.domain.member.exceptions.InvalidPasswordException;
import com.hpmath.domain.member.exceptions.NoSuchMemberException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class AccountLoginService {
    private final MemberRepository memberRepository;

    private final JwtUtils jwtUtils;
    private final TimeProvider timeProvider;
    private final AccountLockService accountLockService;
    private final PasswordHandler passwordHandler;

    @Transactional(noRollbackFor = InvalidPasswordException.class)
    public JwtDto provideJwtByLogin(@NotNull final String phoneNumber, @NotNull @Valid final Password password) {
        final Member member = loadMemberByPhoneNumber(phoneNumber);
        final LocalDateTime now = timeProvider.getCurrentTime();

        if (!accountLockService.checkAllowedToLogin(member, now)) {
            throw new AccountException("잠긴 계정입니다.", ErrorCode.ACCOUNT_POLICY);
        }
        if (!password.isMatch(member.getPassword(), passwordHandler)) {
            accountLockService.updateLoginFailedInfo(member, now);
            throw new InvalidPasswordException(ErrorCode.INVALID_PASSWORD_EXCEPTION);
        }
        accountLockService.unlockWhenLocked(member);
        return createJwtDto(member);
    }

    /**
     * @param refreshToken jwtrefreshToken
     * @return refreshToken을 통한 로그인 성공 시, 계정 잠금이 해제됩니다.
     */
    public JwtDto provideJwtByRefreshToken(@NotNull final String refreshToken) {
        final AuthInfo authInfo = jwtUtils.getAuthInfo(refreshToken);
        final Long memberId = authInfo.memberId();

        final Member member = loadMemberById(memberId);
        accountLockService.unlockWhenLocked(member);

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
