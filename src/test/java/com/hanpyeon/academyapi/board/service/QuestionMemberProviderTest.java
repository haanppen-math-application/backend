package com.hanpyeon.academyapi.board.service;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.board.dto.QuestionRegisterDto;
import com.hanpyeon.academyapi.security.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuestionMemberProviderTest {

    @Mock
    MemberRepository memberRepository;
    QuestionMemberProvider questionMemberProvider;

    @BeforeEach
    void init() {
        questionMemberProvider = new QuestionMemberProvider(new MemberManager(memberRepository));
    }

    @Test
    void getQuestionRelatedMember() {
        Long ownerMemberId = 1l;
        Long targetMemberId = 2l;
        Member ownerMember = Member.builder().role(Role.STUDENT).build();
        Member targetMember = Member.builder().role(Role.TEACHER).build();

        Mockito.when(memberRepository.findById(ownerMemberId))
                        .thenReturn(Optional.of(ownerMember));
        Mockito.when(memberRepository.findById(targetMemberId))
                        .thenReturn(Optional.of(targetMember));

        assertThat(questionMemberProvider.getQuestionRelatedMember(new QuestionRegisterDto(null, null, ownerMemberId, targetMemberId, null)))
                .isEqualTo(new QuestionRelatedMember(ownerMember, targetMember));
    }
}