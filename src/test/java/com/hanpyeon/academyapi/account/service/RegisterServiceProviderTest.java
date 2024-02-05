package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.account.exceptions.AlreadyRegisteredException;
import com.hanpyeon.academyapi.account.mapper.RegisterMapper;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.security.PasswordHandler;
import com.hanpyeon.academyapi.security.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceProviderTest {
    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordHandler passwordHandler;
    RegisterMapper registerMapper = new RegisterMapper();
    RegisterServiceProvider serviceProvider;

    @BeforeEach
    void initRegisterService() {
        this.serviceProvider = new RegisterServiceProvider(memberRepository, registerMapper, passwordHandler);
    }

    @ParameterizedTest
    @MethodSource("provideRegisterRequest")
    void 이미_존재하는_사용자_실패_테스트(RegisterMemberDto requestDto) {
        String phoneNumber = requestDto.phoneNumber();
        Mockito.when(memberRepository.existsByPhoneNumber(phoneNumber)).thenReturn(true);
        LocalDateTime localDateTime = LocalDateTime.now();

        Assertions.assertThatThrownBy(() -> {
                    serviceProvider.registerMember(registerMapper.createMemberTotalDto(requestDto, localDateTime));
                }
        ).isInstanceOf(AlreadyRegisteredException.class);
    }

    @ParameterizedTest
    @MethodSource("provideRegisterRequest")
    void 사용자등록_성공_테스트(RegisterMemberDto requestDto) {
        String phoneNumber = requestDto.phoneNumber();
        Mockito.when(memberRepository.existsByPhoneNumber(phoneNumber)).thenReturn(false);
        LocalDateTime localDateTime = LocalDateTime.now();

        Assertions.assertThatCode(() -> {
            serviceProvider.registerMember(registerMapper.createMemberTotalDto(requestDto, localDateTime));
        }).doesNotThrowAnyException();
    }

    private static RegisterMemberDto createMemberDto(String name, Integer grade, String phoneNumber, String password, Role role) {
        return RegisterMemberDto.builder()
                .name(name)
                .grade(grade)
                .phoneNumber(phoneNumber)
                .password(password)
                .role(role)
                .build();
    }


    public static Stream<Arguments> provideRegisterRequest() {
        return Stream.of(
                Arguments.of(createMemberDto("Heejong", 10, "01099182281", "000", Role.STUDENT)),
                Arguments.of(createMemberDto("Hee12", 11, "010991822281", "121", Role.STUDENT)),
                Arguments.of(createMemberDto("Heejong", 10, "02109931822813", "124", Role.STUDENT)),
                Arguments.of(createMemberDto("Heejong", 10, "120109918122281", "", Role.STUDENT))
        );
    }
}
