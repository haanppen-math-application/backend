package com.hpmath.app.api.member.inner;

import com.hpmath.domain.member.dto.MemberInfoResult;
import com.hpmath.domain.member.service.AccountQueryService;
import com.hpmath.common.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class MemberController {
    private final AccountQueryService accountQueryService;

    @GetMapping("/api/inner/v1/member/role")
    public ResponseEntity<MemberRoleResponse> getMemberRole(
            @RequestParam final Long memberId
    ) {
        final MemberInfoResult memberInfo = accountQueryService.getMyInfo(memberId);
        return ResponseEntity.ok(new MemberRoleResponse(memberInfo.role()));
    }

    @GetMapping("/api/inner/v1/member")
    public ResponseEntity<MemberDetailResponse> getMemberInfo(
            @RequestParam final Long memberId
    ) {
        final MemberInfoResult memberInfo = accountQueryService.getMyInfo(memberId);
        return ResponseEntity.ok(new MemberDetailResponse(
                memberInfo.id(),
                memberInfo.name(),
                memberInfo.grade(),
                memberInfo.role())
        );
    }

    record MemberDetailResponse(
            Long memberId,
            String memberName,
            Integer memberGrade,
            Role role
    ){
    }

    record MemberRoleResponse(
            Role role
    ) {
    }
}
