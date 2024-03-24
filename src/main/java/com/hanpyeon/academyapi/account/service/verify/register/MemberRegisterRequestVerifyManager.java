package com.hanpyeon.academyapi.account.service.verify.register;

import com.hanpyeon.academyapi.account.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.account.exceptions.NotSupportedMemberTypeException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberRegisterRequestVerifyManager {
    private final List<MemberRegisterRequestVerifyHandler> verifications;

    public void verify(final RegisterMemberDto registerMemberDto) {
        verifications.stream()
                .filter(verification -> verification.supports(registerMemberDto))
                .findAny()
                .orElseThrow(() -> new NotSupportedMemberTypeException(ErrorCode.NOT_SUPPORTED_MEMBER_TYPE))
                .checkFields(registerMemberDto);
    }
}
