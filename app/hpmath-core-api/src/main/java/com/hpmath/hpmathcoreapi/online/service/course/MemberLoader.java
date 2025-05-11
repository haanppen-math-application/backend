package com.hpmath.hpmathcoreapi.online.service.course;

import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.domain.member.exceptions.NoSuchMemberException;
import com.hpmath.hpmathcore.BusinessException;
import com.hpmath.hpmathcore.Role;
import com.hpmath.hpmathcore.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberLoader {
    private final MemberRepository memberRepository;

    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    public Member findTeacher(final Long memberId) {
        return memberRepository.findMemberByIdAndRoleAndRemovedIsFalse(memberId, Role.TEACHER)
                .orElseThrow(() -> new NoSuchMemberException(ErrorCode.NO_SUCH_MEMBER));
    }

    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    public List<Member> findStudents(final List<Long> studentIds) {
        final List<Member> students = memberRepository.findMembersByIdIsInAndRoleAndRemovedIsFalse(studentIds, Role.STUDENT);
        if (students.size() != studentIds.size()) {
            throw new BusinessException("학생을 찾지 못함. 찾은 학생 수" + students.size() + " 요청 수 : " + studentIds.size()  , ErrorCode.CANNOT_FIND_USER);
        }
        return students;
    }
}
