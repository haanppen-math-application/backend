package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.MemoQueryCommand;
import com.hanpyeon.academyapi.course.application.dto.MemoQueryRequest;
import com.hanpyeon.academyapi.course.application.dto.MemoView;
import com.hanpyeon.academyapi.course.application.port.in.LoadMemoQuery;
import com.hanpyeon.academyapi.course.application.port.out.QueryMemosPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoadMemoQueryService implements LoadMemoQuery {

    private final QueryMemosPort queryMemosPort;

    @Override
    public Slice<MemoView> loadMemos(final @Valid MemoQueryRequest memoQueryRequest) {;
        final MemoQueryCommand command = this.createCommand(memoQueryRequest);
        return queryMemosPort.loadMemos(command);
    }

    private Pageable createPageable(final Integer pageIndex) {
        return PageRequest.of(pageIndex, 5, Sort.by(Sort.Direction.ASC, "TARGET_DATE"));
    }

    private MemoQueryCommand createCommand(final MemoQueryRequest memoQueryRequest) {
        return new MemoQueryCommand(createPageable(memoQueryRequest.pageIndex()), memoQueryRequest.courseId());
    }
}
