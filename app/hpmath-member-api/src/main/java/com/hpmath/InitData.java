package com.hpmath;

import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.domain.member.dto.Password;
import com.hpmath.domain.member.dto.RegisterMemberCommand;
import com.hpmath.domain.member.service.AccountRegisterService;
import com.hpmath.hpmathcore.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitData {
    private final AccountRegisterService accountRegisterService;
    @PostConstruct
    public void init() {
        log.info("DATABASE : init member data...");
        accountRegisterService.register(
                new RegisterMemberCommand(
                        "test",
                        null,
                        "01000000000",
                        Role.ADMIN,
                        new Password("admin")
                )
        );
        accountRegisterService.register(
                new RegisterMemberCommand(
                        "test",
                        null,
                        "01022222222",
                        Role.TEACHER,
                        new Password("0000")
                )
        );
        accountRegisterService.register(
                new RegisterMemberCommand(
                        "test",
                        null,
                        "01023232323",
                        Role.STUDENT,
                        new Password("0000")
                )
        );
    }
}
