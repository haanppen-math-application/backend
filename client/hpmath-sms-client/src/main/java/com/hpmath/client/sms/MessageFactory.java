package com.hpmath.client.sms;

import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class MessageFactory {

    private final String sender;

    public MessageFactory(@Value("${spring.sms.coolsms.sender}") final String sender) {
        this.sender = sender;
    }

    SingleMessageSendingRequest getMessage(final String targetPhoneNumber, final MessageForm messageForm) {
        final Message message = new Message();
        message.setFrom(sender);
        message.setTo(targetPhoneNumber);
        message.setText(messageForm.getTotalMessage());
        return new SingleMessageSendingRequest(message);
    }
}
