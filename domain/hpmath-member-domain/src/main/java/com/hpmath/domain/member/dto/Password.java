package com.hpmath.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hpmath.domain.member.dto.Password.PasswordDeserializer;
import com.hpmath.domain.member.dto.Password.PasswordSerializer;
import com.hpmath.domain.member.validation.PasswordConstraint;
import com.hpmath.hpmathwebcommon.PasswordHandler;
import java.io.IOException;

@JsonDeserialize(using = PasswordDeserializer.class)
@JsonSerialize(using = PasswordSerializer.class)
public class Password {
    @PasswordConstraint
    @JsonProperty
    private final String rawPassword;

    public Password(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    public boolean isMatch(final String encryptedPassword, final PasswordHandler passwordHandler) {
        return passwordHandler.matches(rawPassword, encryptedPassword);
    }

    public String getEncryptedPassword(final PasswordHandler passwordHandler) {
        return passwordHandler.getEncodedPassword(rawPassword);
    }

    @Override
    public String toString() {
        return "XXXX";
    }

    static class PasswordDeserializer extends JsonDeserializer<Password> {
        @Override
        public Password deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException, JacksonException {
            // JSON에서 비밀번호 값 읽기
            String rawPassword = jsonParser.getText().trim();
            // Password 객체 생성하여 반환
            return new Password(rawPassword);
        }
    }

    static class PasswordSerializer extends JsonSerializer<Password> {
        @Override
        public void serialize(Password password, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                throws IOException {
            jsonGenerator.writeString(password.rawPassword);
        }
    }
}
