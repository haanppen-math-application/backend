package com.hpmath.domain.course.service;

import com.hpmath.client.media.MediaClient;
import com.hpmath.domain.course.repository.MemoRepository;
import com.hpmath.domain.course.dto.MemoQueryCommand;
import com.hpmath.domain.course.dto.Responses.AttachmentViewResponse;
import com.hpmath.domain.course.dto.Responses.MediaViewResponse;
import com.hpmath.domain.course.dto.Responses.MemoViewResponse;
import com.hpmath.domain.course.entity.Memo;
import com.hpmath.domain.course.entity.MemoMedia;
import com.hpmath.domain.course.entity.MemoMediaAttachment;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
@Validated
public class LoadMemoQueryService {
    private final MemoRepository memoRepository;
    private final MediaClient mediaClient;

    @Transactional(readOnly = true)
    public Page<MemoViewResponse> loadMemos(@Valid final MemoQueryCommand command) {
        final List<Memo> memos = memoRepository.queryMemosByCourseId(command.courseId(), command.pageable());
        return new PageImpl<>(memos.stream().map(this::of).toList(), command.pageable(), memos.size());
    }

    private MemoViewResponse of(final Memo memo) {
        return new MemoViewResponse(
                memo.getId(),
                memo.getTitle(),
                memo.getContent(),
                memo.getTargetDate(),
                memo.getMemoMedias().stream()
                        .map(this::of)
                        .toList());
    }

    private MediaViewResponse of(final MemoMedia memoMedia) {
        return new MediaViewResponse(
                memoMedia.getId(),
                mediaClient.getFileInfo(memoMedia.getMediaSrc()).mediaName(),
                memoMedia.getMediaSrc(),
                memoMedia.getSequence(),
                memoMedia.getMemoMediaAttachments().stream()
                        .map(this::of)
                        .toList());
    }

    private AttachmentViewResponse of(final MemoMediaAttachment attachment) {
        return new AttachmentViewResponse(
                attachment.getAttachmentId(),
                mediaClient.getFileInfo(attachment.getMediaSrc()).mediaName(),
                attachment.getMediaSrc());
    }
}


