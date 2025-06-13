package com.hpmath.domain.course.service;

import com.hpmath.client.member.MemberClient;
import com.hpmath.client.member.MemberClient.MemberInfo;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseMemberManager {
    private final MemberClient memberClient;

    public Optional<MemberInfo> getMemberDetail(final Long memberId) {
        try {
            return Optional.of(memberClient.getMemberDetail(memberId));
        } catch (Exception e) {
            log.error("Error getting member info: {}", memberId, e);
            return Optional.empty();
        }
    }
}
