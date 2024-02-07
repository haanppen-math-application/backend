package com.hanpyeon.academyapi.service;

import com.hanpyeon.academyapi.dto.RegisterMemberDto;

public interface MemberVerification {
    boolean supports(RegisterMemberDto memberDto);
    void checkFields(RegisterMemberDto memberDto);
}
