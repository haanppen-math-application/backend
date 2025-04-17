package com.hpmath.hpmathcoreapi.course.application;

import com.hpmath.hpmathcoreapi.course.application.dto.DeleteMemoCommand;
import com.hpmath.hpmathcoreapi.course.application.exception.CourseException;
import com.hpmath.hpmathcoreapi.course.application.port.in.DeleteMemoUseCase;
import com.hpmath.hpmathcoreapi.course.application.port.out.DeleteMemoMediaPort;
import com.hpmath.hpmathcoreapi.course.application.port.out.DeleteMemoPort;
import com.hpmath.hpmathcoreapi.course.application.port.out.LoadMemoPort;
import com.hpmath.hpmathcoreapi.course.application.port.out.ValidateSuperUserPort;
import com.hpmath.hpmathcoreapi.course.domain.Course;
import com.hpmath.hpmathcoreapi.course.domain.Memo;
import com.hpmath.hpmathcoreapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteMemoService implements DeleteMemoUseCase {

    private final LoadMemoPort loadMemoPort;
    private final ValidateSuperUserPort validateSuperUserPort;
    private final DeleteMemoMediaPort deleteMemoMediaPort;
    private final DeleteMemoPort deleteMemoPort;

    @Override
    @Transactional
    public void delete(DeleteMemoCommand deleteMemoCommand) {
        final Memo memo = loadMemoPort.loadMemo(deleteMemoCommand.targetMemoId());
        final Course course = memo.getCourse();

        isCourseOwner(deleteMemoCommand.requestMemberId(), course);
        delete(memo.getMemoId());
    }

    private void delete(final Long memoId) {
        deleteMemoMediaPort.deleteRelatedMedias(memoId);
        deleteMemoPort.deleteMemo(memoId);
    }

    private void isCourseOwner(final Long requestMemberId, final Course course) {
        final Long courseOwnerId = course.getTeacher().id();
        if (requestMemberId.equals(courseOwnerId)) {
            return;
        }
        if (validateSuperUserPort.isSuperUser(requestMemberId)) {
            return;
        }
        throw new CourseException("지울 수 있는 권한 부재",ErrorCode.MEMO_CANNOT_DELETE);
    }
}
