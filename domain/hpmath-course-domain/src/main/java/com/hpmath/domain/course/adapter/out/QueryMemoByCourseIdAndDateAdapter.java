package com.hpmath.domain.course.adapter.out;

import com.hpmath.domain.course.application.dto.MemoQueryByCourseIdAndDateCommand;
import com.hpmath.domain.course.application.dto.Responses.MemoViewResponse;
import com.hpmath.domain.course.application.exception.CourseException;
import com.hpmath.domain.course.application.port.out.QueryMemoByCourseIdAndDatePort;
import com.hpmath.domain.course.entity.Memo;
import com.hpmath.hpmathcore.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryMemoByCourseIdAndDateAdapter implements QueryMemoByCourseIdAndDatePort {

    private final MemoRepository memoRepository;
    private final QueryMemoMediaAdapter queryMemoMediaAdapter;

    @Override
    public MemoViewResponse query(MemoQueryByCourseIdAndDateCommand command) {
        final List<Memo> memos = memoRepository.findAllByCourseIdAndTargetDate(
                command.getCourseId(),
                command.getLocalDate()
        );
        validate(memos);
        if (memos.size() == 0) {
            return null;
        }
        return mapTomMemoView(memos.get(0));
    }

    private void validate(List<Memo> memos) {
        if (memos.size() > 1) {
            throw new CourseException("두개 이상의 메모가 발견됐습니다 : "+ memos.size(),ErrorCode.MEMO_DUPLICATED_EXCEPTION);
        }
    }

    private MemoViewResponse mapTomMemoView(final Memo memo) {
        return new MemoViewResponse(
                memo.getId(),
                memo.getTitle(),
                memo.getContent(),
                memo.getTargetDate(),
                queryMemoMediaAdapter.queryMedias(memo.getId())
        );
    }
}
