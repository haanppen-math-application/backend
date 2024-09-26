package com.hanpyeon.academyapi.account.service.password;

import com.hanpyeon.academyapi.security.PasswordHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountPasswordFactory {
    private final PasswordHandler passwordHandler;

    public AccountPassword createWithEntity(final String encryptedPassword) {
        return AccountPassword.load(encryptedPassword, passwordHandler);
    }

    public AccountPassword createNew(final String rawPassword) {
        return AccountPassword.createNew(rawPassword, passwordHandler);
    }
}
