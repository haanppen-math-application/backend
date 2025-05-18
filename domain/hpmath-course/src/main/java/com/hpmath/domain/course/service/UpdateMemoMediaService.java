package com.hpmath.domain.course.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.domain.course.repository.MemoRepository;
import com.hpmath.domain.course.dto.UpdateMediaMemoCommand;
import com.hpmath.domain.course.entity.Memo;
import com.hpmath.domain.course.exception.MemoMediaException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class UpdateMemoMediaService {
    private final MemoRepository memoRepository;

    @Transactional
    public void updateMediaMemo(@Valid final UpdateMediaMemoCommand command) {
        final Memo memo = memoRepository.findWithCourseAndMediasByMemoId(command.memoId())
                .orElseThrow(() -> new MemoMediaException(ErrorCode.MEMO_NOT_EXIST));

        command.mediaSequences().forEach(sequence -> memo.changeSequence(
                sequence.memoMediaId(), sequence.sequence()));
    }
}
