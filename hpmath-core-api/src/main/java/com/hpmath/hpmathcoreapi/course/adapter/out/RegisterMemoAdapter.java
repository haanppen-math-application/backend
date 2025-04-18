package com.hpmath.hpmathcoreapi.course.adapter.out;

import com.hpmath.hpmathcoreapi.course.application.exception.NoSuchCourseException;
import com.hpmath.hpmathcoreapi.course.application.port.out.RegisterMemoPort;
import com.hpmath.hpmathcoreapi.course.domain.Memo;
import com.hpmath.hpmathcoreapi.course.entity.Course;
import com.hpmath.hpmathcore.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
class RegisterMemoAdapter implements RegisterMemoPort {
    private final CourseRepository courseRepository;
    private final MemoRepository memoRepository;

    @Override
    public Long register(final Memo memo, final Long courseId) {
        final Course course = loadCourse(courseId);
        final com.hpmath.hpmathcoreapi.course.entity.Memo memoEntity = this.mapToMemoEntity(memo, course);
        return memoRepository.save(memoEntity).getId();
    }

    private Course loadCourse(final Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new NoSuchCourseException("적절한 반을 찾을 수 없습니다", ErrorCode.NO_SUCH_COURSE));
    }

    private com.hpmath.hpmathcoreapi.course.entity.Memo mapToMemoEntity(final Memo memo, final Course course) {
        return new com.hpmath.hpmathcoreapi.course.entity.Memo(
                course,
                memo.getTargetDate(),
                memo.getTitle(),
                memo.getContent()
        );
    }
}
