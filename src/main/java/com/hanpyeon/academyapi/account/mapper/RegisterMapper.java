package com.hanpyeon.academyapi.account.mapper;

import com.hanpyeon.academyapi.account.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.account.dto.RegisterMemberTotalDto;
import com.hanpyeon.academyapi.account.dto.RegisterRequestDto;
import com.hanpyeon.academyapi.account.entity.Member;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RegisterMapper {
    public RegisterMemberDto createRegisterMemberDto(final RegisterRequestDto requestDto) {
        return RegisterMemberDto.builder()
                .name(requestDto.name())
                .phoneNumber(requestDto.phoneNumber())
                .grade(requestDto.grade())
                .password(requestDto.password())
                .role(requestDto.role())
                .build();
    }

    public RegisterMemberTotalDto createMemberTotalDto(final RegisterMemberDto registerMemberDto, final LocalDateTime dateTime) {
        return RegisterMemberTotalDto.builder()
                .name(registerMemberDto.name())
                .phoneNumber(registerMemberDto.phoneNumber())
                .grade(registerMemberDto.grade())
                .password(registerMemberDto.password())
                .role(registerMemberDto.role())
                .registerDate(dateTime)
                .build();
    }

    public Member createMemberEntity(final RegisterMemberTotalDto memberTotalDto, final String encodedPassword) {
        return Member.builder()
                .memberName(memberTotalDto.name())
                .phoneNumber(memberTotalDto.phoneNumber())
                .grade(memberTotalDto.grade())
                .password(encodedPassword)
                .userRole(memberTotalDto.role())
                .localDateTime(memberTotalDto.registerDate())
                .build();
    }
}
