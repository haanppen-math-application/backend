package com.hpmath.domain.directory.dao;

import com.hpmath.domain.directory.exception.NoSuchMemberException;
import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.hpmathcore.ErrorCode;
import com.hpmath.hpmathcore.Role;
import java.util.Arrays;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MemberManager {

    private final MemberRepository memberRepository;

    public Member getMemberWithValidated(final Long id, final Predicate<? super Member> predicate) {
        return memberRepository.findMemberByIdAndRemovedIsFalse(id)
                .filter(predicate)
                .orElseThrow(() -> new NoSuchMemberException(predicate + " : 조건에 맞는 사용자를 찾을 수 없습니다.", ErrorCode.NO_SUCH_MEMBER));
    }
    public Member getMemberWithRoleValidated(final Long memberId, final Role... roles) {
        return getMemberWithValidated(
                memberId, member -> Arrays.stream(roles).anyMatch(role -> role.equals(member.getRole()))
        );
    }
}
