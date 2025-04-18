package com.hpmath.academyapi.course.adapter.out;

import com.hpmath.academyapi.course.application.dto.MemoQueryCommand;
import com.hpmath.academyapi.course.controller.Responses.MemoViewResponse;
import com.hpmath.academyapi.course.application.exception.NoSuchCourseException;
import com.hpmath.academyapi.course.application.port.out.QueryMemoMediaPort;
import com.hpmath.academyapi.course.application.port.out.QueryMemosPort;
import com.hpmath.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LoadMemosAdapter implements QueryMemosPort {
    private final MemoRepository memoRepository;
    private final CourseRepository courseRepository;
    private final QueryMemoMediaPort queryMemoMediaPort;

    @Override
    public Page<MemoViewResponse> loadMemos(final MemoQueryCommand memoQueryCommand) {
        isExistCourse(memoQueryCommand.courseId());
        return memoRepository.findByCourseId(memoQueryCommand.courseId(), memoQueryCommand.pageable())
                .map(memo -> new MemoViewResponse(
                        memo.getId(),
                        memo.getTitle(),
                        memo.getContent(),
                        memo.getTargetDate(),
                        queryMemoMediaPort.queryMedias(memo.getId()))
                );
    }

    private void isExistCourse(final Long courseId) {
        if (courseRepository.findById(courseId).isEmpty()) {
            throw new NoSuchCourseException(ErrorCode.NO_SUCH_COURSE);
        }
    }
}
