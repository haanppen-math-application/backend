package com.hanpyeon.academyapi.board.service;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.board.exception.NoSuchMemberException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
@AllArgsConstructor
public class MemberManager {
    private final MemberRepository memberRepository;
    public Member getMemberWithValidated(final Long id, final Predicate<? super Member> predicate){
        return memberRepository.findById(id)
                .filter(predicate)
                .orElseThrow(NoSuchMemberException::new);
    }
}
