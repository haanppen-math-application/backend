package com.hanpyeon.academyapi.account.service.sms;

import com.hanpyeon.academyapi.account.dto.SendPasswordCommand;

public interface MessageSender {
    void sendNewPassword(final SendPasswordCommand sendPasswordCommand);
}
