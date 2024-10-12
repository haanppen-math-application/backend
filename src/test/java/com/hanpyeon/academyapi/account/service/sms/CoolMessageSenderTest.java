package com.hanpyeon.academyapi.account.service.sms;

import com.hanpyeon.academyapi.account.dto.SendPasswordCommand;
import com.hanpyeon.academyapi.account.service.AccountAbstractFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoolMessageSenderTest {

    @Autowired
    CoolMessageSender coolMessageSender;
    @Autowired
    AccountAbstractFactory accountAbstractFactory;

    // onlyForLocal
    void messageTest() {
        coolMessageSender.sendNewPassword(new SendPasswordCommand(accountAbstractFactory.getPhoneNumber("01034330652"), "1234"));
    }
}