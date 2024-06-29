package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.AccountUpdateDto;
import com.hanpyeon.academyapi.account.dto.ModifyStudentRequest;
import com.hanpyeon.academyapi.account.dto.ModifyTeacherRequest;
import com.hanpyeon.academyapi.account.dto.MyAccountInfo;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.AccessDeniedException;
import com.hanpyeon.academyapi.account.exceptions.NoSuchMemberException;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.account.service.update.AccountUpdateManager;
import com.hanpyeon.academyapi.account.service.verify.policy.AccountPolicyManager;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class AccountUpdateService {

    private final MemberRepository memberRepository;
    private final AccountUpdateManager accountUpdateManager;
    private final AccountPolicyManager accountPolicyManager;

    @Transactional
    public Long updateAccount(@Validated final AccountUpdateDto accountUpdateDto) {

        final Member member = getMember(accountUpdateDto.targetMemberId());
        accountUpdateManager.update(accountUpdateDto, member);
        accountPolicyManager.verify(member);
        return member.getId();
    }

    public MyAccountInfo getMyInfo(final Long requestMemberId) {
        final Member member = getMember(requestMemberId);
        return new MyAccountInfo(member.getName(), member.getPhoneNumber(), member.getRole(), member.getGrade());
    }

    @Transactional
    public Long updateAccount(final ModifyStudentRequest modifyStudentRequest) {
        final AccountUpdateDto accountUpdateDto = new AccountUpdateDto(modifyStudentRequest.studentId(), modifyStudentRequest.phoneNumber(), modifyStudentRequest.name(), null, null);
        return this.updateAccount(accountUpdateDto);
    }

    @Transactional
    public Long updateAccount(final ModifyTeacherRequest modifyTeacherRequest) {
        final AccountUpdateDto accountUpdateDto = new AccountUpdateDto(modifyTeacherRequest.targetId(), modifyTeacherRequest.phoneNumber(), modifyTeacherRequest.name(), null, null);
        return this.updateAccount(accountUpdateDto);
    }

    private Member getMember(final Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchMemberException("해당 멤버가 존재하지 않습니다.", ErrorCode.NO_SUCH_MEMBER));
    }
}
