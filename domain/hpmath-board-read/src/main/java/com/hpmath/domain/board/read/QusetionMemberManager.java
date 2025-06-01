package com.hpmath.domain.board.read;

import com.hpmath.client.common.ClientException;
import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.board.read.dto.MemberDetailResult;
import com.hpmath.domain.board.read.model.MemberQueryModel;
import com.hpmath.domain.board.read.repository.MemberQueryModelRepository;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class QusetionMemberManager {
    private final MemberQueryModelRepository memberQueryModelRepository;
    private final MemberClient memberClient;

    @Async("workers")
    public CompletableFuture<MemberQueryModel> get(final Long memberId) {
        return CompletableFuture.completedFuture(memberQueryModelRepository.get(memberId)
                .orElseGet(() -> fetch(memberId)));
    }

    private MemberQueryModel fetch(final Long memberId) {
        MemberQueryModel queryModel;
        try {
            queryModel = MemberQueryModel.of(memberClient.getMemberDetail(memberId));
        } catch (ClientException e) {
            log.error("exception occurs when memberId: {}", memberId, e);
            queryModel = MemberQueryModel.none(memberId);
        }
        return cache(queryModel);
    }

    private MemberQueryModel cache(final MemberQueryModel memberQueryModel) {
        memberQueryModelRepository.update(memberQueryModel);
        return memberQueryModel;
    }
}
