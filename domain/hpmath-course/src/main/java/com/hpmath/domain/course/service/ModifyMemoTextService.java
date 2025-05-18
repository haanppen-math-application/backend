package com.hpmath.domain.course.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.domain.course.dto.ModifyMemoTextCommand;
import com.hpmath.domain.course.entity.Course;
import com.hpmath.domain.course.entity.Memo;
import com.hpmath.domain.course.exception.CourseException;
import com.hpmath.domain.course.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class ModifyMemoTextService {
    private final MemoRepository memoRepository;

    @Transactional
    public void modify(@Validated final ModifyMemoTextCommand command) {
        final Memo memo = loadMemo(command.memoId());
        validateOwner(command.requestMemberId(), memo.getCourse());

        memo.setTitle(command.title());
        memo.setContent(command.content());
    }

    private Memo loadMemo(final Long memoId) {
        return memoRepository.findWithCourseByMemoId(memoId)
                .orElseThrow(() -> new CourseException("존재하지 않는 메모 : " + memoId, ErrorCode.MEMO_NOT_EXIST));
    }


    private void validateOwner(final Long requestMemberId, final Course course) {
        if (requestMemberId.equals(course.getTeacherId())) {
            return;
        }
        throw new CourseException("소유자만 수정할 수 있습니다.", ErrorCode.MEMO_CANNOT_MODIFY);
    }
}
