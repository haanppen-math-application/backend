package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.RegisterMemberTotalDto;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.AlreadyRegisteredException;
import com.hanpyeon.academyapi.account.mapper.RegisterMapper;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.aspect.log.WarnLoggable;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.PasswordHandler;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@AllArgsConstructor
public class RegisterServiceProvider {

    private final MemberRepository repository;
    private final RegisterMapper registerMapper;
    private final PasswordHandler passwordHandler;

    @Transactional
    public void registerMember(@Valid final RegisterMemberTotalDto memberTotalDto) {
        validateRegisterRequest(memberTotalDto);

        String encodedPassword = passwordHandler.getEncodedPassword(memberTotalDto.password());
        Member member = registerMapper.createMemberEntity(memberTotalDto, encodedPassword);

        repository.save(member);
    }

    @WarnLoggable
    private void validateRegisterRequest(final RegisterMemberTotalDto memberTotalDto) {
        if (repository.existsByPhoneNumber(memberTotalDto.phoneNumber())) {
            throw new AlreadyRegisteredException(ErrorCode.ALREADY_REGISTERED);
        }
    }
}
