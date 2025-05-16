package com.hpmath.domain.course.adapter.out;

import com.hpmath.client.member.MemberClient;
import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import com.hpmath.domain.course.application.exception.CourseException;
import com.hpmath.domain.course.application.port.out.ValidateSuperUserPort;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class ValidateSuperUserAdapter implements ValidateSuperUserPort {
    private final MemberClient memberClient;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Boolean isSuperUser(Long memberId) {
        if (Objects.isNull(memberId)) {
            throw new CourseException(ErrorCode.INVALID_MEMBER_TARGET);
        }
        return validateSuperMember(memberId);
    }

    private boolean validateSuperMember(final Long memberId) {
        return memberClient.isMatch(memberId, Role.ADMIN, Role.MANAGER);
    }
}
