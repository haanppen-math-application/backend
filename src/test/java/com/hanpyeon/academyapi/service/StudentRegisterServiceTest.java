package com.hanpyeon.academyapi.service;

import com.hanpyeon.academyapi.dto.RegisterRequestDto;
import com.hanpyeon.academyapi.entity.Member;
import com.hanpyeon.academyapi.exceptions.AlreadyRegisteredException;
import com.hanpyeon.academyapi.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class StudentRegisterServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordHandler passwordHandler;
    RegisterService studentRegisterService;
    @BeforeEach
    void initStudentRegisterService(){
        this.studentRegisterService = new RegisterService(userRepository, passwordHandler);
    }

    @ParameterizedTest
    @MethodSource("provideRegisterRequest")
    void 이미_존재하는_사용자_실패_테스트(RegisterRequestDto requestDto) {
        String phoneNumber = requestDto.phoneNumber();
        Member member = createMember(requestDto);

        Mockito.when(userRepository.findMemberByPhoneNumber(phoneNumber)).thenReturn(Optional.ofNullable(member));

        Assertions.assertThatThrownBy(() -> {
            studentRegisterService.registerMember(requestDto);}
        ).isInstanceOf(AlreadyRegisteredException.class);
    }
    @ParameterizedTest
    @MethodSource("provideRegisterRequest")
    void 사용자등록_성공_테스트(RegisterRequestDto requestDto) {
        String phoneNumber = requestDto.phoneNumber();
        Mockito.when(userRepository.findMemberByPhoneNumber(phoneNumber)).thenReturn(Optional.empty());

        Assertions.assertThatCode(() -> {
            studentRegisterService.registerMember(requestDto);
        }).doesNotThrowAnyException();
    }

    private Member createMember(RegisterRequestDto requestDto) {
        return Member.builder()
                .phoneNumber(requestDto.phoneNumber())
                .memberName(requestDto.name())
                .grade(requestDto.grade())
                .build();
    }


    public static Stream<Arguments> provideRegisterRequest() {
        return Stream.of(
                Arguments.of(new RegisterRequestDto("Heejong", 10, "01099182281", "000")),
                Arguments.of(new RegisterRequestDto("Hee12", 11, "010991822281", "121")),
                Arguments.of(new RegisterRequestDto("Heejong", 10, "02109931822813", "124")),
                Arguments.of(new RegisterRequestDto("Heejong", 10, "120109918122281", ""))
        );
    }
}
