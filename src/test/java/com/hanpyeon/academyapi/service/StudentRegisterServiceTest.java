package com.hanpyeon.academyapi.service;

import com.hanpyeon.academyapi.dto.StudentRegisterRequestDto;
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
    StudentRegisterService studentRegisterService;
    @BeforeEach
    void initStudentRegisterService(){
        this.studentRegisterService = new StudentRegisterService(userRepository);
    }

    @ParameterizedTest
    @MethodSource("provideRegisterRequest")
    void 사용자등록_실패_테스트(StudentRegisterRequestDto requestDto) {
        String phoneNumber = requestDto.studentPhoneNumber();
        Member member = createMember(requestDto);

        Mockito.when(userRepository.findMemberByPhoneNumber(phoneNumber)).thenReturn(Optional.ofNullable(member));

        Assertions.assertThatThrownBy(() -> {
            studentRegisterService.registerUser(requestDto);}
        ).isInstanceOf(AlreadyRegisteredException.class);
    }

    @ParameterizedTest
    @MethodSource("provideRegisterRequest")
    void 사용자등록_성공_테스트(StudentRegisterRequestDto requestDto) {
        String phoneNumber = requestDto.studentPhoneNumber();
        Member member = createMember(requestDto);

        Mockito.when(userRepository.findMemberByPhoneNumber(phoneNumber)).thenReturn(Optional.empty());

        Assertions.assertThatCode(() -> {
            studentRegisterService.registerUser(requestDto);
        }).doesNotThrowAnyException();
    }

    private Member createMember(StudentRegisterRequestDto requestDto) {
        return Member.builder()
                .phoneNumber(requestDto.studentPhoneNumber())
                .userName(requestDto.studentName())
                .grade(requestDto.studentGrade())
                .build();
    }


    public static Stream<Arguments> provideRegisterRequest() {
        return Stream.of(
                Arguments.of(new StudentRegisterRequestDto("Heejong", 10, "01099182281")),
                Arguments.of(new StudentRegisterRequestDto("Hee12", 11, "010991822281")),
                Arguments.of(new StudentRegisterRequestDto("Heejong", 10, "02109931822813")),
                Arguments.of(new StudentRegisterRequestDto("Heejong", 10, "120109918122281"))
        );
    }
}
