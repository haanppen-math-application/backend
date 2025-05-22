package com.hpmath.domain.board.read.model;

import com.hpmath.client.member.MemberClient.MemberInfo;
import com.hpmath.common.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberQueryModel {
    private Long memberId;
    private String memberName;
    private Integer memberGrade;
    private Role role;

    public static MemberQueryModel of(final MemberInfo memberInfo) {
        return new MemberQueryModel(
                memberInfo.memberId(),
                memberInfo.memberName(),
                memberInfo.memberGrade(),
                memberInfo.role()
        );
    }
}
