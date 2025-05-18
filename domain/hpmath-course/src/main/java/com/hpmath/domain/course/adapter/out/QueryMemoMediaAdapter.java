package com.hpmath.domain.course.adapter.out;

import com.hpmath.client.media.MediaClient;
import com.hpmath.client.media.MediaClient.MediaInfo;
import com.hpmath.domain.course.dto.Responses.MediaViewResponse;
import com.hpmath.domain.course.exception.CourseException;
import com.hpmath.domain.course.application.port.out.QueryMemoMediaPort;
import com.hpmath.domain.course.entity.MemoMedia;
import com.hpmath.common.ErrorCode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueryMemoMediaAdapter implements QueryMemoMediaPort {
    private final MemoMediaRepository memoMediaRepository;
    private final QueryMemoMediaAttachmentAdapter queryMemoMediaAttachmentAdapter;
    private final MediaClient mediaClient;

    @Override
    public List<MediaViewResponse> queryMedias(Long memoId) {
        if (Objects.isNull(memoId)) {
            throw new CourseException("메모를 찾을 수 없음 : " + memoId, ErrorCode.MEMO_NOT_EXIST);
        }
        final List<MemoMedia> memoMedias = memoMediaRepository.findAllByMemo_Id(memoId);
        return memoMedias.stream()
                .map(memoMedia -> {
                    final MediaInfo mediaInfo = mediaClient.getFileInfo(memoMedia.getMediaSrc());
                    final MediaViewResponse view = new MediaViewResponse(memoMedia.getId(), mediaInfo.mediaName(), mediaInfo.mediaSrc(), memoMedia.getSequence(), queryMemoMediaAttachmentAdapter.query(memoMedia.getId()));
                    log.debug(view.toString());
                    return view;
                })
                .collect(Collectors.toList());
    }
}
