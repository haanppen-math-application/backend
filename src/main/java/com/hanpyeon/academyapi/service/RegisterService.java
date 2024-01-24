package com.hanpyeon.academyapi.service;

import com.hanpyeon.academyapi.dto.RegisterRequestDto;
import com.hanpyeon.academyapi.entity.Member;
import com.hanpyeon.academyapi.exceptions.AlreadyRegisteredException;
import com.hanpyeon.academyapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
public class RegisterService {
    private final Logger logger = LoggerFactory.getLogger("STUDENT_REGISTER_SERVICE");
    private final UserRepository repository;
    private final PasswordHandler passwordHandler;

    public RegisterService(UserRepository repository, PasswordHandler passwordHandler) {
        this.repository = repository;
        this.passwordHandler = passwordHandler;
    }

    @Transactional
    public void registerMember(@Validated final RegisterRequestDto requestDto) {
        validateRegisterRequest(requestDto);

        String encodedPassword = passwordHandler.getEncodedPassword(requestDto.password());
        Member member = createMember(requestDto, encodedPassword);

        repository.save(member);
        logger.info(member.toString());
    }

    private void validateRegisterRequest(final RegisterRequestDto requestDto) {
        if (repository.findMemberByPhoneNumber(requestDto.phoneNumber()).isPresent()) {
            logger.debug("이미 등록된 사용자(전화번호) 입니다.");
            throw new AlreadyRegisteredException("이미 등록된 전화번호 입니다.");
        }
    }

    private Member createMember(final RegisterRequestDto requestDto, String encodedPassword) {
        return Member.builder()
                .memberName(requestDto.name())
                .password(encodedPassword)
                .grade(requestDto.grade())
                .phoneNumber(requestDto.phoneNumber())
                .build();
    }
}
