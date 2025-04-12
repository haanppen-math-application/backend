package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.controller.Responses.AttachmentViewResponse;
import com.hanpyeon.academyapi.course.application.port.out.QueryMemoMediaAttachmentPort;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class QueryMemoMediaAttachmentAdapter implements QueryMemoMediaAttachmentPort {
    private final MediaAttachmentRepository mediaAttachmentRepository;

    @Override
    public List<AttachmentViewResponse> query(Long memoMediaId) {
        return mediaAttachmentRepository.findAllByMemoMedia_Id(memoMediaId).stream()
                .map(memoMediaAttachment -> new AttachmentViewResponse(memoMediaAttachment.getAttachmentId(), memoMediaAttachment.getMedia().getMediaName(), memoMediaAttachment.getMedia().getSrc()))
                .collect(Collectors.toList());
    }
}
