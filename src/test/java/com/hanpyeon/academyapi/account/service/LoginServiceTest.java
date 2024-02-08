package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.NoSuchMemberException;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.security.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
    @Mock
    MemberRepository memberRepository;
    @Mock
    Member member;
    @Mock
    JwtUtils jwtUtils;
    LoginService loginService;

    @BeforeEach
    void initLoginService() {
        this.loginService = new LoginService(memberRepository, jwtUtils);
    }

    @Test
    void 사용자_찾기_실패_테스트() {
        Mockito.when(memberRepository.findMemberByPhoneNumber(Mockito.any()))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> {
            loginService.provideJwt(Mockito.anyString());
        }).isInstanceOf(NoSuchMemberException.class);
    }

    @Test
    void 사용자_찾기_성공_테스트() {
        Mockito.when(memberRepository.findMemberByPhoneNumber(Mockito.anyString()))
                .thenReturn(Optional.of(member));
        Mockito.when(jwtUtils.generateToken(member.getId(), member.getRole(), member.getName())).thenReturn("12");
        assertThat(loginService.provideJwt(Mockito.anyString())).isNotNull();
    }
}