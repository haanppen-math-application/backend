package com.hpmath.hpmathsmsclient;

class MessageForm {
    private final String contentPrefix = "[한편의 수학]";
    private final String content;

    public MessageForm(final String content) {
        this.content = content;
    }

    public String getTotalMessage() {
        return contentPrefix + "\n" + content;
    }

    @Override
    public String toString() {
        return contentPrefix + " " + content;
    }
}
