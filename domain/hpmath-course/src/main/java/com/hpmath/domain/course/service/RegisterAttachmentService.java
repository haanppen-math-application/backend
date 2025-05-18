package com.hpmath.domain.course.service;

import com.hpmath.common.ErrorCode;
import com.hpmath.domain.course.repository.MediaAttachmentRepository;
import com.hpmath.domain.course.repository.MemoMediaRepository;
import com.hpmath.domain.course.dto.RegisterAttachmentCommand;
import com.hpmath.domain.course.entity.MemoMedia;
import com.hpmath.domain.course.entity.MemoMediaAttachment;
import com.hpmath.domain.course.exception.MemoMediaException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class RegisterAttachmentService {
    private final MemoMediaRepository memoMediaRepository;
    private final MediaAttachmentRepository mediaAttachmentRepository;

    public void register(@Valid final RegisterAttachmentCommand command) {
        final MemoMedia targetMemoMedia = memoMediaRepository.findById(command.memoMediaId())
                .orElseThrow(() -> new MemoMediaException("해당 메모를 찾을 수 없음 : " + command.memoMediaId(), ErrorCode.MEMO_NOT_EXIST));
        final MemoMediaAttachment memoMediaAttachment = MemoMediaAttachment.of(targetMemoMedia, command.mediaSrc());
        mediaAttachmentRepository.save(memoMediaAttachment);
    }
}
