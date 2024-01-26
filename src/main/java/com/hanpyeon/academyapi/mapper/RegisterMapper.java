package com.hanpyeon.academyapi.mapper;

import com.hanpyeon.academyapi.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.dto.RegisterRequestDto;
import com.hanpyeon.academyapi.entity.Member;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RegisterMapper {
    public RegisterMemberDto createRegisterMemberDto(final RegisterRequestDto requestDto, final LocalDateTime dateTime) {
        return RegisterMemberDto.builder()
                .name(requestDto.name())
                .phoneNumber(requestDto.phoneNumber())
                .grade(requestDto.grade())
                .password(requestDto.password())
                .role(requestDto.role())
                .registerDate(dateTime)
                .build();
    }
    public Member createMemberEntity(final RegisterMemberDto registerMemberDto, final String encodedPassword) {
        return Member.builder()
                .memberName(registerMemberDto.name())
                .phoneNumber(registerMemberDto.phoneNumber())
                .grade(registerMemberDto.grade())
                .password(encodedPassword)
                .userRole(registerMemberDto.role())
                .build();
    }
}
