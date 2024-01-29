package com.hanpyeon.academyapi.service;

import com.hanpyeon.academyapi.account.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.account.dto.RegisterMemberTotalDto;
import com.hanpyeon.academyapi.account.exceptions.NotSupportedMemberTypeException;
import com.hanpyeon.academyapi.account.mapper.RegisterMapper;
import com.hanpyeon.academyapi.account.service.MemberVerification;
import com.hanpyeon.academyapi.account.service.RegisterService;
import com.hanpyeon.academyapi.account.service.RegisterServiceProvider;
import com.hanpyeon.academyapi.account.service.TimeProvider;
import com.hanpyeon.academyapi.security.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {
    @Mock
    MemberVerification strategy1;
    @Mock
    MemberVerification strategy2;
    @Mock
    MemberVerification strategy3;
    @Mock
    RegisterServiceProvider serviceProvider;
    @Mock
    TimeProvider timeProvider;
    RegisterMapper registerMapper = new RegisterMapper();
    RegisterService registerService;

    @BeforeEach
    void setUp() {
        this.registerService = new RegisterService(serviceProvider, List.of(strategy1, strategy2, strategy3), registerMapper, timeProvider);
    }

    @ParameterizedTest
    @MethodSource("provideRegisterRequest")
    void 등록_유형별_테스트_실행(RegisterMemberDto memberDto) {
        try {
            registerService.register(memberDto);
        } catch (NotSupportedMemberTypeException e) {
        }

        Mockito.verify(strategy1).supports(memberDto);
        Mockito.verify(strategy2).supports(memberDto);
        Mockito.verify(strategy3).supports(memberDto);
    }

    @ParameterizedTest
    @MethodSource("provideRegisterRequest")
    void 단일_실행_테스트(RegisterMemberDto memberDto) {
        Mockito.lenient().when(strategy1.supports(memberDto)).thenReturn(true);
        Mockito.lenient().when(strategy2.supports(memberDto)).thenReturn(true);
        Mockito.lenient().when(strategy3.supports(memberDto)).thenReturn(true);

        registerService.register(memberDto);

        Mockito.verify(strategy1).checkFields(memberDto);
        Mockito.verify(strategy2, Mockito.never()).checkFields(memberDto);
        Mockito.verify(strategy3, Mockito.never()).checkFields(memberDto);
    }

    @ParameterizedTest
    @MethodSource("provideRegisterRequest")
    void 현재_시간_삽입_테스트(RegisterMemberDto memberDto) {
        LocalDateTime currTime = LocalDateTime.now();
        Mockito.when(strategy1.supports(memberDto)).thenReturn(true);
        Mockito.when(timeProvider.getCurrTime()).thenReturn(currTime);
        ArgumentCaptor<RegisterMemberTotalDto> argumentCaptor = ArgumentCaptor.forClass(RegisterMemberTotalDto.class);

        registerService.register(memberDto);
        Mockito.verify(serviceProvider).registerMember(argumentCaptor.capture());

        assertEquals(argumentCaptor.getValue().registerDate(), currTime);

    }

    private static RegisterMemberDto createMemberDto(String name, Integer grade, Role role, String phoneNumber, String password) {
        return RegisterMemberDto.builder()
                .name(name)
                .grade(grade)
                .phoneNumber(phoneNumber)
                .role(role)
                .password(password)
                .build();
    }


    public static Stream<Arguments> provideRegisterRequest() {
        return Stream.of(
                Arguments.of(createMemberDto("Heejong", 10, Role.ROLE_STUDENT, "01099182281", "000")),
                Arguments.of(createMemberDto("Hee12", 11, Role.ROLE_TEACHER, "010991822281", "121")),
                Arguments.of(createMemberDto("Heejong", 10, Role.ROLE_STUDENT, "02109931822813", "124")),
                Arguments.of(createMemberDto("Heejong", 10, Role.ROLE_TEACHER, "120109918122281", ""))
        );
    }
}