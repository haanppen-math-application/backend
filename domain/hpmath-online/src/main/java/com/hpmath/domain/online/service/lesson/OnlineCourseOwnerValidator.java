package com.hpmath.domain.online.service.lesson;

import com.hpmath.common.BusinessException;
import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class OnlineCourseOwnerValidator {
    void validate(final Role requestMemberRole, final Long requestMemberId, final Long ownerId) {
        if (requestMemberId.equals(ownerId)) {
            return;
        }
        if (!requestMemberRole.equals(Role.TEACHER)) {
            return;
        }
        log.warn("theres no permissions to this user {}", ownerId);
        throw new BusinessException("온라인 수업 정보를 수정할 수 있는 권한 없음", ErrorCode.ONLINE_COURSE_EXCEPTION);
    }
}
