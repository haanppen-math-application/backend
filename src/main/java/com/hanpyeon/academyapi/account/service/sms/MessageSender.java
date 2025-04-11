package com.hanpyeon.academyapi.account.service.sms;

import com.hanpyeon.academyapi.account.dto.SendValidationCodeCommand;

public interface MessageSender {
    void sendValidationCode(final SendValidationCodeCommand sendValidationCodeCommand);
}
