package com.hanpyeon.academyapi.account.service.sms;

import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CoolMessageSender implements MessageSender {

    private final DefaultMessageService defaultMessageService;
    private final MessageFactory messageFactory;

    @Override
    public void sendMessage(final Message message) {
        final MessageForm messageContent = new MessageForm(message.getContent());
        final SingleMessageSendingRequest request = messageFactory.getMessage(message.getTargetPhoneNumber(), messageContent);
        defaultMessageService.sendOne(request);
    }
}
