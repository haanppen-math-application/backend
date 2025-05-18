package com.hpmath.domain.course.service;

import com.hpmath.client.media.MediaClient;
import com.hpmath.domain.course.repository.MemoRepository;
import com.hpmath.domain.course.dto.MemoQueryByCourseIdAndDateCommand;
import com.hpmath.domain.course.dto.Responses.AttachmentViewResponse;
import com.hpmath.domain.course.dto.Responses.MediaViewResponse;
import com.hpmath.domain.course.dto.Responses.MemoViewResponse;
import com.hpmath.domain.course.entity.MemoMedia;
import com.hpmath.domain.course.entity.MemoMediaAttachment;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class QueryMemoByCourseIdAndDateService {
    private final MemoRepository memoRepository;
    private final MediaClient mediaClient;

    public MemoViewResponse loadSingleMemo(@Valid final MemoQueryByCourseIdAndDateCommand command) {
        return memoRepository.findAllByCourseIdAndTargetDate(command.getCourseId(), command.getLocalDate())
                .map(memo -> new MemoViewResponse(
                        memo.getId(),
                        memo.getTitle(),
                        memo.getContent(),
                        memo.getTargetDate(),
                        memo.getMemoMedias().stream()
                                .map(this::mapTo)
                                .toList()
                        )).orElse(null);
    }

    private MediaViewResponse mapTo(final MemoMedia memoMedia) {
        return new MediaViewResponse(
                memoMedia.getId(),
                mediaClient.getFileInfo(memoMedia.getMediaSrc()).mediaName(),
                memoMedia.getMediaSrc(),
                memoMedia.getSequence(),
                memoMedia.getMemoMediaAttachments().stream()
                        .map(this::mapTo)
                        .toList());
    }

    private AttachmentViewResponse mapTo(final MemoMediaAttachment attachment) {
        return new AttachmentViewResponse(
                attachment.getAttachmentId(),
                mediaClient.getFileInfo(attachment.getMediaSrc()).mediaName(),
                attachment.getMediaSrc()
        );
    }
}
