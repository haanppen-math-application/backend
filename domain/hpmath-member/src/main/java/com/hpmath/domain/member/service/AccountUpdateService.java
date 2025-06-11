package com.hpmath.domain.member.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.common.web.PasswordHandler;
import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.domain.member.dto.AccountUpdateCommand;
import com.hpmath.domain.member.dto.Password;
import com.hpmath.domain.member.dto.StudentUpdateCommand;
import com.hpmath.domain.member.dto.UpdateTeacherCommand;
import com.hpmath.domain.member.exceptions.AccountException;
import com.hpmath.domain.member.exceptions.NoSuchMemberException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.REPEATABLE_READ)
@CacheConfig(cacheNames = "hpmath::member::info")
@Validated
public class AccountUpdateService {
    private final MemberRepository memberRepository;
    private final PasswordHandler passwordHandler;

    @CacheEvict(key = "#command.targetMemberId()")
    public void updateMember(@Valid final AccountUpdateCommand command) {
        checkPhoneNumberDuplicated(command.phoneNumber(), command.targetMemberId());

        final Member member = loadMember(command.targetMemberId());

        member.setName(command.name());
        member.setPhoneNumber(command.phoneNumber());
        updatePassword(command.prevPassword(), command.newPassword(), member);
    }

    @CacheEvict(key = "#command.memberId()")
    public void updateMember(@Valid final StudentUpdateCommand command) {
        checkPhoneNumberDuplicated(command.phoneNumber(), command.memberId());

        final Member member = loadMember(command.memberId());

        member.setName(command.name());
        member.setPhoneNumber(command.phoneNumber());
        member.setName(command.name());
    }

    @CacheEvict(key = "#command.memberId()")
    public void updateMember(@Valid final UpdateTeacherCommand command) {
        checkPhoneNumberDuplicated(command.phoneNumber(), command.memberId());

        final Member member = loadMember(command.memberId());

        member.setName(command.name());
        member.setPhoneNumber(command.phoneNumber());
    }

    /**
     * password가 passwordHandler 의존하도록 구성
     *
     * @param prevPassword 이전 비밀번호
     * @param newPassword  새로 설정할 비밀번호
     * @param account      수정할 계정
     */
    private void updatePassword(final Password prevPassword, final Password newPassword, final Member account) {
        if (prevPassword == null) {
            return;
        }

        if (prevPassword.isMatch(account.getPassword(), passwordHandler)) {
            account.setPassword(newPassword.getEncryptedPassword(passwordHandler));
            return;
        }
        throw new AccountException("잘못된 비밀번호 입니다.", ErrorCode.ACCOUNT_POLICY);
    }

    public void checkPhoneNumberDuplicated(final String phoneNumber, final Long memberId) {
        if (memberRepository.existAlreadyWithout(phoneNumber, memberId)) {
            throw new AccountException("이미 등록된 전화번호", ErrorCode.ACCOUNT_POLICY);
        }
    }

    private Member loadMember(final Long targetMemberId) {
        return memberRepository.findMemberByIdAndRemovedIsFalse(targetMemberId)
                .orElseThrow(() -> new NoSuchMemberException("찾을 수 없습니다.", ErrorCode.NO_SUCH_MEMBER));
    }
}
