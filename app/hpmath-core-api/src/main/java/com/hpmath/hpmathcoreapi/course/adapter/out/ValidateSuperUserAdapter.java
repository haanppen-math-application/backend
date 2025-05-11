package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.domain.member.exceptions.NoSuchMemberException;
import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcoreapi.course.application.exception.CourseException;
import com.hpmath.hpmathcoreapi.course.application.port.out.ValidateSuperUserPort;
import com.hpmath.hpmathcore.ErrorCode;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class ValidateSuperUserAdapter implements ValidateSuperUserPort {
    private final MemberRepository memberRepository;
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Boolean isSuperUser(Long memberId) {
        if (Objects.isNull(memberId)) {
            throw new CourseException(ErrorCode.INVALID_MEMBER_TARGET);
        }
        final Member member = memberRepository.findMemberByIdAndRemovedIsFalse(memberId)
                .orElseThrow(() -> new NoSuchMemberException("삭제됐거나, 존재하지 않는 ID memberID", ErrorCode.NO_SUCH_MEMBER));
        if (member.getRole().equals(Role.ADMIN)) {
            return true;
        }
        if (member.getRole().equals(Role.MANAGER)) {
            return true;
        }
        return false;
    }
}
