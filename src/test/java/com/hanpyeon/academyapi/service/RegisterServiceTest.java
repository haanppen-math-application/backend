package com.hanpyeon.academyapi.service;

import com.hanpyeon.academyapi.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.entity.Member;
import com.hanpyeon.academyapi.security.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {
    @Mock
    MemberVerification strategy1;
    @Mock
    MemberVerification strategy2;
    @Mock
    MemberVerification strategy3;
    @Mock
    RegisterServiceProvider serviceProvider;
    RegisterService registerService;
    @BeforeEach
    void setUp() {
        strategy1 = Mockito.mock(MemberVerification.class);
        strategy2 = Mockito.mock(MemberVerification.class);
        strategy3 = Mockito.mock(MemberVerification.class);
        serviceProvider = Mockito.mock(RegisterServiceProvider.class);
        this.registerService = new RegisterService(serviceProvider, new ArrayList<>(List.of(strategy1, strategy2, strategy3)));
    }

    @ParameterizedTest
    @MethodSource("provideRegisterRequest")
    void 등록_유형별_테스트_실행 (RegisterMemberDto memberDto) {
        try {
            registerService.register(memberDto);
        }catch (IllegalArgumentException e){}

        Mockito.verify(strategy1).supports(memberDto);
        Mockito.verify(strategy2).supports(memberDto);
        Mockito.verify(strategy3).supports(memberDto);
    }
    @ParameterizedTest
    @MethodSource("provideRegisterRequest")
    void 단일_실행_테스트(RegisterMemberDto memberDto) {
        Mockito.when(strategy1.supports(memberDto)).thenReturn(true);
        Mockito.when(strategy2.supports(memberDto)).thenReturn(true);
        Mockito.when(strategy3.supports(memberDto)).thenReturn(true);

        registerService.register(memberDto);

        Mockito.verify(strategy1).checkFields(memberDto);
        Mockito.verify(strategy2, Mockito.never()).checkFields(memberDto);
        Mockito.verify(strategy3, Mockito.never()).checkFields(memberDto);

    }

    private static RegisterMemberDto createMemberDto(String name, Integer grade, Role role, String phoneNumber, String password) {
        return RegisterMemberDto.builder()
                .name(name)
                .grade(grade)
                .phoneNumber(phoneNumber)
                .role(role)
                .password(password)
                .registerDate(LocalDateTime.now())
                .build();
    }


    public static Stream<Arguments> provideRegisterRequest() {
        return Stream.of(
                Arguments.of(createMemberDto("Heejong", 10, Role.ROLE_STUDENT, "01099182281", "000")),
                Arguments.of(createMemberDto("Hee12", 11, Role.ROLE_TEACHER, "010991822281", "121")),
                Arguments.of(createMemberDto("Heejong", 10, Role.ROLE_STUDENT,"02109931822813", "124")),
                Arguments.of(createMemberDto("Heejong", 10, Role.ROLE_TEACHER, "120109918122281", ""))
        );
    }
}