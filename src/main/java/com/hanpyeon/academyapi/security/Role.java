package com.hanpyeon.academyapi.security;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = RoleDeserializer.class)
// 싱글톤으로 인스턴스화 됨에 따라 static 하게 사용할 수 없는 불편함
public enum Role {
    STUDENT("student", "STUDENT"),
    TEACHER("teacher", "TEACHER"),
    MANAGER("manager", "MANAGER"),
    ADMIN("admin", "ADMIN");

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
