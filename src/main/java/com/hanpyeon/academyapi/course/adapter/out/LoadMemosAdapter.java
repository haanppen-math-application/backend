package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.dto.MemoQueryCommand;
import com.hanpyeon.academyapi.course.application.dto.MemoQueryRequest;
import com.hanpyeon.academyapi.course.application.dto.MemoView;
import com.hanpyeon.academyapi.course.application.exception.NoSuchCourseException;
import com.hanpyeon.academyapi.course.application.port.out.QueryMemosPort;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LoadMemosAdapter implements QueryMemosPort {
    private final MemoRepository memoRepository;
    private final CourseRepository courseRepository;

    @Override
    public Slice<MemoView> loadMemos(final MemoQueryCommand memoQueryCommand) {
        isExistCourse(memoQueryCommand.courseId());
        return memoRepository.findByCourseId(memoQueryCommand.courseId(), memoQueryCommand.pageable())
                .map(memo -> new MemoView(
                        memo.getId(),
                        memo.getProgressed(),
                        memo.getHomework(),
                        memo.getTargetDate().toLocalDate(),
                        null,
                        null)
                );
    }

    private void isExistCourse(final Long courseId) {
        if (courseRepository.findById(courseId).isEmpty()) {
            throw new NoSuchCourseException(ErrorCode.NO_SUCH_COURSE);
        }
    }
}
