package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.security.Role;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RegisterServiceTest {
    @Autowired
    RegisterService registerService;


    @ParameterizedTest
    @MethodSource("provideRegisterRequest")
    void 등록_성공_테스트(RegisterMemberDto memberDto) {

        assertDoesNotThrow(() -> registerService.register(memberDto));

    }

    @ParameterizedTest
    @MethodSource("provideIllegalRegisterRequest")
    void 등록_실패_테스트(RegisterMemberDto memberDto) {

        assertThatThrownBy(() -> registerService.register(memberDto))
                .isInstanceOf(Exception.class);
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
                Arguments.of(createMemberDto("Heejo", 10, Role.STUDENT, "01099182281", "000")),
                Arguments.of(createMemberDto("Hee12", 11, Role.TEACHER, "010991822281", "121")),
                Arguments.of(createMemberDto("Heng", 10, Role.STUDENT, "01009931822813", "124")),
                Arguments.of(createMemberDto("ejong", 10, Role.TEACHER, "010109918122281", ""))
        );
    }

    public static Stream<Arguments> provideIllegalRegisterRequest() {
        return Stream.of(
                Arguments.of(createMemberDto("Heejo", 10, Role.MANAGER, "01099182281", "000")),
                Arguments.of(createMemberDto("Hee12", 11, Role.ADMIN, "010991822281", "121")),
                Arguments.of(createMemberDto("Heej", null, Role.STUDENT, "02109931822813", "124")),
                Arguments.of(createMemberDto("Heejon132", 10, Role.STUDENT, "02109931822813", "124"))
        );
    }
}