package com.hpmath.domain.course.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.domain.course.dto.MemoRegisterCommand;
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
public class MemoRegisterService {
    private final CourseRepository courseRepository;
    private final MemoRepository memoRepository;

    @Transactional
    public Long register(@Valid final MemoRegisterCommand command) {
        final Course course = courseRepository.findWithMemos(command.targetCourseId())
                .orElseThrow(() -> new NoSuchCourseException("반을 찾을 수 없음", ErrorCode.NO_SUCH_COURSE));

        isOwner(course.getTeacherId(), command.requestMemberId());
        isDuplicated(course, command.registerTargetDate());

        final Memo memo = new Memo(course, command.registerTargetDate(), command.title(), command.content());
        return memoRepository.save(memo).getId();
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
}
