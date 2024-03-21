package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.account.dto.RegisterMemberTotalDto;
import com.hanpyeon.academyapi.account.mapper.RegisterMapper;
import com.hanpyeon.academyapi.account.service.verify.register.MemberRegisterRequestVerifyManager;
import com.hanpyeon.academyapi.aspect.log.WarnLoggable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@WarnLoggable
@AllArgsConstructor
public class RegisterService {
    private final RegisterServiceProvider registerServiceProvider;
    private final MemberRegisterRequestVerifyManager registerRequestVerifyManager;
    private final RegisterMapper registerMapper;
    private final TimeProvider timeProvider;

    public void register(final RegisterMemberDto memberDto) {
        registerRequestVerifyManager.verify(memberDto);
        final RegisterMemberTotalDto memberTotalDto = registerMapper.createMemberTotalDto(memberDto, timeProvider.getCurrTime());
        registerServiceProvider.registerMember(memberTotalDto);
    }
}
