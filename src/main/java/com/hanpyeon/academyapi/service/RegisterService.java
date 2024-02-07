package com.hanpyeon.academyapi.service;

import com.hanpyeon.academyapi.dto.RegisterMemberDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RegisterService {
    private final static Logger LOGGER = LoggerFactory.getLogger("RegisterRoleChecker");
    private final RegisterServiceProvider registerServiceProvider;
    private final List<MemberVerification> verifications;

    public void register(RegisterMemberDto memberDto) {
        verify(memberDto);
        registerServiceProvider.registerMember(memberDto);
    }
    private void verify(RegisterMemberDto memberDto) {
        verifications.stream()
                .filter(memberVerification -> memberVerification.supports(memberDto))
                .findAny()
                .orElseThrow(IllegalArgumentException::new)
                .checkFields(memberDto);
    }
}
