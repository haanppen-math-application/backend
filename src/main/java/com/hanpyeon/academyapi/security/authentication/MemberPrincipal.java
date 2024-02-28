package com.hanpyeon.academyapi.security.authentication;

public record MemberPrincipal(Long memberId, String memberName) {
    @Override
    public String toString() {
        return "MemberPrincipal{" +
                "memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                '}';
    }
}
