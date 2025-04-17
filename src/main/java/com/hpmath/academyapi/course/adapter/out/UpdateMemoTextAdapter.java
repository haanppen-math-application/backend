package com.hpmath.academyapi.course.adapter.out;

import com.hpmath.academyapi.course.application.exception.CourseException;
import com.hpmath.academyapi.course.application.port.out.UpdateMemoTextPort;
import com.hpmath.academyapi.course.domain.Memo;
import com.hpmath.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateMemoTextAdapter implements UpdateMemoTextPort {
    private final MemoRepository memoRepository;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void update(Memo memo) {
        final com.hpmath.academyapi.course.entity.Memo memoEntity = getMemo(memo.getMemoId());
        memoEntity.setContent(memo.getContent());
        memoEntity.setTitle(memo.getTitle());
    }

    private com.hpmath.academyapi.course.entity.Memo getMemo(final Long memoId) {
        return memoRepository.findById(memoId)
                .orElseThrow(() -> new CourseException(ErrorCode.MEMO_NOT_EXIST));
    }
}
