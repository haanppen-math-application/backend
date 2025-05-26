package com.hpmath.domain.course.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.common.Role;
import com.hpmath.domain.course.dto.DeleteMemoCommand;
import com.hpmath.domain.course.dto.MemoRegisterCommand;
import com.hpmath.domain.course.dto.ModifyMemoTextCommand;
import com.hpmath.domain.course.entity.Course;
import com.hpmath.domain.course.entity.Memo;
import com.hpmath.domain.course.exception.CourseException;
import com.hpmath.domain.course.exception.InvalidCourseAccessException;
import com.hpmath.domain.course.exception.NoSuchCourseException;
import com.hpmath.domain.course.repository.CourseRepository;
import com.hpmath.domain.course.repository.MemoRepository;
import jakarta.validation.Valid;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class CourseMemoService {
    private final MemoRepository memoRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public void delete(@Valid final DeleteMemoCommand command) {
        final Memo memo = memoRepository.findWithCourseByMemoId(command.targetMemoId())
                .orElseThrow(() -> new CourseException("존재하지 않는 메모: " + command.targetMemoId(), ErrorCode.MEMO_NOT_EXIST));
        validate(command.requestMemberId(), command.role(), memo.getCourse());

        memoRepository.delete(memo);
    }

    @Transactional
    public Long register(@Valid final MemoRegisterCommand command) {
        final Course course = courseRepository.findWithMemos(command.targetCourseId())
                .orElseThrow(() -> new NoSuchCourseException("반을 찾을 수 없음", ErrorCode.NO_SUCH_COURSE));

        isOwner(course.getTeacherId(), command.requestMemberId());
        isDuplicated(course, command.registerTargetDate());

        final Memo memo = Memo.of(course, command.registerTargetDate(), command.title(), command.content());
        return memoRepository.save(memo).getId();
    }

    @Transactional
    public void modify(@Valid final ModifyMemoTextCommand command) {
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

    private void isDuplicated(final Course course, final LocalDate targetDate) {
        if (course.getMemos().stream()
                .noneMatch(memo -> memo.getTargetDate().equals(targetDate))) {
            return;
        }
        throw new CourseException("같은 날짜에 두개이상의 메모 등록 불가",ErrorCode.MEMO_DUPLICATED_EXCEPTION);
    }

    private void isOwner(final Long courseTeacherId, final Long requestMemberId) {
        if (!courseTeacherId.equals(requestMemberId)) {
            throw new InvalidCourseAccessException("반 담당 선생님이 아닙니다", ErrorCode.INVALID_COURSE_ACCESS);
        }
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
