package com.hpmath.domain.course.adapter.out;

import com.hpmath.domain.course.application.exception.CourseException;
import com.hpmath.domain.course.application.port.out.LoadMemoMediaPort;
import com.hpmath.domain.course.application.port.out.LoadMemoPort;
import com.hpmath.domain.course.domain.Course;
import com.hpmath.domain.course.domain.Memo;
import com.hpmath.domain.course.domain.MemoMedia;
import com.hpmath.common.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoadMemoAdapter implements LoadMemoPort {
    private final MemoRepository memoRepository;
    private final CourseMapper courseMapper;
    private final LoadMemoMediaPort loadMemoMediaPort;

    @Override
    public Memo loadMemo(Long memoId) {
        if (memoId == null) {
            throw new CourseException("메모 아이디가 널 입니다. 다시 확인해주세요", ErrorCode.MEMO_NOT_EXIST);
        }
        return getDomainMemo(memoId);
    }
    private Memo getDomainMemo(final Long memoId) {
        final com.hpmath.domain.course.entity.Memo targetMemo = memoRepository.findById(memoId)
                .orElseThrow(() -> new CourseException("존재하지 않는 메모 : " + memoId, ErrorCode.MEMO_NOT_EXIST));
        final Course course = courseMapper.mapToCourseDomain(targetMemo.getCourse());
        return Memo.createByEntity(targetMemo.getId(), course, targetMemo.getTargetDate(), targetMemo.getTitle(), targetMemo.getContent(), loadMemoMedia(memoId));
    }

    private List<MemoMedia> loadMemoMedia(final Long memoId) {
        return loadMemoMediaPort.loadMedia(memoId);
    }

}
