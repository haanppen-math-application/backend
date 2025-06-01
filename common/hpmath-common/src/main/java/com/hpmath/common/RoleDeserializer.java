package com.hpmath.common;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.Arrays;

public class RoleDeserializer extends JsonDeserializer<Role> {
    @Override
    public Role deserialize(final JsonParser parser, final DeserializationContext context) throws IOException, JacksonException {
        String identifier = parser.getValueAsString();
        if (identifier == null) {
            return null;
        }
        final String lowerIdentifier = identifier.toLowerCase();
        return Arrays.stream(Role.values())
                .filter(role -> role.getIdentifier().equals(lowerIdentifier))
                .findAny()
                .orElse(null);
    }
}
