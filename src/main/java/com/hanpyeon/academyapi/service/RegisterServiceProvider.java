package com.hanpyeon.academyapi.service;

import com.hanpyeon.academyapi.dto.RegisterMemberDto;
import com.hanpyeon.academyapi.entity.Member;
import com.hanpyeon.academyapi.exceptions.AlreadyRegisteredException;
import com.hanpyeon.academyapi.mapper.RegisterMapper;
import com.hanpyeon.academyapi.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@AllArgsConstructor
public class RegisterServiceProvider {

    private final static Logger LOGGER = LoggerFactory.getLogger("RegisterCategoryChecker");
    private final MemberRepository repository;
    private final RegisterMapper registerMapper;
    private final PasswordHandler passwordHandler;

    @Transactional
    public void registerMember(@Validated final RegisterMemberDto registerMemberDto) {
        validateRegisterRequest(registerMemberDto);

        String encodedPassword = passwordHandler.getEncodedPassword(registerMemberDto.password());
        Member member = registerMapper.createMemberEntity(registerMemberDto, encodedPassword);

        repository.save(member);
//        LOGGER.info(member.toString());
    }

    private void validateRegisterRequest(final RegisterMemberDto registerMemberDto) {
        if (repository.existsByPhoneNumber(registerMemberDto.phoneNumber())) {
            LOGGER.debug("이미 등록된 사용자(전화번호) 입니다.");
            throw new AlreadyRegisteredException("이미 등록된 전화번호 입니다.");
        }
    }
}
