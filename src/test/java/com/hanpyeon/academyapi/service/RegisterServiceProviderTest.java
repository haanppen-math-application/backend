package com.hanpyeon.academyapi.service;

import com.hanpyeon.academyapi.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.exceptions.AlreadyRegisteredException;
import com.hanpyeon.academyapi.mapper.RegisterMapper;
import com.hanpyeon.academyapi.repository.MemberRepository;
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
    @Mock
    RegisterMapper registerMapper;
    RegisterServiceProvider serviceProvider;
    @BeforeEach
    void initRegisterService(){
        this.serviceProvider = new RegisterServiceProvider(memberRepository, registerMapper, passwordHandler);
    }

    @ParameterizedTest
    @MethodSource("provideRegisterRequest")
    void 이미_존재하는_사용자_실패_테스트(RegisterMemberDto requestDto) {
        String phoneNumber = requestDto.phoneNumber();
        Mockito.when(memberRepository.existsByPhoneNumber(phoneNumber)).thenReturn(true);

        Assertions.assertThatThrownBy(() -> {
            serviceProvider.registerMember(requestDto);}
        ).isInstanceOf(AlreadyRegisteredException.class);
    }
    @ParameterizedTest
    @MethodSource("provideRegisterRequest")
    void 사용자등록_성공_테스트(RegisterMemberDto requestDto) {
        String phoneNumber = requestDto.phoneNumber();
        Mockito.when(memberRepository.existsByPhoneNumber(phoneNumber)).thenReturn(false);

        Assertions.assertThatCode(() -> {
            serviceProvider.registerMember(requestDto);
        }).doesNotThrowAnyException();
    }

    private static RegisterMemberDto createMemberDto(String name, Integer grade, String phoneNumber, String password) {
        return RegisterMemberDto.builder()
                .name(name)
                .grade(grade)
                .phoneNumber(phoneNumber)
                .password(password)
                .registerDate(LocalDateTime.now())
                .build();
    }


    public static Stream<Arguments> provideRegisterRequest() {
        return Stream.of(
                Arguments.of(createMemberDto("Heejong", 10, "01099182281", "000")),
                Arguments.of(createMemberDto("Hee12", 11, "010991822281", "121")),
                Arguments.of(createMemberDto("Heejong", 10, "02109931822813", "124")),
                Arguments.of(createMemberDto("Heejong", 10, "120109918122281", ""))
        );
    }
}
