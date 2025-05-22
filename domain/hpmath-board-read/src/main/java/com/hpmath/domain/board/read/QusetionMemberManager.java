package com.hpmath.domain.board.read;

import com.hpmath.client.member.MemberClient;
import com.hpmath.domain.board.read.dto.MemberDetailResult;
import com.hpmath.domain.board.read.model.MemberQueryModel;
import com.hpmath.domain.board.read.repository.MemberQueryModelRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QusetionMemberManager {
    private final MemberQueryModelRepository memberQueryModelRepository;
    private final MemberClient memberClient;

    public MemberDetailResult get(final Long memberId) {
        return memberQueryModelRepository.get(memberId)
                .or(() -> fetch(memberId))
                .map(MemberDetailResult::from)
                .orElse(null);
    }

    private Optional<MemberQueryModel> fetch(final Long memberId) {
        final MemberQueryModel model = MemberQueryModel.of(memberClient.getMemberDetail(memberId));
        return Optional.of(cache(model));
    }

    private MemberQueryModel cache(final MemberQueryModel memberQueryModel) {
        memberQueryModelRepository.update(memberQueryModel);
        return memberQueryModel;
    }
}
