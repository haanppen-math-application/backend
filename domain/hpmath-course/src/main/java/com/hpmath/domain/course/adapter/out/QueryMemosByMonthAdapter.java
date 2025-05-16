package com.hpmath.domain.course.adapter.out;

import com.hpmath.domain.course.application.dto.Responses.MemoAppliedDayResponse;
import com.hpmath.domain.course.application.port.out.QueryMemosByMonthPort;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryMemosByMonthAdapter implements QueryMemosByMonthPort {
    private final MemoRepository memoRepository;
    @Override
    public List<MemoAppliedDayResponse> query(final LocalDate startDate, final LocalDate endDate, final Long studentId) {
        return memoRepository.findAllByMonthAndStudentId(startDate, endDate, studentId).stream()
                .map(memo -> new MemoAppliedDayResponse(memo.getCourse().getId(), memo.getCourse().getCourseName(), memo.getId(), memo.getTargetDate()))
                .toList();
    }
}
