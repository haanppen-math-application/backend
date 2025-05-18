package com.hpmath.domain.course.service;

import com.hpmath.domain.course.repository.MemoRepository;
import com.hpmath.domain.course.dto.Responses.MemoAppliedDayResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QueryCourseByMonthService {
    private final MemoRepository memoRepository;

    public List<MemoAppliedDayResponse> query(final LocalDate registeredDate, final Long studentId) {
        final LocalDate startDate = registeredDate.withDayOfMonth(1);
        final LocalDate endDate = registeredDate.withDayOfMonth(registeredDate.lengthOfMonth());

        return memoRepository.findAllByMonthAndStudentId(startDate, endDate, studentId).stream()
                .map(memo -> new MemoAppliedDayResponse(memo.getCourse().getId(), memo.getCourse().getCourseName(),
                        memo.getId(), memo.getTargetDate()))
                .toList();
    }
}
