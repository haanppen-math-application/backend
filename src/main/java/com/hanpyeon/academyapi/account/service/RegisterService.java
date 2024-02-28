package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.account.dto.RegisterMemberTotalDto;
import com.hanpyeon.academyapi.account.exceptions.NotSupportedMemberTypeException;
import com.hanpyeon.academyapi.account.mapper.RegisterMapper;
import com.hanpyeon.academyapi.account.service.verify.MemberVerification;
import com.hanpyeon.academyapi.aspect.log.WarnLoggable;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@WarnLoggable
@AllArgsConstructor
public class RegisterService {
    private final static Logger LOGGER = LoggerFactory.getLogger("RegisterRoleChecker");
    private final RegisterServiceProvider registerServiceProvider;
    private final List<MemberVerification> verifications;
    private final RegisterMapper registerMapper;
    private final TimeProvider timeProvider;

    public void register(final RegisterMemberDto memberDto) {
        verify(memberDto);
        RegisterMemberTotalDto memberTotalDto = registerMapper.createMemberTotalDto(memberDto, timeProvider.getCurrTime());
        registerServiceProvider.registerMember(memberTotalDto);
    }

    private void verify(final RegisterMemberDto memberDto) {
        verifications.stream()
                .filter(memberVerification -> memberVerification.supports(memberDto))
                .findAny()
                .orElseThrow(() -> new NotSupportedMemberTypeException(ErrorCode.NOT_SUPPORTED_MEMBER_TYPE))
                .checkFields(memberDto);
    }
}
