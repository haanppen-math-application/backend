package com.hpmath.domain.board;

import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.board.dto.MemberDetailResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberManager {
    private final MemberClient memberClient;

    public MemberDetailResult load(final Long memberId) {
        try {
            return MemberDetailResult.from(memberClient.getMemberDetail(memberId));
        } catch (Exception e) {
            log.error("not exist member: {}", memberId, e);
            return null;
        }
    }
}
