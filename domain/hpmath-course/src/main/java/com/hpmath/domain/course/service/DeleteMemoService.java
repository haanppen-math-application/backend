package com.hpmath.domain.course.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import com.hpmath.domain.course.repository.MemoRepository;
import com.hpmath.domain.course.dto.DeleteMemoCommand;
import com.hpmath.domain.course.entity.Course;
import com.hpmath.domain.course.entity.Memo;
import com.hpmath.domain.course.exception.CourseException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class DeleteMemoService {
    private final MemoRepository memoRepository;

    @Transactional
    public void delete(@Valid final DeleteMemoCommand command) {
        final Memo memo = memoRepository.findWithCourseByMemoId(command.targetMemoId())
                .orElseThrow(() -> new CourseException("존재하지 않는 메모: " + command.targetMemoId(), ErrorCode.MEMO_NOT_EXIST));
        validate(command.requestMemberId(), command.role(), memo.getCourse());

        memoRepository.delete(memo);
    }

    private void validate(final Long requestMemberId, final Role requestRole, final Course course) {
        final Long courseOwnerId = course.getTeacherId();
        if (requestMemberId.equals(courseOwnerId)) {
            return;
        }
        if (requestRole.equals(Role.ADMIN) || requestRole.equals(Role.MANAGER)) {
            return;
        }
        throw new CourseException("지울 수 있는 권한 부재",ErrorCode.MEMO_CANNOT_DELETE);
    }
}
