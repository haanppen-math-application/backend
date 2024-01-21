package com.hanpyeon.academyapi.service;

import com.hanpyeon.academyapi.dto.StudentRegisterRequestDto;
import com.hanpyeon.academyapi.entity.Member;
import com.hanpyeon.academyapi.exceptions.AlreadyRegisteredException;
import com.hanpyeon.academyapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StudentRegisterService {
    private final Logger logger = LoggerFactory.getLogger("STUDENT_REGISTER_SERVICE");
    private final UserRepository repository;
    public StudentRegisterService(final UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void registerUser(final StudentRegisterRequestDto registerDto) {
        if (repository.findMemberByPhoneNumber(registerDto.studentPhoneNumber()).isPresent()) {
            logger.debug("이미 등록된 학생");
            throw new AlreadyRegisteredException("이미 등록딘 학생입니다.");
        }
        repository.save(createMemberEntity(registerDto));
        logger.info(String.valueOf(repository.findMemberByPhoneNumber(registerDto.studentPhoneNumber())));
    }
    private Member createMemberEntity(final StudentRegisterRequestDto registerDto) {
        return Member.builder()
                .userName(registerDto.studentName())
                .grade(registerDto.studentGrade())
                .phoneNumber(registerDto.studentPhoneNumber())
                .build();
    }
}
