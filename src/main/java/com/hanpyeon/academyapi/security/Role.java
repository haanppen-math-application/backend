package com.hanpyeon.academyapi.security;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = RoleDeserializer.class)
public enum Role {
    STUDENT("student", "ROLE_STUDENT"),
    TEACHER("teacher", "ROLE_TEACHER"),
    MANAGER("manager", "ROLE_MANAGER");

    Role(String identifier, String securityRole) {
        this.identifier = identifier;
        this.securityRole = securityRole;
    }

    private final String identifier;
    private final String securityRole;

    public String getIdentifier() {
        return identifier;
    }

    public String getSecurityRole() {
        return securityRole;
    }
}
