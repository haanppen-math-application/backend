package com.hanpyeon.academyapi.security;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum Role {
    ROLE_STUDENT("student"),
    ROLE_TEACHER("teacher"),
    ROLE_MANAGER("manager");

    Role(String identifier) {
        this.identifier = identifier;
    }

    private final String identifier;

    @JsonCreator
    public static Role getProperRole(String identifier) {
        return Arrays.stream(Role.values())
                .filter(role -> role.identifier.equals(identifier))
                .findAny()
                .orElse(null);
    }
}
