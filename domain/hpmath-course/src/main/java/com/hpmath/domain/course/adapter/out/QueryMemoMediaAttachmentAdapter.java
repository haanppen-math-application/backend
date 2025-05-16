package com.hpmath.domain.course.adapter.out;

import com.hpmath.client.media.MediaClient;
import com.hpmath.client.media.MediaClient.MediaInfo;
import com.hpmath.domain.course.application.dto.Responses.AttachmentViewResponse;
import com.hpmath.domain.course.application.port.out.QueryMemoMediaAttachmentPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class QueryMemoMediaAttachmentAdapter implements QueryMemoMediaAttachmentPort {
    private final MediaAttachmentRepository mediaAttachmentRepository;
    private final MediaClient mediaClient;

    @Override
    public List<AttachmentViewResponse> query(Long memoMediaId) {
        return mediaAttachmentRepository.findAllByMemoMedia_Id(memoMediaId).stream()
                .map(memoMediaAttachment -> {
                    final MediaInfo mediaInfo = mediaClient.getFileInfo(memoMediaAttachment.getMediaSrc());
                    return new AttachmentViewResponse(
                            memoMediaAttachment.getAttachmentId(),
                            mediaInfo.mediaName(),
                            mediaInfo.mediaSrc());
                })
                .toList();
    }
}
