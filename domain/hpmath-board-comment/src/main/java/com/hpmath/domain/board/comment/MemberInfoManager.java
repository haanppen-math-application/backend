package com.hpmath.domain.board.comment;

import com.hpmath.client.common.ClientException;
import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.board.comment.dto.MemberDetailResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberInfoManager {
    private final MemberClient memberClient;

    public MemberDetailResult get(final Long memberId) {
        try {
            return MemberDetailResult.from(memberClient.getMemberDetail(memberId));
        } catch (ClientException ex) {
            log.error(ex.getMessage(), ex);
            return MemberDetailResult.none(memberId);
        }
    }
}
