package com.hanpyeon.academyapi.account.mapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.hanpyeon.academyapi.security.Role;

import java.io.IOException;
import java.util.Arrays;

public class RoleDeserializer extends JsonDeserializer<Role> {
    @Override
    public Role deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
        String identifier = parser.getValueAsString();
        return Arrays.stream(Role.values())
                .filter(role -> role.getIdentifier().equals(identifier))
                .findAny()
                .orElse(null);
    }
}
