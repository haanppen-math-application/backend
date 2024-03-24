package com.hanpyeon.academyapi.account.service.verify.register;

import com.hanpyeon.academyapi.account.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.account.exceptions.NotSupportedMemberTypeException;
import com.hanpyeon.academyapi.security.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class MemberRegisterRequestVerifyHandlerTest {
    @Mock
    MemberRegisterRequestVerifyHandler strategy1;
    @Mock
    MemberRegisterRequestVerifyHandler strategy2;
    @Mock
    MemberRegisterRequestVerifyHandler strategy3;
//    @InjectMocks
    MemberRegisterRequestVerifyManager registerRequestVerifyManager;

    @BeforeEach
    void init() {
        this.registerRequestVerifyManager = new MemberRegisterRequestVerifyManager(List.of(strategy1, strategy2, strategy3));
    }

    @ParameterizedTest
    @MethodSource("provideRegisterRequest")
    void 등록_유형별_테스트_실행(RegisterMemberDto memberDto) {
        try {
            registerRequestVerifyManager.verify(memberDto);
        } catch (NotSupportedMemberTypeException exception){

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

        registerRequestVerifyManager.verify(memberDto);

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
                .build();
    }


    public static Stream<Arguments> provideRegisterRequest() {
        return Stream.of(
                Arguments.of(createMemberDto("Heejong", 10, Role.STUDENT, "01099182281", "000")),
                Arguments.of(createMemberDto("Hee12", 11, Role.TEACHER, "010991822281", "121")),
                Arguments.of(createMemberDto("Heejong", 10, Role.STUDENT, "02109931822813", "124")),
                Arguments.of(createMemberDto("Heejong", 10, Role.TEACHER, "120109918122281", ""))
        );
    }


}