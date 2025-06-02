package com.hpmath.domain.course.service;

import com.hpmath.client.media.MediaClient;
import com.hpmath.domain.course.dto.MemoQueryByCourseIdAndDateCommand;
import com.hpmath.domain.course.dto.MemoQueryCommand;
import com.hpmath.domain.course.dto.Responses.AttachmentViewResponse;
import com.hpmath.domain.course.dto.Responses.MediaViewResponse;
import com.hpmath.domain.course.dto.Responses.MemoAppliedDayResponse;
import com.hpmath.domain.course.dto.Responses.MemoViewResponse;
import com.hpmath.domain.course.entity.Memo;
import com.hpmath.domain.course.entity.MemoMedia;
import com.hpmath.domain.course.entity.MemoMediaAttachment;
import com.hpmath.domain.course.repository.MemoRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
@Transactional(readOnly = true)
public class CourseMemoQueryService {
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

    public List<MemoAppliedDayResponse> query(@NotNull final LocalDate registeredDate, @NotNull final Long studentId) {
        final LocalDate startDate = registeredDate.withDayOfMonth(1);
        final LocalDate endDate = registeredDate.withDayOfMonth(registeredDate.lengthOfMonth());

        return memoRepository.findAllByMonthAndStudentId(startDate, endDate, studentId).stream()
                .map(memo -> new MemoAppliedDayResponse(memo.getCourse().getId(), memo.getCourse().getCourseName(),
                        memo.getId(), memo.getTargetDate()))
                .toList();
    }

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
