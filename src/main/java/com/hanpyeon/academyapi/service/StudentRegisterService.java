package com.hanpyeon.academyapi.service;

import com.hanpyeon.academyapi.dto.StudentRegisterRequestDto;
import com.hanpyeon.academyapi.entity.Member;
import com.hanpyeon.academyapi.exceptions.AlreadyRegisteredException;
import com.hanpyeon.academyapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.function.Function;

@Service
public class StudentRegisterService {
    private final Logger logger = LoggerFactory.getLogger("STUDENT_REGISTER_SERVICE");
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public StudentRegisterService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerMember(@Validated final StudentRegisterRequestDto requestDto) {
        validateMember(requestDto);
        Member member = createMember(requestDto, getProperPasswordProcess(requestDto));
        repository.save(member);
    }
    private Function<String, String> getProperPasswordProcess(StudentRegisterRequestDto requestDto) {
        if (!requestDto.password().isBlank()) {
            return (str) -> passwordEncoder.encode(str);
        }else {
            return (str) -> str;
        }
    }
    private void validateMember(final StudentRegisterRequestDto requestDto) {
        if (repository.findMemberByPhoneNumber(requestDto.studentPhoneNumber()).isPresent()) {
            logger.debug("이미 등록된 사용자(전화번호) 입니다.");
            throw new AlreadyRegisteredException("이미 등록된 전화번호 입니다.");
        }
    }
    private Member createMember(final StudentRegisterRequestDto requestDto, Function<String, String> function) {
        return Member.builder()
                .memberName(requestDto.studentName())
                .password(function.apply(requestDto.password()))
                .grade(requestDto.studentGrade())
                .phoneNumber(requestDto.studentPhoneNumber())
                .build();
    }
}
