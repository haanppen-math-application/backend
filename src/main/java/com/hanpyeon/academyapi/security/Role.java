package com.hanpyeon.academyapi.security;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hanpyeon.academyapi.account.mapper.RoleDeserializer;

@JsonDeserialize(using = RoleDeserializer.class)
public enum Role {
    ROLE_STUDENT("student"),
    ROLE_TEACHER("teacher"),
    ROLE_MANAGER("manager");

    Role(String identifier) {
        this.identifier = identifier;
    }

    private final String identifier;

    public String getIdentifier() {
        return identifier;
    }
}
